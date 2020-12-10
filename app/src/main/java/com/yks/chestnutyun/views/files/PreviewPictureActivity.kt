package com.yks.chestnutyun.views.files

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gyf.immersionbar.ImmersionBar
import com.yks.chestnutyun.R
import com.yks.chestnutyun.customView.CustomDialog
import com.yks.chestnutyun.utils.ActivityHelper
import com.yks.chestnutyun.utils.ListModel
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
        bigImageBack.setOnClickListener{
            finish()
        }
    }

    override fun startObserve() {
        viewModel.mGetPreviewPictureResultStatus.observe(this) {
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
    }

    /**
     * 自定义对话框
     */
    private  fun showLayoutDialog() {
        mDialog = CustomDialog(this,"提示","是否删除此图片?", {
            //取消
            mDialog.dismiss()
        }, {
            //确认
            viewModel.deleteFile(arrayOf(name))
            mDialog.dismiss()

        },"取消","确认")
        mDialog.setCanotBackPress()
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
    }
}