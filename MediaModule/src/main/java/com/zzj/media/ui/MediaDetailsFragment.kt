package com.zzj.media.ui

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.tencent.smtt.sdk.TbsVideo
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.presenter.MediaDetailsPresenter
import com.zzj.media.presenter.view.MediaDetailsView
import kotlinx.android.synthetic.main.media_fragemnt_details.view.*




class MediaDetailsFragment : BaseMvpFragment<MediaDetailsPresenter>(),MediaDetailsView{

    var url = ""


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

        rootView.webView.loadUrl("https://1090ys.com/play/3726~0~0.html")

//        playVideo("http://tj-ctfs.ftn.qq.com/%E7%88%B1%E6%83%85%E5%85%AC%E5%AF%935_1920x1080_%E7%88%B1%E6%83%85%E5%85%AC%E5%AF%935%E7%AC%AC1%E9%9B%86.mp4?ver=5199&rkey=6f7d35e75875a47ff62ff7af1b14892eabebfab595780e906f3eee244e805e93bb3538aa212d72c7e0c66dc2259b4a9b08d43e2fccba5f629a41861539618354")
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