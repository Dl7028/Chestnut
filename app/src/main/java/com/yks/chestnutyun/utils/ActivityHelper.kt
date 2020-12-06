package com.yks.chestnutyun.utils

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.yks.chestnutyun.adaper.ActivityLifecycleCallbacksAdapter
import java.util.*

/**
  * @Description:
  * @Author:         Yu ki-r
  * @CreateDate:     2020/11/30 15:50
 */
object ActivityHelper {

        private val activityStack = Stack<Activity>()
        private val INSTANCE = MyActivityLifecycleCallbacks()
        fun getActivityLifecycleCallbacks(): Application.ActivityLifecycleCallbacks {
            return INSTANCE
        }

    /**
     * 不用 finish 当前 Activity 时直接调用此方法
     *
     * @param classes 需要跳转到的 activity
     */
        fun finishActivity(activity: Activity?) {
            if (activity != null) {
                activityStack.remove(activity)
                activity.finish()
            }
        }

    /**
         * startActivityForResult
         * @param classes
         * @param requestCode
         */
        fun startActivityForResult(classes: Class<*>?, requestCode: Int) {
            startActivityForResult(classes, requestCode)
        }

    /**
     * 需要 finish 当前 Activity 时调用此方法，布尔值参数传入 true
     *
     * @param classes  需要打开的 activity
     * @param isFinish 是否 finish 当前 activity
     */
        @JvmOverloads
        fun startActivity(classes: Class<*>?, isFinish: Boolean = false) {
            val currentActivity = getCurrentActivity()
            val intent = Intent(currentActivity, classes)
            currentActivity!!.startActivity(intent)
            if (isFinish) {
                finishActivity(currentActivity)
            }
        }

        /**
         * 启动 activity， 带上参数
         *
         * @param classes 需要打开的 activity
         * @param hashMap 需要传递的参数
         */
        @JvmOverloads
        fun startActivity(
            classes: Class<*>?,
            hashMap: HashMap<String?, String?>,
            isFinish: Boolean = false
        ) {
            val currentActivity = getCurrentActivity()
            val intent = Intent(currentActivity, classes)
            for ((key, value) in hashMap) {
                intent.putExtra(key, value)
            }
            currentActivity!!.startActivity(intent)
            if (isFinish) {
                finishActivity(currentActivity)
            }
        }

        /**
         * 关闭所有 Activity
         */
        fun closeAllActivity() {
            while (true) {
                val activity = getCurrentActivity() ?: return
                finishActivity(activity)
            }
        }

        /**
         * 得到当前的 Activity
         *
         * @return 当前 Activity
         */
        fun getCurrentActivity(): Activity {
            var activity: Activity? = null
            if (!activityStack.isEmpty()) {
                activity = activityStack.peek()
            }
            return activity!!
        }

        private class MyActivityLifecycleCallbacks() :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activityStack.remove(activity)
                activityStack.push(activity)
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                activityStack.remove(activity)
            }
        }



}
