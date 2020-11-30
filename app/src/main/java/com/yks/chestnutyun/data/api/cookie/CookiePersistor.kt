package com.yks.chestnutyun.data.api.cookie

import okhttp3.Cookie

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/29 23:10
 */
open interface CookiePersistor {
    fun loadAll(): List<Cookie>

    /**
     * Persist all cookies, existing cookies will be overwritten.
     *
     * @param cookies cookies persist
     */
    fun saveAll(cookies: Collection<Cookie?>?)

    /**
     * Removes indicated cookies from persistence.
     *
     * @param cookies cookies to remove from persistence
     */
    fun removeAll(cookies: Collection<Cookie?>?)

    /**
     * Clear all cookies from persistence.
     */
    fun clear()
}