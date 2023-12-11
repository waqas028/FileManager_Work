package com.example.task_1.repository

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.task_1.model.Media
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class MainRepo @Inject constructor(@ApplicationContext private val context: Context) {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getAllVideoListFromStorage() : MutableList<Media>{
        val videoList = mutableListOf<Media>()
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
        )
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                videoList += Media(contentUri, name, size)
            }
        }
        Log.i("MainRepoInfo", "getAllVideoListFromStorage: ${videoList}")
        return videoList
    }

    @SuppressLint("Range")
    fun getAllImageListFromStorage() : MutableList<Media>{
        val imagesList = mutableListOf<Media>()
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.SIZE
        )
        val selection = "${MediaStore.Images.Media.MIME_TYPE} IN ('image/jpeg', 'image/png')"

        val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} ASC"

        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val dateModified = cursor.getInt(dateModifiedColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                imagesList += Media(contentUri, name ?: "", size)
            }
        }
        return imagesList
    }

    fun getSaveVideoImagesList() : Array<File> {
        var allFiles: Array<File> = emptyArray()
        val folder = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
             File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/TaskImages/")
        }else{
             File(Environment.getExternalStorageDirectory().toString() + "/TaskImages/")
        }
        if(folder.exists()){
            allFiles = folder.listFiles { dir, name ->
                name.endsWith(".jpg") || name.endsWith(
                    ".jpeg"
                ) || name.endsWith(".png") || name.endsWith(".mp4")
            }!!
            Log.i("MainRepoInfo", "getSaveVideoImagesList: ${allFiles.size}")
        }
        return allFiles
    }

    fun getCropImagesList(dirName:String) : Array<File> {
        var allFiles: Array<File> = emptyArray()
        val folder = File(Environment.getExternalStorageDirectory().toString() + "/CropDirectory/$dirName")
        if(folder.exists()){
            allFiles = folder.listFiles { dir, name ->
                if(dir.isDirectory){
                    dir.isDirectory
                }else{
                    name.endsWith(".jpg") || name.endsWith(
                        ".jpeg"
                    ) || name.endsWith(".png") || name.endsWith(".mp4")
                }
            }!!
            Log.i("MainRepoInfo", "getCropImagesList: ${allFiles[0].name}")
        }
        return allFiles
    }
}