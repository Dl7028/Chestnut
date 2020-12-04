package com.yks.chestnutyun.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/12/4 11:23
 */
val ACCEPTED_MIMETYPES = arrayOf("image/*")
val ACCEPTED_ALL = arrayOf("*/*")
val ACCEPTED_DOCUMENT = arrayOf("application/pdf","application/vnd.oasis.opendocument.text","text/plain")

/**
 * 定义协议
 *
 */
class GetContentWithMimeTypes : ActivityResultContract<Array<String>, Uri?>() {
    override fun createIntent(
        context: Context,
        input: Array<String>
    ): Intent {
        return Intent(Intent.ACTION_GET_CONTENT) //允许用户选择特定类型的数据并返回它
            .addCategory(Intent.CATEGORY_OPENABLE) //对结果进行过滤，只显示可打开的文件
            .setType("*/*")
            .putExtra(Intent.EXTRA_MIME_TYPES, input);
        /**
         * 额外的用于通信一组可接受的MIME类型。额外的类型是{@code String[]}。值可以是具体MIME类型(如“image/png”)和/或部分MIME类型(如
         * “音频/ *”)。
         */

    }

    override fun getSynchronousResult(
        context: Context,
        input: Array<String>
    ): SynchronousResult<Uri?>? {
        return null
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (intent == null || resultCode != Activity.RESULT_OK) null else intent.data
    }
}
