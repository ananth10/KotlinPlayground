package com.ananth.kotlinplayground.coroutines.coroutine.usecase3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ananth.kotlinplayground.coroutines.coroutine.UiState3
import com.ananth.kotlinplayground.coroutines.coroutine.mockVersionFeaturesAndroid10
import com.ananth.kotlinplayground.coroutines.coroutine.mockVersionFeaturesOreo
import com.ananth.kotlinplayground.coroutines.coroutine.mockVersionFeaturesPie
import com.ananth.kotlinplayground.utils.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class PerformNetworkRequestConcurrentlyViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    private val receivedUiState = mutableListOf<UiState3>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `performNetworkRequestSequentially should load data sequentially`() = mainCoroutineScopeRule.runTest{
        //Arrange
        val responseDelay = 1000L
        val fakeSuccessApi = FakeSuccessApi(responseDelay)
        val viewModel = PerformNetworkRequestConcurrentlyViewModel(fakeSuccessApi)
        observeViewModel(viewModel)
        //Act
        viewModel.performNetworkRequestSequentially()
        val forwardedTime = advanceUntilIdle()
        //Assert
        Assert.assertEquals(
            listOf(
                UiState3.Loading,
                UiState3.Success(
                    listOf(
                        mockVersionFeaturesOreo, mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ), receivedUiState
        )
//        Assert.assertEquals(3000,forwardedTime)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `performNetworkRequestConcurrently should load data concurrently`() = mainCoroutineScopeRule.runBlockingTest{
        //Arrange
        val responseDelay = 1000L
        val fakeSuccessApi = FakeSuccessApi(responseDelay)
        val viewModel = PerformNetworkRequestConcurrentlyViewModel(fakeSuccessApi)
        observeViewModel(viewModel)
        //Act
        viewModel.performNetworkRequestConcurrently()
        val forwardedTime = advanceUntilIdle()
        //Assert
        Assert.assertEquals(
            listOf(
                UiState3.Loading,
                UiState3.Success(
                    listOf(
                        mockVersionFeaturesOreo, mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ), receivedUiState
        )
//        Assert.assertEquals(1000,forwardedTime)
    }

    private fun observeViewModel(viewModel: PerformNetworkRequestConcurrentlyViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiState.add(uiState)
            }
        }
    }
}