package com.meslmawy.ibtkarchallenge.repository

import NetworkBoundRepository
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.api.ChallengeApiService
import com.meslmawy.ibtkarchallenge.dto.AllPeopleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@ExperimentalCoroutinesApi
class ChallengeRepositery(private val apiService: ChallengeApiService) {

    fun getAllPopulars(): Flow<State<AllPeopleResponse>> {
        return object : NetworkBoundRepository<AllPeopleResponse>() {
            override suspend fun fetchFromRemote(): Response<AllPeopleResponse> = apiService.getAllPeople()
        }.asFlow().flowOn(Dispatchers.IO)
    }

}