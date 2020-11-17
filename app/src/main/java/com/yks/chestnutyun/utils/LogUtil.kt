package com.yks.chestnutyun.utils

import android.util.Log

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/17 11:08
 */
private var TAG: String? = "LogUtil"
private var IS_LOG = false
private const val MAX_LENGTH = 4000

fun LogUtil() {}

fun setIsLog(isLog: Boolean) {
    IS_LOG = isLog
}

fun setIsLog(isLog: Boolean, tag: String) {
    TAG = tag
    IS_LOG = isLog
}

fun i(msg: String) {
    if (IS_LOG) {
        val info = getAutoJumpLogInfos()
        val strLength = msg.length
        var start = 0
        var end = MAX_LENGTH
        for (i in 0..99) {
            if (strLength <= end) {
                Log.i(TAG, info[1] + info[2] + " --->> " + msg.substring(start, strLength))
                break
            }
            Log.i(TAG, info[1] + info[2] + " --->> " + msg.substring(start, end))
            start = end
            end += MAX_LENGTH
        }
    }
}

fun i(TAG: String?, msg: String) {
    if (IS_LOG) {
        val info = getAutoJumpLogInfos()
        val strLength = msg.length
        var start = 0
        var end = MAX_LENGTH
        for (i in 0..99) {
            if (strLength <= end) {
                Log.i(TAG, info[1] + info[2] + " --->> " + msg.substring(start, strLength))
                break
            }
            Log.i(TAG, info[1] + info[2] + " --->> " + msg.substring(start, end))
            start = end
            end += MAX_LENGTH
        }
    }
}

fun d(msg: String) {
    if (IS_LOG) {
        val info = getAutoJumpLogInfos()
        val strLength = msg.length
        var start = 0
        var end = MAX_LENGTH
        for (i in 0..99) {
            if (strLength <= end) {
                Log.d(TAG, info[1] + info[2] + " --->> " + msg.substring(start, strLength))
                break
            }
            Log.d(TAG, info[1] + info[2] + " --->> " + msg.substring(start, end))
            start = end
            end += MAX_LENGTH
        }
    }
}

fun d(TAG: String?, msg: String) {
    if (IS_LOG) {
        val info = getAutoJumpLogInfos()
        val strLength = msg.length
        var start = 0
        var end = MAX_LENGTH
        for (i in 0..99) {
            if (strLength <= end) {
                Log.d(TAG, info[1] + info[2] + " --->> " + msg.substring(start, strLength))
                break
            }
            Log.d(TAG, info[1] + info[2] + " --->> " + msg.substring(start, end))
            start = end
            end += MAX_LENGTH
        }
    }
}

fun e(msg: String) {
    if (IS_LOG) {
        val info = getAutoJumpLogInfos()
        val strLength = msg.length
        var start = 0
        var end = MAX_LENGTH
        for (i in 0..99) {
            if (strLength <= end) {
                Log.e(TAG, info[1] + info[2] + " --->> " + msg.substring(start, strLength))
                break
            }
            Log.e(TAG, info[1] + info[2] + " --->> " + msg.substring(start, end))
            start = end
            end += MAX_LENGTH
        }
    }
}

fun e(tag: String?, msg: String) {
    if (IS_LOG) {
        val info = getAutoJumpLogInfos()
        val strLength = msg.length
        var start = 0
        var end = MAX_LENGTH
        for (i in 0..99) {
            if (strLength <= end) {
                Log.e(tag, info[1] + info[2] + " --->> " + msg.substring(start, strLength))
                break
            }
            Log.e(tag, info[1] + info[2] + " --->> " + msg.substring(start, end))
            start = end
            end += MAX_LENGTH
        }
    }
}

private fun getAutoJumpLogInfos(): Array<String> {
    val infos = arrayOf("", "", "")
    val elements = Thread.currentThread().stackTrace
    infos[0] = elements[4].className.substring(elements[4].className.lastIndexOf(".") + 1)
    infos[1] = elements[4].methodName
    infos[2] = "(" + elements[4].fileName + ":" + elements[4].lineNumber + ")"
    return infos
}