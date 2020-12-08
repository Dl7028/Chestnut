package com.yks.chestnutyun.data.repositories

import androidx.lifecycle.MutableLiveData
import com.yks.chestnutyun.data.bean.FileItem
import com.yks.chestnutyun.data.bean.base.ResultData
import com.yks.chestnutyun.data.repositories.base.RemoteDataSource
import com.yks.chestnutyun.utils.ListModel
import okhttp3.MultipartBody
import java.io.File
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
        if (postResult is ResultData.Success) {
            listModel?.postValue(ListModel(showLoading = false, showEnd = true))
        } else if (postResult is ResultData.ErrorMessage) {
            listModel?.postValue(ListModel(showLoading = false, showError = postResult.message))
        }
    }

    /**
     * 获取文件
     *
     * @param listModel
     */
    suspend fun getFileList( listModel: MutableLiveData<ListModel<MutableList<FileItem>>>){
        listModel.postValue(ListModel(showLoading = true))
        val postResult = remoteDataSource.getFileList()
        if (postResult is ResultData.Success) {
            listModel.postValue(ListModel(showLoading = false, showEnd = true,data= postResult.data))
        } else if (postResult is ResultData.ErrorMessage) {
            listModel.postValue(ListModel(showLoading = false, showError = postResult.message))
        }
    }

    /**
     * 获取预览的图片
     *
     * @param filename
     * @param listModel
     */
    suspend fun getPreviewPicture(filename:String, listModel: MutableLiveData<ListModel<File?>>){
        listModel.postValue(ListModel(showLoading = true))
        val getResult = remoteDataSource.getPreviewPicture(filename)

        if (getResult is ResultData.Success) {
            listModel.postValue(ListModel(showLoading = false, showEnd = true,data = getResult.data))
        } else if (getResult is ResultData.ErrorMessage) {
            listModel.postValue(ListModel(showLoading = false, showError = getResult.message))
        }else if (getResult is ResultData.Error){
            listModel.postValue(ListModel(showLoading = false, showError = getResult.exception.toString()))
        }
    }


}