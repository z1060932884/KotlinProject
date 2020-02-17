package com.zzj.media.data

import java.io.Serializable

class UserBean : Serializable {

    var id: Long = 0
    var phone: String? = null
    var username: String? = null
    var password: String? = null
    var nickname: String? = null
    var token: String? = null
    var age: Int = 0
    var description: String? = null
    var gender: Int = 0
    var faceImage: String? = null
}
