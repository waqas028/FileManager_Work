package com.example.task_1.ui.activity

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.task_1.adapter.ImageSlideViewPagerAdapter
import com.example.task_1.adapter.SlidePhotoAdapter
import com.example.task_1.databinding.ActivityImagePreviewBinding
import com.example.task_1.extension.deleteSingleFile
import com.example.task_1.extension.getFilePathFromImageUri
import com.example.task_1.extension.saveImagesBitmap
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.model.Media
import com.example.task_1.utils.Common
import com.example.task_1.utils.Constant
import com.example.task_1.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class ImagePreviewActivity : AppCompatActivity(), CopyImageProgressListener {
    private lateinit var binding: ActivityImagePreviewBinding
    private val mainViewModel : MainViewModel by viewModels()
    @Inject
    lateinit var slidePhotoAdapter : SlidePhotoAdapter
    private lateinit var imageSlideViewPagerAdapter : ImageSlideViewPagerAdapter
    private var imageList = emptyList<Media>()
    private var currentImagePosition = 0
    private lateinit var currentImageUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        lifecycleScope.launch(Dispatchers.IO) { mainViewModel.getImagesList() }
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val targetDirectory = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            }else{
                Environment.getExternalStorageDirectory()
            }
            saveImagesBitmap(Constant.currentImageBitmap1[currentImagePosition],File(targetDirectory, "TaskImages"))
            Log.i(TAG, "onCreate: $targetDirectory")
        }

        binding.deleteButton.setOnClickListener {
            deleteSingleFile(currentImageUri)
        }

        /*binding.idViewPager.apply {
            adapter = imageSlideViewPagerAdapter
            offscreenPageLimit = 3
        }*/

        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.imageList.collect{
                Log.i(TAG, "collectImagesList: $it")
                imageList = it
                withContext(Dispatchers.Main){
                    imageSlideViewPagerAdapter = ImageSlideViewPagerAdapter(this@ImagePreviewActivity, imageList)
                    binding.idViewPager.adapter = imageSlideViewPagerAdapter
                    //imageSlideViewPagerAdapter.differ.submitList(imageList)
                    binding.idViewPager.currentItem = Constant.selectImagePosition
                    binding.imageCounterTextview.text = "${binding.idViewPager.currentItem+1}/${imageList.size}"
                }
            }
        }

        binding.idViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Log.i(TAG, "onPageScrolled: $position")
                binding.numberOfImagesTextview.text = "${position+1}/${imageSlideViewPagerAdapter.count}"
                binding.imageNameTextview.text = imageList[position].name
                currentImagePosition = position
                currentImageUri = imageList[position].uri
            }
            override fun onPageSelected(position: Int) {}
        })

        // Handle backImageButton press
        binding.backImageButton.setOnClickListener {
            if (binding.idViewPager.currentItem > 0) {
                binding.idViewPager.currentItem = binding.idViewPager.currentItem - 1
                binding.imageCounterTextview.text = "${binding.idViewPager.currentItem+1}/${imageList.size}"
            }
        }

        // Handle nextImage button press
        binding.nextImageButton.setOnClickListener {
            if (binding.idViewPager.currentItem < imageSlideViewPagerAdapter.count - 1) {
                binding.idViewPager.currentItem = binding.idViewPager.currentItem + 1
                binding.imageCounterTextview.text = "${binding.idViewPager.currentItem+1}/${imageList.size}"
            }
        }
    }

    companion object{
        const val TAG = "ImagePreviewInfo"
    }

    override fun onProgressUpdate(progress: Int) {}
}