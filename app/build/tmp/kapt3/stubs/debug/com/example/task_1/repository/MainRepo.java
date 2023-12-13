package com.example.task_1.repository;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.example.task_1.model.Media;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.File;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0007J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0007J\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u0011\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/example/task_1/repository/MainRepo;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getAllImageListFromStorage", "", "Lcom/example/task_1/model/Media;", "getAllVideoListFromStorage", "getCropImagesList", "", "Ljava/io/File;", "dirName", "", "(Ljava/lang/String;)[Ljava/io/File;", "getSaveVideoImagesList", "()[Ljava/io/File;", "app_debug"})
public final class MainRepo {
    private final android.content.Context context = null;
    
    @javax.inject.Inject
    public MainRepo(@org.jetbrains.annotations.NotNull
    @dagger.hilt.android.qualifiers.ApplicationContext
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.Q)
    public final java.util.List<com.example.task_1.model.Media> getAllVideoListFromStorage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @android.annotation.SuppressLint(value = {"Range"})
    public final java.util.List<com.example.task_1.model.Media> getAllImageListFromStorage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.io.File[] getSaveVideoImagesList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.io.File[] getCropImagesList(@org.jetbrains.annotations.NotNull
    java.lang.String dirName) {
        return null;
    }
}