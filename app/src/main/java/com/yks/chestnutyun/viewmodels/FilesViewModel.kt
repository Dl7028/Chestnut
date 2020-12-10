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
import java.io.File

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
    val mGetPreviewPictureResultStatus = MutableLiveData<ListModel<File?>>()
    val mDeleteFileResultStatus = MutableLiveData<ListModel<String>>()
    val mRenameFileResultStatus = MutableLiveData<ListModel<String>>()


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

    /**
     * 获取预览的图片
     *
     * @param filename
     */
    fun getPreviewPicture(filename:String){
        viewModelScope.launch(Dispatchers.IO) {
            filesRepository.getPreviewPicture(
                filename,
                mGetPreviewPictureResultStatus
            )
        }
    }

    /**
     * 删除文件
     *
     * @param filename
     */
    fun deleteFile(filename:String){
        viewModelScope.launch(Dispatchers.IO) {
            filesRepository.deleteFile(
                filename,
                mDeleteFileResultStatus
            )
        }
    }

    /**
     * 修改文件名
     *
     * @param oldName
     * @param newName
     */
    fun renameFile(oldName:String, newName:String){
        viewModelScope.launch(Dispatchers.IO) {
            filesRepository.renameFile(
                oldName,
                newName,
                mRenameFileResultStatus
            )
        }
    }
}