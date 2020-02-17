package com.zzj.media.utils

import android.util.Patterns
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
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
//    val pattern:Pattern = Pattern.compile("(?<=url \\ =)[^\\,]+")
//    val matcher:Matcher = pattern.matcher(content)
    val matcher:Matcher = Patterns.WEB_URL.matcher(content)
    while (matcher.find()){
        list = matcher.group()
    }
    return list
}

fun subVideoUrl(content: String) :String{

    if (StringUtils.isEmpty(content))
        return ""

    var url = ""
    val startStr = "H5VideoPlayer::preload() url = "
    val startIndex = content.indexOf(startStr)

    val endIndex = content.indexOf(", position= 0")
    if(startIndex == -1||endIndex == -1){
        return ""
    }
//    LogUtils.e("content",startIndex,endIndex,content)
    url = content.substring(startIndex+startStr.length,endIndex)
    LogUtils.e("url---->",url)
    return url
}


fun playHtml():String{
    return "<html>\n" +
            "<head>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\" />\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,Chrome=1\" />\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=9\" />\n" +
            "    <title>寄生虫</title>\n" +
            "    <link class=\"dplayer-css\" rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/dplayer/dist/DPlayer.min.css\" />\n" +
            "    <script src=\"https://cdn.jsdelivr.net/npm/flv.js/dist/flv.min.js\"></script>\n" +
            "    <script src=\"https://cdn.jsdelivr.net/npm/hls.js/dist/hls.min.js\"></script>\n" +
            "    <script src=\"https://cdn.jsdelivr.net/npm/dashjs/dist/dash.all.min.js\"></script>\n" +
            "    <script src=\"https://cdn.jsdelivr.net/webtorrent/latest/webtorrent.min.js\"></script>\n" +
            "    <script src=\"https://cdn.jsdelivr.net/npm/dplayer/dist/DPlayer.min.js\"></script>\n" +
            "</head>\n" +
            "<style type=\"text/css\">\n" +
            "    body {\n" +
            "        margin: 0px;\n" +
            "        padding: 0px;\n" +
            "        background: #000;\n" +
            "    }\n" +
            "</style>\n" +
            "<body>\n" +
            "<div id=\"iframe\"></div>\n" +
            "<script type=\"text/javascript\">\n" +
            "\t\tfunction initPlayer(){\n" +
            "\t\t    player = new DPlayer({\n" +
            "\t            container: document.getElementById('iframe'),\n" +
            "\t            video: {\n" +
            "\t                url: 'http://leshi.iqiyi-kuyunzy.com/20190806/17395_3c0aaa85/index.m3u8',\n" +
            "\t                type: 'auto',\n" +
            "\t                autoplay: true,\n" +
            "\t                preload: 'auto'\n" +
            "\t            }\n" +
            "\t        });\n" +
            "\t        player.on('canplay', function () {\n" +
            "                player.play();\n" +
            "                player.fullScreen.request();\n" +
            "            });\n" +
            "\t\t}\n" +
            "\t\tvar player;\n" +
            "\t\tinitPlayer();\n" +
            "  </script>\n" +
            "</body>\n" +
            "</html>"
}


