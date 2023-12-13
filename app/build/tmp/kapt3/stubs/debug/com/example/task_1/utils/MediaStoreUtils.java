package com.example.task_1.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import com.example.task_1.model.Media;
import kotlinx.coroutines.Dispatchers;
import java.io.File;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0013\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, d2 = {"Lcom/example/task_1/utils/MediaStoreUtils;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mediaStoreCollection", "Landroid/net/Uri;", "getImages", "", "Lcom/example/task_1/model/Media;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLatestImageFilename", "", "getMediaStoreImageCursor", "Landroid/database/Cursor;", "(Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class MediaStoreUtils {
    private final android.content.Context context = null;
    private final android.net.Uri mediaStoreCollection = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.task_1.utils.MediaStoreUtils.Companion Companion = null;
    private static final java.lang.String imageDataColumnIndex = "_data";
    private static final java.lang.String imageIdColumnIndex = "_id";
    private static final java.lang.String imageNameColumnIndex = "_display_name";
    
    public MediaStoreUtils(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    private final java.lang.Object getMediaStoreImageCursor(android.net.Uri mediaStoreCollection, kotlin.coroutines.Continuation<? super android.database.Cursor> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getLatestImageFilename(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.String> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getImages(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.task_1.model.Media>> continuation) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/example/task_1/utils/MediaStoreUtils$Companion;", "", "()V", "imageDataColumnIndex", "", "imageIdColumnIndex", "imageNameColumnIndex", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}