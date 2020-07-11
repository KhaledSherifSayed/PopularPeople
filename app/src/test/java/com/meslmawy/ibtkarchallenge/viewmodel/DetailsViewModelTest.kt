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