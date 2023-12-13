package com.example.task_1.ui.activity;

import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.task_1.databinding.ActivityCameraPreviewBinding;
import com.example.task_1.model.TempImage;
import dagger.hilt.android.AndroidEntryPoint;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0003X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"IMMERSIVE_FLAG_TIMEOUT", "", "KEY_EVENT_ACTION", "", "KEY_EVENT_EXTRA", "app_debug"})
public final class CameraPreviewActivityKt {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_EVENT_ACTION = "key_event_action";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_EVENT_EXTRA = "key_event_extra";
    private static final long IMMERSIVE_FLAG_TIMEOUT = 500L;
}