package com.zzj.media.presenter

import com.blankj.utilcode.util.LogUtils
import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.http.BaseObserver
import com.zzj.baselibrary.http.HttpResult
import com.zzj.baselibrary.http.ResponseThrowable
import com.zzj.baselibrary.http.RetrofitClient
import com.zzj.baselibrary.rx.exceptionTransformer
import com.zzj.baselibrary.rx.transform
import com.zzj.media.api.APIService
import com.zzj.media.data.MovieBean
import com.zzj.media.data.MySection
import com.zzj.media.presenter.view.MediaHomeView
import java.util.*

class MediaHomePresenter : BasePresenter<MediaHomeView>(){


    val bannerList = arrayListOf<MovieBean>()
    val list = ArrayList<MySection>()

    fun getData(){
        RetrofitClient.instance.create(APIService::class.java)
            .getHomeList()
            .compose(exceptionTransformer())
            .transform(lifecycleOwner)
            .subscribe(object : BaseObserver<HttpResult<List<MySection>>>(){
                override fun showDialog() {
//                    getView()?.onLoading("")
                    LogUtils.e(TAG,"loading")
                }

                override fun onNext(t: HttpResult<List<MySection>>) {
                    if(t.isSuccess){
                        t.result?.let { getView()?.getListData(it) }
                    }else{
                        t.message?.let { getView()?.onError(0, it) }
                    }

                    LogUtils.e(TAG,"onNext")

                }

                override fun hideDialog() {
                    getView()?.onDismiss()
                    LogUtils.e(TAG,"hideDialog")
                }

                override fun onError(e: ResponseThrowable?) {
                    LogUtils.e(TAG,e?.message)
                }

            })

    }



}