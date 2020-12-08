package com.yks.chestnutyun.adaper

import android.graphics.BitmapFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yks.chestnutyun.R
import com.yks.chestnutyun.data.bean.FileItem
import com.yks.chestnutyun.utils.ActivityHelper
import timber.log.Timber
import java.io.File


/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/12/6 23:05
 */
class FileListAdapter(layoutResId: Int) : BaseQuickAdapter<FileItem, BaseViewHolder>(layoutResId) {



    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * 实现此方法，并使用 helper 完成 item 视图的操作
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(holder: BaseViewHolder, item: FileItem) {
        holder.apply {
            setText(R.id.item_file_name, item.filename)
            setText(R.id.item_file_size, item.size)
            setText(R.id.item_file_date, item.updateTime)
        }
        val name = item.filename
        val imageView: ImageView = holder.getView(R.id.item_file_image)


        if(name.endsWith("jpeg")||name.endsWith("phg")||name.endsWith("jpg")){
            Glide.with(ActivityHelper.getCurrentActivity()).load(R.mipmap.picture).into(imageView)
        }

        if(name.endsWith("docx")||name.endsWith("doc")){
            Glide.with(ActivityHelper.getCurrentActivity()).load(R.mipmap.doc).into(imageView)
        }
        if (name.endsWith("pdf")){
            Glide.with(ActivityHelper.getCurrentActivity()).load(R.mipmap.pdf).into(imageView)
        }


    }
}