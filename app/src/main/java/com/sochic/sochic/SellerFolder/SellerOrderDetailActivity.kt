package com.sochic.sochic.SellerFolder

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.OrderFolder.API.OrderAPI
import com.sochic.sochic.OrderFolder.API.OrderDetailAPI
import com.sochic.sochic.OrderFolder.Adapter.MyOrderProductAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_seller_order_detail.*


class SellerOrderDetailActivity : ScActivity() {

    var getIdx : String = ""
    var getSubIdx : String = ""
    var claimCode : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_order_detail)
        getIdx = intent.getStringExtra("idx")
        getSubIdx = intent.getStringExtra("subIdx")
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
        getOrderItem()

        writeBtn3.setOnClickListener {
            finish()
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

                        var adapter = MyOrderProductAdapter(applicationContext,this@SellerOrderDetailActivity,
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

                    }
                }
            }))

    }

}
