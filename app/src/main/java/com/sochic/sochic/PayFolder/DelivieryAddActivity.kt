package com.sochic.sochic.PayFolder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sochic.sochic.MyPageFolder.SearchAddressActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_deliviery_add.*

class DelivieryAddActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliviery_add)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        findBtn.setOnClickListener {
            startActivityForResult(Intent(applicationContext,SearchAddressActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),8282)
        }

        saveBtn.setOnClickListener {
            if(nameEdit.text.toString().isEmpty()) {
                ShowToast("이름을 입력해주세요.")
            }else if(phoneEdit.text.toString().isEmpty()) {
                ShowToast("휴대폰 번호를 입력해주세요.")
            }else if(postEdit.text.toString().isEmpty()) {
                ShowToast("우편 번호를 입력해주세요.")
            }else if(addressEdit.text.toString().isEmpty()) {
                ShowToast("주소를 입력해주세요.")
            }else if(detailAddressEdit.text.toString().isEmpty()) {
                ShowToast("상세 주소를 입력해주세요.")
            }else {
                disposable.add(apiService.ADD_ADDRESS_API(
                    id_user,
                    nameEdit.text.toString(),
                    phoneEdit.text.toString(),
                    postEdit.text.toString(),
                    addressEdit.text.toString(),
                    detailAddressEdit.text.toString()
                )
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                ShowToast("배송지가 추가 되었습니다.")
                                finish()
                            }else {
                                ConnectionError()
                            }
                        }
                    }))
            }
        }
    }

    override fun onActivityResult(paramInt1: Int, paramInt2: Int, paramIntent: Intent?) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent)

        if(paramInt1 == 8282 && paramInt2 == Activity.RESULT_OK) {
            postEdit.setText(paramIntent!!.getStringExtra("arg1"))
            addressEdit.setText(paramIntent!!.getStringExtra("arg2"))
            detailAddressEdit.setText(paramIntent!!.getStringExtra("arg3"))
        }
    }
}
