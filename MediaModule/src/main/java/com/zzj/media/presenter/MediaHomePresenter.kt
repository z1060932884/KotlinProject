package com.zzj.media.presenter

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.rx.transform
import com.zzj.media.presenter.view.MediaHomeView
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class MediaHomePresenter : BasePresenter<MediaHomeView>(){



    fun getData(){

        Observable.create<String> {
            val doc:Document
            val connect:Connection = Jsoup.connect("https://1090ys.com/")
            doc = connect.get()
            LogUtils.e("------>${doc.html()}")

            val elements: Elements = doc.select("div.stui-pannel-bd a")
            elements.forEach {
                val title = it.getElementsByAttribute("title").text()
                var herf = it.attr("href")
                LogUtils.e("------>${title}---->${herf}")
            }
        }
            .transform(lifecycleOwner)
            .subscribe(Consumer {

            }, Consumer {
                ToastUtils.showShort(it.message)
            })


    }

}