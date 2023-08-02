package com.ananth.kotlinplayground.coroutines.coroutine

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


internal class PerformSingleNetworkRequestTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @get:Rule //this rule define executor instead of android main looper and this also configures live data to execute task synchronously instead of asynchronously because if we try to  test any async functionality then assert will execute before async function happens, so that is why we are using InstantTaskExecutorRule to execute async to sync
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return success when network request is successful`() {
        //Arrange
        val testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)
        val fakeApi = FakeSuccessApi()
        val viewModel = PerformSingleNetworkRequest(fakeApi)

        observeViewModel(viewModel)

        //Act
        viewModel.performSingleNetworkRequest()

        //Assert
        Assert.assertEquals(
            listOf(UiState.Loading, UiState.Success(mockAndroidVersions)),
            receivedUiStates
        )

        //cleanup
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `should return error when network request fails`() {
        //Arrange
        Dispatchers.setMain(TestCoroutineDispatcher())
        val fakeApi = FakeErrorApi()
        val viewModel = PerformSingleNetworkRequest(fakeApi)

        observeViewModel(viewModel)
        //Act
        viewModel.performSingleNetworkRequest()

        //Assert
        Assert.assertEquals(
            listOf(UiState.Loading, UiState.Error("Network request failed")), receivedUiStates
        )
    }

    private fun observeViewModel(viewModel: PerformSingleNetworkRequest) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }

}