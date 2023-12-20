package com.example.task_1.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.task_1.model.Media
import com.example.task_1.model.TempImage
import com.example.task_1.repository.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    @RequiresApi(Build.VERSION_CODES.Q)
    fun getVideosList(){
        _videoList.value = mainRepo.getAllVideoListFromStorage()
    }

    fun getImagesList(){
        _imageList.value = mainRepo.getAllImageListFromStorage()
    }

    fun getSaveVideoImagesList(){
        _savedVideoImageList.value = mainRepo.getSaveVideoImagesList()
    }

    fun getCropImagesList(dirName:String){
        _savedCropImageList.value = mainRepo.getCropImagesList(dirName).toList()
    }

    fun getNonEmptyDirectoriesWithFiles(dirName: String){
        _savedCropImageList.value = mainRepo.getNonEmptyDirectoriesWithFiles(dirName)
    }
}