package com.meslmawy.ibtkarchallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.meslmawy.ibtkarchallenge.MainCoroutinesRule
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.data.api.ChallengeApiService
import com.meslmawy.ibtkarchallenge.domain.dto.AllPeopleResponse
import com.meslmawy.ibtkarchallenge.domain.repository.ChallengeRepositery
import com.meslmawy.ibtkarchallenge.getOrAwaitValue
import com.meslmawy.ibtkarchallenge.presentation.main.HomeViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeViewModelTest{

    private lateinit var viewModel: HomeViewModel
    private lateinit var mainRepository: ChallengeRepositery
    private val challengeService: ChallengeApiService = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        mainRepository = ChallengeRepositery(challengeService)
        viewModel = HomeViewModel(mainRepository)
    }

    @Test
    fun refreshPopularPeopleListLiveData() = runBlocking {
        // adding All Popular People as a response
        viewModel.getPopularPeople()
        val value = viewModel.peopleLiveData.getOrAwaitValue()
        assertThat(value,(not(nullValue())))
    }


}

