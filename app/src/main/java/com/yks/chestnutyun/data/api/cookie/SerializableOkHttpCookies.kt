package com.yks.chestnutyun.data.api.cookie

import okhttp3.Cookie
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/15 16:38
 */
class SerializableOkHttpCookies(cookies: Cookie) : Serializable {
    @Transient
    private val cookies: Cookie

    @Transient
    private var clientCookies: Cookie? = null
    fun getCookies(): Cookie {
        var bestCookies = cookies
        if (clientCookies != null) {
            bestCookies = clientCookies as Cookie
        }
        return bestCookies
    }

    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(cookies.name())
        out.writeObject(cookies.value())
        out.writeLong(cookies.expiresAt())
        out.writeObject(cookies.domain())
        out.writeObject(cookies.path())
        out.writeBoolean(cookies.secure())
        out.writeBoolean(cookies.httpOnly())
        out.writeBoolean(cookies.hostOnly())
        out.writeBoolean(cookies.persistent())
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        val name = `in`.readObject() as String
        val value = `in`.readObject() as String
        val expiresAt = `in`.readLong()
        val domain = `in`.readObject() as String
        val path = `in`.readObject() as String
        val secure = `in`.readBoolean()
        val httpOnly = `in`.readBoolean()
        val hostOnly = `in`.readBoolean()
        var builder = Cookie.Builder()
        builder.name(name)
        builder.value(value)
        builder.expiresAt(expiresAt)
        builder = if (hostOnly) builder.hostOnlyDomain(domain) else builder.domain(domain)
        builder.path(path)
        if (secure) {
            builder.secure()
        }
        if (httpOnly) {
            builder.httpOnly()
        }
        clientCookies = builder.build()
    }

    init {
        this.cookies = cookies
    }
}