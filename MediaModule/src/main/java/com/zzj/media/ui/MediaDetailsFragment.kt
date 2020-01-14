package com.zzj.media.ui

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.webkit.*
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.tencent.smtt.sdk.TbsVideo
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.presenter.MediaDetailsPresenter
import com.zzj.media.presenter.view.MediaDetailsView
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class MediaDetailsFragment : BaseMvpFragment<MediaDetailsPresenter>(),MediaDetailsView{

    var url = "";
    var webView: WebView? = null
    fun newInstance(url:String ): MediaDetailsFragment {
        val args = Bundle()
        val fragment = MediaDetailsFragment()
        args.putString("url",url)
        fragment.setArguments(args)
        return fragment
    }

    override fun createPresenter(): MediaDetailsPresenter {
        return MediaDetailsPresenter()
    }

    override fun attachPresenterView() {
        mPresenter.attachView(this)
    }

    override fun initListener() {
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.media_fragemnt_details
    }

    override fun initData() {
        url = arguments?.getString("url").toString()
        mPresenter.getDetailsData(url)
    }

    override fun initView() {
        webView =  WebView(mActivity);
        // 设置WebView属性，能够执行Javascript脚本
        webView!!.getSettings().setJavaScriptEnabled(true)
        // 设置可以访问文件
//        webView!!.settings.allowFileAccess = true
//        webView!!.settings.allowContentAccess = true
        webView!!.settings.domStorageEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        }
        webView!!.settings.mixedContentMode =  android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        webView!!.addJavascriptInterface(MyJavaScriptInterface(), "HTMLOUT")
        webView!!.setWebViewClient(object : WebViewClient() {


            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
                webView!!.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                url:  String?
            ): WebResourceResponse? {
                return super.shouldInterceptRequest(view, url)
                LogUtils.e(TAG,url)
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)

                LogUtils.e(TAG,errorResponse!!.data)

            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                super.onReceivedSslError(view, handler, error)
                if(error!!.primaryError == android.net.http.SslError.SSL_INVALID ){// 校验过程遇到了bug
                    handler!!.proceed()
                }else{
                    handler!!.cancel()
                }

            }
        })
        webView!!.loadUrl("https://1090ys.com/play/3726~0~0.html")

//        playVideo("http://tj-ctfs.ftn.qq.com/%E7%88%B1%E6%83%85%E5%85%AC%E5%AF%935_1920x1080_%E7%88%B1%E6%83%85%E5%85%AC%E5%AF%935%E7%AC%AC1%E9%9B%86.mp4?ver=5199&rkey=6f7d35e75875a47ff62ff7af1b14892eabebfab595780e906f3eee244e805e93bb3538aa212d72c7e0c66dc2259b4a9b08d43e2fccba5f629a41861539618354")
    }
    internal inner class MyJavaScriptInterface {
        @JavascriptInterface
        fun processHTML(html: String) {
            // 注意啦，此处就是执行了js以后 的网页源码
            LogUtils.e(TAG,html)
            val document = Jsoup.parse(html)

        }
    }

    private fun playVideo(url: String) {

        if (TbsVideo.canUseTbsPlayer(activity)) {
            LogUtils.e("getLoclUrl---->" + url)
            val extraData = Bundle()
            extraData.putInt("screenMode", 102)
            TbsVideo.openVideo(context, url, extraData)

        }
    }
}