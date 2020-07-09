package com.meslmawy.ibtkarchallenge.data.api

import com.meslmawy.ibtkarchallenge.domain.dto.ActorDetails
import com.meslmawy.ibtkarchallenge.domain.dto.AllImagesResponse
import com.meslmawy.ibtkarchallenge.domain.dto.AllPeopleResponse
import retrofit2.Response
import retrofit2.http.*


interface ChallengeApiService {


    @GET("popular?api_key=c07cbf1aeb56336a94a6c998fdee50c6")
    suspend fun getAllPeople(): Response<AllPeopleResponse>

    @GET("{person_id}?api_key=c07cbf1aeb56336a94a6c998fdee50c6&language=en-US")
    suspend fun getPersonInfo(@Path(value = "person_id") personId : Int): Response<ActorDetails>

    @GET("{person_id}/images?api_key=c07cbf1aeb56336a94a6c998fdee50c6&language=en-US")
    suspend fun getPersonImages(@Path(value = "person_id") personId : Int): Response<AllImagesResponse>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/person/"
    }
}