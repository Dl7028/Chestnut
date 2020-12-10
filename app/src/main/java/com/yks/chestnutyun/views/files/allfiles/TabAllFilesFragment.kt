package com.yks.chestnutyun.views.files.allfiles

import android.annotation.SuppressLint
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
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber


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
    private  var fileNameList: ArrayList<String> = ArrayList<String>()

    private var ifLongClick = false

    override fun setLayoutResId(): Int  = R.layout.fragment_all_files_tab

    @SuppressLint("SetTextI18n")
    override fun initView() {
        //注册
        EventBus.getDefault().register(this);
        //获取MainActivity中的控件
        val titleBar = requireActivity().findViewById<ConstraintLayout>(R.id.mainTitleBar)
        val titleSelectBar = requireActivity().findViewById<ConstraintLayout>(R.id.mainSelectBar)
        val bottomButtons = requireActivity().findViewById<ConstraintLayout>(R.id.mainBottomButton)
        val bottomSelectButtons = requireActivity().findViewById<LinearLayout>(R.id.mainBottomSelect)
        val cancelTv = requireActivity().findViewById<TextView>(R.id.home_cancel)
        val homeTitle = requireActivity().findViewById<TextView>(R.id.homeTitle)
        val selectAll = requireActivity().findViewById<TextView>(R.id.home_right_button)
        val deleteButton = requireActivity().findViewById<LinearLayout>(R.id.main_delete_ll)


        fragmentAllFilesRv.layoutManager = LinearLayoutManager(requireActivity())
        fragmentAllFilesRv.adapter = mAdapter


        //取消
        cancelTv.setOnClickListener{
            selectToNormal(titleBar, bottomButtons, titleSelectBar, bottomSelectButtons)
            mAdapter.apply {
                cancel()
                notifyDataSetChanged()
            }
            ifLongClick  = false
        }
        //全选
        selectAll.setOnClickListener{
            mAdapter.apply {
                addAll(fileNameList)
                notifyDataSetChanged()

            }
        }
        //删除
        deleteButton.setOnClickListener{
            ToastUtil.showToast("点击了删除")
        }

        //listView的长按事件
        mAdapter.setOnItemLongClickListener(OnItemLongClickListener { adapter, view, position ->
            ifLongClick = true
            normalToSelect(titleBar, bottomButtons, titleSelectBar, bottomSelectButtons)
            mAdapter.apply {
                setCheck(mList[position].filename)
                notifyDataSetChanged()
                homeTitle.text = "已选中${getCheckedSize()}个文件"
            }

            true
        })
        mAdapter.setOnItemClickListener{ adapter, view, position ->
            if(ifLongClick){  // 长按后的点击事件
                mAdapter.apply {
                    setCheck(mList[position].filename)
                    notifyDataSetChanged()
                    homeTitle.text = "已选中${getCheckedSize()}个文件"

                }
            }else{
                val filename = mList[position].filename
                if (filename.endsWith("jpg")||filename.endsWith("png")||filename.endsWith("jpeg")){
                    val intent = Intent()
                    intent.putExtra("filename", filename)
                    intent.setClass(requireActivity(), PreviewPictureActivity::class.java)
                    requireActivity().startActivity(intent)
                }
            }
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
                for (list in mList){
                    fileNameList.add(list.filename)
                }
                var i:Int = 0
                for ( i in fileNameList){
                    Timber.d("数组文件名--------->"+i)
                }
                Timber.d(mList[0].filename)

            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(errorMsg)
            }
        }
    }

    /**
     * 事件总线，观察事件，删除图片后在这里刷新ui
     *
     * @param event
     */
    @Subscribe
    fun updateUi(event: String) {
        Timber.d(event)
        viewModel.getFileList()
    }

    override fun onDestroyView() {
        //取消注册
        EventBus.getDefault().unregister(this);
        super.onDestroyView()
    }
}