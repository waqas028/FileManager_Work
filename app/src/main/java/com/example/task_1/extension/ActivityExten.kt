package com.example.task_1.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.task_1.ui.activity.MainActivity
import com.example.task_1.utils.Common
import com.example.task_1.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


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

fun Activity.saveImagesBitmap(bitmap: Bitmap?,directory:File){
    try {
        Log.i("SavedImageInfo", "saveImagesBitmap:")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val timestamp = System.currentTimeMillis()
        val fileName = "Image_${timestamp}.jpg"
        val outputFile = File(directory, fileName)
        val outputStream = FileOutputStream(outputFile)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        MediaScannerConnection.scanFile(this, arrayOf(outputFile.path), null, null)
        showToast("Image Saved Successfully!")
    } catch (e: Exception) {
        showToast(e.message.toString())
        Log.i("SavedImageInfo", "saveImagesBitmap: $e")
    }
}

fun cropImage(bitmap: Bitmap, originalImagePath: String?){
    try {
        val file = File(originalImagePath.orEmpty())
        Log.i("CropImageInfo", "cropImage: ${file.absolutePath}  //  $originalImagePath")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // Save the cropped bitmap as a JPEG file
        outputStream.flush()
        outputStream.close()
        Log.i("CropImageInfo", "cropImage: image saved")
    }catch (e:Exception){
        Log.i("CropImageInfo", "cropImage: $e")
    }
}

 @RequiresApi(Build.VERSION_CODES.O)
 fun Activity.showDialogue(progress: Int){
    val progressDialog = ProgressDialog(this)
    progressDialog.max = Constant.totalImagesToCopy // Progress Dialog Max Value
    progressDialog.setMessage("Loading...") // Setting Message
    progressDialog.setTitle("ProgressDialog") // Setting Title
    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL) // Progress Dialog Style Horizontal
    progressDialog.setButton("Cancel"
    ) { _, _ ->
        Constant.isItCancel = true
        CoroutineScope(Dispatchers.IO).launch {
            //(this@showDialogue as MainActivity).mainViewModel.getImagesList()
        }
        progressDialog.dismiss()
    }
    progressDialog.show() // Display Progress Dialog
    progressDialog.setCancelable(false)
}

fun Activity.deleteSingleFile(selectImageUri: Uri) {
    val imageFile = File(Common.getFilePathFromImageUri(this,selectImageUri)!!)
    Log.i("DeleteSingleFile", "deleteImages: $selectImageUri  //  ${imageFile.name}")
    val resolver = contentResolver
    val selectionArgsPdf = arrayOf(imageFile.name)
    try {
        resolver.delete(
            selectImageUri,
            MediaStore.Files.FileColumns.DISPLAY_NAME + "=?",
            selectionArgsPdf
        )
        onBackPressed()
    } catch (ex: Exception) {
        ex.printStackTrace()
        Log.i("DeleteSingleFile", "deleteFileUsingDisplayName: $ex")
    }
}