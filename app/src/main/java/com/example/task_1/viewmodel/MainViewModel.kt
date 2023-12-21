package com.example.task_1.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task_1.model.Media
import com.example.task_1.model.TempImage
import com.example.task_1.repository.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {
    private val _videoList = MutableStateFlow(emptyList<Media>())
    val videoList: StateFlow<List<Media>> = _videoList
    private val _imageList = MutableStateFlow(emptyList<Media>())
    val imageList: StateFlow<List<Media>> = _imageList
    private val _savedVideoImageList = MutableStateFlow(emptyList<Media>())
    val savedVideoImageList: StateFlow<List<Media>> = _savedVideoImageList
    private val _savedCropImageList = MutableStateFlow(emptyList<Media>())
    val savedCropImageList: StateFlow<List<Media>> = _savedCropImageList
    val currentFragment = MutableStateFlow(0)
    val cameraTempImageList = MutableStateFlow(mutableListOf<TempImage>())

    private val _savedVideoImageListLivedata: MutableLiveData<List<Media>> = MutableLiveData()
    val savedVideoImageListLivedata: LiveData<List<Media>> = _savedVideoImageListLivedata
    @RequiresApi(Build.VERSION_CODES.Q)
    fun getVideosList(){
        _videoList.value = mainRepo.getAllVideoListFromStorage()
    }

    fun getImagesList(){
        _imageList.value = mainRepo.getAllImageListFromStorage()
    }

    suspend fun getSaveVideoImagesList(){
        _savedVideoImageList.value = mainRepo.getSaveVideoImagesList()
        /*withContext(Dispatchers.Main){
            _savedVideoImageListLivedata.value = mainRepo.getSaveVideoImagesList()
        }
        _savedVideoImageListLivedata.postValue(mainRepo.getSaveVideoImagesList())
        Log.i("ImagePreviewInfo", "getSaveVideoImagesList: MainVM: ${this.hashCode()}  ${_savedVideoImageList.value.size}  //  ${_savedVideoImageListLivedata.value?.size}")*/
    }

    fun getCropImagesList(dirName:String){
        _savedCropImageList.value = mainRepo.getCropImagesList(dirName).toList()
    }

    fun getNonEmptyDirectoriesWithFiles(dirName: String){
        _savedCropImageList.value = mainRepo.getNonEmptyDirectoriesWithFiles(dirName)
    }
}