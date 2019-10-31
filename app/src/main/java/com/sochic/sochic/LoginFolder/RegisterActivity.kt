package com.sochic.sochic.LoginFolder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.firebase.iid.FirebaseInstanceId
import com.jakewharton.rxbinding2.widget.RxTextView
import com.sochic.sochic.LoginFolder.API.LoginAPI
import com.sochic.sochic.R
import com.sochic.sochic.SettingFolder.API.TermUrlAPI
import com.sochic.sochic.SettingFolder.ScWebActivity
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.UserFolder.User
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : ScActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        registerBtn.setOnClickListener {
            if(emailEdit.text.toString().isEmpty()) {
                ShowToast("이메일을 입력해주세요.")
            }else if(!emailErrorLabel.text.toString().equals("사용 가능한 이메일입니다.")){
                ShowToast("이메일을 확인해주세요.")
            }else if(passEdit.text.toString().isEmpty()){
                ShowToast("비밀번호를 입력해주세요.")
            }else if(!passEdit.text.toString().equals(passReEdit.text.toString())) {
                ShowToast("비밀번호가 일치하지 않습니다.")
            }else {
                var postMarket = ""
                if(marketingRadio.isSelected) {
                    postMarket = "1"
                }else {
                    postMarket = "0"
                }

                disposable.add(apiService.REGISTER_API(emailEdit.text.toString(),
                    passEdit.text.toString(),codeEdit.text.toString(),
                    postMarket,
                    "AOS",
                    FirebaseInstanceId.getInstance().token.toString()
                    ).subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<LoginAPI>() {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }


                        override fun onSuccess(t: LoginAPI) {
                            if(t.success) {

                                var user = User(t.id_user)
                                appController.prefManager.storeUser(user)
                                finish()
                                goTo(AddInfoActivity::class.java)

                            }else {
                                ShowToast("이미 가입된 아이디입니다.")
                            }
                        }
                    }))

            }

        }

        marketingBtn.setOnClickListener {
            marketingRadio.isSelected = !marketingRadio.isSelected
        }

        marketingRadio.setOnClickListener {
            marketingBtn.callOnClick()
        }
        RxTextView.textChanges(emailEdit).subscribe {
            if(it.toString().isEmpty()){
                emailErrorLabel.visibility = View.INVISIBLE
                emailErrorLabel.setText("")
            }else{
                if(isValidEmail(it)) {
                    disposable.add(apiService.EMAIL_CHECK_API(emailEdit.text.toString())
                        .subscribeOn(io)
                        .observeOn(thread)
                        .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                            override fun onError(e: Throwable?) {
                                ConnectionError()
                            }

                            override fun onSuccess(t: TrueFalseAPI) {
                                if(t.success) {
                                    emailErrorLabel.visibility = View.VISIBLE
                                    emailErrorLabel.setText("사용 가능한 이메일입니다.")
                                }else {
                                    emailErrorLabel.visibility = View.VISIBLE
                                    emailErrorLabel.setText("이미 가입된 이메일입니다.")
                                }
                            }
                        }))
                }else {
                    emailErrorLabel.visibility = View.VISIBLE
                    emailErrorLabel.setText("올바른 이메일 형식이 아닙니다.")
                }
            }

        }

        RxTextView.textChanges(passReEdit).subscribe {
            if(!it.toString().isEmpty()) {
                if(passEdit.text.toString().equals(passReEdit.text.toString())) {
                    passErrorLabel.visibility = View.INVISIBLE
                    passErrorLabel.setText("")
                }else {
                    passErrorLabel.visibility = View.VISIBLE
                    passErrorLabel.setText("비밀번호가 일치하지 않습니다.")
                }
            }
        }

        disposable.add(apiService.MARKET_TERM_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TermUrlAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: TermUrlAPI) {
                    if(t.success) {
                        showBtn.setOnClickListener {
                            startActivity(Intent(applicationContext,ScWebActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                .putExtra("url",t.url)
                                .putExtra("title","마케팅 이용약관"))
                        }
                    }
                }
            }))
    }
}
