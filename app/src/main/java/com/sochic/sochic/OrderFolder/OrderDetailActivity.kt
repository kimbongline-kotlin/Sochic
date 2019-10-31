package com.sochic.sochic.OrderFolder

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.OrderFolder.API.*
import com.sochic.sochic.OrderFolder.Adapter.MyOrderProductAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : ScActivity() {

    var getIdx : String = ""
    var getSubIdx : String = ""
    var claimCode : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        getIdx = intent.getStringExtra("idx")
        getSubIdx = intent.getStringExtra("subIdx")
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        getOrderItem()

    }

    fun getOrderItem() {

        disposable.add(apiService.ORDER_DETAIL_API(id_user,getIdx,getSubIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderDetailAPI> () {
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

                        var adapter = MyOrderProductAdapter(applicationContext,this@OrderDetailActivity,
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
                        orderStateCheck()
                    }
                }
            }))

    }

    fun orderStateCheck() {
        disposable.add(apiService.ORDER_STATE_API(getIdx,getSubIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderStateAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderStateAPI) {
                    if(t.order_confirm >= 3) {
                        deliveryShoBtn.visibility = View.GONE
                    }else if(t.order_confirm < 3) {
                        deliveryShoBtn.visibility = View.GONE
                    }

                    if(t.order_confirm == 10 || t.order_confirm == 7) {
                        exchangeView.visibility = View.VISIBLE
                        exchangeInfo()
                    }

                    if(t.order_confirm == 8 || t.order_confirm == 11) {
                        returnView.visibility = View.VISIBLE
                        returnInfo()
                    }

                    if(t.order_confirm == 9 || t.order_confirm ==6) {
                        cancelView.visibility = View.VISIBLE
                        chacelInfo()
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

                    }else {
                        ConnectionError()
                    }
                }
            }))
    }

    fun chacelInfo() {
        disposable.add(apiService.ORDER_CANCEL_INFO_API(getIdx,claimCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderCancelInfoAPI> ()  {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderCancelInfoAPI) {
                    if(t.success) {
                        cancelLabel.setText(t.title)
                    }
                }
            }))
    }

    fun returnInfo() {
        disposable.add(apiService.ORDER_RETURN_INFO_VIEW(getIdx,claimCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderReturnInfoView> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderReturnInfoView) {
                    if(t.success) {
                        returnLabel.setText(t.title)
                        returnDetailLabel.setText(t.detail_info)
                        returnAddressLabel.setText(t.post_number + "\n" + t.address + "\n" + t.address_detail)
                        returnInfoLabel.setText("(" + t.bank_name + ")" + " " + t.bank_number)
                    }
                }
            }))
    }
}
