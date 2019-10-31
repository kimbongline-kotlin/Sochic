package com.sochic.sochic.PayFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sochic.sochic.MyPageFolder.API.AddressInfoAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_delivery_edit.*
import kotlinx.android.synthetic.main.activity_delivery_edit.addressEdit
import kotlinx.android.synthetic.main.activity_delivery_edit.backBtn
import kotlinx.android.synthetic.main.activity_delivery_edit.detailAddressEdit
import kotlinx.android.synthetic.main.activity_delivery_edit.nameEdit
import kotlinx.android.synthetic.main.activity_delivery_edit.phoneEdit
import kotlinx.android.synthetic.main.activity_delivery_edit.postEdit


class DeliveryEditActivity : ScActivity() {

    var getIdx : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_edit)
        supportActionBar!!.hide()

        getIdx = intent.getStringExtra("idx")

        backBtn.setOnClickListener {
            finish()
        }

        getData()

        removeBtn.setOnClickListener {
            disposable.add(apiService.DELETE_ADDRESS_API(getIdx,id_user)
                .subscribeOn(io)
                .observeOn(thread)
                .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                    override fun onError(e: Throwable?) {
                        ConnectionError()
                    }

                    override fun onSuccess(t: TrueFalseAPI) {
                        if(t.success) {
                            finish()
                            ShowToast("배송지가 삭제되었습니다.")
                        }else {
                            ConnectionError()
                        }
                    }
                }))
        }
        modifyBtn.setOnClickListener {
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
                disposable.add(apiService.MODIFY_ADDRESS_API(
                    getIdx,
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
                                ShowToast("배송지가 수정 되었습니다.")
                                finish()
                            }else {
                                ConnectionError()
                            }
                        }
                    }))
            }
        }
    }

    fun getData() {
        disposable.add(apiService.ADDRESS_INFO_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<AddressInfoAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: AddressInfoAPI) {
                    if(t.success) {
                        nameEdit.setText(t.name)
                        phoneEdit.setText(t.phone)
                        postEdit.setText(t.post_number)
                        addressEdit.setText(t.address)
                        detailAddressEdit.setText(t.address_detail)
                    }
                }
            }))
    }
}
