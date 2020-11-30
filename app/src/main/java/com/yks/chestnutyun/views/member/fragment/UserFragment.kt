package com.yks.chestnutyun.views.member.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.yks.chestnutyun.R
import com.yks.chestnutyun.customView.CustomDialog
import com.yks.chestnutyun.databinding.FragmentUserBinding
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.utils.UriToFilePathUtil
import com.yks.chestnutyun.viewmodels.UserViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
@RuntimePermissions
class UserFragment: BaseFragment() {

    private val REQUEST_CODE_CHOOSE: Int = 1001
    private val TAG: String? = "UserFragment"
    private val viewModel: UserViewModel by viewModels()
    private   lateinit var mDialog: CustomDialog



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
            viewModel =viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun setLayoutResId(): Int = R.layout.fragment_user




    override fun initView() {
        val bundle = requireActivity().intent.extras!!
        val name = bundle.getString("username")!!
        viewModel.getUserInfo(name)


        user_nick_name.setOnClickListener{
            findNavController().navigate(R.id.action_nav_user_center_fragment_to_nav_user_change_nickname_fragment)
        }
       userPersonalizedSignature.setOnClickListener{
            findNavController().navigate(R.id.action_nav_user_center_fragment_to_nav_user_change_sign_nature_fragment)
        }
        cancelBackBtn.setOnClickListener{
            requireActivity().finish()
        }
        personalImage.setOnClickListener{
            getPictureWithPermissionCheck() //权限访问

        }


    }

    override fun initData() {
    }

    override fun startObserve() {
        //获取用户信息
        viewModel.mGetUserInfoResultStatus.observe(this){
            if (it.showEnd) {
                //显示数据
                binding.apply {
                    userNickName.text = it.data?.nickname
                    userPhoneNumber.text = it.data?.phoneNumber
                    userEmilAddress.text = it.data?.email.toString()
                    userPersonalizedSignature.text = it.data?.personalizedSignature
                }
                //显示图片
                if (it.data?.portrait!=null) Glide.with(this).load(it.data.portrait).into(personalImage)
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtils.showToast(activity, "" + it.showError)
            }
        }
        //上传用户头像
        viewModel.mPostPortraitResultStatus.observe(this){
            if (it.showEnd) {
                ToastUtil.showToast("更改图片成功")
                }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(it.showError)
            }
        }
    }

    /**
     * 获取图片
     *
     */
    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
     fun getPicture(){
        Matisse.from(this)
            .choose(MimeType.ofImage())
            .countable(false)
            .maxSelectable(1)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(GlideEngine())
            .forResult(REQUEST_CODE_CHOOSE)
    }

    /**
     * 拒绝
     *
     */
    @OnPermissionDenied(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun onGalleryDenied(){
        ToastUtil.showToast("未授权权限，部分功能不能使用")
        Timber.d("拒绝")
    }

    /**
     * 不再询问
     *
     */
    @OnNeverAskAgain(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun onGalleryAskAgain() {
        Timber.d("不再询问")

        mDialog = CustomDialog(activity, "未获取权限", "是否设置权限？", {
            //取消

            mDialog.dismiss()
        }, {
            //确认
            jumpSetting()
            mDialog.dismiss()
        }, "取消", "确认")
        mDialog.setCanotBackPress()
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
    }


    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK){
            val mSelect:List<Uri> = Matisse.obtainResult(data)
            Glide.with(this).load(mSelect[0]).into(personalImage) //加载图片

            val path = UriToFilePathUtil.uriToFileQ(requireActivity(),mSelect[0]).toString()
            val file = File(path)
//            val requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file) //构建图片Body

            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull()) //构建图片Body
            val body: MultipartBody.Part = MultipartBody.Part.createFormData("portrait", file.name, requestBody)
            viewModel.postPortrait(body)

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


}