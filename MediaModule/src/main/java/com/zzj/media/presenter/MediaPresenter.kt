package com.zzj.media.presenter

import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.http.RetrofitClient
import com.zzj.baselibrary.rx.exceptionTransformer
import com.zzj.baselibrary.rx.transform
import com.zzj.media.presenter.api.APIService
import com.zzj.media.presenter.view.MediaView
import io.reactivex.functions.Consumer
import org.jetbrains.anko.info

class MediaPresenter : BasePresenter<MediaView>() {


    fun getData() {
        RetrofitClient.instance.create(APIService::class.java)
            .getBaidu()
            .compose(exceptionTransformer())
            .transform(lifecycleOwner)
            .subscribe(Consumer {
                info{
                    it
                }
            }, Consumer {
               info { it.message }
            })
        getView().getDataSuccess("hahasdkjaksljd")
    }
}