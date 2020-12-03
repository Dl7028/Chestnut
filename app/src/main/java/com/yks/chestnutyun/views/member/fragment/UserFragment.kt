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
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.utils.UriToFilePathUtil
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
@RuntimePermissions
@RequiresApi(Build.VERSION_CODES.Q)
class UserFragment: BaseFragment() {

    private val REQUEST_CODE_CHOOSE: Int = 1001
    private val TAG: String? = "UserFragment"
    private val viewModel: UserViewModel by viewModels()
    private lateinit var mDialog: CustomDialog
    val ACCEPTED_MIMETYPES = arrayOf("image/jpeg", "image/png")


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
        viewModel.getUserInfo(name)


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
            getPictureWithPermissionCheck() //权限访问

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
                }
                //显示图片
                if (it.data?.portrait != null) Glide.with(this).load(it.data.portrait)
                    .into(personalImage)
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(it.showError)
            }
        }
        //上传用户头像
        viewModel.mPostPortraitResultStatus.observe(this) {
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
    fun getPicture() {
        //构造数据，页面跳转
       selectPicture.launch(ACCEPTED_MIMETYPES)
    }

    /**
     * 拒绝
     *
     */
    @OnPermissionDenied(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun onGalleryDenied() {
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
            val path = UriToFilePathUtil.uriToFileQ(requireActivity(), uri).toString()
            val file = File(path)
//            val requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file) //构建图片Body

            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull()) //构建图片Body
            val body: MultipartBody.Part =
                MultipartBody.Part.createFormData("portrait", file.name, requestBody)
            viewModel.postPortrait(body)
        }
    }

    /**
     * 定义协议
     *
     */
    class GetContentWithMimeTypes : ActivityResultContract<Array<String>, Uri?>() {
        override fun createIntent(
            context: Context,
            input: Array<String>
        ): Intent {
            return Intent(Intent.ACTION_GET_CONTENT)
                .addCategory(Intent.CATEGORY_OPENABLE)
                .setType("*/*")
                .putExtra(Intent.EXTRA_MIME_TYPES, input);

        }

        override fun getSynchronousResult(
            context: Context,
            input: Array<String>
        ): SynchronousResult<Uri?>? {
            return null
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return if (intent == null || resultCode != Activity.RESULT_OK) null else intent.data
        }
    }
}