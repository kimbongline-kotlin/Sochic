package com.sochic.sochic.OrderFolder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.MyPageFolder.SearchAddressActivity
import com.sochic.sochic.OrderFolder.API.*
import com.sochic.sochic.OrderFolder.Adapter.OrderDataAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_order_cancel.*
import kotlinx.android.synthetic.main.activity_order_exchange.*
import kotlinx.android.synthetic.main.activity_order_exchange.addressEdit
import kotlinx.android.synthetic.main.activity_order_exchange.allRadioBtn
import kotlinx.android.synthetic.main.activity_order_exchange.applyBtn
import kotlinx.android.synthetic.main.activity_order_exchange.backBtn
import kotlinx.android.synthetic.main.activity_order_exchange.cancelBtn
import kotlinx.android.synthetic.main.activity_order_exchange.detailAddressEdit
import kotlinx.android.synthetic.main.activity_order_exchange.findBtn
import kotlinx.android.synthetic.main.activity_order_exchange.oCodeLabel
import kotlinx.android.synthetic.main.activity_order_exchange.postEdit
import kotlinx.android.synthetic.main.activity_order_exchange.rSpinner
import kotlinx.android.synthetic.main.activity_order_exchange.recycler

class OrderExchangeActivity : ScActivity() {


    var getIdx : String = ""
    var getSubIdx : String = ""
    var item = ArrayList<String>()
    var exReasonItem = ArrayList<ExchangeReasonAPI.ExchageReasonList>()
    var reReasonItem = ArrayList<ReturnReasonAPI.ReturnReasonList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_exchange)
        supportActionBar!!.hide()
        backBtn.setOnClickListener {
            finish()
        }

        getIdx = intent.getStringExtra("idx")
        getSubIdx = intent.getStringExtra("subIdx")


        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)


        changeBtn.setOnClickListener {
            refundBtn.isSelected =false
            changeBtn.isSelected  = true

            changeBtn.setTextColor(resources.getColor(R.color.blackColor))
            refundBtn.setTextColor(resources.getColor(R.color.borderLineColor))

            changeLayer.visibility = View.VISIBLE
            refundLayer.visibility = View.GONE
            getExReason()
        }

        refundBtn.setOnClickListener {
            refundBtn.isSelected =true
            changeBtn.isSelected  = false

            refundBtn.setTextColor(resources.getColor(R.color.blackColor))
            changeBtn.setTextColor(resources.getColor(R.color.borderLineColor))

            changeLayer.visibility = View.GONE
            refundLayer.visibility = View.VISIBLE
            getReReason()
        }

        allRadioBtn.setOnClickListener {
            if(allRadioBtn.isSelected) {
                (recycler.adapter as OrderDataAdapter).allData(false)
                allRadioBtn.isSelected = false
            }else {
                (recycler.adapter as OrderDataAdapter).allData(true)
                allRadioBtn.isSelected = true
            }
        }


        findBtn.setOnClickListener {
            startActivityForResult(
                Intent(applicationContext, SearchAddressActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),8282)

        }

        var bankItem = ArrayList<String>()
        bankItem.add("은행 선택")
        bankItem.add("국민은행")
        bankItem.add("신한은행")
        bankItem.add("카카오뱅크")

        bSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,bankItem)


        changeBtn.callOnClick()

        getData()

        getPrice()
        cancelBtn.setOnClickListener {
            finish()
        }

        applyBtn.setOnClickListener {
            var selectItem = ArrayList<String>()
            var getData = (recycler.adapter as OrderDataAdapter).getData

            for (i in 0 ..getData.size) {
                if(i == getData.size) {
                    if(changeBtn.isSelected) {
                        if(selectItem.size == 0 ) {
                            ShowToast("교환할 상품을 선택해주세요.")
                        }else if(rSpinner.selectedItemPosition == 0) {
                            ShowToast("교환 사유를 선택해주세요.")
                        }else if(postEdit.text.toString().isEmpty()){
                            ShowToast("회수지 우편번호를 입력해주세요.")
                        }else if(addressEdit.text.toString().isEmpty()) {
                            ShowToast("회수지 주소를 입력해주세요.")
                        }else if(detailAddressEdit.text.toString().isEmpty()) {
                            ShowToast("회수지 상세 주소를 입력해주세요.")
                        }else if(infoEdit.text.toString().isEmpty()){
                            ShowToast("상세사유를 입력해주세요.")
                        }else {
                            disposable.add(apiService.ORDER_EXCHANGE_API(id_user,
                                getIdx,selectItem,
                                exReasonItem.get(rSpinner.selectedItemPosition).exidx,
                                postEdit.text.toString(),
                                addressEdit.text.toString(),
                                detailAddressEdit.text.toString(),
                                infoEdit.text.toString())
                                .subscribeOn(io)
                                .observeOn(thread)
                                .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                                    override fun onError(e: Throwable?) {
                                        ConnectionError()
                                    }

                                    override fun onSuccess(t: TrueFalseAPI) {
                                        if(t.success) {
                                            ShowToast("교환 접수를 신청하였습니다.")
                                            finish()
                                        }else {
                                            ConnectionError()
                                        }
                                    }
                                }))
                        }
                    }else {
                        if(selectItem.size == 0 ) {
                            ShowToast("반품할 상품을 선택해주세요.")
                        }else if(rSpinner.selectedItemPosition == 0) {
                            ShowToast("반품 사유를 선택해주세요.")
                        }else if(postEdit.text.toString().isEmpty()){
                            ShowToast("회수지 우편번호를 입력해주세요.")
                        }else if(addressEdit.text.toString().isEmpty()) {
                            ShowToast("회수지 주소를 입력해주세요.")
                        }else if(detailAddressEdit.text.toString().isEmpty()) {
                            ShowToast("회수지 상세 주소를 입력해주세요.")
                        }else if(infoEdit.text.toString().isEmpty()){
                            ShowToast("상세사유를 입력해주세요.")
                        }else if(bSpinner.selectedItemPosition == 0){
                            ShowToast("환불 은행을 선택해주세요.")
                        }else if(bNumberLabel.text.toString().isEmpty()){
                            ShowToast("환불계좌를 입력해주세요.")
                        }else {
                            disposable.add(apiService.ORDER_RETURN_API(id_user,
                                getIdx,selectItem,
                                exReasonItem.get(rSpinner.selectedItemPosition).exidx,
                                postEdit.text.toString(),
                                addressEdit.text.toString(),
                                detailAddressEdit.text.toString(),
                                infoEdit.text.toString(),
                                bankItem.get(bSpinner.selectedItemPosition),
                                bNumberLabel.text.toString())
                                .subscribeOn(io)
                                .observeOn(thread)
                                .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                                    override fun onError(e: Throwable?) {
                                        ConnectionError()
                                    }

                                    override fun onSuccess(t: TrueFalseAPI) {
                                        if(t.success) {
                                            ShowToast("반품 접수를 신청하였습니다.")
                                            finish()
                                        }else {
                                            ConnectionError()
                                        }
                                    }
                                }))
                        }
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
            .subscribeWith(object : DisposableSingleObserver<OrderShortInfoAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderShortInfoAPI) {
                    var tempData = ArrayList<OrderAPI.OrderList.OrderItemList>()
                    if(t.success) {

                        oCodeLabel.setText(t.date + " " + getIdx)
                        tempData = t.response as ArrayList<OrderAPI.OrderList.OrderItemList>
                    }


                    var adapter = OrderDataAdapter(applicationContext,this@OrderExchangeActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    fun getExReason() {
        item.clear()
        exReasonItem.clear()
        disposable.add(apiService.EXCHANGE_REASON_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ExchangeReasonAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: ExchangeReasonAPI) {

                    if(t.success) {
                        item.add("교환 사유를 선택해주세요.")
                        exReasonItem = t.response as ArrayList<ExchangeReasonAPI.ExchageReasonList>
                        for (i in 0 ..t.response.size) {
                            if(i == t.response.size) {
                                rSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,item)

                            }else {
                                item.add(t.response.get(i).exname)
                            }
                        }
                    }
                }
            }))
    }

    fun getReReason() {
        item.clear()
        reReasonItem.clear()
        disposable.add(apiService.RETURN_REASON_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ReturnReasonAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: ReturnReasonAPI) {

                    if(t.success) {
                        item.add("반품 사유를 선택해주세요.")
                        reReasonItem = t.response as ArrayList<ReturnReasonAPI.ReturnReasonList>
                        for (i in 0 ..t.response.size) {
                            if(i == t.response.size) {
                                rSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,item)

                            }else {
                                item.add(t.response.get(i).rename)
                            }
                        }
                    }
                }
            }))
    }

    fun getPrice() {
        disposable.add(apiService.ORDER_EXCHANGE_PRICE_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderExchangePriceAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderExchangePriceAPI) {
                    if(t.success) {
                        exchangePriceLabel.setText(PriceUtil.set(t.exchange_price.toString()) + "원")
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

    override fun onActivityResult(paramInt1: Int, paramInt2: Int, paramIntent: Intent?) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent)

        if(paramInt1 == 8282 && paramInt2 == Activity.RESULT_OK) {
            postEdit.setText(paramIntent!!.getStringExtra("arg1"))
            addressEdit.setText(paramIntent!!.getStringExtra("arg2"))
            detailAddressEdit.setText(paramIntent!!.getStringExtra("arg3"))
        }
    }
}
