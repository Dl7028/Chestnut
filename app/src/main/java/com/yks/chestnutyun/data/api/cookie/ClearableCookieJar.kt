package com.yks.chestnutyun.data.api.cookie

import okhttp3.CookieJar

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/29 23:36
 */
interface ClearableCookieJar : CookieJar {
    /**
     * Clear all the session cookies while maintaining the persisted ones.
     */
    fun clearSession()

    /**
     * Clear all the cookies from persistence and from the cache.
     */
    fun clear()
}