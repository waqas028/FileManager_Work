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
import androidx.core.net.toUri
import com.example.task_1.model.Media
import com.example.task_1.utils.Common
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
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                Log.i("MainRepoInfo", "getAllVideoListFromStorage: $id")
                videoList += Media(id.toInt(),contentUri, name, size)
            }
        }
        Log.i("MainRepoInfo", "getAllVideoListFromStorage: ${videoList.size}")
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
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                imagesList += Media(id.toInt(),contentUri, name ?: "", size)
            }
        }
        Log.i("MainRepoInfo", "getAllImageListFromStorage: ${imagesList.size}")
        return imagesList
    }

    fun getSaveVideoImagesList() : List<Media> {
        var imagesList: List<Media> = emptyList()
        val folder = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
             File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/TaskImages")
        }else{
             File(Environment.getExternalStorageDirectory().toString() + "/TaskImages")
        }
        if(folder.exists()){
            val allFiles = folder.listFiles { dir, name ->
                name.endsWith(".jpg") || name.endsWith(
                    ".jpeg"
                ) || name.endsWith(".png") || name.endsWith(".mp4")
            } ?: emptyArray()
            Log.i("MainRepoInfo", "getSaveVideoImagesList: AllFileList: ${allFiles.size}")
            imagesList = allFiles.map { file ->
                Media(1, file.toUri(),file.name,1)
            }
        }
        Log.i("MainRepoInfo", "getSaveVideoImagesList: AllImageList-> ${imagesList.size}")
        return imagesList
    }

    fun getCropImagesList(dirName:String) : List<Media> {
        var imagesList: List<Media> = emptyList()
        var allFiles: Array<File> = emptyArray()
        val targetDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Crop_Directory")
        val folder = File("$targetDirectory/$dirName")
        Log.i("MainRepoInfo", "getCropImagesList: folder:${folder.isDirectory}  // ${folder.listFiles()?.isEmpty() == true}  //   Size:${folder.listFiles()?.size}")
        if (folder.isDirectory && folder.listFiles()?.isEmpty() == true) {
            Log.i("MainRepoInfo", "getCropImagesList: Folder Delete:$folder  //  Size:${folder.listFiles()?.size}")
            folder.delete()
        }
        if(folder.exists()){
            allFiles = folder.listFiles { dir, name ->
                Log.i("MainRepoInfo", "getCropImagesList: name:$name")
                getCropImagesList(name)
                if(dir.isDirectory && dir.listFiles()?.isNotEmpty() == true){
                    dir.isDirectory
                }else{
                    name.endsWith(".jpg") || name.endsWith(
                        ".jpeg"
                    ) || name.endsWith(".png")
                }
            } ?: emptyArray()
            imagesList = allFiles.map { file ->
                Media(1, file.toUri(),file.name,1)
            }
        }
        Log.i("MainRepoInfo", "getCropImagesList: FinalList Return:${folder.exists()}  //  List-> ${imagesList.size}   //  Size:${allFiles.size}  // $allFiles")
        return imagesList
    }

    fun getNonEmptyDirectoriesWithFiles(directoryName: String): List<Media> {
        val nonEmptyDirectories = mutableListOf<Media>()
        val targetDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Crop_Directory")
        val folder = File("$targetDirectory/$directoryName")
        if (folder.isDirectory) {
            val subdirectories = folder.listFiles { file -> file.isDirectory } ?: emptyArray()
            Log.i("MainRepoInfo", "getNonEmptyDirectoriesWithFiles: Total Dir:${subdirectories.size}  //  ")
            for (directory in subdirectories) {
                val filesInDirectory = directory.listFiles() ?: arrayOf()
                Log.i("MainRepoInfo", "getNonEmptyDirectoriesWithFiles: File in Directory:${filesInDirectory.size}")
                if (filesInDirectory.isNotEmpty()) {
                    // Directory has content, add it to the list of non-empty directories
                    nonEmptyDirectories.add(Media(1,directory.toUri(),directory.name,1))
                } else {
                    // Directory is empty, delete it
                    directory.delete()
                }
            }
        }
        Log.i("MainRepoInfo", "getNonEmptyDirectoriesWithFiles: ${nonEmptyDirectories.size}")
        return nonEmptyDirectories
    }
}