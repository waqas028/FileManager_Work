package com.example.task_1.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import com.example.task_1.model.TempImage;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR&\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00100\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u000b\"\u0004\b\u001c\u0010\rR\u001a\u0010\u001d\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u000b\"\u0004\b\u001f\u0010\rR\u001a\u0010 \u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u000b\"\u0004\b\"\u0010\r\u00a8\u0006#"}, d2 = {"Lcom/example/task_1/utils/Constant;", "", "()V", "SELECT_IMAGE_NAME", "", "SELECT_IMAGE_PATH", "SELECT_VIDEO_NAME", "SELECT_VIDEO_PATH", "currentFragment", "", "getCurrentFragment", "()I", "setCurrentFragment", "(I)V", "currentImageBitmap1", "Ljava/util/HashMap;", "Landroid/graphics/Bitmap;", "getCurrentImageBitmap1", "()Ljava/util/HashMap;", "setCurrentImageBitmap1", "(Ljava/util/HashMap;)V", "isItCancel", "", "()Z", "setItCancel", "(Z)V", "selectImagePosition", "getSelectImagePosition", "setSelectImagePosition", "selectVideoPosition", "getSelectVideoPosition", "setSelectVideoPosition", "totalImagesToCopy", "getTotalImagesToCopy", "setTotalImagesToCopy", "app_debug"})
public final class Constant {
    @org.jetbrains.annotations.NotNull
    public static final com.example.task_1.utils.Constant INSTANCE = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String SELECT_IMAGE_PATH = "SelectImage Path";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String SELECT_IMAGE_NAME = "SelectImage Name";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String SELECT_VIDEO_PATH = "Select Video Path";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String SELECT_VIDEO_NAME = "Select Video Name";
    private static int selectImagePosition = 0;
    private static int selectVideoPosition = 0;
    @org.jetbrains.annotations.NotNull
    private static java.util.HashMap<java.lang.Integer, android.graphics.Bitmap> currentImageBitmap1;
    private static int totalImagesToCopy = 0;
    private static int currentFragment = 0;
    private static boolean isItCancel = false;
    
    private Constant() {
        super();
    }
    
    public final int getSelectImagePosition() {
        return 0;
    }
    
    public final void setSelectImagePosition(int p0) {
    }
    
    public final int getSelectVideoPosition() {
        return 0;
    }
    
    public final void setSelectVideoPosition(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.HashMap<java.lang.Integer, android.graphics.Bitmap> getCurrentImageBitmap1() {
        return null;
    }
    
    public final void setCurrentImageBitmap1(@org.jetbrains.annotations.NotNull
    java.util.HashMap<java.lang.Integer, android.graphics.Bitmap> p0) {
    }
    
    public final int getTotalImagesToCopy() {
        return 0;
    }
    
    public final void setTotalImagesToCopy(int p0) {
    }
    
    public final int getCurrentFragment() {
        return 0;
    }
    
    public final void setCurrentFragment(int p0) {
    }
    
    public final boolean isItCancel() {
        return false;
    }
    
    public final void setItCancel(boolean p0) {
    }
}