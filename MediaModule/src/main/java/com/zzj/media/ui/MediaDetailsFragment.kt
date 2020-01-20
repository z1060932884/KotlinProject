package com.zzj.media.ui

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.TbsVideo
import com.tencent.smtt.sdk.WebView
import com.ycbjie.webviewlib.X5WebView
import com.ycbjie.webviewlib.X5WebViewClient
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.adapter.MediaDetailsPlayUrlAdapter
import com.zzj.media.data.MovieBean
import com.zzj.media.data.MovieDetailsBean
import com.zzj.media.presenter.MediaDetailsPresenter
import com.zzj.media.presenter.view.MediaDetailsView
import kotlinx.android.synthetic.main.media_fragemnt_details.*


class MediaDetailsFragment : BaseMvpFragment<MediaDetailsPresenter>(), MediaDetailsView {

    var url = ""

    var playUrlAdapter:MediaDetailsPlayUrlAdapter? = null

    var webView:X5WebView? = null

    override fun parseDetailsSuccess(movieDetailBean: MovieDetailsBean) {
        Glide.with(mActivity).load(movieDetailBean!!.moviePicture)
            .into(ivImage as ImageView)
        tvTitle.setText(movieDetailBean.movieTitle)
        tvDirect.setText(movieDetailBean.movieDirector)
        playUrlAdapter?.setNewData(movieDetailBean.playerUrls)
    }




    override fun parseVideoUrlSuccess(url: String) {
        LogUtils.e(TAG, url)
        //开始加载网页  解析播放地址
        mPresenter.timerVideoUrl()

        webView?.loadUrl(url)
    }

    /**
     * 获取播放地址成功
     */
    override fun getPlayUrlSuccess(url: String) {
        LogUtils.e(TAG, url)
//        webView.loadUrl(url)
        playVideo(url)
    }


    fun newInstance(url: String): MediaDetailsFragment {
        val args = Bundle()
        val fragment = MediaDetailsFragment()
        args.putString("url", url)
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
        playUrlAdapter?.setOnItemClickListener { adapter, view, position ->
            //开始加载网页  解析播放地址
            mPresenter.timerVideoUrl()
            webView = X5WebView(mActivity)
            webView?.loadUrl( (adapter.data[position]as MovieBean).url )
        }
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.media_fragemnt_details
    }

    override fun initData() {
        url = arguments?.getString("url").toString()
        mPresenter.getDetailsData(url)

    }

    override fun initView() {
        //初始化log
        mPresenter.init()
        webView = X5WebView(mActivity)
        //初始化recyclerView
        recyclerView.layoutManager = LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false)
        playUrlAdapter = MediaDetailsPlayUrlAdapter(R.layout.meida_item_details_play_url)
        recyclerView.adapter = playUrlAdapter
//        playVideo("http://tj-ctfs.ftn.qq.com/%E7%88%B1%E6%83%85%E5%85%AC%E5%AF%935_1920x1080_%E7%88%B1%E6%83%85%E5%85%AC%E5%AF%935%E7%AC%AC1%E9%9B%86.mp4?ver=5199&rkey=6f7d35e75875a47ff62ff7af1b14892eabebfab595780e906f3eee244e805e93bb3538aa212d72c7e0c66dc2259b4a9b08d43e2fccba5f629a41861539618354")
    }

    private inner class MyX5WebViewClient(webView: X5WebView, context: Context) :
        X5WebViewClient(webView, context) {

        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            view.loadUrl(url)
            return  true
        }

        override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
        ): Boolean {
            view.loadUrl(request.url.toString())
            return true
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