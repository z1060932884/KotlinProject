package com.zzj.media.presenter

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.rx.transform
import com.zzj.media.data.MovieBean
import com.zzj.media.data.MySection
import com.zzj.media.data.Video
import com.zzj.media.presenter.view.MediaHomeView
import com.zzj.media.utils.subBracketContent
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.functions.Consumer
import org.jetbrains.anko.debug
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.ArrayList

class MediaHomePresenter : BasePresenter<MediaHomeView>(){

    public val BASE_URL = "https://1090ys.com/"
    val bannerList = arrayListOf<MovieBean>()
    val list = ArrayList<MySection>()

    fun getData(){
        var type = ""
        Observable.create<String> {
            val doc:Document
            val connect:Connection = Jsoup.connect(BASE_URL)
            doc = connect.get()
//            LogUtils.e("------>${doc.html()}")

            val elements: Elements = doc.select("div.stui-pannel-bd a")

            elements.forEach {
                val title = it.getElementsByAttribute("title").text()
                val herf = it.attr("href")
                val picture = it.attr("style")
//                LogUtils.e("------>${title}---->${herf}---->${subBracketContent(picture)}")
                val movieBean  = MovieBean()
                movieBean.title = title
                movieBean.url = BASE_URL+herf
                movieBean.picture = subBracketContent(picture)
                bannerList.add(movieBean)
            }

            it.onNext("1")

            disposeList(doc,it)

        }
            .transform(lifecycleOwner)
            .subscribe(Consumer {
                debug { "执行完成" }
                if(it.equals("1")){
                    LogUtils.e(TAG,"banner获取成功")
                    getView().getBannerDataSuccess(bannerList)
                }else{
                    LogUtils.e(TAG,"list获取成功")
                    getView().getListData(list)
                }
            }, Consumer {
                LogUtils.e(TAG,it.message)
                ToastUtils.showShort(it.message)
            })


    }

    private fun disposeList(doc: Document, it: ObservableEmitter<String>) {
//        val elements:Elements =  doc.getElementsByClass("stui-pannel-box clearfix")
        val elements:Elements =  doc.getElementsByClass("stui-pannel-box")

        elements.forEach {

            var title = it.select("h3.title").text()
            if(!StringUtils.isEmpty(title)&&!title.equals("友情链接")){
                if(title.contains(" ")){
                    title = title.split(" ")[0]
                }
                list.add(MySection(true, title, true))
                val element  = it.getElementsByClass("stui-vodlist__thumb lazyload")
                element.forEach {
                    val videoTitle = it.attr("title")
                    val herf = it.attr("href")
                    val picture = it.attr("data-original")
//                    LogUtils.e(TAG, "-----$videoTitle----$herf-----$picture")
                    list.add(MySection(Video(picture, videoTitle)))
                }

            }
            LogUtils.e(TAG, it.select("h3.title").text())
        }
        it.onNext("2")
    }

}