package com.yks.chestnutyun.views.files.allfiles

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.google.gson.Gson
import com.yks.chestnutyun.R
import com.yks.chestnutyun.adaper.FileListAdapter
import com.yks.chestnutyun.customView.CustomDialog
import com.yks.chestnutyun.data.bean.FileItem
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.viewmodels.FilesViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import com.yks.chestnutyun.views.files.PreviewPictureActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_all_files_tab.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
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
    private  var fileNameList: ArrayList<String> = ArrayList<String>() //获取的文件列表
    private var isCheckedList: ArrayList<String> = ArrayList<String>()  //被选中的文件名字list
    private  lateinit var mDialog:CustomDialog

    private lateinit var titleBar:ConstraintLayout
    private lateinit var titleSelectBar:ConstraintLayout
    private lateinit var bottomButtons:ConstraintLayout
    private lateinit var bottomSelectButtons:LinearLayout

    private var ifLongClick = false

    override fun setLayoutResId(): Int  = R.layout.fragment_all_files_tab

    @SuppressLint("SetTextI18n")
    override fun initView() {
        //注册
        EventBus.getDefault().register(this);
        //获取MainActivity中的控件
         titleBar = requireActivity().findViewById<ConstraintLayout>(R.id.mainTitleBar)
         titleSelectBar = requireActivity().findViewById<ConstraintLayout>(R.id.mainSelectBar)
         bottomButtons = requireActivity().findViewById<ConstraintLayout>(R.id.mainBottomButton)
         bottomSelectButtons = requireActivity().findViewById<LinearLayout>(R.id.mainBottomSelect)
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
                removeAll()
                notifyDataSetChanged()
            }
            ifLongClick  = false
        }
        //全选
        selectAll.setOnClickListener{
            mAdapter.apply {
                addAll(fileNameList)
                homeTitle.text = "已选中${getCheckedSize()}个文件"
                isCheckedList = fileNameList
                notifyDataSetChanged()

            }
        }
        //删除
        deleteButton.setOnClickListener{
            showLayoutDialog()
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
//                Timber.d(mList[0].filename)

            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(errorMsg)
            }
        }
        //删除事件观察
        viewModel.mDeleteFileResultStatus.observe(this) {
            if (it.showLoading) showProgressDialog(R.string.delete_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                selectToNormal(titleBar, bottomButtons, titleSelectBar, bottomSelectButtons)
                ToastUtil.showToast(it.data!!)
                mAdapter.apply{
                    setNotChecked(isCheckedList)
                    notifyDataSetChanged()
                }
                viewModel.getFileList()
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


    /**
     * 提示框
     *
     */
    private  fun showLayoutDialog() {
        mDialog = CustomDialog(requireActivity(), "提示", "是否删除选中的图片?", {
            //取消
            mDialog.dismiss()
        }, {
            //确认
            isCheckedList = mAdapter.getCheckedList()
            val gson = Gson()
            val obj = gson.toJson(isCheckedList)
            val body = obj.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            viewModel.deleteFile(body)
            mDialog.dismiss()

        }, "取消", "确认")
        mDialog.setCanotBackPress()
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
    }
}