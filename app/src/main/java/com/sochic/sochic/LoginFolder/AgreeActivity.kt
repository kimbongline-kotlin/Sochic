package com.sochic.sochic.LoginFolder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.sochic.sochic.LoginFolder.API.TermAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_agree.*

class AgreeActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agree)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        allAgreeRadio.setOnClickListener {
            if(allAgreeRadio.isSelected){
                allAgreeRadio.isSelected = false
                serviceRadio.isSelected = false
                privateRadio.isSelected = false
            }else {
                allAgreeRadio.isSelected = true
                serviceRadio.isSelected = true
                privateRadio.isSelected = true
            }
        }

        allAgreeBtn.setOnClickListener {
            allAgreeRadio.callOnClick()
        }

        serviceRadio.setOnClickListener {
            serviceRadio.isSelected = !serviceRadio.isSelected

            allCheck()
        }

        serviceBtn.setOnClickListener {
            serviceRadio.callOnClick()
        }

        privateRadio.setOnClickListener {
            privateRadio.isSelected = !privateRadio.isSelected

            allCheck()
        }

        privateBtn.setOnClickListener {
            privateRadio.callOnClick()
        }

        nextBtn.setOnClickListener {
            if(allAgreeRadio.isSelected) {

                finish()

                if(intent.getStringExtra("type") != null) {
                    Log.d("object",intent.getStringExtra("type"))
                    Log.d("object",intent.getStringExtra("snsId"))
                    var type = intent.getStringExtra("type")
                    var snsId = intent.getStringExtra("snsId")
                    var intent = Intent(applicationContext,SocialRegisterActivity::class.java)
                    intent.putExtra("type",type)
                    intent.putExtra("snsId", snsId)

                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                }else {
                    goTo(RegisterActivity::class.java)
                }

            }else {
                ShowToast("필수 약관에 동의해주세요,")
            }
        }
        getData()
    }

    fun allCheck() {
        if(serviceRadio.isSelected && privateRadio.isSelected) {
            allAgreeRadio.isSelected = true
        }else {
            allAgreeRadio.isSelected = false
        }
    }

    fun getData() {
        disposable.add(apiService.TERM_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TermAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: TermAPI) {
                    if(t.success) {
                        sWebView.loadUrl(t.url_1)
                        pWebView.loadUrl(t.url_2)
                    }
                }
            }))
    }
}
