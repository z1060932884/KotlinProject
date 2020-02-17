package com.zzj.media.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.tencent.smtt.sdk.TbsVideo
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.adapter.MediaDetailsPlayUrlAdapter
import com.zzj.media.data.MovieDetailsBean
import com.zzj.media.data.MovieUrlBean
import com.zzj.media.presenter.MediaDetailsPresenter
import com.zzj.media.presenter.view.MediaDetailsView
import kotlinx.android.synthetic.main.media_fragemnt_details.*


class MediaDetailsFragment : BaseMvpFragment<MediaDetailsPresenter>(), MediaDetailsView {

    var title:String? = null
    var playUrl = ""

    var id: Long = 0

    var playUrlAdapter: MediaDetailsPlayUrlAdapter? = null

    var detailBean: MovieDetailsBean? = null

    var isExpand: Boolean = false

    override fun parseDetailsSuccess(movieDetailBean: MovieDetailsBean?) {
        loadDetailData(movieDetailBean)
    }


    fun newInstance(id: Long): MediaDetailsFragment {
        val args = Bundle()
        val fragment = MediaDetailsFragment()
        args.putLong("id", id)
        fragment.setArguments(args)
        return fragment
    }

    fun newInstance(id: MovieDetailsBean): MediaDetailsFragment {
        val args = Bundle()
        val fragment = MediaDetailsFragment()
        args.putSerializable("data", id)
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
            for (urlBean in adapter.data){
                (urlBean as MovieUrlBean).isSelect = false
            }
            val movieUrlBean: MovieUrlBean = adapter.data.get(position) as MovieUrlBean
            playUrl = movieUrlBean.url
            webView.loadUrl(movieUrlBean.url)
//            webView.load(playHtml())
            movieUrlBean.isSelect = true
            adapter.notifyDataSetChanged()
            //请求观看次数
            mPresenter.watch(title!!)
//            playVideo(movieUrlBean.url)
        }
        tvMore.setOnClickListener {
            if(isExpand){
                tvContent.visibility = View.GONE
            }else{
                tvContent.visibility = View.VISIBLE
            }
            isExpand = !isExpand
        }
    }


    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.media_fragemnt_details
    }

    override fun initData() {
        id = arguments?.getLong("id")!!
        detailBean = arguments?.getSerializable("data") as MovieDetailsBean?
        if (detailBean != null) {
            loadDetailData(detailBean)
        }
        if (id != 0L) {
            mPresenter.getDetailsData(id)
        }

    }

    override fun initView() {
        //初始化recyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
        playUrlAdapter = MediaDetailsPlayUrlAdapter(R.layout.meida_item_details_play_url)
        recyclerView.adapter = playUrlAdapter

        val data = Bundle()

        data.putBoolean("standardFullScreen", false)
//true表示标准全屏，false表示X5全屏；不设置默认false，

        data.putBoolean("supportLiteWnd", true)
//false：关闭小窗；true：开启小窗；不设置默认true，

        data.putInt("DefaultVideoScreen", 1)
//1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

        webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data)
        webView.webViewClient = WebClient()
    }


    /**
     * 加载详情数据到界面
     */
    private fun loadDetailData(movieDetailBean: MovieDetailsBean?) {
        llRoot.visibility = View.VISIBLE
        Glide.with(mActivity).load(movieDetailBean!!.picture)
            .into(ivImage as ImageView)
        title = movieDetailBean.title
        tvTitle.setText(movieDetailBean.title)
        tvStatus.setText(movieDetailBean.type + " · ${movieDetailBean.status}/${movieDetailBean.remark}")
        tvContent.setText(
            "导演 : ${movieDetailBean.director}\n演员 : ${movieDetailBean.actor} " +
                    "\n地区 : ${movieDetailBean.area}\n更新时间 : ${movieDetailBean.updateTime}" +
                    "\n上映时间 : ${movieDetailBean.showTime}\n简介 : ${movieDetailBean.synopsis}"
        )
        val playUrlList: List<String> =
            GsonUtils.fromJson<List<String>>(movieDetailBean.playUrlList2, List::class.java)
        var urlBeans = arrayListOf<MovieUrlBean>()
        for (element in playUrlList) {
            if (element.contains("$")) {
                var item = element.split("$")
                var urlBean = MovieUrlBean()
                urlBean.title = item[0]
                urlBean.url = item[1]
                urlBeans.add(urlBean)
            }
        }
        playUrl = urlBeans[0].url
        urlBeans[0].isSelect = true
        webView.loadUrl(playUrl)
        playUrlAdapter?.setNewData(urlBeans)

        //请求观看次数
        mPresenter.watch(title!!)
    }

    /**
     * webView的client
     */
    inner class WebClient : WebViewClient(){

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            ivVideoBg.visibility = View.GONE

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