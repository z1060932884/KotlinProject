package com.zzj.media.data

import java.io.Serializable

class MovieDetailsBean : Serializable{


    var id: Long = 0

    var createAt: String? = null
    var updateAt: String? = null
    /**
     * 名称
     */
    var title: String? = null

    /**
     * 备注
     */
    var remark: String? = null
    /**
     * 演员
     */
    var actor: String? = null

    /**
     * 图片
     */
    var picture: String? = null
    /**
     * 导演
     */
    var director: String? = null
    /**
     * 类型
     */
    var type: String? = null
    /**
     * 地区
     */
    var area: String? = null
    /**
     * 更新时间
     */
    var updateTime: String? = null
    /**
     * 更新集数状态
     */
    var status: String? = null
    /**
     * 语言
     */
    var language: String? = null
    /**
     * 上映时间
     */
    var showTime: String? = null
    /**
     * 简介
     */
    var synopsis: String? = null
    /**
     * 播放地址
     */
    var playUrlList: String? = null
    /**
     * 播放地址2
     */
    var playUrlList2: String? = null
    /**
     * 下载地址
     */
    var downUrlList: String? = null
}
