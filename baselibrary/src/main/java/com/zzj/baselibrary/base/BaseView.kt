package com.zzj.baselibrary.base

interface BaseView {


    /**
     * Loading弹窗
     */
    fun onLoading(message:String)

    /**
     * 错误消息
     */
    fun onError(code:Int,message: String)

    /**
     * 关闭Loading弹窗
     */
    fun onDismiss()
}