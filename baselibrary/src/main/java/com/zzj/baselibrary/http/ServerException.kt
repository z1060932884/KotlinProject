package com.zzj.baselibrary.http

class ServerException(val code: Int, val msg: String) : RuntimeException()

