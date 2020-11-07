package com.yks.chestnutyun.common

import com.google.gson.JsonParseException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/7 18:43
 */
/**
 * 处理请求层的错误,对可能的已知的错误进行处理
 */
fun handlingExceptions(e: Throwable) {
    when (e) {
        is CancellationException -> {}
        is SocketTimeoutException -> {}
        is JsonParseException -> {}
        else -> {}
    }



}


