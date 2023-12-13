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
import com.example.task_1.ui.fragments.ImagesFragment;
import com.example.task_1.ui.fragments.SavedImageFragment;
import com.example.task_1.ui.fragments.VideosFragment;
import com.example.task_1.utils.Constant;
import com.example.task_1.viewmodel.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0003J\b\u0010\u000f\u001a\u00020\u000eH\u0003J\b\u0010\u0010\u001a\u00020\u000eH\u0003J\b\u0010\u0011\u001a\u00020\u000eH\u0003J\b\u0010\u0012\u001a\u00020\u000eH\u0003J\u0012\u0010\u0013\u001a\u00020\u000e2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0015J+\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0017\u00a2\u0006\u0002\u0010\u001eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/example/task_1/ui/activity/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/task_1/databinding/ActivityMainBinding;", "mainViewModel", "Lcom/example/task_1/viewmodel/MainViewModel;", "getMainViewModel", "()Lcom/example/task_1/viewmodel/MainViewModel;", "mainViewModel$delegate", "Lkotlin/Lazy;", "viewPagerAdapter", "Lcom/example/task_1/adapter/ViewPagerAdapter;", "checkPermissionForAllVersion", "", "checkRequestPermissionAboveTen", "checkRequestPermissionBelowTen", "checkRequestPermissionFor14", "getAllListOfData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "Companion", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.task_1.databinding.ActivityMainBinding binding;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy mainViewModel$delegate = null;
    private com.example.task_1.adapter.ViewPagerAdapter viewPagerAdapter;
    @org.jetbrains.annotations.NotNull
    public static final com.example.task_1.ui.activity.MainActivity.Companion Companion = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TAG = "MainActivityInfo";
    public static final int READ_WRITE_REQUEST_CODE = 123;
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.task_1.viewmodel.MainViewModel getMainViewModel() {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.TIRAMISU)
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.TIRAMISU)
    private final void checkPermissionForAllVersion() {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.Q)
    private final void checkRequestPermissionAboveTen() {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.Q)
    private final void checkRequestPermissionBelowTen() {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.TIRAMISU)
    private final void checkRequestPermissionFor14() {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.TIRAMISU)
    @java.lang.Override
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull
    int[] grantResults) {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.Q)
    private final void getAllListOfData() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/example/task_1/ui/activity/MainActivity$Companion;", "", "()V", "READ_WRITE_REQUEST_CODE", "", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}