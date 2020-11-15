package com.yks.chestnutyun.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yks.chestnutyun.data.bean.User
import com.yks.chestnutyun.data.bean.base.ResultData
import com.yks.chestnutyun.data.repositories.base.RemoteDataSource
import com.yks.chestnutyun.utils.ListModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Description:    修改个人信息的repository
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/10 11:17
 */
@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {


    private val TAG: String? = "UserRepository"

    /**
     * 修改用户信息
     */
    suspend fun modifyUserMessages(user: User, listModel:  MutableLiveData<ListModel<Int>>?) {
        listModel?.postValue(ListModel(showLoading = true))
        val modifyResult = remoteDataSource.modifyUserMessages(user)
        Log.d(TAG,"修改结果："+modifyResult)
        if (modifyResult is ResultData.Success) {   //更改成功
            listModel?.postValue(ListModel(showLoading = false, showEnd = true))
        } else if (modifyResult is ResultData.ErrorMessage) { //获取验证码失败
            listModel?.postValue(ListModel(showLoading = false, showError = modifyResult.message))
        }
    }

}