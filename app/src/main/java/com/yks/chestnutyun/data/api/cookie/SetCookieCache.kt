package com.yks.chestnutyun.data.api.cookie

import okhttp3.Cookie
import java.util.*

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/29 23:24
 */
class SetCookieCache : CookieCache {
    private val cookies: MutableSet<IdentifiableCookie>



    override fun addAll(newCookies: List<Cookie>) {
        for (cookie in IdentifiableCookie.decorateAll(newCookies)) {
            cookies.remove(cookie)
            cookies.add(cookie)
        }
    }

    override fun clear() {
        cookies.clear()
    }

    override fun iterator(): Iterator<Cookie> {
        return SetCookieCacheIterator()
    }

    private inner class SetCookieCacheIterator : MutableIterator<Cookie> {
        private val iterator: MutableIterator<IdentifiableCookie> = cookies.iterator()
        override fun hasNext(): Boolean {
            return iterator.hasNext()
        }

        override fun next(): Cookie {
            return iterator.next().getCookie()
        }

        override fun remove() {
            iterator.remove()
        }

    }

    init {
        cookies = HashSet<IdentifiableCookie>()
    }
}
