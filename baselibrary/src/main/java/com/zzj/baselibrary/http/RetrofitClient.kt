package com.zzj.baselibrary.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient{
    val CONNECT_TIME_OUT = 100
    val READ_TIME_OUT = 100
    val WRITE_TIME_OUT = 100
    val ws_url = "ws://192.168.3.6:8088/ws"
    var baseUrl = "http://192.168.3.6:8080/smile/"
    //    public static String baseUrl = "http://192.168.0.27:8080/smile/";
    //    public static final String ws_url = "ws://192.168.0.27:8088/ws";
    //    public static String baseUrl = "http://106.12.22.215:8080/smile/";
    //    public static final String ws_url = "ws://106.12.22.215:8088/ws";
    var web_baseUrl = "http://106.12.22.215:8080/bengshiwei-html/"


    private var retrofit: Retrofit? = null

    var loggingInterceptor =
        HttpLoggingInterceptor()

    companion object {
        val instance: RetrofitClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitClient()
        }
    }

    init {

        val commonParamsInterceptor = CommonParamsInterceptor()
        //打印retrofit日志
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(commonParamsInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();
    }


    fun <T> create(service: Class<T>): T {
        return retrofit!!.create(service)
    }

}
