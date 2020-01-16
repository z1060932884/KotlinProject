package com.zzj.baselibrary.http

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.Response

//拦截器以及公用参数的处理
class CommonParamsInterceptor : Interceptor {


    val TYPE_JSON: MediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder: Request.Builder = request.newBuilder();
        val token = ""
        val version = "1.0.0"
        builder.addHeader("device-typ", "3")
        builder.addHeader("channel-id", "")
        builder.addHeader("version", version)
        builder.addHeader("token", token)

        return chain.proceed(builder.build())

    }

/*
    fun methodPost(request: Request, builder: Request.Builder): Request {
        val jsonObject = JSONObject()
        var requestBody = request.body
        //有好几种body
        when (requestBody) {
            is FormBody -> {
                val oldBody = request.body as FormBody?
                for (i in 0 until oldBody!!.size()) {
                    jsonObject.put(oldBody.encodedName(i), oldBody.encodedValue(i))
                }
                requestBody = RequestBody.create(TYPE_JSON, JSONObject.toJSONString(jsonObject))
            }
        }

        return builder.post(requestBody).build()
    }*/
}