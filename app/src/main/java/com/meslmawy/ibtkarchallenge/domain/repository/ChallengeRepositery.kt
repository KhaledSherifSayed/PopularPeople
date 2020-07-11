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

package com.meslmawy.ibtkarchallenge.domain.repository

import NetworkBoundRepository
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.data.api.ChallengeApiService
import com.meslmawy.ibtkarchallenge.domain.dto.ActorDetails
import com.meslmawy.ibtkarchallenge.domain.dto.AllImagesResponse
import com.meslmawy.ibtkarchallenge.domain.dto.AllPeopleResponse
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

    fun getPersonInfo(id : Int): Flow<State<ActorDetails>> {
        return object : NetworkBoundRepository<ActorDetails>() {
            override suspend fun fetchFromRemote(): Response<ActorDetails> = apiService.getPersonInfo(id)
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getPersonImages(id : Int): Flow<State<AllImagesResponse>> {
        return object : NetworkBoundRepository<AllImagesResponse>() {
            override suspend fun fetchFromRemote(): Response<AllImagesResponse> = apiService.getPersonImages(id)
        }.asFlow().flowOn(Dispatchers.IO)
    }

}