package com.zzj.media.api

import io.reactivex.Observable
import retrofit2.http.GET

interface APIService {

    @GET("http://www.baidu.com")
    fun getBaidu() : Observable<String>
    @GET("https://1090ys.com/play/3726~0~0.html")
    fun get1090():Observable<String>
}