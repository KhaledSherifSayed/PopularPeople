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


import androidx.annotation.MainThread
import com.meslmawy.ibtkarchallenge.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import retrofit2.Response

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<T> {

    fun asFlow() = flow<State<T>> {

        // Emit Loading State
        emit(State.loading())

        try {
            // Fetch latest data from remote
            val apiResponse = fetchFromRemote()

            // Parse body
            val response = apiResponse.body()

            // Check for response validation
            if (apiResponse.isSuccessful && response != null)
                emit(State.success(response))
            else
                // Something went wrong! Emit Error state.
                emit(State.error(apiResponse.message()))

        } catch (e: Exception) {
            // Exception occurred! Emit error
            //emit(State.error("Please check your internet connection and try again"))
            emit(State.error(e.message.toString()))
        }
    }

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<T>
}