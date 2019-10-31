package com.sochic.sochic.MyPageFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sochic.sochic.MyPageFolder.API.InviteAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_invite.*

class InviteActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        getData()
    }

    fun getData() {
        disposable.add(apiService.INVITE_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<InviteAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: InviteAPI) {
                    if(t.success) {
                        codeLabel.setText(t.rec_code)
                        savePointLabel.setText(PriceUtil.set(t.save_point.toString()) + "P")
                        friendLabel.setText(PriceUtil.set(t.cnt.toString()) + "명")

                        copyBtn.setOnClickListener {
                            setClipboard(applicationContext,t.rec_code,"친구 초대 코드가 복사되었습니다.")
                        }
                    }
                }
            }))
    }
}
