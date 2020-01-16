package com.zzj.media.presenter

import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.Utils
import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.logcollector.LogCollector
import com.zzj.baselibrary.rx.transform
import com.zzj.media.presenter.view.MediaDetailsView
import com.zzj.media.utils.subVideoContent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class MediaDetailsPresenter : BasePresenter<MediaDetailsView>(){

    private var mDisposable: Disposable? = null
    //获取日志
    private var logCollector: LogCollector? = null

    fun init(){
        logCollector= LogCollector.getInstance(Utils.getApp())
            .setCleanCache(true)
            .setStringWithType("videopreload", null)
        logCollector?.start()
    }
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

    public fun timerVideoUrl(){
        Observable.interval(1000,1000, TimeUnit.MILLISECONDS)
            .transform(lifecycleOwner)
            .subscribe(object : Observer<Long> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onNext(t: Long) {
                    getVideoUrl()
                }

                override fun onError(e: Throwable) {
                }

            })
    }


    fun getVideoUrl(){
        val  cacheFile = logCollector?.cacheFile
        if(cacheFile != null){
            var content = FileIOUtils.readFile2String(cacheFile)
            content = subVideoContent(content)
            LogUtils.e(TAG,content)
            if(!StringUtils.isEmpty(content)){
                mDisposable?.dispose()
                //停止日志获取
                logCollector?.onStop()
            }
        }
    }
}
