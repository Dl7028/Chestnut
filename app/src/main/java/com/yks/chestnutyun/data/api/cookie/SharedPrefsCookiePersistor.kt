package com.yks.chestnutyun.data.api.cookie

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import okhttp3.Cookie
import java.util.*

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/29 23:03
 */
@SuppressLint("CommitPrefEdits")
class SharedPrefsCookiePersistor(sharedPreferences: SharedPreferences) : CookiePersistor {
    private val sharedPreferences: SharedPreferences = sharedPreferences

    constructor(context: Context) : this(
        context.getSharedPreferences(
            "CookiePersistence",
            Context.MODE_PRIVATE
        )
    )


  override  fun loadAll(): List<Cookie> {
        val cookies: MutableList<Cookie> = ArrayList(sharedPreferences.all.size)
        for ((_, value) in sharedPreferences.all) {
            val serializedCookie = value as String
            val cookie: Cookie? = SerializableCookie().decode(serializedCookie)
            if (cookie != null) {
                cookies.add(cookie)
            }
        }
        return cookies
    }

    /**
     * Persist all cookies, existing cookies will be overwritten.
     *
     * @param cookies cookies persist
     */
    override fun saveAll(cookies: Collection<Cookie?>?) {
        val editor = sharedPreferences.edit()
        for (cookie in cookies!!) {
            editor.putString(cookie?.let { createCookieKey(it) }, SerializableCookie().encode(cookie))
        }
        editor.apply()
    }

    /**
     * Removes indicated cookies from persistence.
     *
     * @param cookies cookies to remove from persistence
     */
    override fun removeAll(cookies: Collection<Cookie?>?) {
        val editor = sharedPreferences.edit()
        if (cookies != null) {
            for (cookie in cookies) {
                editor.remove(cookie?.let { createCookieKey(it) })
            }
        }
        editor.apply()
    }




    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private fun createCookieKey(cookie: Cookie): String {
            return (if (cookie.secure) "https" else "http") + "://" + cookie.domain + cookie.path + "|" + cookie.name
        }
    }

}
