package com.sochic.sochic.SellerFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.OrderFolder.API.OrderAPI
import com.sochic.sochic.OrderFolder.API.OrderDetailAPI
import com.sochic.sochic.OrderFolder.API.OrderExchangeInfoAPI
import com.sochic.sochic.OrderFolder.Adapter.MyOrderProductAdapter
import com.sochic.sochic.R
import com.sochic.sochic.SellerFolder.API.SellerReDeliveryAPI
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver

import kotlinx.android.synthetic.main.activity_seller_number_edit.*
import kotlinx.android.synthetic.main.activity_seller_number_edit.addressLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.backBtn
import kotlinx.android.synthetic.main.activity_seller_number_edit.completeBtn
import kotlinx.android.synthetic.main.activity_seller_number_edit.cpnLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.deliveryPriceLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.memoLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.oCodeLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.orderEmailLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.orderNameLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.orderPhoneLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.payInfoLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.payTypeLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.pointLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.postLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.productPriceLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.reDeliveryLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.reDeliveryLayer
import kotlinx.android.synthetic.main.activity_seller_number_edit.reNumberEdit
import kotlinx.android.synthetic.main.activity_seller_number_edit.reSpinner
import kotlinx.android.synthetic.main.activity_seller_number_edit.receiverLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.receiverPhoneLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.recycler
import kotlinx.android.synthetic.main.activity_seller_number_edit.saveBtn
import kotlinx.android.synthetic.main.activity_seller_number_edit.savePointLabel
import kotlinx.android.synthetic.main.activity_seller_number_edit.totalPriceLabel

class SellerNumberEditActivity : ScActivity() {

    var getIdx : String = ""
    var getSubIdx : String = ""
    var claimCode : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_number_edit)
        supportActionBar!!.hide()

        getIdx = intent.getStringExtra("idx")
        getSubIdx = intent.getStringExtra("subIdx")
        supportActionBar!!.hide()


        var tagItem = ArrayList<String> ()
        tagItem.add("택배사 선택")
        tagItem.add("CJ 택배")
        tagItem.add("우체국 택배")

        reSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,tagItem)


        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
        getOrderItem()

        completeBtn.setOnClickListener {
            if(reDeliveryLayer.visibility == View.VISIBLE) {
                disposable.add(apiService.SELLER_NUMBER_ACT_API(getSubIdx)
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                ShowToast("송장번호 입력이 완료되었습니다.")
                                finish()
                            }else {
                                ConnectionError()
                            }
                        }
                    }))
            }else {
                ShowToast("회수 및 교환출고 송장번호를 입력해주세요.")
            }
        }
        saveBtn.setOnClickListener {
            if(reSpinner.selectedItemPosition == 0) {
                ShowToast("택배사를 선해주세요.")
            }else if(reNumberEdit.text.toString().isEmpty()){
                ShowToast("송장 번호를 입력해주세요.")
            }else {
                disposable.add(apiService.SELLER_NUMBER_ADD_API(getSubIdx,tagItem.get(reSpinner.selectedItemPosition),reNumberEdit.text.toString())
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                ShowToast("송장번호가 저장되었습니다.")
                                getOrderItem()
                            }else {
                                ConnectionError()
                            }
                        }
                    }))
            }
        }


    }

    fun getOrderItem() {

        disposable.add(apiService.ORDER_DETAIL_API(id_user,getIdx,getSubIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderDetailAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderDetailAPI) {
                    if(t.success) {
                        oCodeLabel.setText(t.date + " " + t.o_code)
                        productPriceLabel.setText(PriceUtil.set(t.product_price.toString()) + "원")
                        deliveryPriceLabel.setText(PriceUtil.set(t.d_price.toString()) + "원")
                        pointLabel.setText(PriceUtil.set(t.p_price.toString()) + "원")
                        cpnLabel.setText(PriceUtil.set(t.c_price.toString()) + "원")
                        totalPriceLabel.setText(PriceUtil.set(t.t_price.toString()) + "원")
                        if(t.p_type == 0) {

                            payTypeLabel.setText("신용카드")
                        }

                        claimCode = t.claim_code
                        payInfoLabel.setText(PriceUtil.set(t.t_price.toString()) + "" + "\n" + t.c_name)

                        var adapter = MyOrderProductAdapter(applicationContext,this@SellerNumberEditActivity,
                            t.o_response as ArrayList<OrderAPI.OrderList.OrderItemList>
                            ,t.sub_o_code)
                        recycler.adapter = adapter
                        adapter.notifyDataSetChanged()

                        savePointLabel.setText(PriceUtil.set(t.save_point.toString()) + "P")

                        orderNameLabel.setText(t.o_name)
                        orderPhoneLabel.setText(t.o_phone)
                        orderEmailLabel.setText(t.o_email)
                        receiverLabel.setText(t.d_name)
                        receiverPhoneLabel.setText(t.d_phone)
                        postLabel.setText(t.d_post_number)
                        addressLabel.setText(t.d_address)
                        memoLabel.setText(t.d_memo)

                        reDeliveryCheck()
                     //   exchangeInfo()

                    }
                }
            }))

    }

    fun reDeliveryCheck() {
        disposable.add(apiService.SELLER_NUMBER_CHECK_API(id_user,getIdx,getSubIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: TrueFalseAPI) {
                    if(t.success) {

                        reDeliveryLayer.visibility = View.VISIBLE
                        getReDeliveryData()
                    }else {
                        reDeliveryLayer.visibility = View.GONE
                    }
                }
            }))
    }

    fun getReDeliveryData() {
        disposable.add(apiService.SELLER_NUMBER_API(id_user,getIdx,getSubIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SellerReDeliveryAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: SellerReDeliveryAPI) {
                    if(t.success) {
                        reDeliveryLabel.setText(t.info)
                    }else {
                        ConnectionError()
                    }
                }
            }))
    }
}
