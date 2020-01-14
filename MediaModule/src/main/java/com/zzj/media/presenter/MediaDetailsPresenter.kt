package com.zzj.media.presenter

import com.blankj.utilcode.util.LogUtils
import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.rx.transform
import com.zzj.media.data.MovieBean
import com.zzj.media.presenter.view.MediaDetailsView
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.jsoup.Jsoup

class MediaDetailsPresenter : BasePresenter<MediaDetailsView>(){



    fun getDetailsData(url:String){
        Observable.create<MovieBean> {
            val connect = Jsoup.connect(url)
            val document = connect.get()
            LogUtils.e(TAG,document.html())
        }
            .transform(lifecycleOwner)
            .subscribe(Consumer {
                LogUtils.e(TAG,"执行完成")
            }, Consumer {
                LogUtils.e(TAG,it.message)
            })
    }
}
