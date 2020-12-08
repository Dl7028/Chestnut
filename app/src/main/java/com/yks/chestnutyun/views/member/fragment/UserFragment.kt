package com.yks.chestnutyun.views.member.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.yks.chestnutyun.R
import com.yks.chestnutyun.customView.CustomDialog
import com.yks.chestnutyun.databinding.FragmentUserBinding
import com.yks.chestnutyun.ext.setOnClickWithFilter
import com.yks.chestnutyun.utils.*
import com.yks.chestnutyun.viewmodels.UserViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import timber.log.Timber
import java.io.File

/**
 * @Description:    用户中心的fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/9 15:25
 */
@AndroidEntryPoint
class UserFragment: BaseFragment() {

    private val TAG: String = "UserFragment"
    private val viewModel: UserViewModel by viewModels()
    private lateinit var mDialog: CustomDialog


    private lateinit var binding: FragmentUserBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //初始化binding
        binding = DataBindingUtil.inflate<FragmentUserBinding>(
            inflater,
            setLayoutResId(),
            container,
            false
        )
        binding.apply {
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun setLayoutResId(): Int = R.layout.fragment_user


    override fun initView() {

        val bundle = requireActivity().intent.extras!!
        val name = bundle.getString("username")!!
        viewModel.getUserInfo(name) //


        user_nick_name.setOnClickWithFilter {
            findNavController().navigate(R.id.action_nav_user_center_fragment_to_nav_user_change_nickname_fragment)
        }
        userPersonalizedSignature.setOnClickWithFilter {
            findNavController().navigate(R.id.action_nav_user_center_fragment_to_nav_user_change_sign_nature_fragment)
        }
        cancelBackBtn.setOnClickWithFilter {
            requireActivity().finish()
        }
        personalImage.setOnClickWithFilter {
//            getPictureWithPermissionCheck() //权限访问
            selectPicture.launch(ACCEPTED_MIMETYPES)


        }


    }

    override fun initData() {
    }

    override fun startObserve() {
        //获取用户信息
        viewModel.mGetUserInfoResultStatus.observe(this) {
            if (it.showEnd) {
                //显示数据
                binding.apply {
                    userNickName.text = it.data?.nickname
                    userPhoneNumber.text = it.data?.phoneNumber
                    userEmilAddress.text = it.data?.email.toString()
                    userPersonalizedSignature.text = it.data?.personalizedSignature
                    Timber.d(it.data?.portrait)
                }
                //显示图片
                if (it.data?.portrait != null) Glide.with(this).load(it.data.portrait)
                    .into(personalImage)
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(errorMsg)
            }
        }
        //上传用户头像
        viewModel.mPostPortraitResultStatus.observe(this) {
            if (it.showLoading) showProgressDialog(R.string.post_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtil.showToast("更改图片成功")
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(it.showError)
            }
        }
    }




    /**
     * 跳转到设置的页面
     *
     */
    private fun jumpSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    /**
     * 1.注册协议，获取ActivityResultLauncher
     */
    private val selectPicture = registerForActivityResult(GetContentWithMimeTypes()) { uri ->
        uri?.let {
            Glide.with(this).load(uri).into(personalImage) //加载图片
//            val path = UriToFilePathUtil.uriToFileQ(requireActivity(), uri).toString()
            val path = PathUtils.getPath(requireActivity(),uri).toString()
            val file = File(path)

            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull()) //构建图片Body
            val body: MultipartBody.Part =
                MultipartBody.Part.createFormData("portrait", file.name, requestBody)
            viewModel.postPortrait(body)
        }
    }


}