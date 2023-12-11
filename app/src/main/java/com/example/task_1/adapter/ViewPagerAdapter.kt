package com.example.task_1.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.task_1.ui.fragments.CropImagesFragment
import com.example.task_1.ui.fragments.ImagesFragment
import com.example.task_1.ui.fragments.SavedImageFragment
import com.example.task_1.ui.fragments.VideosFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm)  {

    override fun getCount(): Int  = 4

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> ImagesFragment()
            1 -> VideosFragment()
            2 -> SavedImageFragment()
            3 -> CropImagesFragment()
            else -> ImagesFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position){
            0 -> "Images"
            1 -> "Videos"
            2 -> "Saved"
            3 -> "Crop"
            else -> "Images"
        }
    }
}