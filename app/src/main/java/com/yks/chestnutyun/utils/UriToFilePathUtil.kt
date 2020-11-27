package com.yks.chestnutyun.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import kotlin.math.roundToInt
import kotlin.random.Random


/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/27 15:55
 */
object UriToFilePathUtil {



    fun getFilePathByUri(context: Context, uri: Uri): String? {
        var path: String? = null
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                // ExternalStorageProvider
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    path = Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    return path
                }
            } else if (isDownloadsDocument(uri)) {
                // DownloadsProvider
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri: Uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id)
                )
                path = getDataColumn(context, contentUri, null, null)
                return path
            } else if (isMediaDocument(uri)) {
                // MediaProvider
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
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
                val selectionArgs = arrayOf(split[1])
                path = getDataColumn(context, contentUri, selection, selectionArgs)
                return path
            }
        }
        return null
    }

    private fun getDataColumn(
        context: Context,
        uri: Uri?,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor =
                uri?.let { context.contentResolver.query(
                    it,
                    projection,
                    selection,
                    selectionArgs,
                    null
                ) }
            if (cursor != null && cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.getAuthority()
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.getAuthority()
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.getAuthority()
    }


    /**
     *  android10 以后，无法拿到外部文件的绝对路径，需要把共享文件复制到沙盒目录下，再进行文件操作
     *  沙盒里的文件（file开头），可以直接转成File 使用，共享文件（content开头）如果要操作，需要先复制到沙盒目录下
     *
     * @param context
     * @param uri
     * @return 文件
     */
    @RequiresApi(Build.VERSION_CODES.Q)
     fun uriToFileQ(context: Context, uri: Uri): File? =
        if (uri.scheme == ContentResolver.SCHEME_FILE) //若是沙盒里的文件，file开头
            File(requireNotNull(uri.path)) //返回文件
        else if (uri.scheme == ContentResolver.SCHEME_CONTENT) { //共享文件
            //把文件保存到沙盒
            val contentResolver = context.contentResolver

            val displayName = run {
                val cursor = contentResolver.query(uri, null, null, null, null)
                cursor?.let {
                    if(it.moveToFirst())
                        it.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    else null
                }
            }?:"${System.currentTimeMillis()}${Random.nextInt(0, 9999)}.${
                MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri))}"

            val ios = contentResolver.openInputStream(uri)

            if (ios != null) {
                File("${context.externalCacheDir!!.absolutePath}/$displayName")
                    .apply {
                        val fos = FileOutputStream(this)
                        FileUtils.copy(ios, fos)
                        fos.close()
                        ios.close()
                    }
            } else null
        } else null



    @RequiresApi(api = Build.VERSION_CODES.Q)
    fun uriToFileApiQ(uri: Uri,context: Context): File? {
        var file: File? = null
        //android10以上转换
        if (uri.scheme == ContentResolver.SCHEME_FILE) {
            file = File(uri.path)
        } else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //把文件复制到沙盒目录
            val contentResolver: ContentResolver = context.contentResolver
            val cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor!!.moveToFirst()) {
                val displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                try {
                    val ios: InputStream = contentResolver.openInputStream(uri)!!
                    val cache: File = File(
                        context.externalCacheDir?.absolutePath,
                        ((Math.random() + 1) * 1000).roundToInt().toString() + displayName
                    )
                    val fos = FileOutputStream(cache)
                    ios.let { FileUtils.copy(it, fos) }
                    file = cache
                    fos.close()
                    ios.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return file
    }
}