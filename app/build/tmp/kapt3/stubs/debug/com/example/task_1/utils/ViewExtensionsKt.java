package com.example.task_1.utils;

import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, d1 = {"\u0000*\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\f\u0010\u0007\u001a\u00020\u0004*\u00020\bH\u0007\u001a\n\u0010\t\u001a\u00020\u0004*\u00020\n\u001a\u0014\u0010\u000b\u001a\u00020\u0004*\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"ANIMATION_FAST_MILLIS", "", "ANIMATION_SLOW_MILLIS", "hideSystemUI", "", "window", "Landroid/view/Window;", "padWithDisplayCutout", "Landroid/view/View;", "showImmersive", "Landroidx/appcompat/app/AlertDialog;", "simulateClick", "Landroid/widget/ImageButton;", "delay", "app_debug"})
public final class ViewExtensionsKt {
    public static final long ANIMATION_FAST_MILLIS = 50L;
    public static final long ANIMATION_SLOW_MILLIS = 100L;
    
    public static final void simulateClick(@org.jetbrains.annotations.NotNull
    android.widget.ImageButton $this$simulateClick, long delay) {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.P)
    public static final void padWithDisplayCutout(@org.jetbrains.annotations.NotNull
    android.view.View $this$padWithDisplayCutout) {
    }
    
    public static final void showImmersive(@org.jetbrains.annotations.NotNull
    androidx.appcompat.app.AlertDialog $this$showImmersive) {
    }
    
    private static final void hideSystemUI(android.view.Window window) {
    }
}