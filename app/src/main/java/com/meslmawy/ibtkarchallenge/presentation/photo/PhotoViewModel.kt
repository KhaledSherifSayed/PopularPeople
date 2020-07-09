package com.meslmawy.ibtkarchallenge.presentation.photo

import androidx.lifecycle.ViewModel
import com.meslmawy.ibtkarchallenge.domain.repository.ChallengeRepositery
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PhotoViewModel(val repository: ChallengeRepositery)  : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}