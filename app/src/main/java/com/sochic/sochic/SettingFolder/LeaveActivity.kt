package com.sochic.sochic.SettingFolder

import android.os.Bundle
import android.view.View
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_leave.*


class LeaveActivity : ScActivity() {

    var reason : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        initLayer()

        rBtn1.setOnClickListener {
            initLayer()
            rBtn1.isSelected = !rBtn1.isSelected
            if(rBtn1.isSelected) {
                reason = rBtn1.text.toString()
            }else {
                reason = ""
            }
        }

        rBtn2.setOnClickListener {
            initLayer()
            rBtn2.isSelected = !rBtn2.isSelected
            if(rBtn2.isSelected) {
                reason = rBtn2.text.toString()
            }else {
                reason = ""
            }
        }


        rBtn3.setOnClickListener {
            initLayer()
            rBtn3.isSelected = !rBtn3.isSelected
            if(rBtn3.isSelected) {
                reason = rBtn3.text.toString()
            }else {
                reason = ""
            }
        }

        rBtn4.setOnClickListener {
            initLayer()
            rBtn4.isSelected = !rBtn4.isSelected
            if(rBtn4.isSelected) {
                reason = rBtn4.text.toString()
            }else {
                reason = ""
            }
        }

        rBtn5.setOnClickListener {
            initLayer()
            rBtn5.isSelected = !rBtn5.isSelected
            if(rBtn5.isSelected) {
                reason = rBtn5.text.toString()
            }else {
                reason = ""
            }
        }

        rBtn6.setOnClickListener {
            initLayer()
            rBtn6.isSelected = !rBtn6.isSelected
            if(rBtn6.isSelected) {
                reason = rBtn6.text.toString()
            }else {
                reason = ""
            }
        }

        rBtn7.setOnClickListener {
            initLayer()
            rBtn7.isSelected = !rBtn7.isSelected
            if(rBtn7.isSelected) {
                reason = rBtn7.text.toString()
            }else {
                reason = ""
            }
        }

        rBtn8.setOnClickListener {
            initLayer()
            rBtn8.isSelected = !rBtn8.isSelected
            if(rBtn8.isSelected) {
                reason = rBtn8.text.toString()
            }else {
                reason = ""
            }
        }

        rBtn9.setOnClickListener {
            initLayer()
            rBtn9.isSelected = !rBtn9.isSelected
            if(rBtn9.isSelected) {
                reason = rBtn9.text.toString()
            }else {
                reason = ""
            }
        }

        leaveBtn.setOnClickListener {
            if(reason.toString().isEmpty()) {
                ShowToast("탈퇴 사유를 선택해주세요.")
            }else {
                scAlert.leavePreAlert(this, View.OnClickListener {
                    if(it.id == R.id.lPreCancelBtn) {
                        scAlert.simpleDialog.dismiss()
                    }else if(it.id == R.id.lPreOkBtn){
                        scAlert.simpleDialog.dismiss()

                        disposable.add(apiService.LEAVE_API(id_user,reason)
                            .subscribeOn(io)
                            .observeOn(thread)
                            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                                override fun onError(e: Throwable?) {
                                    ConnectionError()
                                }

                                override fun onSuccess(t: TrueFalseAPI) {
                                    if(t.success) {
                                        scAlert.leaveComAlert(this@LeaveActivity, View.OnClickListener {
                                            if(it.id == R.id.lComBtn){
                                                ShowToast("이용해주셔서 감사합니다.")
                                                appController.logout()
                                            }
                                        })
                                    }else {
                                        ConnectionError()
                                    }
                                }
                            }))

                    }
                })

            }
        }
    }

    fun initLayer() {
        reason = ""
        rBtn1.isSelected = false
        rBtn2.isSelected = false
        rBtn3.isSelected = false
        rBtn4.isSelected = false
        rBtn5.isSelected = false
        rBtn6.isSelected = false
        rBtn7.isSelected = false
        rBtn8.isSelected = false
        rBtn9.isSelected = false
    }
}
