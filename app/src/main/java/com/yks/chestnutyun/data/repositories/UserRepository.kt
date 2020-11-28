package com.yks.chestnutyun.data.repositories

import androidx.lifecycle.MutableLiveData
import com.yks.chestnutyun.data.bean.User
import com.yks.chestnutyun.data.bean.base.ResultData
import com.yks.chestnutyun.data.repositories.base.RemoteDataSource
import com.yks.chestnutyun.utils.ListModel
import okhttp3.MultipartBody
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
     *  获取用户信息
     *
     * @param name
     */
    suspend fun getUserInfo(name: String,listModel:  MutableLiveData<ListModel<User>>?) {
        listModel?.postValue(ListModel(showLoading = true))
        val getResult = remoteDataSource.getUserInfo(name)
        if (getResult is ResultData.Success) {   //获取用户成功
            listModel?.postValue(ListModel(showLoading = false, showEnd = true,data=getResult.data.data))
        } else if (getResult is ResultData.ErrorMessage) { //获取用户失败
            listModel?.postValue(ListModel(showLoading = false, showError = getResult.message))
        }
    }

    /**
     *  修改用户信息
     *
     * @param user 用户实例
     * @param listModel 结果判断
     */
    suspend fun modifyUserMessages(user: User, listModel:  MutableLiveData<ListModel<Int>>?) {
        listModel?.postValue(ListModel(showLoading = true))
        val modifyResult = remoteDataSource.modifyUserMessages(user)
        if (modifyResult is ResultData.Success) {   //更改成功
            listModel?.postValue(ListModel(showLoading = false, showEnd = true))
        } else if (modifyResult is ResultData.ErrorMessage) { //获取验证码失败
            listModel?.postValue(ListModel(showLoading = false, showError = modifyResult.message))
        }else if (modifyResult is ResultData.Error){

        }
    }

    /**
     * 上传用户图片
     *
     * @param portrait
     * @param listModel
     */
    suspend fun postPortrait(part: MultipartBody.Part, listModel: MutableLiveData<ListModel<String>>?){
        listModel?.postValue(ListModel(showLoading = true))
        val postResult = remoteDataSource.postPortrait(part)
        if (postResult is ResultData.Success) {   //更改成功
            listModel?.postValue(ListModel(showLoading = false, showEnd = true))
        } else if (postResult is ResultData.ErrorMessage) { //获取验证码失败
            listModel?.postValue(ListModel(showLoading = false, showError = postResult.message))
        }
    }

}