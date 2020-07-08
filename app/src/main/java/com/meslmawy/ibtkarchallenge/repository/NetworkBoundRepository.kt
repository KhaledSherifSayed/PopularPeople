
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