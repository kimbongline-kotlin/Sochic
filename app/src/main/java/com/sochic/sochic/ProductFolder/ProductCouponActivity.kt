package com.sochic.sochic.ProductFolder

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.MyPageFolder.API.CouponAPI
import com.sochic.sochic.ProductFolder.Adapter.ProductCouponAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScTransActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_product_coupon.*

class ProductCouponActivity : ScTransActivity() {

    var idx : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_coupon)

        backBtn.setOnClickListener {
            finish()
        }

        backView.setOnClickListener {
            finish()
        }
        idx = intent.getStringExtra("idx")

        recyclerView.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        getItem()
    }

    fun getItem() {
        disposable.add(apiService.PRODUCT_CPN_LIST_API(id_user,idx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<CouponAPI>() {
                override fun onError(e: Throwable) {
                    ConnectionError()
                }

                override fun onSuccess(t: CouponAPI) {
                    var tempData = ArrayList<CouponAPI.CouponList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<CouponAPI.CouponList>
                    }
                    var adapter = ProductCouponAdapter(applicationContext,this@ProductCouponActivity,tempData,0)
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

}
