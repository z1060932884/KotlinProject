package com.zzj.media.presenter

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.rx.transform
import com.zzj.media.data.MovieBean
import com.zzj.media.presenter.view.MediaHomeView
import com.zzj.media.utils.subBracketContent
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.jetbrains.anko.debug
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class MediaHomePresenter : BasePresenter<MediaHomeView>(){

    public val BASE_URL = "https://1090ys.com/"


    fun getData(){
        var type = ""
        Observable.create<List<MovieBean>> {
            val doc:Document
            val connect:Connection = Jsoup.connect(BASE_URL)
            doc = connect.get()
            LogUtils.e("------>${doc.html()}")

            val elements: Elements = doc.select("div.stui-pannel-bd a")
            val bannerList = arrayListOf<MovieBean>()
            elements.forEach {
                val title = it.getElementsByAttribute("title").text()
                val herf = it.attr("href")
                val picture = it.attr("style")
                LogUtils.e("------>${title}---->${herf}---->${subBracketContent(picture)}")
                val movieBean  = MovieBean()
                movieBean.title = title
                movieBean.url = BASE_URL+herf
                movieBean.picture = subBracketContent(picture)
                bannerList.add(movieBean)
            }
            type = "1"
            it.onNext(bannerList)

        }
            .transform(lifecycleOwner)
            .subscribe(Consumer {
                debug { "执行完成" }
                if(type.equals("1")){
                    getView().getBannerDataSuccess(it)
                }
            }, Consumer {
                LogUtils.e(TAG,it.message)
                ToastUtils.showShort(it.message)
            })


    }

}