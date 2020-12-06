package com.yks.chestnutyun.views.files.allfiles

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yks.chestnutyun.R
import com.yks.chestnutyun.adaper.FileListAdapter
import com.yks.chestnutyun.data.bean.FileItem
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.viewmodels.FilesViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_all_files_tab.*
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * @Description:    全部文件标签的Fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/31 10:51
 */
@AndroidEntryPoint
class TabAllFilesFragment: BaseFragment() {

    private val  viewModel: FilesViewModel by viewModels()
    private val mAdapter: FileListAdapter = FileListAdapter(R.layout.item_file)


    override fun setLayoutResId(): Int  = R.layout.fragment_all_files_tab

    override fun initView() {
        fragmentAllFilesRv.layoutManager = LinearLayoutManager(requireActivity())
        fragmentAllFilesRv.adapter = mAdapter
    }

    override fun initData() {
        viewModel.getFileList()
    }

    override fun startObserve() {
        viewModel.mGetFileListResultStatus.observe(this){
            if (it.showLoading) showProgressDialog(R.string.loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                mAdapter.setNewInstance(it.data)
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(it.showError)
            }
            dismissProgressDialog()
        }
    }



}