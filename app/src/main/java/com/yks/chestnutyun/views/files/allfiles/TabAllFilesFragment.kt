package com.yks.chestnutyun.views.files.allfiles

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.yks.chestnutyun.MainActivity
import com.yks.chestnutyun.R
import com.yks.chestnutyun.adaper.FileListAdapter
import com.yks.chestnutyun.data.bean.FileItem
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.viewmodels.FilesViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import com.yks.chestnutyun.views.files.PreviewPictureActivity
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
    private lateinit var mList:MutableList<FileItem>


    override fun setLayoutResId(): Int  = R.layout.fragment_all_files_tab

    override fun initView() {
        //获取MainActivity中的控件
        val titleBar = requireActivity().findViewById<ConstraintLayout>(R.id.mainTitleBar)
        val titleSelectBar = requireActivity().findViewById<ConstraintLayout>(R.id.mainSelectBar)
        val bottomButtons = requireActivity().findViewById<ConstraintLayout>(R.id.mainBottomButton)
        val bottomSelectButtons = requireActivity().findViewById<LinearLayout>(R.id.mainBottomSelect)
        val cancelTv = requireActivity().findViewById<TextView>(R.id.home_cancel)
        val deleteButton = requireActivity().findViewById<LinearLayout>(R.id.main_delete_ll)

        fragmentAllFilesRv.layoutManager = LinearLayoutManager(requireActivity())
        fragmentAllFilesRv.adapter = mAdapter
        //取消
        cancelTv.setOnClickListener{
            selectToNormal(titleBar, bottomButtons, titleSelectBar, bottomSelectButtons)
        }
        //删除
        deleteButton.setOnClickListener{
            ToastUtil.showToast("点击了删除")
        }

        //listView的长按事件
        mAdapter.setOnItemLongClickListener(OnItemLongClickListener { adapter, view, position ->
            ToastUtil.showToast("长按事件")
            normalToSelect(titleBar, bottomButtons, titleSelectBar, bottomSelectButtons)

            true
        })
        mAdapter.setOnItemClickListener{ adapter, view, position ->
            val filename = mList[position].filename
            val intent = Intent()
            intent.putExtra("filename", filename)
            intent.setClass(requireActivity(), PreviewPictureActivity::class.java)
            requireActivity().startActivity(intent)
        }



    }

    /**
     * 菜单布局转为正常布局
     *
     * @param titleBar
     * @param bottomButtons
     * @param titleSelectBar
     * @param bottomSelectButtons
     */
    private fun selectToNormal(
        titleBar: ConstraintLayout,
        bottomButtons: ConstraintLayout,
        titleSelectBar: ConstraintLayout,
        bottomSelectButtons: LinearLayout
    ) {
        titleBar.visibility = View.VISIBLE
        bottomButtons.visibility = View.VISIBLE
        titleSelectBar.visibility = View.GONE
        bottomSelectButtons.visibility = View.GONE
    }

    /**
     * 正常布局转为菜单布局
     *
     * @param titleBar
     * @param bottomButtons
     * @param titleSelectBar
     * @param bottomSelectButtons
     */
    private fun normalToSelect(
        titleBar: ConstraintLayout,
        bottomButtons: ConstraintLayout,
        titleSelectBar: ConstraintLayout,
        bottomSelectButtons: LinearLayout
    ) {
        titleBar.visibility = View.GONE
        bottomButtons.visibility = View.GONE
        titleSelectBar.visibility = View.VISIBLE
        bottomSelectButtons.visibility = View.VISIBLE
    }

    override fun initData() {
        viewModel.getFileList()
    }

    override fun startObserve() {
        viewModel.mGetFileListResultStatus.observe(this) {
//            if (it.showLoading) showProgressDialog(R.string.loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                mList = it.data!!
                mAdapter.setNewInstance(mList)
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(it.showError)
            }
            dismissProgressDialog()
        }


    }
}