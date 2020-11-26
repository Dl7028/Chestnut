package com.yks.chestnutyun.views.member.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yks.chestnutyun.R
import com.yks.chestnutyun.data.bean.User
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.viewmodels.UserViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_change_nature_sign.*

/**
 * @Description:    修改 个性签名的Fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/10 10:21
 */
@AndroidEntryPoint
class ChangeNatureSignFragment : BaseFragment() {

    private val viewModel: UserViewModel by viewModels()
    override fun setLayoutResId(): Int = R.layout.fragment_change_nature_sign

    override fun initView() {
        cancelBackBtn.setOnClickListener{
            //取消
            findNavController().navigateUp()
        }
        saveMessageTv.setOnClickListener{
            modifySignatureMessages()
        }

    }

    /**
     * 修改个性签名
     *r
     */
    private fun modifySignatureMessages() {
        var signature= modifySignatureEdt.text.toString()
        val user = User()
        if(signature.isEmpty()){
            signature = "立即添加"
        } else if(signature.length >100){
            ToastUtil.showToast("签名字数不能超过100")
        }
        user.personalizedSignature = signature
        viewModel.modifyUserMessages(user)
    }

    override fun initData() {
    }

    override fun startObserve() {
        viewModel.mModifyResultStatus.observe(this){
            if (it.showLoading) showProgressDialog(R.string.save_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtils.showToast(activity, "" + it.showEnd)  //请求成功
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtils.showToast(activity, "" + it.showError)
            }
        }
    }

}