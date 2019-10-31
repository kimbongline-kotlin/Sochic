package com.sochic.sochic.LoginFolder

import android.os.Bundle
import android.os.Handler
import com.sochic.sochic.HomeFolder.HomeActivity
import com.sochic.sochic.LoginFolder.API.UserInfoAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_register_complete.*

class RegisterCompleteActivity : ScActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_complete)

        supportActionBar!!.hide()

        disposable.add(apiService.USER_INFO_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<UserInfoAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: UserInfoAPI) {
                    if(t.success) {
                        textView7.setText("${t.email}님,\nSOCHIC에 오신 걸 환영합니다.")
                    }else {
                        ConnectionError()
                    }
                }
            }))

        Handler().postDelayed({
            finish()
            goTo(HomeActivity::class.java)
        }, 1000)
    }
}
