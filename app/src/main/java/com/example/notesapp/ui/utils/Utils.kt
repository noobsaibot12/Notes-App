package com.example.notesapp.ui.utils

import android.content.Context
import android.net.Uri
import java.io.File

fun saveImageToInternalStorage(context: Context, uri: Uri): String {

    val contentResolver = context.contentResolver
    val fileName = "${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    contentResolver.openInputStream(uri)?.use { inputStream ->

        file.outputStream().use { outputStream ->

            inputStream.copyTo(outputStream)

        }

    }

    return file.absolutePath
}