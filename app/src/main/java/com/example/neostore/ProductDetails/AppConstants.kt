package com.example.neostore.ProductDetails

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import okio.Okio
import java.io.File
import java.io.IOException
import java.net.URISyntaxException

object AppConstants {
    val MEDIA_FOLDER = Environment.getExternalStorageDirectory()
        .toString() + File.separator + "NeoStore_Downloaded_Images"
    val DOWNLOADS_FOLDER = MEDIA_FOLDER + File.separator + "Downloads/"
}

