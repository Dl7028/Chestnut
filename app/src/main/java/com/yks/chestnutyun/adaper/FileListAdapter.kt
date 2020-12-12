package com.yks.chestnutyun.adaper

import android.widget.CheckBox
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yks.chestnutyun.R
import com.yks.chestnutyun.data.bean.FileItem
import com.yks.chestnutyun.utils.ActivityHelper


/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/12/6 23:05
 */
class FileListAdapter(layoutResId: Int) : BaseQuickAdapter<FileItem, BaseViewHolder>(layoutResId) {


    private var filename:String?=null
    private var isCheck= false
    private var map: MutableMap<String, Boolean> = mutableMapOf()




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
            val name = item.filename
            val imageView: ImageView = holder.getView(R.id.item_file_image)
            val checkBox : CheckBox = holder.getView(R.id.checkBox)

            checkBox.setOnCheckedChangeListener(null) ////在初始化checkBox状态和设置状态变化监听事件之前先把状态变化监听事件设置为null

            //然后设置CheckBox状态
            checkBox.isChecked = map.isNotEmpty() && map.containsKey(item.filename)
                //然后设置状态变化监听事件

            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

                /**
                 * Called when the checked state of a compound button has changed.
                 *
                 * @param buttonView The compound button view whose state has changed.
                 * @param isCheckeds  The new checked state of buttonView.
                 */
                if (isChecked) {
                    map[item.filename] = true  //存储checkbox 选中时的文件名
                } else {
                    map.remove(item.filename) //移除未选中的文件名
                }
            }


            if(name.endsWith("jpeg")||name.endsWith("phg")||name.endsWith("jpg")){
                Glide.with(ActivityHelper.getCurrentActivity()).load(R.mipmap.picture).into(
                    imageView
                )
            }
            if(name.endsWith("docx")||name.endsWith("doc")){
                Glide.with(ActivityHelper.getCurrentActivity()).load(R.mipmap.doc).into(imageView)
            }
            if (name.endsWith("pdf")){
                Glide.with(ActivityHelper.getCurrentActivity()).load(R.mipmap.pdf).into(imageView)
            }

            }
        }



    /**
     * 长按时设计点击事件为checkbox 的点击事件
     *
     * @param name
     */
    fun setCheck(name: String){
        if (map.containsKey(name)){
            map.remove(name)
        }else{
            map[name] = true
        }
    }

    /**
     * 取消时清空，map
     *
     */
    fun removeAll(){
        map.clear()
    }

    /**
     * 获取选中的长度
     *
     * @return
     */
    fun getCheckedSize(): Int = map.size

    /**
     * 全选
     *
     */
    fun addAll(filenames: ArrayList<String>){
        for (filename in filenames) {
            map[filename] =true
        }
    }

    /**
     * 获取所有选中的文件名
     *
     * @return
     */
    fun getCheckedList():ArrayList<String>{
         val checkedFileList = ArrayList<String>()

        for ((key, value) in map) {
            if (value) {
                checkedFileList.add(key)
            }
        }
        return checkedFileList
    }

    /**
     * 多选删除后，设移除map中的删除的文件名
     *
     * @param list
     */
    fun setNotChecked(lists: ArrayList<String>){
        for(filename in lists){
            map.remove(filename)
        }
    }

    /**
     * 移除map中重命名的文件
     *
     * @param filename
     */
    fun setNotCheckedRename(filename: String){
        map.remove(filename)

    }
}
