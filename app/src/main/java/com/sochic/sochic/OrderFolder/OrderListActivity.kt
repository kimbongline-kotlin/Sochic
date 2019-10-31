package com.sochic.sochic.OrderFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.OrderFolder.API.OrderAPI
import com.sochic.sochic.OrderFolder.Adapter.MyOrderAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_order_list.*

class OrderListActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        supportActionBar!!.hide()

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        var dItem = ArrayList<String>()
        dItem.add("전체")
        dItem.add("1주일")
        dItem.add("1개월")
        dItem.add("3개월")
        dItem.add("6개월")
        dItem.add("1년")

        dSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,dItem)

        var oItem = ArrayList<String>()
        oItem.add("전체")
        oItem.add("입금대기")
        oItem.add("결제완료")
        oItem.add("배송준비")
        oItem.add("배송중")
        oItem.add("배송완료")
        oItem.add("구매확정")
        oItem.add("취소완료")
        oItem.add("교환완료")
        oItem.add("반품완료")
        oItem.add("취소접수")
        oItem.add("교환접수")
        oItem.add("반품접수")

        oSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,oItem)



        getItem(0,-1)

        backBtn.setOnClickListener {
            finish()
        }


        dSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getItem(dSpinner.selectedItemPosition,oSpinner.selectedItemPosition-1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        oSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getItem(dSpinner.selectedItemPosition,oSpinner.selectedItemPosition-1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun getItem(duration : Int, order_confirm : Int) {
        disposable.add(apiService.ORDER_API(id_user,duration,order_confirm)
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

                    var adapter = MyOrderAdapter(applicationContext,this@OrderListActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))

    }
}
