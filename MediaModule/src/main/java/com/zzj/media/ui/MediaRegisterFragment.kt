package com.zzj.media.ui

import android.os.Bundle
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
import kotlinx.android.synthetic.main.media_fragment_register.*

class MediaRegisterFragment : BaseFragment(){
    override fun initListener() {

        tv_register.setOnClickListener {
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
            register(phone, password)
        }
    }

    /**
     * 注册请求
     */
    private fun register(phone: String, password: String) {
        RetrofitClient.instance.create(UserApiService::class.java)
            .register(phone, password)
            .compose(exceptionTransformer())
            .transform(this)
            .subscribe(object :BaseObserver<HttpResult<UserBean>>(){
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
                }

                override fun showDialog() {
                    loading("")
                }

            })
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {

        return R.layout.media_fragment_register
    }

    override fun initData() {
    }

    override fun initView() {
    }

}