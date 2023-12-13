package com.example.task_1.extension;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import com.example.task_1.ui.activity.MainActivity;
import com.example.task_1.utils.Common;
import com.example.task_1.utils.Constant;
import kotlinx.coroutines.Dispatchers;
import java.io.File;
import java.io.FileOutputStream;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u001a\u0012\u0010\u0006\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u0016\u0010\n\u001a\u0004\u0018\u00010\u0005*\u00020\u00072\u0006\u0010\u000b\u001a\u00020\tH\u0007\u001a\u0016\u0010\f\u001a\u0004\u0018\u00010\u0005*\u00020\u00072\u0006\u0010\u000b\u001a\u00020\tH\u0007\u001a\u0016\u0010\r\u001a\u0004\u0018\u00010\t*\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0007\u001a\u001c\u0010\u0010\u001a\u00020\u0001*\u00020\u00072\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0011\u001a\u00020\u000f\u001a\u0014\u0010\u0012\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0014H\u0007\u001a\u0012\u0010\u0015\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0005\u001a\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u0003*\u00020\u00072\u0006\u0010\u000b\u001a\u00020\t\u00a8\u0006\u0018"}, d2 = {"cropImage", "", "bitmap", "Landroid/graphics/Bitmap;", "originalImagePath", "", "deleteSingleFile", "Landroid/app/Activity;", "selectImageUri", "Landroid/net/Uri;", "getFilePathFromImageUri", "uri", "getFilePathFromVideoUri", "getImageContentUri", "imageFile", "Ljava/io/File;", "saveImagesBitmap", "directory", "showDialogue", "progress", "", "showToast", "message", "uriToBitmap", "app_debug"})
public final class ActivityExtenKt {
    
    @org.jetbrains.annotations.Nullable
    @android.annotation.SuppressLint(value = {"Range"})
    public static final java.lang.String getFilePathFromImageUri(@org.jetbrains.annotations.NotNull
    android.app.Activity $this$getFilePathFromImageUri, @org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    @android.annotation.SuppressLint(value = {"Range"})
    public static final java.lang.String getFilePathFromVideoUri(@org.jetbrains.annotations.NotNull
    android.app.Activity $this$getFilePathFromVideoUri, @org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public static final android.graphics.Bitmap uriToBitmap(@org.jetbrains.annotations.NotNull
    android.app.Activity $this$uriToBitmap, @org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
    
    public static final void showToast(@org.jetbrains.annotations.NotNull
    android.app.Activity $this$showToast, @org.jetbrains.annotations.NotNull
    java.lang.String message) {
    }
    
    @org.jetbrains.annotations.Nullable
    @android.annotation.SuppressLint(value = {"Range"})
    public static final android.net.Uri getImageContentUri(@org.jetbrains.annotations.NotNull
    android.app.Activity $this$getImageContentUri, @org.jetbrains.annotations.NotNull
    java.io.File imageFile) {
        return null;
    }
    
    public static final void saveImagesBitmap(@org.jetbrains.annotations.NotNull
    android.app.Activity $this$saveImagesBitmap, @org.jetbrains.annotations.Nullable
    android.graphics.Bitmap bitmap, @org.jetbrains.annotations.NotNull
    java.io.File directory) {
    }
    
    public static final void cropImage(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bitmap, @org.jetbrains.annotations.Nullable
    java.lang.String originalImagePath) {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    public static final void showDialogue(@org.jetbrains.annotations.NotNull
    android.app.Activity $this$showDialogue, int progress) {
    }
    
    public static final void deleteSingleFile(@org.jetbrains.annotations.NotNull
    android.app.Activity $this$deleteSingleFile, @org.jetbrains.annotations.NotNull
    android.net.Uri selectImageUri) {
    }
}