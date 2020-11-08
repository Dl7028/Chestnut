package com.yks.chestnutyun.data.api

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

import com.yks.chestnutyun.app.MyApplication.Companion.CONTEXT
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @Description:    网络请求接收 cookie 的拦截器
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/8 22:45
 */
class ReceivedCookiesInterceptor: Interceptor {

    @SuppressLint("CommitPrefEdits")
    override fun intercept(chain: Interceptor.Chain): Response {
        //拦截cookie 保存在response中
       val response = chain.proceed(chain.request())
        if (response.headers("Set-Cookie").isNotEmpty()){ //有Cookie的情况下
            val cookies: HashSet<String> = HashSet()
            for (s in  response.headers("Set-Cookie")){
                cookies.add(s)      //保存Cookie 到 HashSet 中
            }
            //将保存的Cookie存放在本地中
            val sharedPreferences: SharedPreferences = CONTEXT.getSharedPreferences("cookieData", Context.MODE_PRIVATE) //创建一个SharedPreferences对象
            val edit:SharedPreferences.Editor = sharedPreferences.edit() //实例化SharedPreferences.Editor对象
            edit.putStringSet("cookie",cookies) //将获取过来的值放入文件
            edit.apply() //提交
        }

        return response
    }
}