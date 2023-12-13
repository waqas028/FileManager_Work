package com.example.task_1.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.example.task_1.adapter.ViewPagerAdapter;
import com.example.task_1.databinding.ActivityMainBinding;
import com.example.task_1.ui.fragments.CropImagesFragment;
import com.example.task_1.ui.fragments.ImagesFragment;
import com.example.task_1.ui.fragments.SavedImageFragment;
import com.example.task_1.ui.fragments.VideosFragment;
import com.example.task_1.utils.Constant;
import com.example.task_1.viewmodel.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2 = {"Lcom/example/task_1/ui/activity/Fragments;", "", "label", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "IMAGE_FRAGMENTS", "VIDEO_FRAGMENTS", "SAVED_FRAGMENTS", "CROP_FRAGMENTS", "app_debug"})
public enum Fragments {
    /*public static final*/ IMAGE_FRAGMENTS /* = new IMAGE_FRAGMENTS(null) */,
    /*public static final*/ VIDEO_FRAGMENTS /* = new VIDEO_FRAGMENTS(null) */,
    /*public static final*/ SAVED_FRAGMENTS /* = new SAVED_FRAGMENTS(null) */,
    /*public static final*/ CROP_FRAGMENTS /* = new CROP_FRAGMENTS(null) */;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String label = null;
    
    Fragments(java.lang.String label) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLabel() {
        return null;
    }
}