package com.example.task_1.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.task_1.adapter.ImageSlideViewPagerAdapter
import com.example.task_1.databinding.ActivityImagePreviewBinding
import com.example.task_1.extension.deleteSingleFile
import com.example.task_1.extension.saveImageBitmapAboveAndroid10
import com.example.task_1.extension.saveImagesBitmapBelowAndroid10
import com.example.task_1.extension.showToast
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.model.Media
import com.example.task_1.utils.Constant
import com.example.task_1.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class ImagePreviewActivity : AppCompatActivity(), CopyImageProgressListener {
    private lateinit var binding: ActivityImagePreviewBinding
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var imageSlideViewPagerAdapter : ImageSlideViewPagerAdapter
    private var mediaImageList = emptyList<Media>()
    private var currentImagePosition = 0
    private lateinit var currentImageUri : Uri
    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val previousFragmentId = intent.getIntExtra("SavedImageFragId",1)
        val previousFragmentName = intent.getStringExtra("SavedImageFragName").orEmpty()
        val previousFragmentUri = intent.getStringExtra("SavedImageFragUri").orEmpty()
        val previousFragmentSize = intent.getIntExtra("SavedImageFragSize",1)
        val previousFragmentParentDirName = intent.getStringExtra("SavedImageFragDirName").orEmpty()
        Log.i(TAG, "onCreate: $previousFragmentName")
        if(previousFragmentName.isNotEmpty()){
            if(previousFragmentName.startsWith("Crop_")){
                coroutineScope.launch(Dispatchers.IO) { mainViewModel.getCropImagesList(previousFragmentParentDirName) }
            }else{
                coroutineScope.launch(Dispatchers.IO) { mainViewModel.getSaveVideoImagesList() }
            }
            binding.deleteButton.visibility = View.VISIBLE
        }else{
            coroutineScope.launch(Dispatchers.IO) { mainViewModel.getImagesList() }
            binding.saveButton.visibility = View.VISIBLE
        }

        //collect save image list
        coroutineScope.launch {
            delay(100)
            mainViewModel.savedVideoImageList.collect{savedFileList->
                Log.i(TAG, "collectSavedImageListFromStorage: Collect Saved Image Flow: ${savedFileList.size}  $savedFileList")
                mediaImageList = savedFileList.filter { file->
                    file.name.endsWith(".jpg") ||
                            file.name.endsWith(".jpeg") ||
                            file.name.endsWith(".png") }
                    .map { file->
                        Media(file.id, file.uri, file.name, 1)
                    }
                Log.i(TAG, "onCreate: ${mediaImageList.size}  $mediaImageList")
                currentImagePosition = mediaImageList.indexOf(Media(previousFragmentId,Uri.parse(previousFragmentUri),previousFragmentName,previousFragmentSize))
                withContext(Dispatchers.Main){
                    Log.i(TAG, "onCreate: Position->$currentImagePosition  //  mediaImageList->${mediaImageList.size}  mediaImageList->$mediaImageList")
                    imageSlideViewPagerAdapter = ImageSlideViewPagerAdapter(this@ImagePreviewActivity, mediaImageList)
                    binding.idViewPager.adapter = imageSlideViewPagerAdapter
                    binding.idViewPager.currentItem = currentImagePosition
                    updateImageCount(Constant.selectImagePosition)
                }
            }
        }

        /*coroutineScope.launch {
            mainViewModel.savedVideoImageListLivedata.observe(this@ImagePreviewActivity){
                Log.i(TAG, "onCreate: Collect Livedata: ${it.size}")
            }
        }*/

        //collect crop image list
        coroutineScope.launch {
            mainViewModel.savedCropImageList.collect{
                Log.i(TAG, "onCreate: Collect Crop Image-> ${it.size}")
                mediaImageList = it
                currentImagePosition = mediaImageList.indexOf(Media(previousFragmentId,Uri.parse(previousFragmentUri),previousFragmentName,previousFragmentSize))
                imageSlideViewPagerAdapter = ImageSlideViewPagerAdapter(this@ImagePreviewActivity, mediaImageList)
                binding.idViewPager.adapter = imageSlideViewPagerAdapter
                binding.idViewPager.currentItem = currentImagePosition
                updateImageCount(Constant.selectImagePosition)
            }
        }

        //collect image list from Storage
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
                Log.i(TAG, "onPageScrolled: $currentImageUri")
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
            lifecycleScope.launch(Dispatchers.IO) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    saveImageBitmapAboveAndroid10(mediaImageList[currentImagePosition].uri){
                        lifecycleScope.launch(Dispatchers.Main){
                            showToast(it)
                            val intent = Intent().apply {
                                putExtra("getSavedImageList", "UpdateSavedImagesList")
                            }
                            setResult(Activity.RESULT_OK, intent)
                        }
                    }
                }else{
                    saveImagesBitmapBelowAndroid10(mediaImageList[currentImagePosition].uri){
                        lifecycleScope.launch(Dispatchers.Main){
                            showToast(it)
                            val intent = Intent().apply {
                                putExtra("getSavedImageList", "UpdateSavedImagesList")
                            }
                            setResult(Activity.RESULT_OK, intent)
                        }
                    }
                }
            }
        }

        binding.deleteButton.setOnClickListener {
            Log.i(TAG, "onCreate: Delete Button Click->$currentImageUri")
            lifecycleScope.launch(Dispatchers.IO) {
                deleteSingleFile(currentImageUri){
                    lifecycleScope.launch(Dispatchers.Main) {
                        val intent = Intent().apply {
                            putExtra(Constant.PREVIOUS_DIR_NAME, previousFragmentParentDirName)
                        }
                        setResult(Activity.RESULT_OK, intent)
                        onBackPressed()
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateImageCount(position: Int) {
        val currentCount = position + 1
        binding.imageCounterTextview.text = "$currentCount / ${mediaImageList.size}"
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    companion object{
        const val TAG = "ImagePreviewInfo"
    }

    override fun onProgressUpdate(progress: Int) {}
    override fun onDeleteOrCopyItemListener() {}
}