package com.yks.chestnutyun.data.api.cookie

import okhttp3.Cookie
import okhttp3.HttpUrl
import java.util.*

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/29 23:09
 */
class PersistentCookieJar(cache: CookieCache, persistor: CookiePersistor) : ClearableCookieJar {

    private val cache: CookieCache = cache
    private val persistor: CookiePersistor = persistor
    init {
        this.cache.addAll(persistor.loadAll())
    }
    /**
     * Clear all the session cookies while maintaining the persisted ones.
     */
    override fun clearSession() {
        cache.clear()
        cache.addAll(persistor.loadAll())
    }

    /**
     * Clear all the cookies from persistence and from the cache.
     */
    override fun clear() {

        cache.clear()
        persistor.clear()
    }

    /**
     * Load cookies from the jar for an HTTP request to [url]. This method returns a possibly
     * empty list of cookies for the network request.
     *
     * Simple implementations will return the accepted cookies that have not yet expired and that
     * [match][Cookie.matches] [url].
     */
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookiesToRemove: MutableList<Cookie?> = ArrayList()
        val validCookies: MutableList<Cookie> = ArrayList<Cookie>()



            val it:MutableIterator<Cookie> = cache.iterator() as MutableIterator<Cookie>
            while (it.hasNext()) {
                val currentCookie = it.next()
                if (isCookieExpired(currentCookie)) {
                    cookiesToRemove.add(currentCookie)
                    it.remove()
                } else if (currentCookie.matches(url)) {
                    validCookies.add(currentCookie)
                }
            }

        persistor.removeAll(cookiesToRemove)

        return validCookies
    }

    /**
     * Saves [cookies] from an HTTP response to this store according to this jar's policy.
     *
     * Note that this method may be called a second time for a single HTTP response if the response
     * includes a trailer. For this obscure HTTP feature, [cookies] contains only the trailer's
     * cookies.
     */
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cache.addAll(cookies)
        persistor.saveAll(PersistentCookieJar.filterPersistentCookies(cookies))
    }

    companion object {
        private fun filterPersistentCookies(cookies: List<Cookie>): List<Cookie> {
            val persistentCookies: MutableList<Cookie> = ArrayList()
            for (cookie in cookies) {
                if (cookie.persistent) {
                    persistentCookies.add(cookie)
                }
            }
            return persistentCookies
        }
        private fun isCookieExpired(cookie: Cookie): Boolean {
            return cookie.expiresAt < System.currentTimeMillis()
        }
    }
}

