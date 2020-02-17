package com.zzj.media.api

import com.zzj.baselibrary.http.HttpResult
import com.zzj.media.data.BtBean
import com.zzj.media.data.MovieBean
import com.zzj.media.data.MovieDetailsBean
import com.zzj.media.data.MySection
import com.zzj.media.utils.BASE_URL
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {



    @GET("http://www.baidu.com")
    fun getBaidu() : Observable<String>
    @GET("https://1090ys.com/play/3726~0~0.html")
    fun get1090():Observable<String>

    /**
     * 获取播放地址
     */
    @GET("http://mintehao.com/niuxyun.php")
    fun getPlayUrl(@Query("id") id:String) :Observable<String>
    /**
     * 获取首页列表
     */
    @GET(BASE_URL+"smile/video/home")
    fun getHomeList() :Observable<HttpResult<List<MySection>>>
    /**
     * 获取首页列表
     */
    @GET(BASE_URL+"smile/video/listByType")
    fun getMovieList(@Query("page") page:Int, @Query("type") type:String) :Observable<HttpResult<List<MovieBean>>>
    /**
     * 获取视频详情
     */
    @GET(BASE_URL+"smile/video/details")
    fun getVideoDetails(@Query("id") id:Long) :Observable<HttpResult<MovieDetailsBean>>
    /**
     * 获取视频详情
     */
    @GET(BASE_URL+"smile/video/btVideo")
    fun btVideo(@Query("page") page:Int) :Observable<HttpResult<List<BtBean>>>
    /**
     * 视频搜索
     */
    @GET(BASE_URL+"smile/video/search")
    fun search(@Query("name") name:String) :Observable<HttpResult<List<MovieDetailsBean>>>
    /**
     * 视频观看
     */
    @GET(BASE_URL+"smile/video/watch")
    fun watch(@Query("name") name:String) :Observable<HttpResult<String>>
}