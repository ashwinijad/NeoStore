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

class FileUtilss private constructor() {
    companion object {
        fun createDirectory(path: String?): Boolean {
            val file = File(path)
            return if (!file.exists()) {
                if (file.mkdirs()) {
                    true
                } else {
                    false
                }
            } else {
                println("directory already exists.")
                true
            }
        }

        fun createFile(path: String?): Boolean {
            var status = false
            status = if (path != null) {
                val file = File(path)
                if (!file.exists()) {
                    try {
                        file.createNewFile()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        false
                    }
                } else {
                    true
                }
            } else {
                println("null file path found")
                false
            }
            return status
        }

        /**
         * Delete folder content. Deletes the files and directories inside the given
         * directory.
         *
         * @param path the directory path
         */
        fun deleteFolderContent(path: String?) {
            val dir = File(path)
            if (dir.isDirectory) {
                val children = dir.list()
                for (i in children.indices) {
                    File(dir, children[i]).delete()
                }
            }
        }

        fun deleteFolderContentRecursive(path: String?) {
            val dir = File(path)
            if (dir.isDirectory) {
                val children = dir.list()
                for (i in children.indices) {
                    val file = File(dir, children[i])
                    if (file.isDirectory) {
                        deleteFolderContentRecursive(file.absolutePath)
                    } else {
                        file.delete()
                    }
                }
            }
        }

        fun deleteDirectory(directory: String?): Boolean {
            val path = File(directory)
            if (path.exists()) {
                val files = path.listFiles() ?: return true
                for (i in files.indices) {
                    if (files[i].isDirectory) {
                        deleteDirectory(files[i].absolutePath)
                    } else {
                        files[i].delete()
                    }
                }
            }
            return path.delete()
        }

        fun deleteFile(filePath: String?): Boolean {
            val file = File(filePath)
            return if (file.exists()) {
                file.delete()
            } else false
        }

        fun deleteFileByUri(uri: Uri?, contentResolver: ContentResolver) {
            contentResolver.delete(uri!!, null, null)
        }

        fun getUri(file: File?): Uri? {
            return if (file != null) {
                Uri.fromFile(file)
            } else null
        }

        fun isMediaUri(uri: Uri): Boolean {
            return "media".equals(uri.authority, ignoreCase = true)
        }

        fun isLocal(url: String?): Boolean {
            return if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
                true
            } else false
        }

        fun getExtension(uri: String?): String? {
            if (uri == null) {
                return null
            }
            val dot = uri.lastIndexOf(".")
            return if (dot >= 0) {
                uri.substring(dot)
            } else {
                // No extension.
                ""
            }
        }

        fun getMimeType(file: File): String? {
            val extension = getExtension(file.name)
            return if (extension!!.length > 0) MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                extension.substring(1)) else "application/octet-stream"
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Throws(URISyntaxException::class)
        fun getPath(context: Context, uri: Uri): String? {
            var uri = uri
            val needToCheckUri = Build.VERSION.SDK_INT >= 19
            var selection: String? = null
            var selectionArgs: Array<String>? = null
            // Uri is different in versions after KITKAT (Android 4.4), we need to
            // deal with different Uris.
            if (needToCheckUri && DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id))
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    if ("image" == type) {
                        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    selection = "_id=?"
                    selectionArgs = arrayOf(split[1])
                }
            }
            if ("content".equals(uri.scheme, ignoreCase = true)) {
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                var cursor: Cursor? = null
                try {
                    cursor = context.contentResolver.query(uri,
                        projection,
                        selection,
                        selectionArgs,
                        null)
                    val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index)
                    }
                } catch (e: Exception) {
                }
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
            return null
        }

        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        fun copyFile(sourceFile: File?, destinationFile: File?) {
            try {
                val bufferedSink = Okio.buffer(Okio.sink(destinationFile))
                bufferedSink.writeAll(Okio.source(sourceFile))
                bufferedSink.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    init {
        throw AssertionError()
    }
}