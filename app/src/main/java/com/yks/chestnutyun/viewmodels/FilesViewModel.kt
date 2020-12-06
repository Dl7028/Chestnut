package com.yks.chestnutyun.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yks.chestnutyun.data.bean.FileItem
import com.yks.chestnutyun.data.repositories.FilesRepository
import com.yks.chestnutyun.utils.ListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/12/4 20:17
 */
class FilesViewModel @ViewModelInject constructor(
    private val filesRepository: FilesRepository
): ViewModel() {
    val mPostFileResultStatus = MutableLiveData<ListModel<String>>()
    val mGetFileListResultStatus = MutableLiveData<ListModel<MutableList<FileItem>>>()

    /**
     * 上传图片
     *
     * @param part
     */
    fun postFile(part: MultipartBody.Part){
        viewModelScope.launch(Dispatchers.IO) {
            filesRepository.postFile(
                part,
                mPostFileResultStatus
            )
        }
    }

    /**
     * 获取文件列表
     *
     */
    fun getFileList(){
        viewModelScope.launch(Dispatchers.IO) {
            filesRepository.getFileList(
                mGetFileListResultStatus
            )
        }
    }
}