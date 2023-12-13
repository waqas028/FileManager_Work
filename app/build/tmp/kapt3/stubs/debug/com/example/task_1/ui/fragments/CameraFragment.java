package com.example.task_1.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.annotation.RequiresApi;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraState;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.task_1.R;
import com.example.task_1.databinding.CameraUiContainerBinding;
import com.example.task_1.databinding.FragmentCameraBinding;
import com.example.task_1.model.TempImage;
import com.example.task_1.ui.activity.CameraPreviewActivity;
import com.example.task_1.utils.MediaStoreUtils;
import com.example.task_1.viewmodel.MainViewModel;
import kotlinx.coroutines.Dispatchers;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.R)
@kotlin.Suppress(names = {"DEPRECATION"})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u00c2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 T2\u00020\u0001:\u0002TUB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u00101\u001a\u00020\u00122\u0006\u00102\u001a\u00020\u00122\u0006\u00103\u001a\u00020\u0012H\u0002J\b\u00104\u001a\u000205H\u0003J\b\u00106\u001a\u000207H\u0002J\b\u00108\u001a\u000207H\u0002J\u0010\u00109\u001a\u0002052\u0006\u0010:\u001a\u00020;H\u0002J\u0010\u0010<\u001a\u0002052\u0006\u0010=\u001a\u00020>H\u0016J&\u0010?\u001a\u0004\u0018\u00010@2\u0006\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010D2\b\u0010E\u001a\u0004\u0018\u00010FH\u0016J\b\u0010G\u001a\u000205H\u0016J\b\u0010H\u001a\u000205H\u0016J\u001a\u0010I\u001a\u0002052\u0006\u0010J\u001a\u00020K2\b\u0010E\u001a\u0004\u0018\u00010FH\u0016J\u0010\u0010L\u001a\u0002052\u0006\u0010:\u001a\u00020;H\u0002J\u0010\u0010M\u001a\u0002052\u0006\u0010N\u001a\u00020OH\u0002J\u0011\u0010P\u001a\u000205H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010QJ\b\u0010R\u001a\u000205H\u0002J\b\u0010S\u001a\u000205H\u0003R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010#\u001a\u00020$8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\'\u0010\u001a\u001a\u0004\b%\u0010&R\u000e\u0010(\u001a\u00020)X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082.\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006V"}, d2 = {"Lcom/example/task_1/ui/fragments/CameraFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_fragmentCameraBinding", "Lcom/example/task_1/databinding/FragmentCameraBinding;", "broadcastManager", "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;", "camera", "Landroidx/camera/core/Camera;", "cameraExecutor", "Ljava/util/concurrent/ExecutorService;", "cameraProvider", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "cameraUiContainerBinding", "Lcom/example/task_1/databinding/CameraUiContainerBinding;", "currentTimeSession", "", "displayId", "", "displayListener", "Landroid/hardware/display/DisplayManager$DisplayListener;", "displayManager", "Landroid/hardware/display/DisplayManager;", "getDisplayManager", "()Landroid/hardware/display/DisplayManager;", "displayManager$delegate", "Lkotlin/Lazy;", "fragmentCameraBinding", "getFragmentCameraBinding", "()Lcom/example/task_1/databinding/FragmentCameraBinding;", "imageAnalyzer", "Landroidx/camera/core/ImageAnalysis;", "imageCapture", "Landroidx/camera/core/ImageCapture;", "lensFacing", "mainViewModel", "Lcom/example/task_1/viewmodel/MainViewModel;", "getMainViewModel", "()Lcom/example/task_1/viewmodel/MainViewModel;", "mainViewModel$delegate", "mediaStoreUtils", "Lcom/example/task_1/utils/MediaStoreUtils;", "preview", "Landroidx/camera/core/Preview;", "sessionImageCounter", "volumeDownReceiver", "Landroid/content/BroadcastReceiver;", "windowManager", "Landroid/view/WindowManager;", "aspectRatio", "width", "height", "bindCameraUseCases", "", "hasBackCamera", "", "hasFrontCamera", "observeCameraState", "cameraInfo", "Landroidx/camera/core/CameraInfo;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreateView", "Landroid/widget/FrameLayout;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onResume", "onViewCreated", "view", "Landroid/view/View;", "removeCameraStateObservers", "setGalleryThumbnail", "imageUri", "Landroid/net/Uri;", "setUpCamera", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateCameraSwitchButton", "updateCameraUi", "Companion", "LuminosityAnalyzer", "app_debug"})
public final class CameraFragment extends androidx.fragment.app.Fragment {
    private com.example.task_1.databinding.FragmentCameraBinding _fragmentCameraBinding;
    private final kotlin.Lazy mainViewModel$delegate = null;
    private com.example.task_1.databinding.CameraUiContainerBinding cameraUiContainerBinding;
    private androidx.localbroadcastmanager.content.LocalBroadcastManager broadcastManager;
    private com.example.task_1.utils.MediaStoreUtils mediaStoreUtils;
    private int displayId = -1;
    private int lensFacing = androidx.camera.core.CameraSelector.LENS_FACING_BACK;
    private androidx.camera.core.Preview preview;
    private androidx.camera.core.ImageCapture imageCapture;
    private androidx.camera.core.ImageAnalysis imageAnalyzer;
    private androidx.camera.core.Camera camera;
    private androidx.camera.lifecycle.ProcessCameraProvider cameraProvider;
    private android.view.WindowManager windowManager;
    private int sessionImageCounter = 0;
    private long currentTimeSession = 0L;
    private final kotlin.Lazy displayManager$delegate = null;
    private java.util.concurrent.ExecutorService cameraExecutor;
    private final android.content.BroadcastReceiver volumeDownReceiver = null;
    private final android.hardware.display.DisplayManager.DisplayListener displayListener = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.task_1.ui.fragments.CameraFragment.Companion Companion = null;
    private static final java.lang.String TAG = "CameraFragInfo";
    private static final java.lang.String FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final java.lang.String PHOTO_TYPE = "image/jpeg";
    private static final double RATIO_4_3_VALUE = 1.3333333333333333;
    private static final double RATIO_16_9_VALUE = 1.7777777777777777;
    
    public CameraFragment() {
        super();
    }
    
    private final com.example.task_1.databinding.FragmentCameraBinding getFragmentCameraBinding() {
        return null;
    }
    
    private final com.example.task_1.viewmodel.MainViewModel getMainViewModel() {
        return null;
    }
    
    private final android.hardware.display.DisplayManager getDisplayManager() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    @java.lang.Override
    public android.widget.FrameLayout onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    public void onConfigurationChanged(@org.jetbrains.annotations.NotNull
    android.content.res.Configuration newConfig) {
    }
    
    private final void setGalleryThumbnail(android.net.Uri imageUri) {
    }
    
    private final java.lang.Object setUpCamera(kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.R)
    private final void bindCameraUseCases() {
    }
    
    private final void removeCameraStateObservers(androidx.camera.core.CameraInfo cameraInfo) {
    }
    
    private final void observeCameraState(androidx.camera.core.CameraInfo cameraInfo) {
    }
    
    private final int aspectRatio(int width, int height) {
        return 0;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.R)
    private final void updateCameraUi() {
    }
    
    private final void updateCameraSwitchButton() {
    }
    
    private final boolean hasBackCamera() {
        return false;
    }
    
    private final boolean hasFrontCamera() {
        return false;
    }
    
    @java.lang.Override
    public void onResume() {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B2\u0012+\b\u0002\u0010\u0002\u001a%\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003j\u0004\u0018\u0001`\t\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\f\u0010\u001a\u001a\u00020\u001b*\u00020\u001cH\u0002R\u000e\u0010\u000b\u001a\u00020\fX\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R/\u0010\u0015\u001a#\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u00030\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/example/task_1/ui/fragments/CameraFragment$LuminosityAnalyzer;", "Landroidx/camera/core/ImageAnalysis$Analyzer;", "listener", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "luma", "", "Lcom/example/task_1/ui/fragments/LumaListener;", "(Lkotlin/jvm/functions/Function1;)V", "frameRateWindow", "", "frameTimestamps", "Ljava/util/ArrayDeque;", "", "<set-?>", "framesPerSecond", "getFramesPerSecond", "()D", "lastAnalyzedTimestamp", "listeners", "Ljava/util/ArrayList;", "analyze", "image", "Landroidx/camera/core/ImageProxy;", "toByteArray", "", "Ljava/nio/ByteBuffer;", "app_debug"})
    static final class LuminosityAnalyzer implements androidx.camera.core.ImageAnalysis.Analyzer {
        private final int frameRateWindow = 8;
        private final java.util.ArrayDeque<java.lang.Long> frameTimestamps = null;
        private final java.util.ArrayList<kotlin.jvm.functions.Function1<java.lang.Double, kotlin.Unit>> listeners = null;
        private long lastAnalyzedTimestamp = 0L;
        private double framesPerSecond = -1.0;
        
        public LuminosityAnalyzer() {
            super();
        }
        
        public LuminosityAnalyzer(@org.jetbrains.annotations.Nullable
        kotlin.jvm.functions.Function1<? super java.lang.Double, kotlin.Unit> listener) {
            super();
        }
        
        public final double getFramesPerSecond() {
            return 0.0;
        }
        
        private final byte[] toByteArray(java.nio.ByteBuffer $this$toByteArray) {
            return null;
        }
        
        @java.lang.Override
        public void analyze(@org.jetbrains.annotations.NotNull
        androidx.camera.core.ImageProxy image) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/task_1/ui/fragments/CameraFragment$Companion;", "", "()V", "FILENAME", "", "PHOTO_TYPE", "RATIO_16_9_VALUE", "", "RATIO_4_3_VALUE", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}