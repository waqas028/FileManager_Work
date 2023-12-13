package com.example.task_1.adapter;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.task_1.R;
import com.example.task_1.extension.MyActionModeCallback;
import com.example.task_1.interfaces.CopyImageProgressListener;
import com.example.task_1.model.Media;
import com.example.task_1.utils.Common;
import com.example.task_1.utils.Constant;
import kotlinx.coroutines.Dispatchers;
import java.io.File;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 62\b\u0012\u0004\u0012\u00020\u00020\u0001:\u000267B\u001d\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0002\u0010\bJ\u0006\u0010%\u001a\u00020\u0007J\b\u0010&\u001a\u00020\u000fH\u0016J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020\rH\u0002J\u0018\u0010(\u001a\u00020\u00072\u0006\u0010)\u001a\u00020\u00022\u0006\u0010*\u001a\u00020\u000fH\u0016J\u0018\u0010+\u001a\u00020\u00022\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u000fH\u0016J\u000e\u0010/\u001a\u00020\u00072\u0006\u00100\u001a\u00020\u000fJ\b\u0010#\u001a\u00020\u0007H\u0002J\u001a\u00101\u001a\u00020\u00072\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00070\u001fJ\u001a\u00103\u001a\u00020\u00072\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00070\u001fJ\u0010\u00104\u001a\u00020\u00072\u0006\u0010\'\u001a\u00020\rH\u0002J\b\u00105\u001a\u00020\u0007H\u0002R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001f\u0010\u0014\u001a\u0010\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\r0\r0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\r0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u001e\u001a\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001c\u0010\"\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00068"}, d2 = {"Lcom/example/task_1/adapter/ImagesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/task_1/adapter/ImagesAdapter$ViewHolder;", "progressListener", "Lcom/example/task_1/interfaces/CopyImageProgressListener;", "onDeleteItemListener", "Lkotlin/Function0;", "", "(Lcom/example/task_1/interfaces/CopyImageProgressListener;Lkotlin/jvm/functions/Function0;)V", "actionMode", "Landroid/view/ActionMode;", "checkPosition", "", "Lcom/example/task_1/model/Media;", "copiedImagesCount", "", "getCopiedImagesCount", "()I", "setCopiedImagesCount", "(I)V", "differ", "Landroidx/recyclerview/widget/AsyncListDiffer;", "kotlin.jvm.PlatformType", "getDiffer", "()Landroidx/recyclerview/widget/AsyncListDiffer;", "differCallback", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "isSelected", "", "menuSelection", "onCopyClickListener", "Lkotlin/Function1;", "getOnDeleteItemListener", "()Lkotlin/jvm/functions/Function0;", "onItemClickListener", "selectAllItems", "selectedItems", "clearSelections", "getItemCount", "item", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onPageUpdate", "onPageSelected", "stOnCopyClickListener", "listener", "stOnItemClickListener", "toggleSelection", "unSelectAllItems", "Companion", "ViewHolder", "app_debug"})
public final class ImagesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.task_1.adapter.ImagesAdapter.ViewHolder> {
    private final com.example.task_1.interfaces.CopyImageProgressListener progressListener = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteItemListener = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.task_1.adapter.ImagesAdapter.Companion Companion = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TAG = "ImagesAdapterInfo";
    private int copiedImagesCount = 0;
    private java.util.List<java.lang.Boolean> isSelected;
    private final java.util.List<com.example.task_1.model.Media> selectedItems = null;
    private android.view.ActionMode actionMode;
    private int menuSelection;
    private java.util.List<com.example.task_1.model.Media> checkPosition;
    private boolean selectAllItems = false;
    private final androidx.recyclerview.widget.DiffUtil.ItemCallback<com.example.task_1.model.Media> differCallback = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.recyclerview.widget.AsyncListDiffer<com.example.task_1.model.Media> differ = null;
    private kotlin.jvm.functions.Function1<? super com.example.task_1.model.Media, kotlin.Unit> onItemClickListener;
    private kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCopyClickListener;
    
    public ImagesAdapter(@org.jetbrains.annotations.Nullable
    com.example.task_1.interfaces.CopyImageProgressListener progressListener, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteItemListener) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlin.jvm.functions.Function0<kotlin.Unit> getOnDeleteItemListener() {
        return null;
    }
    
    public final int getCopiedImagesCount() {
        return 0;
    }
    
    public final void setCopiedImagesCount(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.recyclerview.widget.AsyncListDiffer<com.example.task_1.model.Media> getDiffer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public com.example.task_1.adapter.ImagesAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.example.task_1.adapter.ImagesAdapter.ViewHolder holder, int position) {
    }
    
    public final void stOnItemClickListener(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.task_1.model.Media, kotlin.Unit> listener) {
    }
    
    public final void stOnCopyClickListener(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> listener) {
    }
    
    private final void toggleSelection(com.example.task_1.model.Media item) {
    }
    
    private final boolean isSelected(com.example.task_1.model.Media item) {
        return false;
    }
    
    public final void clearSelections() {
    }
    
    private final void selectAllItems() {
    }
    
    private final void unSelectAllItems() {
    }
    
    public final void onPageUpdate(int onPageSelected) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f\u00a8\u0006\u000f"}, d2 = {"Lcom/example/task_1/adapter/ImagesAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "imageNameTextview", "Landroid/widget/TextView;", "getImageNameTextview", "()Landroid/widget/TextView;", "imageview", "Landroid/widget/ImageView;", "getImageview", "()Landroid/widget/ImageView;", "selectImageview", "getSelectImageview", "app_debug"})
    public static final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView imageNameTextview = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.ImageView imageview = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.ImageView selectImageview = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View itemView) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getImageNameTextview() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.ImageView getImageview() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.ImageView getSelectImageview() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/task_1/adapter/ImagesAdapter$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}