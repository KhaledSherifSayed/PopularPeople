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



package com.meslmawy.ibtkarchallenge.network

import com.meslmawy.ibtkarchallenge.data.api.ChallengeApiService
import com.meslmawy.ibtkarchallenge.domain.dto.ActorDetails
import com.meslmawy.ibtkarchallenge.domain.dto.AllImagesResponse
import com.meslmawy.ibtkarchallenge.domain.dto.AllPeopleResponse
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

class IbatkarApiServiceTest : ApiAbstract<ChallengeApiService>() {

    private lateinit var service: ChallengeApiService
    private lateinit var allPeopleResponse : AllPeopleResponse
    private lateinit var peopleInfoResponse: ActorDetails
    private lateinit var peopleImageResponse: AllImagesResponse

    @Before
    fun initService() {
        service = createService(ChallengeApiService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchPopularPeopleListTest()   {
        enqueueResponse("/PopularPeoPleResponse.json")
        allPeopleResponse = requireNotNull(service.getAllPeopleTest().execute().body())
        mockWebServer.takeRequest()
        assertThat(allPeopleResponse.results?.get(0)?.id, `is`(4494))
        assertThat(allPeopleResponse.results?.get(0)?.profile_path, `is`("/p3wxUblbPwRVzTp7jW1lXISKIob.jpg"))
        assertThat(allPeopleResponse.results?.get(0)?.name, `is`("Lisa Ann Walter"))
        assertThat(allPeopleResponse.results?.get(0)?.known_for?.get(0)?.title, `is`("Bruce Almighty"))
    }

    @Throws(IOException::class)
    @Test
    fun fetchPeopleInfoTest() {
        enqueueResponse("/PeoPleInfoResponse.json")
        peopleInfoResponse = requireNotNull(service.getPersonInfoTest(4494).execute().body())
        mockWebServer.takeRequest()
        assertThat(peopleInfoResponse.id, `is`(4494))
        assertThat(peopleInfoResponse.known_for_department, `is`("Acting"))
        assertThat(peopleInfoResponse.gender, `is`(1))
        assertThat(peopleInfoResponse.place_of_birth, `is`("Silver Spring, Maryland, USA"))
    }

    @Throws(IOException::class)
    @Test
    fun fetchPeopleImages() {
        enqueueResponse("/PeopleImagerResponse.json")
        peopleImageResponse = requireNotNull(service.getPersonImagesTest(4494).execute().body())
        mockWebServer.takeRequest()
        assertThat(peopleImageResponse.profiles?.get(0)?.file_path, `is`("/jKiSaeRO4IPJoHsjGRqMpdrZu88.jpg"))
        assertThat(peopleImageResponse.profiles?.get(1)?.file_path, `is`("/uL8s2YDwuCigsLGKDSnBkCzfEdr.jpg"))
        assertThat(peopleImageResponse.profiles?.get(2)?.file_path, `is`("/3xeMzT5zOC0UM9DgHne3oGZmf1R.jpg"))
        assertThat(peopleImageResponse.profiles?.get(3)?.file_path, `is`("/p3wxUblbPwRVzTp7jW1lXISKIob.jpg"))
    }
}
