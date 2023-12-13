package com.example.task_1.viewmodel;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import com.example.task_1.model.Media;
import com.example.task_1.repository.MainRepo;
import com.example.task_1.utils.MediaStoreUtils;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;
import javax.inject.Inject;

@dagger.hilt.android.lifecycle.HiltViewModel
@androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u000e\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"J\u0006\u0010#\u001a\u00020\u001cJ\u000e\u0010$\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"J\u0006\u0010%\u001a\u00020\u001cJ\b\u0010&\u001a\u00020\u001cH\u0007R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00070\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\'"}, d2 = {"Lcom/example/task_1/viewmodel/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "mainRepo", "Lcom/example/task_1/repository/MainRepo;", "(Lcom/example/task_1/repository/MainRepo;)V", "_allImageList", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/example/task_1/model/Media;", "_imageList", "_savedCropImageList", "Ljava/io/File;", "_savedVideoImageList", "", "_videoList", "allImageList", "Lkotlinx/coroutines/flow/StateFlow;", "getAllImageList", "()Lkotlinx/coroutines/flow/StateFlow;", "imageList", "getImageList", "savedCropImageList", "getSavedCropImageList", "savedVideoImageList", "getSavedVideoImageList", "videoList", "getVideoList", "getAllImages", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCropImagesList", "dirName", "", "getImagesList", "getNonEmptyDirectoriesWithFiles", "getSaveVideoImagesList", "getVideosList", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.task_1.repository.MainRepo mainRepo = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.task_1.model.Media>> _videoList = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.task_1.model.Media>> videoList = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.task_1.model.Media>> _imageList = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.task_1.model.Media>> imageList = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.io.File[]> _savedVideoImageList = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.io.File[]> savedVideoImageList = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.io.File>> _savedCropImageList = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.io.File>> savedCropImageList = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.task_1.model.Media>> _allImageList = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.task_1.model.Media>> allImageList = null;
    
    @javax.inject.Inject
    public MainViewModel(@org.jetbrains.annotations.NotNull
    com.example.task_1.repository.MainRepo mainRepo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.task_1.model.Media>> getVideoList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.task_1.model.Media>> getImageList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.io.File[]> getSavedVideoImageList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.io.File>> getSavedCropImageList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.task_1.model.Media>> getAllImageList() {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.Q)
    public final void getVideosList() {
    }
    
    public final void getImagesList() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getAllImages(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    public final void getSaveVideoImagesList() {
    }
    
    public final void getCropImagesList(@org.jetbrains.annotations.NotNull
    java.lang.String dirName) {
    }
    
    public final void getNonEmptyDirectoriesWithFiles(@org.jetbrains.annotations.NotNull
    java.lang.String dirName) {
    }
}