package com.example.task_1.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.task_1.adapter.ImagesAdapter;
import com.example.task_1.databinding.FragmentImagesBinding;
import com.example.task_1.interfaces.CopyImageProgressListener;
import com.example.task_1.ui.activity.ImagePreviewActivity;
import com.example.task_1.ui.activity.MainActivity;
import com.example.task_1.utils.Constant;
import com.example.task_1.viewmodel.MainViewModel;
import kotlinx.coroutines.Dispatchers;

@androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
@kotlin.Suppress(names = {"DEPRECATION"})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\b\u0007\u0018\u0000 \'2\u00020\u00012\u00020\u0002:\u0001\'B\u0005\u00a2\u0006\u0002\u0010\u0003J$\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020#H\u0016J\u001a\u0010$\u001a\u00020 2\u0006\u0010%\u001a\u00020\u00182\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0017J\b\u0010&\u001a\u00020 H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/example/task_1/ui/fragments/ImagesFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/example/task_1/interfaces/CopyImageProgressListener;", "()V", "_binding", "Lcom/example/task_1/databinding/FragmentImagesBinding;", "binding", "getBinding", "()Lcom/example/task_1/databinding/FragmentImagesBinding;", "imagesAdapter", "Lcom/example/task_1/adapter/ImagesAdapter;", "getImagesAdapter", "()Lcom/example/task_1/adapter/ImagesAdapter;", "setImagesAdapter", "(Lcom/example/task_1/adapter/ImagesAdapter;)V", "mainViewModel", "Lcom/example/task_1/viewmodel/MainViewModel;", "getMainViewModel", "()Lcom/example/task_1/viewmodel/MainViewModel;", "mainViewModel$delegate", "Lkotlin/Lazy;", "progressDialog", "Landroid/app/ProgressDialog;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "", "onProgressUpdate", "progress", "", "onViewCreated", "view", "showDialogue", "Companion", "app_debug"})
public final class ImagesFragment extends androidx.fragment.app.Fragment implements com.example.task_1.interfaces.CopyImageProgressListener {
    private com.example.task_1.databinding.FragmentImagesBinding _binding;
    private final kotlin.Lazy mainViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull
    private com.example.task_1.adapter.ImagesAdapter imagesAdapter;
    private android.app.ProgressDialog progressDialog;
    @org.jetbrains.annotations.NotNull
    public static final com.example.task_1.ui.fragments.ImagesFragment.Companion Companion = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TAG = "ImagesFragInfo";
    
    public ImagesFragment() {
        super();
    }
    
    private final com.example.task_1.databinding.FragmentImagesBinding getBinding() {
        return null;
    }
    
    private final com.example.task_1.viewmodel.MainViewModel getMainViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.task_1.adapter.ImagesAdapter getImagesAdapter() {
        return null;
    }
    
    public final void setImagesAdapter(@org.jetbrains.annotations.NotNull
    com.example.task_1.adapter.ImagesAdapter p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
    
    @java.lang.Override
    public void onProgressUpdate(int progress) {
    }
    
    private final void showDialogue() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/task_1/ui/fragments/ImagesFragment$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}