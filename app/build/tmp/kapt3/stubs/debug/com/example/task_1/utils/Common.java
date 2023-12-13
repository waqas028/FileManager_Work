package com.example.task_1.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ActionMode;
import com.example.task_1.adapter.ImagesAdapter;
import com.example.task_1.interfaces.CopyImageProgressListener;
import com.example.task_1.model.Media;
import kotlinx.coroutines.Dispatchers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006JP\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0016J\u0018\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001aJ\u001e\u0010\u001b\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u0006JV\u0010\u001e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0016J\u001c\u0010\u001f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007J\u001a\u0010 \u001a\u0004\u0018\u00010\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u001a\u0010!\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u0006H\u0007J\u001a\u0010\"\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u0006H\u0007J\u000e\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u000b\u00a8\u0006&"}, d2 = {"Lcom/example/task_1/utils/Common;", "", "()V", "copyFile", "", "sourceFile", "Ljava/io/File;", "copyFileToFolder", "context", "Landroid/content/Context;", "sourceImagePath", "", "progressListener", "Lcom/example/task_1/interfaces/CopyImageProgressListener;", "mode", "Landroid/view/ActionMode;", "copiedImagesCount", "", "selectedItems", "", "Lcom/example/task_1/model/Media;", "onComplete", "Lkotlin/Function0;", "createVideoThumb", "Landroid/graphics/Bitmap;", "uri", "Landroid/net/Uri;", "deleteImages", "selectImageUri", "imageFile", "deleteMultipleFile", "getFilePathFromImageUri", "getFilePathFromVideoUri", "getImageContentUri", "getVideoContentUri", "isImageFile", "", "path", "app_debug"})
public final class Common {
    @org.jetbrains.annotations.NotNull
    public static final com.example.task_1.utils.Common INSTANCE = null;
    
    private Common() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    @android.annotation.SuppressLint(value = {"Range"})
    public final java.lang.String getFilePathFromImageUri(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    @android.annotation.SuppressLint(value = {"Range"})
    public final java.lang.String getFilePathFromVideoUri(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    @android.annotation.SuppressLint(value = {"Range"})
    public final android.net.Uri getImageContentUri(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.io.File imageFile) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    @android.annotation.SuppressLint(value = {"Range"})
    public final android.net.Uri getVideoContentUri(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.io.File imageFile) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.graphics.Bitmap createVideoThumb(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
    
    public final void copyFile(@org.jetbrains.annotations.NotNull
    java.io.File sourceFile) {
    }
    
    public final boolean isImageFile(@org.jetbrains.annotations.NotNull
    java.lang.String path) {
        return false;
    }
    
    public final void deleteImages(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.net.Uri selectImageUri, @org.jetbrains.annotations.NotNull
    java.io.File imageFile) {
    }
    
    public final void deleteMultipleFile(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.net.Uri selectImageUri, @org.jetbrains.annotations.NotNull
    java.io.File imageFile, @org.jetbrains.annotations.Nullable
    android.view.ActionMode mode, @org.jetbrains.annotations.Nullable
    com.example.task_1.interfaces.CopyImageProgressListener progressListener, int copiedImagesCount, @org.jetbrains.annotations.NotNull
    java.util.List<com.example.task_1.model.Media> selectedItems, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onComplete) {
    }
    
    public final void copyFileToFolder(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    java.lang.String sourceImagePath, @org.jetbrains.annotations.Nullable
    com.example.task_1.interfaces.CopyImageProgressListener progressListener, @org.jetbrains.annotations.Nullable
    android.view.ActionMode mode, int copiedImagesCount, @org.jetbrains.annotations.NotNull
    java.util.List<com.example.task_1.model.Media> selectedItems, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onComplete) {
    }
}