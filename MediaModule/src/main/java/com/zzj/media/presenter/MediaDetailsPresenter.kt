package com.zzj.media.presenter

import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.Utils
import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.logcollector.LogCollector
import com.zzj.baselibrary.rx.transform
import com.zzj.media.data.MovieBean
import com.zzj.media.data.MovieDetailsBean
import com.zzj.media.presenter.view.MediaDetailsView
import com.zzj.media.utils.BASE_URL
import com.zzj.media.utils.subVideoContent
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*
import kotlin.concurrent.timerTask

class MediaDetailsPresenter : BasePresenter<MediaDetailsView>(){

    private var mDisposable: Disposable? = null
    //获取日志
    private var logCollector: LogCollector? = null
    //视频播放网页
    private var videoUrl = "";
    private var detailsBean:MovieDetailsBean? = null

    private var timer : Timer? = null
    var timerTask:TimerTask = timerTask  {
        getVideoUrl()
    }
    fun init(){
        logCollector= LogCollector.getInstance(Utils.getApp())
            .setCleanCache(true)
            .setStringWithType("videopreload", null)

        timer = Timer()

    }



    fun getDetailsData(url:String){
        Observable.create<Int> {
            val connect = Jsoup.connect(url)
            connect.header(
                "Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
            )
            connect.header("Accept-Encoding", "gzip, deflate, sdch")
            connect.header("Accept-Language", "zh-CN,zh;q=0.8")
            connect.header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"
            )
            val document = connect.get()
            parseDetailsData(document,it)
        }
            .transform(lifecycleOwner)
            .subscribe(Consumer {
                if(it == 1){
                    //解析播放网页成功
                    getView().parseVideoUrlSuccess(videoUrl)
                }else if(it == 2){
                    getView().parseDetailsSuccess(detailsBean!!)
                }
                LogUtils.e(TAG,"执行完成")
            }, Consumer {
                LogUtils.e(TAG,it.message)
            })

    }

    /**
     * 解析详情页的数据
     */
    private fun parseDetailsData(document: Document, emitter: ObservableEmitter<Int>) {
        val imageElement = document.selectFirst("div.stui-content__thumb a")
        val titleElement = document.selectFirst("div.stui-content__detail h3")
        val directorElement = document.select("div.stui-content__detail p")
        val title = titleElement.text()
        var director = ""
        directorElement.forEach {
            director += it.text()+"\n"
        }

        val image = imageElement.attr("data-original")
        LogUtils.e(TAG,title,director,image)
         detailsBean = MovieDetailsBean()
        detailsBean?.moviePicture = image
        detailsBean?.movieTitle = title
        detailsBean?.movieDirector= director

        //解析播放地址 play_1
        val playUrlElement = document.getElementById("play_1")
        val playUrlElements = playUrlElement.getElementsByTag("a")
        var movieBeans = arrayListOf<MovieBean>()
        playUrlElements.forEach {
            val title = it.attr("title")
            val url = it.attr("href")
            val movieBean = MovieBean()
            movieBean.url = BASE_URL+url
            movieBean.title = title
            movieBeans.add(movieBean)
        }
        detailsBean?.playerUrls = movieBeans
        emitter.onNext(2)
    }

    /**
     * 解析播放网页
     */
    private fun parsePlayVideoUrl(document: Document, emitter: ObservableEmitter<Int>) {
        val elements = document.select("div.play-btn a")
        elements.forEach {
          LogUtils.e(TAG,it.attr("href"))
            videoUrl = BASE_URL+it.attr("href")
            emitter.onNext(1)
        }

    }

    /**
     * 定时获取播放地址
     */
    public fun timerVideoUrl(){
        //停止日志获取
        logCollector?.onStop()
        logCollector?.start()
        timer?.schedule(timerTask,0,1000)
        /*Observable.interval(1000,1000, TimeUnit.MILLISECONDS)
            .transform(lifecycleOwner)
            .subscribe(object : Observer<Long> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onNext(t: Long) {

                }

                override fun onError(e: Throwable) {
                }

            })*/
    }


    fun getVideoUrl(){
        val  cacheFile = logCollector?.cacheFile
        if(cacheFile != null){
            var content = FileIOUtils.readFile2String(cacheFile)
            content = subVideoContent(content)
            LogUtils.e(TAG,content)
            if(!StringUtils.isEmpty(content)){
                //获取播放地址成功
                getView().getPlayUrlSuccess(content)
                mDisposable?.dispose()
                //停止日志获取
                logCollector?.onStop()
            }
        }
    }
}
