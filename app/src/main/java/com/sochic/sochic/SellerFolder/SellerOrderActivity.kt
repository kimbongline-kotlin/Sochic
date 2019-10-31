package com.sochic.sochic.SellerFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.OrderFolder.API.OrderAPI
import com.sochic.sochic.OrderFolder.Adapter.MyOrderAdapter
import com.sochic.sochic.R
import com.sochic.sochic.SellerFolder.Adapter.SellerOrderAdapter
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_seller_order.*
import kotlinx.android.synthetic.main.fragment_mypage.*

class SellerOrderActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_order)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        var dItem = ArrayList<String>()
        dItem.add("전체")
        dItem.add("1주일")
        dItem.add("1개월")
        dItem.add("3개월")
        dItem.add("6개월")
        dItem.add("1년")

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        dSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,dItem)
        getItem()
    }


    override fun onResume() {
        super.onResume()
        getItem()
    }
    fun getItem() {
        disposable.add(apiService.SELLER_ORDER_LIST_API(id_user,dSpinner.selectedItemPosition,0)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderAPI) {
                    var tempData = ArrayList<OrderAPI.OrderList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<OrderAPI.OrderList>
                    }
                    var adapter = SellerOrderAdapter(applicationContext,this@SellerOrderActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }
}
