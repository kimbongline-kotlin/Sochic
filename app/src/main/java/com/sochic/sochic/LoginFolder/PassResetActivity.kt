package com.sochic.sochic.LoginFolder

import android.annotation.SuppressLint
import android.os.Bundle
import com.jakewharton.rxbinding2.widget.RxTextView
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_pass_reset.*

@SuppressLint("CheckResult")
class PassResetActivity : ScActivity() {

    var getIdx : String = ""

    var dynamics : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_reset)
        supportActionBar!!.hide()

        if(intent.getStringExtra("idx")  != null) {
            getIdx = intent.getStringExtra("idx")
            dynamics = true
        }else {
            getIdx = id_user
            dynamics = false

        }


        backBtn.setOnClickListener {
            if(dynamics) {
                finish()
                goTo(LoginActivity::class.java)
            }else {
                finish()
            }
        }


        RxTextView.textChanges(newPassEdit).subscribe {
            if(it.toString().isEmpty()){
                passErrorLabel.setText("비밀번호를 한번 더 입력해주세요.")
            } else if(!it.toString().equals(passEdit.text.toString())) {
                passErrorLabel.setText("비밀번호가 일치하지 않습니다.")
            }else {
                passErrorLabel.setText("")
            }
        }
        changeBtn.setOnClickListener {
            if(passEdit.text.toString().isEmpty()){
                ShowToast("비밀번호를 입력해주세요.")
            }else if(!newPassEdit.text.toString().equals(passEdit.text.toString())) {
                ShowToast("비밀번호가 일치하지 않습니다.")
            }else {
                disposable.add(apiService.PASS_CHANGE_API(getIdx,passEdit.text.toString())
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                finish()
                                ShowToast("비밀번호가 변경되었습니다.")
                                appController.logout()
                            }
                        }
                    }))
            }
        }



    }
}
