package com.example.task_1.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.task_1.model.Media
import com.example.task_1.repository.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {
    private val _videoList = MutableStateFlow(emptyList<Media>())
    val videoList: StateFlow<List<Media>> = _videoList
    private val _imageList = MutableStateFlow(emptyList<Media>())
    val imageList: StateFlow<List<Media>> = _imageList
    private val _savedVideoImageList = MutableStateFlow(emptyArray<File>())
    val savedVideoImageList: StateFlow<Array<File>> = _savedVideoImageList
    private val _savedCropImageList = MutableStateFlow(emptyList<File>())
    val savedCropImageList: StateFlow<List<File>> = _savedCropImageList
    val currentFragment = MutableStateFlow(0)
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