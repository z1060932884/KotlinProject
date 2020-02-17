package com.zzj.media.presenter

import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.http.BaseObserver
import com.zzj.baselibrary.http.HttpResult
import com.zzj.baselibrary.http.ResponseThrowable
import com.zzj.baselibrary.http.RetrofitClient
import com.zzj.baselibrary.rx.exceptionTransformer
import com.zzj.baselibrary.rx.transform
import com.zzj.media.api.APIService
import com.zzj.media.data.MovieDetailsBean
import com.zzj.media.presenter.view.MediaSearchView

class MediaSearchPresenter :BasePresenter<MediaSearchView>(){


    fun search(content:String){

        RetrofitClient.instance.create(APIService::class.java)
            .search(content)
            .compose(exceptionTransformer())
            .transform(lifecycleOwner)
            .subscribe(object :BaseObserver<HttpResult<List<MovieDetailsBean>>>(){
                override fun onNext(t: HttpResult<List<MovieDetailsBean>>) {
                    if(t.isSuccess){
                        t.result?.let { it1 -> getView()?.searchSuccess(it1) }
                    }

                }

                override fun hideDialog() {
                    getView()?.onDismiss()
                }

                override fun onError(e: ResponseThrowable?) {
                }

                override fun showDialog() {
                    getView()?.onLoading("")
                }

            })
    }

}