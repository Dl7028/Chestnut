package com.yks.chestnutyun.views.files.allfiles

import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.ListFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.gauravk.bubblenavigation.BubbleNavigationLinearView
import com.yks.chestnutyun.R
import com.yks.chestnutyun.adaper.FileListAdapter
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
        val titleBar = requireActivity().findViewById<ConstraintLayout>(R.id.mainTitleBar)
        val titleSelectBar = requireActivity().findViewById<ConstraintLayout>(R.id.mainSelectBar)
        val bottomButtons = requireActivity().findViewById<ConstraintLayout>(R.id.mainBottomButton)
        val bottomSelectButtons = requireActivity().findViewById<LinearLayout>(R.id.mainBottomSelect)
        fragmentAllFilesRv.layoutManager = LinearLayoutManager(requireActivity())
        fragmentAllFilesRv.adapter = mAdapter
        mAdapter.setOnItemLongClickListener(OnItemLongClickListener { adapter, view, position ->
            ToastUtil.showToast("长按事件")
            titleBar.visibility = View.GONE
            bottomButtons.visibility = View.GONE
            titleSelectBar.visibility = View.VISIBLE
            bottomSelectButtons.visibility = View.VISIBLE

             true
        })

        //处理返回事件
        requireActivity().onBackPressedDispatcher.addCallback(this) {

        }

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