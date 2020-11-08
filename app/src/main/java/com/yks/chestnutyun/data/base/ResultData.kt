package com.yks.chestnutyun.data.base


/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/7 22:04
 */

sealed class ResultData<out T : Any> {

    data class Success<out T : Any>(val data: T) : ResultData<T>()
    data class Error(val exception: Exception) : ResultData<Nothing>()
    data class ErrorMessage(val message: String) : ResultData<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is ErrorMessage -> message
        }
    }
}