package com.example.task_1.adapter;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.task_1.ui.fragments.CropImagesFragment;
import com.example.task_1.ui.fragments.ImagesFragment;
import com.example.task_1.ui.fragments.SavedImageFragment;
import com.example.task_1.ui.fragments.VideosFragment;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0017J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0006H\u0016\u00a8\u0006\f"}, d2 = {"Lcom/example/task_1/adapter/ViewPagerAdapter;", "Landroidx/fragment/app/FragmentStatePagerAdapter;", "fm", "Landroidx/fragment/app/FragmentManager;", "(Landroidx/fragment/app/FragmentManager;)V", "getCount", "", "getItem", "Landroidx/fragment/app/Fragment;", "position", "getPageTitle", "", "app_debug"})
public final class ViewPagerAdapter extends androidx.fragment.app.FragmentStatePagerAdapter {
    
    public ViewPagerAdapter(@org.jetbrains.annotations.NotNull
    androidx.fragment.app.FragmentManager fm) {
        super(null);
    }
    
    @java.lang.Override
    public int getCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.Q)
    @java.lang.Override
    public androidx.fragment.app.Fragment getItem(int position) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public java.lang.CharSequence getPageTitle(int position) {
        return null;
    }
}