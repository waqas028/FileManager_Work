package com.example.task_1.ui.activity

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.task_1.adapter.ImageSlideViewPagerAdapter
import com.example.task_1.databinding.ActivityImagePreviewBinding
import com.example.task_1.extension.deleteSingleFile
import com.example.task_1.extension.saveImagesBitmap
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.model.Media
import com.example.task_1.utils.Constant
import com.example.task_1.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class ImagePreviewActivity : AppCompatActivity(), CopyImageProgressListener {
    private lateinit var binding: ActivityImagePreviewBinding
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var imageSlideViewPagerAdapter : ImageSlideViewPagerAdapter
    private var mediaImageList = emptyList<Media>()
    private var currentImagePosition = 0
    private lateinit var currentImageUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        lifecycleScope.launch(Dispatchers.IO) { mainViewModel.getImagesList() }
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.imageList.collect{
                Log.i(TAG, "collectImagesList: $it")
                mediaImageList = it
                withContext(Dispatchers.Main){
                    imageSlideViewPagerAdapter = ImageSlideViewPagerAdapter(this@ImagePreviewActivity, mediaImageList)
                    binding.idViewPager.adapter = imageSlideViewPagerAdapter
                    binding.idViewPager.currentItem = Constant.selectImagePosition
                    updateImageCount(Constant.selectImagePosition)
                }
            }
        }

        binding.idViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Log.i(TAG, "onPageScrolled: $position")
                updateImageCount(position)
                currentImagePosition = position
                currentImageUri = mediaImageList[position].uri
                binding.imageNameTextview.text = mediaImageList[position].name
            }
            override fun onPageSelected(position: Int) {}
        })

        binding.previousButton.setOnClickListener {
            val currentItem = binding.idViewPager.currentItem
            if (currentItem > 0) {
                binding.idViewPager.currentItem = currentItem - 1
            }
        }

        binding.nextButton.setOnClickListener {
            val currentItem = binding.idViewPager.currentItem
            if (currentItem < mediaImageList.size - 1) {
                binding.idViewPager.currentItem = currentItem + 1
            }
        }

        binding.saveButton.setOnClickListener {
            val targetDirectory = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            }else{
                Environment.getExternalStorageDirectory()
            }
            saveImagesBitmap(Constant.currentImageBitmap[currentImagePosition],File(targetDirectory, "TaskImages"))
            Log.i(TAG, "onCreate: $targetDirectory")
        }

        binding.deleteButton.setOnClickListener {
            deleteSingleFile(currentImageUri)
        }
    }

    private fun updateImageCount(position: Int) {
        val currentCount = position + 1
        binding.imageCounterTextview.text = "$currentCount / ${mediaImageList.size}"
    }

    companion object{
        const val TAG = "ImagePreviewInfo"
    }

    override fun onProgressUpdate(progress: Int) {}
}