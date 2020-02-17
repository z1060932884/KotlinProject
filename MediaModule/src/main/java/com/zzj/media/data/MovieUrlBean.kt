package com.zzj.media.data

import java.io.Serializable

/**
 * 视频详情里的播放地址列表bean
 */
class MovieUrlBean :Serializable{

    var title = ""

    var url = ""

    var isSelect:Boolean  = false

}