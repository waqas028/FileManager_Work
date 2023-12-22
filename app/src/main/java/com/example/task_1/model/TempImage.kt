package com.example.task_1.model

import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri

data class TempImage(
    var id: Int,
    val imageUri: Uri,
    val currentTimeSession: String,
    val rect: Rect? = null,
    val bitmap: Bitmap?
)