/*
 *
 * Copyright (c) 2020 Khaled  Sherif
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 */

package com.meslmawy.ibtkarchallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.meslmawy.ibtkarchallenge.MainCoroutinesRule
import com.meslmawy.ibtkarchallenge.data.api.ChallengeApiService
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

