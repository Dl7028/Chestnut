package com.yks.chestnutyun.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/12/3 18:11
 */
/**
 * 文件Uri转路径（兼容各品牌手机）
 */
object PathUtils {
    /**
     * android7.0以上处理方法
     */
    private fun getFilePathForN(context: Context, uri: Uri): String? {
        try {
            val returnCursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
            val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            val name: String = returnCursor.getString(nameIndex)
            val file = File(context.filesDir, name)
            val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = inputStream.available()
            val bufferSize = Math.min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream.read(buffers).also { read = it } != -1) {
                outputStream.write(buffers, 0, read)
            }
            returnCursor.close()
            inputStream.close()
            outputStream.close()
            return file.path
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 全平台处理方法
     */
    @Throws(Exception::class)
    fun getPath(context: Context, uri: Uri): String? {
        val isN = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
        if (isN) {
            return getFilePathForN(context, uri)
        }

        // DocumentProvider
        if ( DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return context.getExternalFilesDir(null)?.path.toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri: Uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), id.toLong()
                )
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    /**
     * 获取此Uri的数据列的值。这对于MediaStore uri和其他基于文件的内容提供程序非常有用。
     */
    private fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val column_index: Int =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)
                return cursor.getString(column_index)
            }
        } catch (e: IllegalArgumentException) {
            //do nothing
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }
}