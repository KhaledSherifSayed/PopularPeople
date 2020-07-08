package com.meslmawy.ibtkarchallenge.presentation.details

import androidx.lifecycle.ViewModel
import com.meslmawy.ibtkarchallenge.domain.repository.ChallengeRepositery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DetailsViewModel (val repository: ChallengeRepositery)  : ViewModel() {

}