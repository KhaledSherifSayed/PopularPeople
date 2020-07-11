package com.meslmawy.ibtkarchallenge.presentation.photo

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meslmawy.ibtkarchallenge.domain.repository.ChallengeRepositery
import kotlinx.coroutines.*
import java.io.File


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PhotoViewModel(val repository: ChallengeRepositery) : ViewModel() {

    private val _imageFound = MutableLiveData<Boolean>()
    val imageFound: LiveData<Boolean>
        get() = _imageFound
    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()


    fun checkImageFound(fileName: String) {
        checkFolderExist()
        val direct = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
                    + File.separator +
                    "PopularImagesWallper"
                    + File.separator + fileName
        )
        _imageFound.value = direct.exists()
    }

    fun checkFolderExist() {
        val sdDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val direct = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "PopularImagesWallper")
        if (!direct.exists()) {
            val wallpaperDirectory = File(sdDirectory, "PopularImagesWallper")
            wallpaperDirectory.mkdirs()
        }
    }

    fun deleteImage(fileName: String) {
        val direct = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
                    + File.separator +
                    "PopularImagesWallper"
                    + File.separator + fileName
        )
        direct.delete()
        if(!direct.exists()){
            image_deleted()
        }
    }

    fun image_saved(){
        _imageFound.value = true
    }

    fun image_deleted(){
        _imageFound.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}