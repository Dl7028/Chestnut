package com.yks.chestnutyun.data.api.cookie

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/15 16:37
 */
class CookiesManager(context: Context?) : CookieJar {

    private val cookieStore: PersistentCookieStore = PersistentCookieStore(context!!)


    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie?>) {
        if (cookies.isNotEmpty()) {
            for (item in cookies) {
                cookieStore.add(url, item!!)
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore.get(url)
    }

}