package com.yks.chestnutyun.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @Description:    基类注入hilt 依赖
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/2 21:55
 */

@HiltAndroidApp
class BaseApplication : Application() {
}