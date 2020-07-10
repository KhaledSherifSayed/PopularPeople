/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    fun fetchPopularPeopleListTest() {
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
