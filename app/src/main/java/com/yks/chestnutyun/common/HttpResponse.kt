package com.yks.chestnutyun.common

import com.yks.chestnutyun.utils.ToastUtils

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/7 18:44
 */
// 简单说明:密封类结合when让可能情况都是已知的,代码维护性更高。
sealed class HttpResponse

data class Success<out T>(val data: T) : HttpResponse()
data class Failure(val error: HttpError) : HttpResponse()


enum class HttpError(val code: Int, val errorMsg: String?) {
    USER_EXIST(20001, "user does not exist"),
    PARAMS_ERROR(20002, "params is error")
    // ...... more
}
/**
 * 处理响应层的错误
 */
fun handlingApiExceptions(e: HttpError) {
    when (e) {
        HttpError.USER_EXIST -> {}
        HttpError.PARAMS_ERROR -> {}
        // .. more
    }
}



