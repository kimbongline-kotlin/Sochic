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
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.activity_seller_exchange.*
import kotlinx.android.synthetic.main.activity_seller_exchange.addressLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.backBtn
import kotlinx.android.synthetic.main.activity_seller_exchange.cpnLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.deliveryPriceLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.exchangeAddressLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.exchangeInfoLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.exchangeLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.memoLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.oCodeLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.orderEmailLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.orderNameLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.orderPhoneLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.payInfoLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.payTypeLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.pointLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.postLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.productPriceLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.receiverLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.receiverPhoneLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.recycler
import kotlinx.android.synthetic.main.activity_seller_exchange.savePointLabel
import kotlinx.android.synthetic.main.activity_seller_exchange.totalPriceLabel
import kotlin.system.exitProcess


class SellerExchangeActivity : ScActivity() {

    var getIdx : String = ""
    var getSubIdx : String = ""
    var claimCode : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_exchange)
        supportActionBar!!.hide()

        getIdx = intent.getStringExtra("idx")
        getSubIdx = intent.getStringExtra("subIdx")
        supportActionBar!!.hide()


        var tagItem = ArrayList<String> ()
        tagItem.add("택배사 선택")
        tagItem.add("CJ 택배")
        tagItem.add("우체국 택배")

        reSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,tagItem)
        exSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,tagItem)


        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
        getOrderItem()

        completeBtn.setOnClickListener {
            if(exLayer.visibility == View.VISIBLE && reDeliveryLayer.visibility == View.VISIBLE) {
                disposable.add(apiService.SELLER_EX_COMPLETE_ACT_API(claimCode)
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                ShowToast("교환완료로 변경되었습니다.")
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
            if(reSpinner.selectedItemPosition != 0 && exSpinner.selectedItemPosition == 0) {
                if(reNumberEdit.text.toString().isEmpty()){
                    ShowToast("회수 송장번호를 입력해주세요.")
                }else {
                    disposable.add(apiService.SELLER_REDELIVERY_ADD_API(claimCode,tagItem.get(reSpinner.selectedItemPosition),reNumberEdit.text.toString())
                        .subscribeOn(io)
                        .observeOn(thread)
                        .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                            override fun onError(e: Throwable?) {
                                ConnectionError()
                            }

                            override fun onSuccess(t: TrueFalseAPI) {
                                if(t.success) {
                                    ShowToast("회수 송장번호가 저장되었습니다.")
                                    getOrderItem()
                                }else {
                                    ConnectionError()
                                }
                            }
                        }))
                }
            }else if(reSpinner.selectedItemPosition == 0 && exSpinner.selectedItemPosition != 0) {
                if(exNumberEdit.text.toString().isEmpty()) {
                    ShowToast("교환출고 송장번호를 입력해주세요.")
                }else {
                    disposable.add(apiService.SELLER_EXCHANGE_ADD_API(claimCode,tagItem.get(exSpinner.selectedItemPosition),exNumberEdit.text.toString())
                        .subscribeOn(io)
                        .observeOn(thread)
                        .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                            override fun onError(e: Throwable?) {
                                ConnectionError()
                            }

                            override fun onSuccess(t: TrueFalseAPI) {
                                if(t.success) {
                                    ShowToast("교환출고 송장번호가 저장되었습니다.")
                                    getOrderItem()
                                }else {
                                    ConnectionError()
                                }
                            }
                        }))
                }
            }else if(reSpinner.selectedItemPosition != 0 && exSpinner.selectedItemPosition != 0) {
                if(reNumberEdit.text.toString().isEmpty()){
                    ShowToast("회수 송장번호를 입력해주세요.")
                }else if(exNumberEdit.text.toString().isEmpty()) {
                    ShowToast("교환출고 송장번호를 입력해주세요.")
                }else {
                    disposable.add(apiService.SELLER_REDELIVERY_ADD_API(claimCode,tagItem.get(reSpinner.selectedItemPosition),reNumberEdit.text.toString())
                        .subscribeOn(io)
                        .observeOn(thread)
                        .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                            override fun onError(e: Throwable?) {
                                ConnectionError()
                            }

                            override fun onSuccess(t: TrueFalseAPI) {
                                if(t.success) {
                                    disposable.add(apiService.SELLER_EXCHANGE_ADD_API(claimCode,tagItem.get(exSpinner.selectedItemPosition),exNumberEdit.text.toString())
                                        .subscribeOn(io)
                                        .observeOn(thread)
                                        .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                                            override fun onError(e: Throwable?) {
                                                ConnectionError()
                                            }

                                            override fun onSuccess(t5: TrueFalseAPI) {
                                                if(t5.success) {
                                                    ShowToast("송장번호가 저장되었습니다.")
                                                    getOrderItem()
                                                }else {
                                                    ConnectionError()
                                                }
                                            }
                                        }))
                                }else {
                                    ConnectionError()
                                }
                            }
                        }))
                }

            }else {
                ShowToast("택배사를 선택해주세요.")
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

                        var adapter = MyOrderProductAdapter(applicationContext,this@SellerExchangeActivity,
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

                        exchangeInfo()

                    }
                }
            }))

    }

    fun exchangeInfo() {

        disposable.add(apiService.ORDER_EXCHANGE_INFO_API(getIdx,claimCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderExchangeInfoAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderExchangeInfoAPI) {
                    if(t.success) {

                        exchangeLabel.setText(t.title)
                        exchangeInfoLabel.setText(t.detail_info)
                        exchangeAddressLabel.setText(t.post_number + "\n" + t.address + "\n" + t.address_detail)

                        reDeliveryCheck()
                        exDeliveryCheck()
                    }else {
                        ConnectionError()
                    }
                }
            }))
    }

    fun reDeliveryCheck() {
        disposable.add(apiService.SELLER_REDELIVERY_CHECK_API(id_user,getIdx,claimCode)
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
        disposable.add(apiService.SELLER_RE_DELIVERY_API(id_user,getIdx,claimCode)
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



    fun exDeliveryCheck() {
        disposable.add(apiService.SELLER_EXCHANGE_CHECK_API(id_user,getIdx,claimCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: TrueFalseAPI) {
                    if(t.success) {

                        exLayer.visibility = View.VISIBLE
                        exDeliveryData()
                    }else {
                        exLayer.visibility = View.GONE
                    }
                }
            }))
    }

    fun exDeliveryData() {
        disposable.add(apiService.SELLER_EXCHANGE_API(
            id_user,getIdx,claimCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SellerReDeliveryAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: SellerReDeliveryAPI) {
                    if(t.success) {
                        exNumberLabel.setText(t.info)
                    }else {
                        ConnectionError()
                    }
                }
            }))
    }
}
