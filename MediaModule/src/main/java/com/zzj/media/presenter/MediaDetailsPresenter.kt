package com.zzj.media.presenter

import com.blankj.utilcode.util.LogUtils
import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.http.RetrofitClient
import com.zzj.baselibrary.rx.exceptionTransformer
import com.zzj.baselibrary.rx.transform
import com.zzj.media.api.APIService
import com.zzj.media.presenter.view.MediaDetailsView

class MediaDetailsPresenter : BasePresenter<MediaDetailsView>() {




    fun init() {
    }


    /**
     * 获取详情数据
     */
    fun getDetailsData(id: Long) {
        RetrofitClient.instance.create(APIService::class.java)
            .getVideoDetails(id)
            .compose(exceptionTransformer())
            .transform(lifecycleOwner)
            .subscribe({
                if(it.isSuccess){
                    getView()?.parseDetailsSuccess(it.result)
                }
            }, {
                LogUtils.e(it.message)
            })
    }

    /**
     * 观看次数
     */
    fun watch(title:String){
        RetrofitClient.instance.create(APIService::class.java)
            .watch(title)
            .compose(exceptionTransformer())
            .transform(lifecycleOwner)
            .subscribe {

            }
    }


}
