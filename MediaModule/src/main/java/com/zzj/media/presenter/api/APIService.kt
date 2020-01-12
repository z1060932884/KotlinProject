package com.zzj.media.presenter.api

import io.reactivex.Observable
import retrofit2.http.GET

interface APIService {

    @GET("http://www.baidu.com")
    fun getBaidu() : Observable<String>
}