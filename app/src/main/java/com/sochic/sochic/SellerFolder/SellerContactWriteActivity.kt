package com.sochic.sochic.SellerFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sochic.sochic.R
import com.sochic.sochic.SellerFolder.API.SellerContactDetailAPI
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_seller_contact_write.*

class SellerContactWriteActivity : ScActivity() {

    var getIdx : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_contact_write)
        supportActionBar!!.hide()

        getIdx = intent.getStringExtra("idx")
        backBtn.setOnClickListener {
            finish()
        }

        writeBtn.setOnClickListener {
            if(answerEdit.text.toString().isEmpty()) {
                ShowToast("답변을 입력해주세요.")
            }else {
                disposable.add(apiService.SELLER_CONTACT_ANSWER_API(getIdx,answerEdit.text.toString())
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                ShowToast("답변을 등록하였습니다.")
                                finish()
                            }else {
                                ConnectionError()
                            }
                        }
                    }))
            }
        }

        getData()
    }

    fun getData() {
        disposable.add(apiService.SELLER_CONTACT_DETAIL_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SellerContactDetailAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: SellerContactDetailAPI) {
                    if(t.success) {
                        questionLabel.setText(t.question)
                    }
                }
            }))
    }
}
