package com.zzj.media.presenter.view

import com.zzj.baselibrary.base.BaseView
import com.zzj.media.data.MovieDetailsBean

interface MediaDetailsView :BaseView{

    /**
     * 解析视频播放网页成功
     */

    fun parseVideoUrlSuccess( url:String);

    /**
     * 解析视频详情数据成功
     */
    fun parseDetailsSuccess( movieDetailBean: MovieDetailsBean)
    /**
     * 获取播放地址成功
     */
    fun getPlayUrlSuccess(url: String)
}