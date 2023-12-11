package com.example.task_1.ui.activity

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.task_1.databinding.ActivityVideoPreviewBinding
import com.example.task_1.extension.getFilePathFromVideoUri
import com.example.task_1.extension.showToast
import com.example.task_1.utils.Common
import com.example.task_1.utils.Constant
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class VideoPreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoPreviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectVideoUri = intent.extras?.get(Constant.SELECT_VIDEO_PATH) as Uri
        val selectVideoName = intent.getStringExtra(Constant.SELECT_VIDEO_NAME)
        binding.videoNameTextview.text = selectVideoName
        Log.i(TAG, "onCreate: $selectVideoUri  //  $selectVideoName")
        binding.videoView.setVideoURI(selectVideoUri)
        binding.videoView.requestFocus()

        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)
        mediaController.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(mediaController)

        binding.saveButton.setOnClickListener {
            saveVideo(Common.getFilePathFromImageUri(this,selectVideoUri)!!)
        }

        binding.deleteButton.setOnClickListener {
            deleteVideo(selectVideoUri)
        }
    }

    private fun saveVideo(inputPath: String) {
        val destinationFile = File(Environment.getExternalStorageDirectory(), "TaskImages/Video_${System.currentTimeMillis()}.mp4")
        if (!destinationFile.parentFile.exists()) { // Check if parent directory exists
            destinationFile.parentFile.mkdirs() // Create parent directory if needed
        }
        try {
            val sourceStream = FileInputStream(File(inputPath)).channel
            val destinationStream = FileOutputStream(destinationFile).channel
            destinationStream.transferFrom(sourceStream, 0, sourceStream.size())
            sourceStream.close()
            destinationStream.close()
            Log.i(TAG, "saveVideo: Done")
            showToast("Video Saved Successfully")
        } catch (e: IOException) {
            Log.i(TAG, "saveVideo: $e")
            showToast(e.message.toString())
        }
    }

    private fun deleteVideo(selectVideoUri: Uri) {
        val imageFile = File(getFilePathFromVideoUri(selectVideoUri)!!)
        Log.i(TAG, "deleteImages: $selectVideoUri  //  ${imageFile.name}")
        val resolver = contentResolver
        val selectionArgsPdf = arrayOf(imageFile.name)
        try {
            resolver.delete(
                selectVideoUri,
                MediaStore.Files.FileColumns.DISPLAY_NAME + "=?",
                selectionArgsPdf
            )
            onBackPressed()
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.i(TAG, "deleteFileUsingDisplayName: $ex")
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