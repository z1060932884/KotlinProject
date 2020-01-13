package com.zzj.baselibrary.utils;

import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.zzj.baselibrary.R;
import com.zzj.baselibrary.base.BaseMvpActivity;


/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/9 14:48
 * @desc :  toolbar的帮助类
 * @version: 1.0
 */
public class ToolbarHelper {
    private BaseMvpActivity activity;
    private Toolbar toolbar;
    private String title;

    public ToolbarHelper(Context context, Toolbar toolbar, boolean isShow){
        activity = (BaseMvpActivity) context;
        this.toolbar = toolbar;
        isShowNavigationIcon(isShow);
    }
    public ToolbarHelper(Context context, Toolbar toolbar, String title, boolean isShow){
        activity = (BaseMvpActivity) context;
        this.toolbar = toolbar;
        this.title = title;
        initToolbar(isShow);
    }
    public ToolbarHelper(Context context, Toolbar toolbar, String title){
        activity = (BaseMvpActivity) context;
        this.toolbar = toolbar;
        this.title = title;
        initToolbar(true);
    }

    public void initToolbar(boolean isShow){
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(activity.getResources().getColor(R.color.base_text_dark));
        isShowNavigationIcon(isShow);
    }

    public void isShowNavigationIcon(boolean isShow){
        activity.setSupportActionBar(toolbar);
        if(isShow){
            toolbar.setNavigationIcon(R.drawable.ic_back_black);
        }else {
            toolbar.setNavigationIcon(null);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.getSupportFragmentManager().getBackStackEntryCount() > 1) {
//                    activity.pop();
                } else {
                    ActivityCompat.finishAfterTransition(activity);
                }

            }
        });
    }

}
