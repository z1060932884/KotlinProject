package com.zzj.media.presenter

import com.zzj.baselibrary.base.BasePresenter
import com.zzj.media.presenter.view.MediaView

class MediaPresenter : BasePresenter<MediaView>(){



    fun getData(){
        getView().getDataSuccess("hahasdkjaksljd")
    }
}