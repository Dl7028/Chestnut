package com.yks.chestnutyun.data.api

import android.content.Context
import com.yks.chestnutyun.app.MyApplication.Companion.CONTEXT
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @Description:    添加本地Cookie进行网络访问的拦截器
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/8 23:00
 */
class AddCookiesInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()
        val preferences = CONTEXT.getSharedPreferences("cookieData", Context.MODE_PRIVATE)
                                                    .getStringSet("cookie", null);

        if (preferences!=null){
            for(cookie in preferences){
                builder.addHeader("Cookie",cookie)
            }
        }
        return chain.proceed(builder.build())
    }


}