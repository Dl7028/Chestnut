package com.yks.chestnutyun.views.files

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import com.yks.chestnutyun.R
import com.yks.chestnutyun.adaper.*
import com.yks.chestnutyun.adaper.VIDEO_PAGE_INDEX
import com.yks.chestnutyun.views.base.BaseFragment
import com.yks.chestnutyun.databinding.FragmentTabViewPagerBinding
import com.yks.chestnutyun.utils.*
import com.yks.chestnutyun.utils.ACCEPTED_ALL
import com.yks.chestnutyun.viewmodels.FilesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tab_view_pager.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.io.File

/**
 * @Description:    管理 ViewPager 的fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/30 11:14
 */
@AndroidEntryPoint
class TabFilesViewPagerFragment : BaseFragment() {
    private lateinit var viewPagerBinding: FragmentTabViewPagerBinding

    private val viewModel: FilesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewPagerBinding = FragmentTabViewPagerBinding.inflate(inflater,container,false)
        initView()
        return viewPagerBinding.root

    }

    override fun setLayoutResId(): Int  = R.layout.fragment_tab_view_pager

    override fun initView() {
        val pagerAdapter = ChestnutPagerAdapter(this)
        val tabLayout = viewPagerBinding.tabs
        val viewPager = viewPagerBinding.viewPager
        viewPager.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->     //设置tabLayout
            tab.text = getTabTitle(position)
        }.attach()
        viewPagerBinding.selectDocumentButton.setOnClickListener{
            selectDocument.launch(ACCEPTED_DOCUMENT)
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
        viewModel.mPostFileResultStatus.observe(this){
//            if (it.showLoading) showProgressDialog(R.string.post_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                EventBus.getDefault().post("增加了文件");
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast( errorMsg)
            }
        }
    }

    private val selectDocument = registerForActivityResult(GetContentWithMimeTypes()){uri->
        if (uri!=null){
            Timber.d("getContent获取到的uri为：${uri.toString()}")
            val file = File(PathUtils.getPath(requireActivity(), uri).toString())
            val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull()) //构建图片Body
            val body: MultipartBody.Part =
                MultipartBody.Part.createFormData("file", file.name, requestBody)
            viewModel.postFile(body)
            Timber.d(file.path)

        }
    }




    //获取tabLayout对应位置的标题
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            ALL_FILES_PAGE_INDEX -> getString(R.string.all_files)
            MUSIC_PAGE_INDEX ->getString(R.string.music)
            PICTURE_PAGE_INDEX ->getString(R.string.picture)
            else -> null
        }
    }


}