package com.example.task_1.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.task_1.databinding.ActivityVideoPreviewBinding
import com.example.task_1.extension.deleteSingleFile
import com.example.task_1.extension.saveVideo
import com.example.task_1.extension.showToast
import com.example.task_1.utils.Common
import com.example.task_1.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoPreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoPreviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectVideoUri = intent.extras?.get(Constant.SELECT_VIDEO_PATH) as Uri
        val selectVideoName = intent.getStringExtra(Constant.SELECT_VIDEO_NAME)
        val previousFragmentName = intent.getStringExtra(Constant.PREVIOUS_FRAG_NAME).orEmpty()
        Log.i(TAG, "onCreate: $selectVideoUri  //  $selectVideoName  //  $previousFragmentName")
        if(previousFragmentName == Constant.SAVED_FRAG_NAME){
            binding.deleteButton.visibility = View.VISIBLE
        }else{
            binding.saveButton.visibility = View.VISIBLE
        }

        binding.videoNameTextview.text = selectVideoName
        binding.videoView.setVideoURI(selectVideoUri)
        binding.videoView.requestFocus()
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)
        mediaController.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(mediaController)

        binding.saveButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                saveVideo(Common.getFilePathFromVideoUri(this@VideoPreviewActivity,selectVideoUri)!!){
                    lifecycleScope.launch(Dispatchers.Main){
                        showToast(it)
                        val intent = Intent().apply {
                            putExtra("getSavedVideoList", "UpdateVideoList")
                        }
                        setResult(Activity.RESULT_OK, intent)
                    }
                }
            }
        }

        binding.deleteButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                deleteSingleFile(selectVideoUri){
                    lifecycleScope.launch(Dispatchers.Main) {
                        showToast(it)
                        val intent = Intent().apply {
                            putExtra("key", "DeleteSingleVideo")
                        }
                        setResult(Activity.RESULT_OK, intent)
                        onBackPressed()
                    }
                }
            }
        }

        binding.backButtonImageview.setOnClickListener{
            onBackPressed()
        }
    }

    companion object{
        const val TAG = "VideoPreviewInfo"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.stopPlayback()
    }
}