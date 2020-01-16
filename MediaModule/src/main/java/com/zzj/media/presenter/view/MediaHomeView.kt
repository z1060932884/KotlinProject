package com.zzj.media.presenter.view

import com.zzj.baselibrary.base.BaseView
import com.zzj.media.data.MovieBean
import com.zzj.media.data.MySection
import java.util.ArrayList

interface MediaHomeView :BaseView{

    fun getBannerDataSuccess(movieBeans: List<MovieBean>)
    fun getListData(list: List<MySection>)

}