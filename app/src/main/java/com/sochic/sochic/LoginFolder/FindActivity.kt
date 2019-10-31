package com.sochic.sochic.LoginFolder

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sochic.sochic.LoginFolder.API.FindEmailAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_find.*


class FindActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }



        findBtn.setOnClickListener {

            if(emailEdit.text.toString().isEmpty()) {
                ShowToast("이메일 주소를 입력해주세요.")
            }else{
                disposable.add(apiService.FIND_EMAIL_API(emailEdit.text.toString())
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<FindEmailAPI> () {
                        override fun onError(e: Throwable?) {

                        }

                        override fun onSuccess(t: FindEmailAPI) {
                            if(t.success) {
                                finish()
                                startActivity(Intent(applicationContext,FindResultActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("email",t.email).putExtra("date",t.date).putExtra("id_user",t.id_user))
                            }else {
                                emailErrorLabel.visibility = View.VISIBLE
                                emailErrorLabel.setText("가입된 아이디가 없습니다.")
                            }
                        }
                    }))
            }
        }
    }
}






