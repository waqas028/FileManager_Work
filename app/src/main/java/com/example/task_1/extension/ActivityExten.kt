package com.example.task_1.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.task_1.ui.activity.VideoPreviewActivity
import com.example.task_1.utils.Common
import com.example.task_1.utils.Constant
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

@SuppressLint("Range")
fun Activity.getFilePathFromImageUri(uri: Uri): String? {
    val contentResolver = contentResolver
    val cursor = contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
    if (cursor != null && cursor.moveToFirst()) {
        val filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()
        return filePath
    }
    return null
}

@SuppressLint("Range")
fun Activity.getFilePathFromVideoUri(uri: Uri): String? {
    val contentResolver = contentResolver
    val cursor = contentResolver.query(uri, arrayOf(MediaStore.Video.Media.DATA), null, null, null)
    if (cursor != null && cursor.moveToFirst()) {
        val filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
        cursor.close()
        return filePath
    }
    return null
}

fun Activity.uriToBitmap(uri:Uri): Bitmap?{
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream.close()
    return bitmap
}

fun Activity.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("Range")
fun Activity.getImageContentUri(imageFile: File): Uri? {
    val filePath = imageFile.absolutePath
    val cursor = contentResolver.query(
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
            contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )
        } else {
            null
        }
    }
}

fun Activity.saveImagesBitmapBelowAndroid10(imageUri: Uri, onSaveImageComplete : (String) -> Unit){
    Log.i("SavedImageInfo", "saveImagesBitmapBelowAndroid10: $imageUri")
    try {
        /*val inputStream = contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val directory = File(Environment.getExternalStorageDirectory().toString() + "/TaskImages")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val timestamp = System.currentTimeMillis()
        val fileName = "Save_${timestamp}.jpg"
        val outputFile = File(directory, fileName)
        val outputStream = FileOutputStream(outputFile)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        outputStream.close()*/
        val sourceDir = File(Common.getFilePathFromImageUri(this,imageUri).orEmpty())
        val directory = File(Environment.getExternalStorageDirectory().toString() + "/TaskImages")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val fileName = "Save_${System.currentTimeMillis()}.jpg"
        val extension = fileName.substringAfterLast(".") // Extract extension
        var targetFileName = fileName

        // Loop until a unique target file name is found
        var i = 1
        while (File(directory, targetFileName).exists()) {
            targetFileName = "${fileName.substringBeforeLast(".")}_$i.$extension"
            i++
        }
        FileInputStream(sourceDir).use { inputStream ->
            FileOutputStream(File(directory, targetFileName)).use { outputStream ->
                val buffer = ByteArray(8192) // Increase buffer size for better performance
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    if (Constant.isItCancel) break // Check cancellation flag during loop
                    outputStream.write(buffer, 0, bytesRead)
                }
            }
        }
        //MediaScannerConnection.scanFile(this, arrayOf(outputFile.path), null, null)
        onSaveImageComplete("Image Saved Successfully!")
        Log.i("SavedImageInfo", "saveImagesBitmapBelowAndroid10: Image Saved Successfully!")
    } catch (e: Exception) {
        onSaveImageComplete(e.message.toString())
        Log.i("SavedImageInfo", "saveImagesBitmapBelowAndroid10: $e")
    }
}

fun Activity.saveImageBitmapAboveAndroid10(imageUri : Uri,onSaveImageComplete : (String) -> Unit){
    val inputStream = contentResolver.openInputStream(imageUri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    val resolver = contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "Save_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/TaskImages") // Optional directory
    }
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        val outputStream = resolver.openOutputStream(it)
        outputStream?.use {output->
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, output)
        }
    }
    onSaveImageComplete("Image Saved Successfully!")
}

fun cropImage(bitmap: Bitmap, currentTimeSession: String?, onCropComplete: () -> Unit){
    try {
        val dirName = "Crop_Directory/Crop_${currentTimeSession}"
        val filename = "Crop_${System.currentTimeMillis()}.jpg"
        val rootDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dirName)
        if (!rootDirectory.exists()) {
            rootDirectory.mkdirs()
        }
        val imageFile = File(rootDirectory, filename)
        Log.i("CropImageInfo", "cropImage: ${imageFile.absolutePath} ")
        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // Save the cropped bitmap as a JPEG file
        outputStream.flush()
        outputStream.close()
        Log.i("CropImageInfo", "cropImage: image saved")
        onCropComplete()
    }catch (e:Exception){
        Log.i("CropImageInfo", "cropImage: $e")
    }
}

@SuppressLint("Range")
fun Activity.deleteSingleFile(selectImageUri: Uri, onDeleteComplete:(String) -> Unit) {
    val imageFile = File(selectImageUri.path.orEmpty())
    Log.i("DeleteSingleFile", "deleteImages: $selectImageUri  //  ${imageFile.name}")
    try {
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.Q){
            if(imageFile.name.endsWith(".mp4")){
                val projection = arrayOf(MediaStore.Video.Media._ID)
                val selection = MediaStore.Video.Media.DATA + " = ?"
                val selectionArgs = arrayOf(selectImageUri.path)
                val cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null)

                if (cursor != null && cursor.moveToFirst()) {
                    val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                    contentResolver.delete(contentUri, null, null)
                    cursor.close()
                }
            }else{
                val projection = arrayOf(MediaStore.Images.Media._ID)
                val selection = MediaStore.Images.Media.DATA + " = ?"
                val selectionArgs = arrayOf(selectImageUri.path)
                val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null)

                if (cursor != null && cursor.moveToFirst()) {
                    val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
                    val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    contentResolver.delete(contentUri, null, null)
                    cursor.close()
                }
            }
        }else{
            if(imageFile.exists()){
                imageFile.delete()
            }
        }
        onDeleteComplete("Delete Video Successfully!")
    } catch (ex: Exception) {
        ex.printStackTrace()
        Log.i("DeleteSingleFile", "deleteFileUsingDisplayName: $ex")
    }
}

fun Activity.saveVideo(inputPath: String, onSaveVideoComplete: (String) -> Unit) {
    Log.i("VideoSavedInfo", "saveVideo: $inputPath")
    val folder = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/TaskImages")
    }else{
        File(Environment.getExternalStorageDirectory().toString() + "/TaskImages")
    }
    if (!folder.exists()) { // Check if parent directory exists
        folder.mkdirs() // Create parent directory if needed
    }
    val destinationFile = File(folder, "Video_${System.currentTimeMillis()}.mp4")
    try {
        val sourceStream = FileInputStream(File(inputPath)).channel
        val destinationStream = FileOutputStream(destinationFile).channel
        destinationStream.transferFrom(sourceStream, 0, sourceStream.size())
        sourceStream.close()
        destinationStream.close()
        onSaveVideoComplete("Video Saved Successfully")
    } catch (e: IOException) {
        onSaveVideoComplete(e.message.toString())
        Log.i("VideoSavedInfo", "saveVideo: $e")
    }
}