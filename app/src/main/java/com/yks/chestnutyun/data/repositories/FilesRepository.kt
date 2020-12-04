package com.yks.chestnutyun.data.repositories

import androidx.lifecycle.MutableLiveData
import com.yks.chestnutyun.data.bean.base.ResultData
import com.yks.chestnutyun.data.repositories.base.RemoteDataSource
import com.yks.chestnutyun.utils.ListModel
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/12/4 20:09
 */
@Singleton
class FilesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
){

    /**
     * 上传文件
     *
     * @param
     * @param listModel
     */
    suspend fun postFile(part: MultipartBody.Part, listModel: MutableLiveData<ListModel<String>>?){
        listModel?.postValue(ListModel(showLoading = true))
        val postResult = remoteDataSource.postFile(part)
        if (postResult is ResultData.Success) {   //更改成功
            listModel?.postValue(ListModel(showLoading = false, showEnd = true))
        } else if (postResult is ResultData.ErrorMessage) { //获取验证码失败
            listModel?.postValue(ListModel(showLoading = false, showError = postResult.message))
        }
    }
}