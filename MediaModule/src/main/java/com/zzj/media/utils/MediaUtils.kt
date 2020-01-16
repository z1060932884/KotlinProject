package com.zzj.media.utils

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern


fun subBracketContent(content:String):String{

    var list = ""
    val pattern:Pattern = Pattern.compile("(?<=url\\()[^\\)]+")
    val matcher:Matcher = pattern.matcher(content)
    while (matcher.find()){
        list = matcher.group()
    }
    return list
}
fun subVideoContent(content:String):String{

    var list = ""
//    val pattern:Pattern = Pattern.compile("(?<=url\\=)[^\\,]+")
    val matcher:Matcher = Patterns.WEB_URL.matcher(content)
    while (matcher.find()){
        list = matcher.group()
    }
    return list
}


