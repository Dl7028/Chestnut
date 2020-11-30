package com.yks.chestnutyun.data.api.cookie

import okhttp3.Cookie

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/29 23:10
 */
open interface CookieCache : Iterable<Cookie?> {
    /**
     * Add all the new cookies to the session, existing cookies will be overwritten.
     *
     * @param cookies
     */
    fun addAll(cookies: List<Cookie>)

    /**
     * Clear all the cookies from the session.
     */
    fun clear()
}
