package com.example.task_1.utils

import android.graphics.Bitmap
import android.view.ActionMode
import com.example.task_1.model.TempImage

object Constant {
    const val SELECT_VIDEO_PATH = "Select Video Path"
    const val SELECT_VIDEO_NAME = "Select Video Name"
    const val PREVIOUS_FRAG_NAME = "Previous Frag Name"
    const val SAVED_FRAG_NAME = "Saved Frag"

    var selectImagePosition = 0
    var selectVideoPosition = 0

    //use in slide adapter but now its not used
    var currentImageBitmap = HashMap<Int, Bitmap>()

    var totalImagesToCopy = 0

    var isItCancel = false
    var actionMode : ActionMode? = null

    var cropImageList : MutableMap<Int, TempImage> = mutableMapOf()

}