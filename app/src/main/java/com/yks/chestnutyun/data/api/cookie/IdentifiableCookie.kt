package com.yks.chestnutyun.data.api.cookie

import okhttp3.Cookie
import java.util.*

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/29 23:24
 */
internal class IdentifiableCookie(cookie: Cookie) {
    private val cookie: Cookie = cookie
    fun getCookie(): Cookie {
        return cookie
    }

    override fun equals(other: Any?): Boolean {
        if (other !is IdentifiableCookie) return false
        val that = other
        return that.cookie.name == cookie.name && that.cookie.domain == cookie.domain && that.cookie.path == cookie.path && that.cookie.secure == cookie.secure && that.cookie.hostOnly == cookie.hostOnly
    }

    override fun hashCode(): Int {
        var hash = 17
        hash = 31 * hash + cookie.name.hashCode()
        hash = 31 * hash + cookie.domain.hashCode()
        hash = 31 * hash + cookie.path.hashCode()
        hash = 31 * hash + if (cookie.secure) 0 else 1
        hash = 31 * hash + if (cookie.hostOnly) 0 else 1
        return hash
    }

    companion object {
        fun decorateAll(cookies: Collection<Cookie>): List<IdentifiableCookie> {
            val identifiableCookies: MutableList<IdentifiableCookie> = ArrayList(cookies.size)
            for (cookie in cookies) {
                identifiableCookies.add(IdentifiableCookie(cookie))
            }
            return identifiableCookies
        }
    }

}
