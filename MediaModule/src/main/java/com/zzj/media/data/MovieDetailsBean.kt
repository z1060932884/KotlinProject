package com.zzj.media.data

class MovieDetailsBean{

    /**
     * 图片
     */
    var moviePicture:String? = null
    /**
     * 标题
     */
    var movieTitle:String? = null
    /**
     * 导演
     */
    var movieDirector:String? = null
    /**
     * 演员
     */
    var movieActor:String? = null
    /**
     * 类型
     */
    var movieType:String? = null
    /**
     * 简介
     */
    var movieBrief:String? = null
    /**
     * 播放地列表
     */
    var playerUrls: ArrayList<MovieBean>? = null
}
