package com.example.task_1.ui.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task_1.R
import com.example.task_1.databinding.FragmentGalleryBinding
import com.example.task_1.extension.cropImage
import com.example.task_1.model.TempImage
import com.example.task_1.utils.Constant
import com.example.task_1.utils.MediaStoreUtils
import com.example.task_1.utils.padWithDisplayCutout
import com.example.task_1.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryFragment : Fragment() {
    private var _fragmentGalleryBinding: FragmentGalleryBinding? = null
    private val fragmentGalleryBinding get() = _fragmentGalleryBinding!!
    private val mainViewModel : MainViewModel by activityViewModels()
    private var imageUriList = mutableListOf<TempImage>()
    private lateinit var mediaPagerAdapter: MediaPagerAdapter
    private var mediaList: MutableMap<Int, TempImage> = mutableMapOf()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _fragmentGalleryBinding = FragmentGalleryBinding.inflate(inflater, container, false)
        return fragmentGalleryBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            // Get images this app has access to from MediaStore
            mainViewModel.cameraTempImageList.collect{
                Log.i(TAG, "onCreate: ${it.size}  // $it")
                imageUriList.addAll(it)
                fragmentGalleryBinding.imageCounterTextview.text = "${fragmentGalleryBinding.photoViewPager.currentItem+1}/${it.size}"
                /*for(i in it.indices){
                    mediaList[i] = TempImage(
                        i,
                        MediaStoreUtils(requireContext()).getImages()[i].uri,
                        "",
                        Rect(0,0,1512,2688)
                    )
                    //mediaList.add(TempImage(i,MediaStoreUtils(requireContext()).getImages()[i].uri,""))

                }*/
                withContext(Dispatchers.Main){
                    (fragmentGalleryBinding.photoViewPager.adapter as MediaPagerAdapter)
                        .setMediaListAndNotify(imageUriList)
                }
            }
        }


        // Populate the ViewPager and implement a cache of two media items
        mediaPagerAdapter = MediaPagerAdapter(childFragmentManager, imageUriList)
        fragmentGalleryBinding.photoViewPager.apply {
            offscreenPageLimit = 15
            adapter = mediaPagerAdapter
        }

        // Make sure that the cutout "safe area" avoids the screen notch if any
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Use extension method to pad "inside" view containing UI using display cutout's bounds
            fragmentGalleryBinding.cutoutSafeArea.padWithDisplayCutout()
        }

        // Handle back button press
        fragmentGalleryBinding.backButton.setOnClickListener {
            mainViewModel.cameraTempImageList.value = mutableListOf()
            Constant.cropImageList.clear()
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigateUp()
        }

        // Handle backImageButton press
        fragmentGalleryBinding.backImageButton.setOnClickListener {
            if (fragmentGalleryBinding.photoViewPager.currentItem > 0) {
                fragmentGalleryBinding.photoViewPager.currentItem = fragmentGalleryBinding.photoViewPager.currentItem - 1
                fragmentGalleryBinding.imageCounterTextview.text = "${fragmentGalleryBinding.photoViewPager.currentItem+1}/${imageUriList.size}"
            }
        }

        // Handle nextImage button press
        fragmentGalleryBinding.nextImageButton.setOnClickListener {
            if (fragmentGalleryBinding.photoViewPager.currentItem < mediaPagerAdapter.itemCount - 1) {
                fragmentGalleryBinding.photoViewPager.currentItem = fragmentGalleryBinding.photoViewPager.currentItem + 1
                fragmentGalleryBinding.imageCounterTextview.text = "${fragmentGalleryBinding.photoViewPager.currentItem+1}/${imageUriList.size}"
            }
        }

        //handle Done Button work
        fragmentGalleryBinding.doneButton.setOnClickListener{
            showDialogue()
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                var cropImageCopyCount = 0
                Constant.cropImageList.forEach{
                    cropImageCopyCount++
                    Log.i(TAG, "onViewCreated: $cropImageCopyCount  //   $it")
                    /*val inputStream = requireActivity().contentResolver.openInputStream(it.value.imageUri)
                    val originalBitmap = BitmapFactory.decodeStream(inputStream)
                    Log.i(TAG, "onViewCreated: Rect Width-> ${it.value.rect?.width()}  height-> ${it.value.rect?.height()!!}   o_width-> ${originalBitmap.width}   o_height-> ${originalBitmap.height}")
                    val originalCropRect = it.value.rect
                    val height = if(it.value.rect?.height()!! > originalBitmap.height){
                        originalBitmap.height
                    } else {
                        originalCropRect?.height()
                    }
                    val width = if(it.value.rect?.width()!! > originalBitmap.width){
                        originalBitmap.width
                    } else {
                        originalCropRect?.width()
                    }
                    Log.i(TAG, "onViewCreated: Width-> $width    /   height->$height")
                    val croppedBitmap = Bitmap.createBitmap(
                        originalBitmap,
                        originalCropRect?.left!!,
                        originalCropRect.top,
                        width!!,
                        height!!,null,false
                    )*/
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) { progressDialog.progress = cropImageCopyCount }
                    cropImage(it.value.bitmap!!,it.value.currentTimeSession){
                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
                            progressDialog.progress = cropImageCopyCount
                            Log.i(TAG, "onViewCreated: dialogue $cropImageCopyCount")
                            if(it.value.id == Constant.cropImageList.size) {
                                progressDialog.dismiss()
                                Log.i(TAG, "onViewCreated: dialogue dismiss")
                                Navigation.findNavController(requireActivity(), R.id.fragment_container).navigateUp()
                            }
                        }
                        Log.i(TAG, "onViewCreated: image Crop Saved ${it.value.id}  $cropImageCopyCount  ${Constant.cropImageList.size}")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _fragmentGalleryBinding = null
        Constant.cropImageList.clear()
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
            return mediaList[position].id.toLong() ?: 0L
        }
        override fun containsItem(itemId: Long): Boolean {
            return true
        }
        fun setMediaListAndNotify(mediaList: MutableList<TempImage>) {
            this.mediaList = mediaList
            notifyDataSetChanged()
        }
    }

    private fun showDialogue() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.apply {
            max = Constant.cropImageList.size // Progress Dialog Max Value
            setMessage("Loading...") // Setting Message
            setTitle("ProgressDialog") // Setting Title
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL) // Progress Dialog Style Horizontal
            show() // Display Progress Dialog
            setCancelable(false)
        }
    }
}