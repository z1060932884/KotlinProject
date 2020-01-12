package com.zzj.baselibrary.http

//返回值的统一处理基类
class HttpResult<T> {

    var code: Int = 0
    var message: String? = null
    var result: T? = null

    val isSuccess: Boolean
        get() = if (code == 0) true else false

}