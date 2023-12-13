package com.example.task_1.ui.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.task_1.adapter.ImageSlideViewPagerAdapter;
import com.example.task_1.adapter.SlidePhotoAdapter;
import com.example.task_1.databinding.ActivityImagePreviewBinding;
import com.example.task_1.interfaces.CopyImageProgressListener;
import com.example.task_1.model.Media;
import com.example.task_1.utils.Common;
import com.example.task_1.utils.Constant;
import com.example.task_1.viewmodel.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;
import java.io.File;
import javax.inject.Inject;

@androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 !2\u00020\u00012\u00020\u0002:\u0001!B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0012\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0014J\u0010\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020\u0007H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0015\u001a\u00020\u00168\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006\""}, d2 = {"Lcom/example/task_1/ui/activity/ImagePreviewActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/example/task_1/interfaces/CopyImageProgressListener;", "()V", "binding", "Lcom/example/task_1/databinding/ActivityImagePreviewBinding;", "currentImagePosition", "", "currentImageUri", "Landroid/net/Uri;", "imageList", "", "Lcom/example/task_1/model/Media;", "imageSlideViewPagerAdapter", "Lcom/example/task_1/adapter/ImageSlideViewPagerAdapter;", "mainViewModel", "Lcom/example/task_1/viewmodel/MainViewModel;", "getMainViewModel", "()Lcom/example/task_1/viewmodel/MainViewModel;", "mainViewModel$delegate", "Lkotlin/Lazy;", "slidePhotoAdapter", "Lcom/example/task_1/adapter/SlidePhotoAdapter;", "getSlidePhotoAdapter", "()Lcom/example/task_1/adapter/SlidePhotoAdapter;", "setSlidePhotoAdapter", "(Lcom/example/task_1/adapter/SlidePhotoAdapter;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onProgressUpdate", "progress", "Companion", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint
public final class ImagePreviewActivity extends androidx.appcompat.app.AppCompatActivity implements com.example.task_1.interfaces.CopyImageProgressListener {
    private com.example.task_1.databinding.ActivityImagePreviewBinding binding;
    private final kotlin.Lazy mainViewModel$delegate = null;
    @javax.inject.Inject
    public com.example.task_1.adapter.SlidePhotoAdapter slidePhotoAdapter;
    private com.example.task_1.adapter.ImageSlideViewPagerAdapter imageSlideViewPagerAdapter;
    private java.util.List<com.example.task_1.model.Media> imageList;
    private int currentImagePosition = 0;
    private android.net.Uri currentImageUri;
    @org.jetbrains.annotations.NotNull
    public static final com.example.task_1.ui.activity.ImagePreviewActivity.Companion Companion = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TAG = "ImagePreviewInfo";
    
    public ImagePreviewActivity() {
        super();
    }
    
    private final com.example.task_1.viewmodel.MainViewModel getMainViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.task_1.adapter.SlidePhotoAdapter getSlidePhotoAdapter() {
        return null;
    }
    
    public final void setSlidePhotoAdapter(@org.jetbrains.annotations.NotNull
    com.example.task_1.adapter.SlidePhotoAdapter p0) {
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    public void onProgressUpdate(int progress) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/task_1/ui/activity/ImagePreviewActivity$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}