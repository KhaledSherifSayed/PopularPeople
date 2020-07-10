package com.meslmawy.ibtkarchallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.meslmawy.ibtkarchallenge.MainCoroutinesRule
import com.meslmawy.ibtkarchallenge.data.api.ChallengeApiService
import com.meslmawy.ibtkarchallenge.domain.repository.ChallengeRepositery
import com.meslmawy.ibtkarchallenge.getOrAwaitValue
import com.meslmawy.ibtkarchallenge.presentation.details.DetailsViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var mainRepository: ChallengeRepositery
    private val challengeService: ChallengeApiService = mock()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()


    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        mainRepository = ChallengeRepositery(challengeService)
        viewModel = DetailsViewModel(mainRepository)
    }

    @Test
    fun refreshPersonInfoandImagesLiveData() {
        viewModel.getPersonInfo(4494)
        viewModel.getActorImages(4494)
        val info = viewModel.personInfo.getOrAwaitValue()
        val images = viewModel.personImages.getOrAwaitValue()
        assertThat(info,(CoreMatchers.not(CoreMatchers.nullValue())))
        assertThat(images,(CoreMatchers.not(CoreMatchers.nullValue())))
    }
}