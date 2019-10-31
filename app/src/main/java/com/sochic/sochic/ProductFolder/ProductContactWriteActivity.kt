package com.sochic.sochic.ProductFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_product_contact_write.*

class ProductContactWriteActivity : ScActivity() {

    var getIdx = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_contact_write)
        supportActionBar!!.hide()

        getIdx = intent.getStringExtra("idx")
        backBtn.setOnClickListener {
            finish()
        }

        writeBtn.setOnClickListener {
            if(titleEdit.text.toString().isEmpty()) {
                ShowToast("문의 제목을 입력해주세요.")
            }else if(contentsEdit.text.toString().isEmpty()) {
                ShowToast("문의 내용을 입력해주세요.")
            }else {
                disposable.add(apiService.PRODUCT_CONTACT_WRITE_API(
                    getIdx,
                    id_user,
                    titleEdit.text.toString(),
                    contentsEdit.text.toString()
                )
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                finish()
                                ShowToast("문의가 작성되었습니다.")
                            }else {
                                ConnectionError()
                            }
                        }
                    }))
            }
        }
    }
}
