package com.yks.chestnutyun.views.files

import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gyf.immersionbar.ImmersionBar
import com.yks.chestnutyun.R
import com.yks.chestnutyun.customView.CustomDialog
import com.yks.chestnutyun.customView.RenamePopupWindow
import com.yks.chestnutyun.utils.ActivityHelper
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.viewmodels.FilesViewModel
import com.yks.chestnutyun.views.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_preview_image.*
import kotlinx.android.synthetic.main.bigimage_bottom_layout.*
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/12/8 22:40
 */
@AndroidEntryPoint
class PreviewPictureActivity: BaseActivity() {

    private val viewModel: FilesViewModel by viewModels()
    private  lateinit var mDialog:CustomDialog
    private lateinit var name:String
    private lateinit var newName:String


    override fun setLayoutId(): Int = R.layout.activity_preview_image

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).titleBar(previewPictureTitle).init()
        val bundle = intent.extras!!
         name = bundle.getString("filename")!!
        viewModel.getPreviewPicture(name) // 获取预览图片
        bigImageName.text = name
        //删除图片
        bigImageDeleteButton.setOnClickListener{
            showLayoutDialog()
        }
        //返回
        bigImageBack.setOnClickListener{
            ActivityHelper.finishActivity(this)
        }
        //重命名
        bigImageRenameButton.setOnClickListener{
            showPopupWindow()
        }
    }

    /**
     *
     *重命名的PopupWindow
     */
    private fun showPopupWindow() {
        val renamePopupWindow = RenamePopupWindow(this, name)
        //点击事件
        renamePopupWindow.setOnItemClickListener(object : RenamePopupWindow.OnItemClickListener {
            override fun onOkClick(nickName: String?) {
                Timber.d(name.substring(name.lastIndexOf(".") + 1))
                viewModel.renameFile(name,nickName!!)
                newName = nickName
                renamePopupWindow.dismiss()
            }

        })
        //设置动画
        renamePopupWindow.showAtLocation(
            findViewById(R.id.big_image_layout),  // 设置layout在PopupWindow中显示的位置
            Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0
        )
        //取消
        renamePopupWindow.setOnDismissListener {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        }
    }

    override fun startObserve() {
        viewModel.mGetPreviewPictureResultStatus.observe(this) {
            if(it.showLoading) showProgressDialog(R.string.loading)  else dismissProgressDialog()  //显示/隐藏 进度条

            if (it.showEnd) {
                Glide.with(this)
                    .load(it.data)
                    .skipMemoryCache(true) // 不使用内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                    .into(bigImage)
                Timber.d(it.data?.path)
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(errorMsg)
                Timber.d(errorMsg)
            }
        }
        viewModel.mDeleteFileResultStatus.observe(this) {
            if(it.showLoading) showProgressDialog(R.string.delete_loading)  else dismissProgressDialog()  //显示/隐藏 进度条

            if (it.showEnd) {
                ToastUtil.showToast(it.data!!)
                //事件的发送
                EventBus.getDefault().post("删除了文件");
                ActivityHelper.finishActivity(this)
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(errorMsg)
                Timber.d(errorMsg)
            }
        }
        viewModel.mRenameFileResultStatus.observe(this) {
            if(it.showLoading) showProgressDialog(R.string.rename_loading)  else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtil.showToast(it.data!!)
                bigImageName.text = newName
                //事件的发送
                EventBus.getDefault().post("更改了文件名");
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(errorMsg)
                Timber.d(errorMsg)
            }
        }
    }

    /**
     * 自定义对话框
     */
    private  fun showLayoutDialog() {
        mDialog = CustomDialog(this, "提示", "是否删除此图片?", {
            //取消
            mDialog.dismiss()
        }, {
            //确认
            val list = ArrayList<String>()
            list.add(name)
            val array = list.toTypedArray()  //list转为数组
            val str = list.joinToString(",")


            for (s in array) {

                Timber.d("数组-------------->" + s)
            }
            viewModel.deleteFile(str)
            mDialog.dismiss()

        }, "取消", "确认")
        mDialog.setCanotBackPress()
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityHelper.finishActivity(this)

    }
}