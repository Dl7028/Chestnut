package com.yks.chestnutyun.data.api.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/15 16:17
 */

class PersistenceCookieJar: CookieJar {

    val cache =ArrayList<Cookie>()
    /**
     * TODO Http请求结束，Response 中有cookie 时回调
     *
     * @param url
     * @param cookies
     */
    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        //缓存Cookie
        cache.addAll(cookies)
    }

    /**
     * TODO http 发送请求前回调
     * Request中设置cookies
     * @param url
     * @return
     */
    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        //过期的Cookies
        val invalidCookies = ArrayList<Cookie>()
        //有效的Cookies
        val validCookies = ArrayList<Cookie>()
        for (cookie in cache){
            if (cookie.expiresAt()<System.currentTimeMillis()){ //判断是否过期
                invalidCookies.add(cookie)
            }else if(cookie.matches(url)){ //匹配cookie 对应url
                validCookies.add(cookie)
            }
        }
        //移出无效cookies
        cache.removeAll(invalidCookies)
        return validCookies
    }

}