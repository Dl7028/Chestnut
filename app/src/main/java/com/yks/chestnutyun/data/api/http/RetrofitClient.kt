package com.yks.chestnutyun.data.api.http

import com.yks.chestnutyun.data.api.ApiService
import com.yks.chestnutyun.ext.CookieClass.cookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
  * @Description:
  * @Author:         Yu ki-r
  * @CreateDate:     2020/11/29 23:35
 */
class RetrofitClient private constructor() : BaseRetrofitClient() {
    val service by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        getService(ApiService::class.java)
    }

    companion object {
        @Volatile
        private var instance: RetrofitClient? = null
        fun getInStance() = instance ?: synchronized(this) {
            instance ?: RetrofitClient().also { instance = it }
        }

    }

    //okHttp 扩展
    override fun handleBuilder(builder: OkHttpClient.Builder) {
        builder.cookieJar(cookieJar)
    }
    //retrofit扩展
    override fun retrofitBuilder(builder: Retrofit.Builder) {
    }
}