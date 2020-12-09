package com.yks.chestnutyun.data.repositories

import androidx.lifecycle.MutableLiveData
import com.yks.chestnutyun.data.bean.base.ResultData
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



    /**
     * 获取验证码
     */
    suspend fun getCode(userName: String,listModel: MutableLiveData<ListModel<Int>>?){
        listModel?.postValue(ListModel(showLoading = true))
        val getCodeResult = remoteDataSource.getCode(userName)
        setListModel(getCodeResult, listModel)
    }



    /**
     * 注册
     */
    suspend fun register(username: String, password: String, verificationCode: String, listModel: MutableLiveData<ListModel<Int>>?) {
        listModel?.postValue(ListModel(showLoading = true))
        val registerResult = remoteDataSource.register(username, password, verificationCode)
        setListModel(registerResult, listModel)
    }
    /**
     * 登录
     */
    suspend fun login(username: String, password: String, listModel: MutableLiveData<ListModel<Int>>?){

        listModel?.postValue(ListModel(showLoading=true))
        val loginResult = remoteDataSource.login(username, password)
        setListModel(loginResult, listModel)
    }

    /**
     * 设置listModel 的值
     *
     * @param registerResult
     * @param listModel
     */
    private fun setListModel(
        registerResult: ResultData<String>,
        listModel: MutableLiveData<ListModel<Int>>?
    ) {
        if (registerResult is ResultData.Success) {   //成功
            listModel?.postValue(ListModel(showLoading = false, showEnd = true))
        } else if (registerResult is ResultData.ErrorMessage) { //失败
            listModel?.postValue(ListModel(showLoading = false, showError = registerResult.message))
        }
    }

}



