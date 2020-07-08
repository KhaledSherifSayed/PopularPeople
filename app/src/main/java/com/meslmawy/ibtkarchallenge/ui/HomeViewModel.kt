package com.meslmawy.ibtkarchallenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.dto.AllPeopleResponse
import com.meslmawy.ibtkarchallenge.repository.ChallengeRepositery
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