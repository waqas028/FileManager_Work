package com.example.task_1.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.ActionMode
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.model.Media
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import java.util.Locale


object Common {
    @SuppressLint("Range")
    fun getFilePathFromImageUri(context: Context, uri: Uri?): String? {
        val contentResolver = context.contentResolver
        val cursor = uri?.let { contentResolver.query(it, arrayOf(MediaStore.Images.Media.DATA), null, null, null) }
        if (cursor != null && cursor.moveToFirst()) {
            val filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            cursor.close()
            return filePath
        }
        return null
    }

    @SuppressLint("Range")
    fun getFilePathFromVideoUri(context: Context, uri: Uri): String? {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, arrayOf(MediaStore.Video.Media.DATA), null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
            cursor.close()
            return filePath
        }
        return null
    }

    @SuppressLint("Range")
    fun getImageContentUri(context: Context,imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + "=? ", arrayOf(filePath), null
        )
        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            cursor.close()
            Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id)
        } else {
            if (imageFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
                )
            } else {
                null
            }
        }
    }

    @SuppressLint("Range")
    fun getVideoContentUri(context: Context,imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Video.Media._ID),
            MediaStore.Video.Media.DATA + "=? ", arrayOf(filePath), null
        )
        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            cursor.close()
            Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "" + id)
        } else {
            if (imageFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Video.Media.DATA, filePath)
                context.contentResolver.insert(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values
                )
            } else {
                null
            }
        }
    }

    fun createVideoThumb(context: Context, uri: Uri): Bitmap? {
        try {
            val mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(context, uri)
            return mediaMetadataRetriever.frameAtTime
        } catch (ex: Exception) {
            Log.i("createVideoThumb", "createVideoThumb: $ex")
        }
        return null

    }

    fun copyFile(sourceFile: File) {
        val newFolder = File(Environment.getExternalStorageDirectory(), "TaskImages")
        if (!newFolder.exists()) {
            newFolder.mkdirs()
        }
        val source: FileChannel? = FileInputStream(sourceFile).channel
        val destination: FileChannel? = FileOutputStream(newFolder).channel
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size())
        }
        source?.close()
        destination?.close()
    }

    fun isImageFile(path: String): Boolean {
        val extension = path.substringAfterLast(".")
        return listOf("jpg", "jpeg", "png", "bmp").contains(extension.toLowerCase(Locale.ROOT))
    }

    fun deleteImages(context: Context,selectImageUri: Uri,imageFile:File) {
        //val imageFile = File(Common().getFilePathFromImageUri(context,selectImageUri)!!)
        Log.i("deleteImagesInfo", "deleteImages: $selectImageUri  //  ${imageFile.name}")
        val resolver = context.contentResolver
        val selectionArgsPdf = arrayOf(imageFile.name)
        try {
            resolver.delete(
                selectImageUri,
                MediaStore.Files.FileColumns.DISPLAY_NAME + "=?",
                selectionArgsPdf
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.i("deleteImagesInfo", "deleteFileUsingDisplayName: $ex")
        }
    }

    fun deleteMultipleFile(
        context: Context,
        selectImageUri: Uri,
        imageFile: File,
        mode: ActionMode?,
        progressListener: CopyImageProgressListener?,
        copiedImagesCount: Int,
        selectedItems: MutableList<Media>,
        onComplete: () -> Unit
    ) {
        Log.i("DeleteImagesInfo", "deleteImages: $selectImageUri  //  ${imageFile.name}")
        val resolver = context.contentResolver
        val selectionArgsPdf = arrayOf(imageFile.name)
        try {
            resolver.delete(
                selectImageUri,
                MediaStore.Files.FileColumns.DISPLAY_NAME + "=?",
                selectionArgsPdf
            )
            CoroutineScope(Dispatchers.Main).launch {
                progressListener?.onProgressUpdate(copiedImagesCount)
                Log.i("DeleteImagesInfo", "deleteFileUsingDisplayName: Image progress $copiedImagesCount")
                if(copiedImagesCount == selectedItems.size){
                    delay(1000)
                    onComplete()
                    mode?.finish() // Finish the ActionMode
                    Log.i("DeleteImagesInfo", "deleteFileUsingDisplayName: Image Delete $copiedImagesCount")
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            progressListener?.onProgressUpdate(copiedImagesCount)
            Log.i("DeleteImagesInfo", "deleteFileUsingDisplayName: $ex")
        }
    }

    fun copyFileToFolder(
        context: Context,
        sourceImagePath: String?,
        progressListener: CopyImageProgressListener?,
        mode: ActionMode?,
        copiedImagesCount: Int,
        selectedItems: MutableList<Media>,
        onComplete: () -> Unit
    ) {
        val sourceFile = File(sourceImagePath.orEmpty())
        val destinationDir = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TaskImages")
        }else{
            File(Environment.getExternalStorageDirectory(), "TaskImages")
        }
        if (!destinationDir.exists()) {
            destinationDir.mkdirs()
        }
        if (!sourceFile.exists() || !sourceFile.isFile) {
            Log.i("CopyImageInfo", "copyImageToFolder: Source file doesn't exist or is not a file")
            return
        }
        val imageName = if (selectedItems[0].name.endsWith(".mp4")){
            "TaskVideo_${System.currentTimeMillis()}.mp4"
        }else{
            "TaskImages_${sourceFile.name}"
        }
        val destinationFile = File(destinationDir, imageName)
        MediaScannerConnection.scanFile(context, arrayOf(destinationFile.path), null, null)
        try {
            val inputStream = FileInputStream(sourceFile)
            val outputStream = FileOutputStream(destinationFile,true)
            val buffer = ByteArray(1024)
            var length: Int
            var totalBytesCopied = 0L
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
                totalBytesCopied += length
            }
            inputStream.close()
            outputStream.close()
            CoroutineScope(Dispatchers.Main).launch {
                progressListener?.onProgressUpdate(copiedImagesCount)
                if(copiedImagesCount == selectedItems.size){
                    Log.i("CopyImageInfo", "copyImageFolder: Image copy $copiedImagesCount // ${selectedItems.size}")
                    onComplete()
                    mode?.finish() // Finish the ActionMode
                }
            }
            Log.i("CopyImageInfo", "copyImageToFolder: Image copied to destination folder: $copiedImagesCount  //  ${selectedItems.size}")
        } catch (e: Exception) {
            Log.i("CopyImageInfo", "copyImageToFolder: $e")
        }
    }


    fun copyImagesToFolder(
        sourceImagePath: String,
        progressListener: CopyImageProgressListener?,
        mode: ActionMode?,
        copiedImagesCount: Int,
        selectedItems: MutableList<Media>,
        onComplete: () -> Unit
    ) {
        val sourceDir = File(sourceImagePath)
        val targetFile = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TaskImages")
        }else{
            File(Environment.getExternalStorageDirectory(), "TaskImages")
        }
        if (!targetFile.exists()) {
            targetFile.mkdirs()
        }

        val fileName = sourceDir.name
        val extension = fileName.substringAfterLast(".") // Extract extension
        var targetFileName = fileName

        // Loop until a unique target file name is found
        var i = 1
        while (File(targetFile, targetFileName).exists()) {
            targetFileName = "${fileName.substringBeforeLast(".")}_$i.$extension"
            i++
        }

        try {
            // Try-with-resources for automatic stream closing
            FileInputStream(sourceDir).use { inputStream ->
                FileOutputStream(File(targetFile, targetFileName)).use { outputStream ->
                    val buffer = ByteArray(8192) // Increase buffer size for better performance
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        if (Constant.isItCancel) break // Check cancellation flag during loop
                        outputStream.write(buffer, 0, bytesRead)
                    }
                }
            }
            CoroutineScope(Dispatchers.Main).launch {
                progressListener?.onProgressUpdate(copiedImagesCount)
                if(copiedImagesCount == selectedItems.size){
                    Log.i("CopyImageInfo", "copyImageFolder: Image copy $copiedImagesCount // ${selectedItems.size}")
                    onComplete()
                    mode?.finish() // Finish the ActionMode
                }
            }
        } catch (e: Exception) {
            Log.e("CopyImageInfo", "Error copying $fileName: $e")
        }

        // Display the number of copied images
        Log.i("CopyImageInfo", "copyImagesToFolder: Copied $copiedImagesCount images")
    }
}