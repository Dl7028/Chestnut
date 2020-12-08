package com.yks.chestnutyun.views.files

import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.yks.chestnutyun.R
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.viewmodels.FilesViewModel
import com.yks.chestnutyun.views.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_preview_image.*
import timber.log.Timber

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/12/8 22:40
 */
@AndroidEntryPoint
class PreviewPictureActivity: BaseActivity() {

    private val viewModel: FilesViewModel by viewModels()

    override fun setLayoutId(): Int = R.layout.activity_preview_image

    override fun initView(savedInstanceState: Bundle?) {
        val bundle = intent.extras!!
        val name = bundle.getString("filename")!!
        viewModel.getPreviewPicture(name) // 获取预览图片
    }

    override fun startObserve() {
        viewModel.mGetPreviewPictureResultStatus.observe(this) {
            if (it.showLoading) showProgressDialog(R.string.loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                Glide.with(this).load(it.data?.path).into(bigImage)
                Timber.d(it.data?.path)

            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(errorMsg)
                Timber.d(errorMsg)
            }


        }

    }
}