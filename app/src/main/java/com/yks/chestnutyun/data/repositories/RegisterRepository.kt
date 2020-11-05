package com.yks.chestnutyun.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.yks.chestnutyun.base.BaseBean
import com.yks.chestnutyun.data.network.NetWorkManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:24
 */

@Singleton
class RegisterRepository @Inject constructor(){

    //获取登录请求返回的数据并判断是否注册成功
    suspend fun register(username:String,password:String,verificationCode:String):Boolean  { //返回一个布尔值的liveData对象
            val baseBean = NetWorkManager.register(username, password, verificationCode)
            if (baseBean.code == 0) {
                //注册成功
                return true
            } else {
                //注册失败
                throw Exception(baseBean.message)
            }
     }

    //获取注册的验证码
    suspend fun getCode(userName:String): String{
        val baseBean = NetWorkManager.getCode(userName)
        Log.d("获取验证码","验证码--------------->   "+baseBean.code+"------" +baseBean.data+"------"+baseBean.message)
        if (baseBean.code==200){
            return baseBean.data
        }else{
            Log.d("异常---------------------",""+baseBean.message)
            throw Exception(baseBean.message)
        }
    }

}

