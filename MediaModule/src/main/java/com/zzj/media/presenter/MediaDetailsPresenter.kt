package com.zzj.media.presenter

import com.zzj.baselibrary.base.BasePresenter
import com.zzj.media.presenter.view.MediaDetailsView

class MediaDetailsPresenter : BasePresenter<MediaDetailsView>(){



    fun getDetailsData(url:String){
//        Observable.create<MovieBean> {
//            val connect = Jsoup.connect("https://1090ys.com/play/3726~0~0.html")
//            connect.header(
//                "Accept",
//                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
//            )
//            connect.header("Accept-Encoding", "gzip, deflate, sdch")
//            connect.header("Accept-Language", "zh-CN,zh;q=0.8")
//            connect.header(
//                "User-Agent",
//                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"
//            )
//            val document = connect.get()
//            val elements = document.select("dplayer-video-wrap")
//
//            LogUtils.e(TAG,document.html())
//            LogUtils.e(TAG,elements.text())
//        }
//            .transform(lifecycleOwner)
//            .subscribe(Consumer {
//                LogUtils.e(TAG,"执行完成")
//            }, Consumer {
//                LogUtils.e(TAG,it.message)
//            })



    }
}
