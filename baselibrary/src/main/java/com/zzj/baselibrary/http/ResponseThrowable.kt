package com.zzj.baselibrary.http

/**
 * Created zzj
 */

class ResponseThrowable(throwable: Throwable, var code: Int) : Exception(throwable) {

    override var message: String? = null
}
