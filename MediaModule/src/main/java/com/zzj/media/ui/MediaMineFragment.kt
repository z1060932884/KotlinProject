package com.zzj.media.ui

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zzj.baselibrary.base.BaseFragment
import com.zzj.baselibrary.utils.MMKVUtils
import com.zzj.media.R
import kotlinx.android.synthetic.main.media_fragment_mine.*

class MediaMineFragment : BaseFragment(){
    override fun initListener() {
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {

        return R.layout.media_fragment_mine
    }

    override fun initData() {
        if(MMKVUtils.getInstance().getLong("user_id",0L) != 0L){
            Glide.with(this).load(MMKVUtils.getInstance().getString("face_image"))
                .apply(RequestOptions().placeholder(R.drawable.default_head)).into(ivHeader)
            tvName.setText(MMKVUtils.getInstance().getString("user_name"))
        }
    }

    override fun initView() {
        //点击头像
        ivHeader.setOnClickListener {
            if(MMKVUtils.getInstance().getLong("user_id",0L) == 0L){
                _mActivity.startWithPop(MediaLoginFragment())
            }else{
                //上传头像

            }
        }
        //点击昵称
        tvName.setOnClickListener {
            if(MMKVUtils.getInstance().getLong("user_id",0L) == 0L){
                _mActivity.startWithPop(MediaLoginFragment())
            }else{

            }
        }


    }

}
