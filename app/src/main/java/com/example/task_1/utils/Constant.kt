package com.example.task_1.utils

import android.graphics.Bitmap
import android.view.ActionMode

object Constant {
    const val SELECT_IMAGE_PATH = "SelectImage Path"
    const val SELECT_IMAGE_NAME = "SelectImage Name"

    const val SELECT_VIDEO_PATH = "Select Video Path"
    const val SELECT_VIDEO_NAME = "Select Video Name"

    var selectImagePosition = 0
    var selectVideoPosition = 0

    var currentImageBitmap = HashMap<Int, Bitmap>()

    var totalImagesToCopy = 0
    var currentFragment = 0

    var isItCancel = false

    //var currentImageCaptureSession = 0L

    var actionMode : ActionMode? = null
}