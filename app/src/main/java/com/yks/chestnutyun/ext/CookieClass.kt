package com.yks.chestnutyun.ext

import com.yks.chestnutyun.app.MyApplication
import com.yks.chestnutyun.data.api.cookie.PersistentCookieJar
import com.yks.chestnutyun.data.api.cookie.SetCookieCache
import com.yks.chestnutyun.data.api.cookie.SharedPrefsCookiePersistor


/**
  * @Description:
  * @Author:         Yu ki-r
  * @CreateDate:     2020/11/29 23:02
 */
object CookieClass {
    /**Cookie*/
    val cookiePersistor = SharedPrefsCookiePersistor(MyApplication.CONTEXT)
    val cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersistor) //cookies持久化缓存
    //清除Cookie
    fun clearCookie() = cookieJar.clear()

    //是否有Cookie
    fun hasCookie() = cookiePersistor.loadAll().isNotEmpty()
}