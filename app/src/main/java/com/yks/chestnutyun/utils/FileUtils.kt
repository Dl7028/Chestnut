package com.yks.chestnutyun.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.loader.content.CursorLoader
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.*


/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/27 23:34
 */
object FileUtils {

    /**
     * IO流操作，网络上通过IO流方式获取图片
     *
     * @param body
     * @return
     */
     fun getFileFromResponse(body: ResponseBody,context:Context): File? {
        var file:File? = null

            // 私有存储，存储预览的图片
            val preViewPicture = File(context.getExternalFilesDir(null).toString() + File.separator.toString()+"picture.png")
            val inputStream: InputStream = body.byteStream() //获取输入流
            val outputStream: OutputStream =  FileOutputStream(preViewPicture) //文件输出流
            try {
                val fileReader = ByteArray(2048)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                while (true) {
                    val read: Int = inputStream.read(fileReader) //读取输入流
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read) //写入输出流
                    fileSizeDownloaded += read.toLong()  //下载的进度
                    Timber.d( "file download: $fileSizeDownloaded of $fileSize")
                }
                outputStream.flush() //刷新
                file = preViewPicture

                return file
            } catch (e: IOException) {
                e.printStackTrace()
                return null

            } finally {
                inputStream.close()  //关闭
                outputStream.close()
            }

    }



}