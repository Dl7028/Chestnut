package com.yks.chestnutyun.utils

import android.webkit.DownloadListener
import com.yks.chestnutyun.app.MyApplication.Companion.CONTEXT
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.*
import java.text.DecimalFormat


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
     fun getFileFromResponse(body: ResponseBody): File? {
        var file:File? = null

        // 私有存储，存储预览的图片
        val preViewPicture = File(CONTEXT.cacheDir.toString() + File.separator.toString() + "picture.jpg")
        val inputStream: InputStream = body.byteStream() //获取输入流

        val outputStream: OutputStream =  FileOutputStream(preViewPicture) //文件输出流，向文件写入数据

        try {
            val fileReader = ByteArray(1024)
            val fileSize = body.contentLength()
            var fileSizeDownloaded: Long = 0

            while (true) {
                val read: Int = inputStream.read(fileReader) //读取输入流
                if (read == -1) {
                    break
                }
                outputStream.write(fileReader, 0, read) //写入输出流
                fileSizeDownloaded += read.toLong()  //下载的进度
                Timber.d("file download: $fileSizeDownloaded of $fileSize")
            }
            Timber.d("输出流----->")
            outputStream.flush() //刷新此输出流，并强制将所有已缓冲的输出字节写入该流中
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


    fun writeFile2Disk(response: Response<ResponseBody>, file: File ) {
        var currentLength: Long = 0
        var os: OutputStream? = null
        val `is`: InputStream = response.body()!!.byteStream() //获取下载输入流
        val totalLength: Long = response.body()!!.contentLength()
        try {
            os = FileOutputStream(file) //输出流
            var len: Int
            val buff = ByteArray(1024)
            while (`is`.read(buff).also { len = it } != -1) {
                os.write(buff, 0, len)
                currentLength += len.toLong()
                Timber.e( "当前进度: $currentLength")
                //计算当前下载百分比，并经由回调传出
                //当百分比为100时下载结束，调用结束回调，并传出下载后的本地路径
                if ((100 * currentLength / totalLength).toInt() == 100) {
                     //下载完成
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (os != null) {
                try {
                    os.close() //关闭输出流
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (`is` != null) {
                try {
                    `is`.close() //关闭输入流
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
    




    fun setSize(size: Long): String {
        //获取到的size为：1705230
        val GB = 1024 * 1024 * 1024 //定义GB的计算常量
        val MB = 1024 * 1024 //定义MB的计算常量
        val KB = 1024 //定义KB的计算常量
        val df = DecimalFormat("0.00") //格式化小数
        var resultSize = ""
        resultSize = if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            df.format(size / GB.toFloat()).toString() + "GB   "
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            df.format(size / MB.toFloat()).toString() + "MB   "
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            df.format(size / KB.toFloat()).toString() + "KB   "
        } else {
            size.toString() + "B   "
        }
        return resultSize
    }






}