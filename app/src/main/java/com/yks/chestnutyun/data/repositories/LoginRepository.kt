package com.yks.chestnutyun.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yks.chestnutyun.data.bean.base.ResultData
import com.yks.chestnutyun.data.api.NetWorkManager
import com.yks.chestnutyun.data.repositories.base.RemoteDataSource
import com.yks.chestnutyun.utils.ListModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:24
 */

@Singleton
class LoginRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    private val TAG: String? = "RegisterRepository"


    //获取注册的验证码
    suspend fun getCode(userName: String): Boolean {
        val baseBean = NetWorkManager.getCode(userName)
        return baseBean.code == 1
    }


    /**
     * 注册
     */
    suspend fun register(
        username: String,
        password: String,
        verificationCode: String,
        listModel: MutableLiveData<ListModel<Int>>?
    ) {
        listModel?.postValue(ListModel(showLoading = true))
        val registerResult = remoteDataSource.register(username, password, verificationCode)
        if (registerResult is ResultData.Success) {   //注册成功
            listModel?.postValue(ListModel(showLoading = false, showEnd = true))
        } else if (registerResult is ResultData.ErrorMessage) { //注册失败
            listModel?.postValue(ListModel(showLoading = false, showError = registerResult.message))
        }else if (registerResult is ResultData.Error){
            listModel?.postValue(ListModel(showLoading = false, showError = "异常"))
        }
    }
    /**
     * 登录
     */
    suspend fun login(
        username: String,
        password: String,
        listModel: MutableLiveData<ListModel<Int>>?
    ){
        listModel?.postValue(ListModel(showLoading=true))
        val loginResult = remoteDataSource.toLogin(username, password)
        if (loginResult is ResultData.Success) {
            listModel?.postValue(ListModel(showLoading = false, showEnd = true))
        } else if (loginResult is ResultData.ErrorMessage) {
            listModel?.postValue(ListModel(showLoading = false, showError = loginResult.message))
        }else if (loginResult is ResultData.Error){
            listModel?.postValue(ListModel(showLoading = false, showError = " "))
        }
    }

}



