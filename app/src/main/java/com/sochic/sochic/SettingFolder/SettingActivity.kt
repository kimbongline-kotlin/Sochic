package com.sochic.sochic.SettingFolder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sochic.sochic.MyPageFolder.ProfileActivity
import com.sochic.sochic.R
import com.sochic.sochic.SettingFolder.API.TermUrlAPI
import com.sochic.sochic.SplashFolder.StartGuideActivity
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        outBtn.setOnClickListener {
           // appController.logout()
            goTo(LeaveActivity::class.java)
        }


        profileChangeBtn.setOnClickListener {
            goTo(ProfileActivity::class.java)
        }

        contactBtn.setOnClickListener {
            goTo(ContactActivity::class.java)
        }

        guidBtn.setOnClickListener {
            goTo(StartGuideActivity::class.java)
        }

        getTerms()

        logoutBtn.setOnClickListener {
            appController.logout()
        }
        getPushCheck()

        pushBtn.setOnClickListener {
            disposable.add(apiService.PUSH_CON_API(id_user)
                .subscribeOn(io)
                .observeOn(thread)
                .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                    override fun onError(e: Throwable?) {
                        ConnectionError()
                    }

                    override fun onSuccess(t: TrueFalseAPI?) {
                        getPushCheck()
                    }
                }))
        }
    }

    fun getPushCheck() {
        disposable.add(apiService.PUSH_CHECK_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: TrueFalseAPI) {
                    pushSwitch.isChecked = t.success
                }
            }))
    }

    fun getTerms() {
        disposable.add(apiService.SERVICE_TERM_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TermUrlAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: TermUrlAPI) {
                    if(t.success) {
                        sTermBtn.setOnClickListener {
                            startActivity(Intent(applicationContext,ScWebActivity::class.java)
                                .putExtra("title","서비스 이용 약관")
                                .putExtra("url" ,t.url)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                        }
                    }
                }
            }))

        disposable.add(apiService.PRIVATE_TERM_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TermUrlAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: TermUrlAPI) {
                    if(t.success) {
                        pTermBtn.setOnClickListener {
                            startActivity(Intent(applicationContext,ScWebActivity::class.java)
                                .putExtra("title","개인정보 취급방침")
                                .putExtra("url" ,"${t.url}")
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                        }
                    }
                }
            }))
    }
}
