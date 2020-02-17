package com.zzj.media.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.zzj.baselibrary.base.BaseFragment
import com.zzj.baselibrary.http.BaseObserver
import com.zzj.baselibrary.http.HttpResult
import com.zzj.baselibrary.http.ResponseThrowable
import com.zzj.baselibrary.http.RetrofitClient
import com.zzj.baselibrary.rx.exceptionTransformer
import com.zzj.baselibrary.rx.transform
import com.zzj.baselibrary.utils.MMKVUtils
import com.zzj.media.R
import com.zzj.media.api.UserApiService
import com.zzj.media.data.UserBean
import kotlinx.android.synthetic.main.media_fragment_login.*

class MediaLoginFragment :BaseFragment(){
    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.media_fragment_login
    }


    override fun initListener() {

        tv_register.setOnClickListener{ _mActivity.start(MediaRegisterFragment()) }

        tv_login.setOnClickListener {
            //注册
            val phone = et_phone.text.toString().trim { it <= ' ' }
            if (!RegexUtils.isMobileExact(phone)) {
                ToastUtils.showShort("请输入正确的手机号")
                return@setOnClickListener
            }
            val password = et_password.text.toString().trim { it <= ' ' }
            if (StringUtils.isEmpty(password)) {
                ToastUtils.showShort("请输入密码")

                return@setOnClickListener
            }
            val userBean = UserBean()
            userBean.phone = phone
            userBean.password = (password)
            login(userBean)
        }
    }

    private fun login(userBean: UserBean) {
        loading("")
        RetrofitClient.instance.create(UserApiService::class.java)
            .login(userBean)
            .compose(exceptionTransformer())
            .transform(this)
            .subscribe (object :BaseObserver<HttpResult<UserBean>>(){
                override fun showDialog() {

                }

                override fun onNext(t: HttpResult<UserBean>) {
                    if(t.isSuccess){
                        MMKVUtils.getInstance().put("user_id", t.result!!.id)
                        MMKVUtils.getInstance().put("phone", t.result!!.phone)
                        MMKVUtils.getInstance().put("password", t.result!!.password)
                        MMKVUtils.getInstance().put("token", t.result!!.token)
                        MMKVUtils.getInstance()
                            .put("face_image", t.result!!.faceImage)
                        MMKVUtils.getInstance().put("nick_name", t.result!!.nickname)
                        MMKVUtils.getInstance().put("user_name", t.result!!.username)
                        _mActivity.startWithPop(MediaMainFragment())
                    }else{
                        ToastUtils.showShort(t.message)
                    }
                }

                override fun hideDialog() {
                    dismiss()
                }

                override fun onError(e: ResponseThrowable?) {
                    ToastUtils.showShort(e?.message)
                }

            } )

    }

    override fun initData() {

    }

    override fun initView() {
        initAnims()
    }


    /**
     * 初始化logo图片以及底部注册、登录的按钮动画
     */
    private fun initAnims() {
        //初始化底部注册、登录的按钮动画
        //以控件自身所在的位置为原点，从下方距离原点200像素的位置移动到原点
        val tranLogin = ObjectAnimator.ofFloat(cd_login, "translationY", 300f, 50f)
        val tranRegister = ObjectAnimator.ofFloat(tv_register, "translationY", 250f, 0f)
        //        ObjectAnimator tranRegister = ObjectAnimator.ofFloat(tvRegister, "translationY", 200, 0);
        //将注册、登录的控件alpha属性从0变到1
        val alphaLogin = ObjectAnimator.ofFloat(cd_login, "alpha", 0f, 1f)
        val alphaRegister = ObjectAnimator.ofFloat(tv_register, "alpha", 0f, 1f)
        //        ObjectAnimator alphaPhone = ObjectAnimator.ofFloat(et_phone, "alpha", 0, 1);
        //        ObjectAnimator alphaPasswor = ObjectAnimator.ofFloat(et_password, "alpha", 0, 1);
        val bottomAnim = AnimatorSet()
        bottomAnim.duration = 1000
        //同时执行控件平移和alpha渐变的动画
        bottomAnim.play(tranLogin).with(alphaLogin).with(alphaRegister).with(tranRegister)

        //获取屏幕高度
        val manager = _mActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(metrics)
        val screenHeight = metrics.heightPixels

        //通过测量，获取ivLogo的高度
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        iv_logo.measure(w, h)
        val logoHeight = iv_logo.getMeasuredHeight()

        //初始化ivLogo的移动和缩放动画
        val transY = (screenHeight - logoHeight) * 0.40f
        //ivLogo向上移动 transY 的距离
        val tranLogo = ObjectAnimator.ofFloat(iv_logo, "translationY", 0f, -transY)
        //ivLogo在X轴和Y轴上都缩放0.75倍
        val scaleXLogo = ObjectAnimator.ofFloat(iv_logo, "scaleX", 1f, 0.75f)
        val scaleYLogo = ObjectAnimator.ofFloat(iv_logo, "scaleY", 1f, 0.75f)
        val logoAnim = AnimatorSet()
        logoAnim.duration = 1000
        logoAnim.play(tranLogo).with(scaleXLogo).with(scaleYLogo)
        logoAnim.start()
        logoAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //logo图片的动画结束时,开始播放底部注册、登录按钮的动画
                bottomAnim.start()
            }
        })
    }
}