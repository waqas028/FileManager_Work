package com.example.task_1.model

import android.net.Uri

data class Media(
    val id: Int,
    val uri: Uri,
    val name: String,
    val size: Int
)
