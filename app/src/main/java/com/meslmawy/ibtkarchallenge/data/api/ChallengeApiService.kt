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

package com.meslmawy.ibtkarchallenge.data.api

import com.meslmawy.ibtkarchallenge.BuildConfig
import com.meslmawy.ibtkarchallenge.domain.dto.ActorDetails
import com.meslmawy.ibtkarchallenge.domain.dto.AllImagesResponse
import com.meslmawy.ibtkarchallenge.domain.dto.AllPeopleResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ChallengeApiService {


    @GET("popular?api_key=" + BuildConfig.API_KEY)
    suspend fun getAllPeople(): Response<AllPeopleResponse>

    @GET("{person_id}?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    suspend fun getPersonInfo(@Path(value = "person_id") personId : Int): Response<ActorDetails>

    @GET("{person_id}/images?api_key=" + BuildConfig.API_KEY +"&language=en-US")
    suspend fun getPersonImages(@Path(value = "person_id") personId : Int): Response<AllImagesResponse>

    @GET("popular?api_key=" + BuildConfig.API_KEY)
    fun getAllPeopleTest(): Call<AllPeopleResponse>

    @GET("{person_id}?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    fun getPersonInfoTest(@Path(value = "person_id") personId : Int): Call<ActorDetails>

    @GET("{person_id}/images?api_key=" +  BuildConfig.API_KEY + "&language=en-US")
    fun getPersonImagesTest(@Path(value = "person_id") personId : Int): Call<AllImagesResponse>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/person/"
    }
}