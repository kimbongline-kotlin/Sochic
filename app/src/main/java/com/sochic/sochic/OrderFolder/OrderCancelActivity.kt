package com.sochic.sochic.OrderFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.OrderFolder.API.CancelReasonAPI
import com.sochic.sochic.OrderFolder.API.OrderAPI
import com.sochic.sochic.OrderFolder.API.OrderShortInfoAPI
import com.sochic.sochic.OrderFolder.Adapter.OrderDataAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_order_cancel.*
import kotlinx.android.synthetic.main.activity_order_cancel.oCodeLabel


class OrderCancelActivity : ScActivity() {

    var item = ArrayList<String>()
    var reasonItem = ArrayList<CancelReasonAPI.CancelReasonList>()
    var getIdx : String = ""
    var getSubIdx : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_cancel)
        supportActionBar!!.hide()

        getIdx = intent.getStringExtra("idx")
        getSubIdx = intent.getStringExtra("subIdx")

        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        getReason()
        getData()

        allRadioBtn.setOnClickListener {
            if(allRadioBtn.isSelected) {
                (recycler.adapter as OrderDataAdapter).allData(false)
                allRadioBtn.isSelected = false
            }else {
                (recycler.adapter as OrderDataAdapter).allData(true)
                allRadioBtn.isSelected = true
            }
        }

        cancelBtn.setOnClickListener {
            finish()
        }

        applyBtn.setOnClickListener {
            var selectItem = ArrayList<String>()
            var getData = (recycler.adapter as OrderDataAdapter).getData
            for (i in 0 ..getData.size) {
                if(i == getData.size) {
                    if(selectItem.size == 0 ) {
                        ShowToast("취소할 상품을 선택해주세요.")
                    }else if(rSpinner.selectedItemPosition == 0) {
                        ShowToast("취소 사유를 선택해주세요.")
                    }else {
                        disposable.add(apiService.ORDER_CANCEL_API(id_user,getIdx,selectItem,reasonItem.get(rSpinner.selectedItemPosition - 1).canidx)
                            .subscribeOn(io)
                            .observeOn(thread)
                            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                                override fun onError(e: Throwable?) {
                                    ConnectionError()
                                }

                                override fun onSuccess(t: TrueFalseAPI) {
                                    if(t.success) {
                                        ShowToast("주문이 취소되었습니다.")
                                        finish()
                                    }else {
                                        ConnectionError()
                                    }
                                }
                            }))
                    }
                }else {
                    if(getData.get(i).select) {
                        selectItem.add(getData.get(i).sub_o_index)
                    }
                }
            }
        }
    }

    fun getData() {
        disposable.add(apiService.ORDER_SHORT_INFO_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderShortInfoAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderShortInfoAPI) {
                    var tempData = ArrayList<OrderAPI.OrderList.OrderItemList>()
                    if(t.success) {

                        oCodeLabel.setText(t.date + " " + getIdx)
                        tempData = t.response as ArrayList<OrderAPI.OrderList.OrderItemList>
                    }

                    Log.d("object",tempData.size.toString())
                    var adapter = OrderDataAdapter(applicationContext,this@OrderCancelActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }


    fun getReason() {
        disposable.add(apiService.CANCEL_REASON_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<CancelReasonAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: CancelReasonAPI) {

                    if(t.success) {
                        item.add("취소 사유를 선택해주세요.")
                        reasonItem = t.response as ArrayList<CancelReasonAPI.CancelReasonList>
                        for (i in 0 ..t.response.size) {
                            if(i == t.response.size) {
                                rSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,item)

                            }else {
                                item.add(t.response.get(i).canname)
                            }
                        }
                    }
                }
            }))
    }

    fun allCheck(getData : ArrayList<OrderAPI.OrderList.OrderItemList>) {
        var allRadio = true
        for (i in 0..getData.size) {
            if (i == getData.size) {

                allRadioBtn.isSelected = allRadio
            }else {
                if(!getData.get(i).select) {
                    allRadio = false
                }
            }
        }
    }
}
