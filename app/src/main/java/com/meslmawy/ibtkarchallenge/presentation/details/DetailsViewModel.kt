package com.meslmawy.ibtkarchallenge.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.domain.dto.ActorDetails
import com.meslmawy.ibtkarchallenge.domain.dto.AllImagesResponse
import com.meslmawy.ibtkarchallenge.domain.repository.ChallengeRepositery
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DetailsViewModel (val repository: ChallengeRepositery)  : ViewModel() {

    private val _personInfo = MutableLiveData<State<ActorDetails>>()
    val personInfo : LiveData<State<ActorDetails>>
        get() = _personInfo

    private val _personImages= MutableLiveData<State<AllImagesResponse>>()
    val personImages : LiveData<State<AllImagesResponse>>
        get() = _personImages

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getPersonInfo(id : Int){
        coroutineScope.launch {
            repository.getPersonInfo(id).collect {
                _personInfo.value = it
            }
        }
    }

    fun getActorImages(id : Int){
        coroutineScope.launch {
            repository.getPersonImages(id).collect {
                _personImages.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}