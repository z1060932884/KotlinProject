package com.zzj.media.ui

import android.os.Build
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.LogUtils
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.presenter.MediaDetailsPresenter
import com.zzj.media.presenter.view.MediaDetailsView


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
        webView!!.getSettings().setAllowFileAccess(true)
        webView!!.getSettings().setAllowFileAccessFromFileURLs(true)
        webView!!.getSettings().setAllowContentAccess(true)
        webView!!.getSettings().setDomStorageEnabled(true)
        webView!!.getSettings().setAppCacheEnabled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView!!.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
        }

        webView!!.addJavascriptInterface(MyJavaScriptInterface(), "HTMLOUT")
        webView!!.setWebViewClient(object : WebViewClient() {


            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
                webView!!.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
            }
        })
        webView!!.loadUrl("https://1090ys.com/play/3726~0~0.html")
    }
    internal inner class MyJavaScriptInterface {
        @JavascriptInterface
        fun processHTML(html: String) {
            // 注意啦，此处就是执行了js以后 的网页源码
            LogUtils.e(TAG,html)
        }
    }
}