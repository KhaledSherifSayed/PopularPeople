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

package com.meslmawy.ibtkarchallenge.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.domain.dto.AllPeopleResponse
import com.meslmawy.ibtkarchallenge.domain.repository.ChallengeRepositery
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeViewModel(val repository: ChallengeRepositery)  : ViewModel() {

    private val _allPeopleLiveData = MutableLiveData<State<AllPeopleResponse>>()

    val peopleLiveData: LiveData<State<AllPeopleResponse>>
        get() = _allPeopleLiveData

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getPopularPeople(){
        coroutineScope.launch {
            repository.getAllPopulars().collect {
                _allPeopleLiveData.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}