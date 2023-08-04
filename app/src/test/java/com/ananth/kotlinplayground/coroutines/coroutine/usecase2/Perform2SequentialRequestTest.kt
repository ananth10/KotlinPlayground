package com.ananth.kotlinplayground.coroutines.coroutine.usecase2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ananth.kotlinplayground.coroutines.coroutine.UiState
import com.ananth.kotlinplayground.coroutines.coroutine.UiState2
import com.ananth.kotlinplayground.coroutines.coroutine.mockVersionFeaturesAndroid10
import com.ananth.kotlinplayground.utils.MainCoroutineScopeRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


internal class Perform2SequentialRequestTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val receivedUiStates = mutableListOf<UiState2>()

    @Test
    fun `should return success when both network request are successful`() {
        //Arrange
         val fakeSuccessApi = FakeSuccessApi()
         val viewModel = Perform2SequentialRequest(fakeSuccessApi)
         observeViewModel(viewModel)
        //Act
         viewModel.perform2SequentialRequest()
        //Assert
        Assert.assertEquals(
            listOf(UiState2.Loading, UiState2.Success(mockVersionFeaturesAndroid10)), receivedUiStates
        )
    }

    @Test
    fun `should return error when first network request fails`() {
        //Arrange
         val fakeErrorApi = FakeErrorApi()
         val viewModel = Perform2SequentialRequest(fakeErrorApi)
          observeViewModel(viewModel)
        //Act
         viewModel.perform2SequentialRequest()
        //Assert
         Assert.assertEquals(listOf(UiState2.Loading,UiState2.Error("Network request failed!")),receivedUiStates)
    }

    @Test
    fun `should return error when second network request fails`() {
        //Arrange
         val fakeFeatureErrorApi = FakeFeatureErrorApi()
         val viewModel = Perform2SequentialRequest(fakeFeatureErrorApi)
         observeViewModel(viewModel)
        //Act
         viewModel.perform2SequentialRequest()
        //Assert
        Assert.assertEquals(listOf(UiState2.Loading,UiState2.Error("Network request failed!")),receivedUiStates)
    }

    private fun observeViewModel(viewModel: Perform2SequentialRequest) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}