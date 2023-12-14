package com.example.task_1.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task_1.R
import com.example.task_1.databinding.FragmentGalleryBinding
import com.example.task_1.model.TempImage
import com.example.task_1.ui.activity.CameraPreviewActivity
import com.example.task_1.utils.MediaStoreUtils
import com.example.task_1.utils.padWithDisplayCutout
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {
    private var _fragmentGalleryBinding: FragmentGalleryBinding? = null
    private val fragmentGalleryBinding get() = _fragmentGalleryBinding!!
    private lateinit var mediaPagerAdapter: MediaPagerAdapter
    private var mediaList: MutableList<TempImage> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            // Get images this app has access to from MediaStore
            for(i in (activity as CameraPreviewActivity).imageUriList.indices){
                mediaList.add(TempImage(i,MediaStoreUtils(requireContext()).getImages()[i].uri))
            }
            Log.i(TAG, "onCreate: ${(activity as CameraPreviewActivity).imageUriList.size}")
            (fragmentGalleryBinding.photoViewPager.adapter as MediaPagerAdapter)
                .setMediaListAndNotify(mediaList)
            Log.i(TAG, "onCreate: ")
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _fragmentGalleryBinding = FragmentGalleryBinding.inflate(inflater, container, false)
        return fragmentGalleryBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Populate the ViewPager and implement a cache of two media items
        mediaPagerAdapter = MediaPagerAdapter(childFragmentManager, mediaList)
        fragmentGalleryBinding.photoViewPager.apply {
            offscreenPageLimit = 2
            adapter = mediaPagerAdapter
        }
        Log.i(TAG, "onViewCreated: ")

        // Make sure that the cutout "safe area" avoids the screen notch if any
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Use extension method to pad "inside" view containing UI using display cutout's bounds
            fragmentGalleryBinding.cutoutSafeArea.padWithDisplayCutout()
        }

        // Handle back button press
        fragmentGalleryBinding.backButton.setOnClickListener {
            (activity as CameraPreviewActivity).imageUriList.clear()
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigateUp()
        }

        // Handle backImageButton press
        fragmentGalleryBinding.backImageButton.setOnClickListener {
            if (fragmentGalleryBinding.photoViewPager.currentItem > 0) {
                fragmentGalleryBinding.photoViewPager.currentItem = fragmentGalleryBinding.photoViewPager.currentItem - 1
                fragmentGalleryBinding.imageCounterTextview.text = "${fragmentGalleryBinding.photoViewPager.currentItem+1}/${(activity as CameraPreviewActivity).imageUriList.size}"
            }
        }

        // Handle nextImage button press
        fragmentGalleryBinding.nextImageButton.setOnClickListener {
            if (fragmentGalleryBinding.photoViewPager.currentItem < mediaPagerAdapter.itemCount - 1) {
                fragmentGalleryBinding.photoViewPager.currentItem = fragmentGalleryBinding.photoViewPager.currentItem + 1
                fragmentGalleryBinding.imageCounterTextview.text = "${fragmentGalleryBinding.photoViewPager.currentItem+1}/${(activity as CameraPreviewActivity).imageUriList.size}"
            }
        }

        fragmentGalleryBinding.imageCounterTextview.text = "${fragmentGalleryBinding.photoViewPager.currentItem+1}/${(activity as CameraPreviewActivity).imageUriList.size}"
    }

    override fun onDestroyView() {
        _fragmentGalleryBinding = null
        super.onDestroyView()
    }

    companion object{
        const val TAG = "GalleryFragInfo"
    }

    inner class MediaPagerAdapter(fm: FragmentManager, private var mediaList: MutableList<TempImage>) :
        FragmentStateAdapter(fm, lifecycle) {
        override fun getItemCount(): Int = mediaList.size
        override fun createFragment(position: Int): Fragment = PhotoFragment.create(position)
        override fun getItemId(position: Int): Long {
            return mediaList[position].id.toLong()
        }
        override fun containsItem(itemId: Long): Boolean {
            return null != mediaList.firstOrNull { it.id.toLong() == itemId }
        }
        fun setMediaListAndNotify(mediaList: MutableList<TempImage>) {
            this.mediaList = mediaList
            notifyDataSetChanged()
        }
    }
}