package com.sochic.sochic.ProductFolder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.PayFolder.PrePaidActivity
import com.sochic.sochic.ProductFolder.API.OptionCountCheckAPI
import com.sochic.sochic.ProductFolder.API.OptionItemAPI
import com.sochic.sochic.ProductFolder.API.OptionZeroAPI
import com.sochic.sochic.ProductFolder.API.OrderTempAPI
import com.sochic.sochic.ProductFolder.Adapter.ProductOptionAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScTransActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_product_option.*

class ProductOptionActivity : ScTransActivity() {

    var optionFirst = ArrayList<String>()
    var optionSecond = ArrayList<String>()
    var optionListItem = ArrayList<Map<String,String>>()
    var firstOptionData = ArrayList<OptionItemAPI.OptionItemList>()
    var totalPrice : Int = 0
    var multiOption : Int = 0

    var getIdx : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_option)

        getIdx = intent.getStringExtra("idx")
        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        getCounterCheck()

        backBtn.setOnClickListener {
            finish()
        }

        cartBtn.setOnClickListener {
            if(multiOption == 0) {

                var datas = (recycler.adapter as ProductOptionAdapter).getData
                var o_idx = datas.get(0).get("idx")
                var o_cnt = datas.get(0).get("cnt")
                disposable.add(apiService.NONE_ITEM_ADD_CART_API(getIdx,id_user,o_idx,o_cnt!!.toInt(),totalPrice)
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {

                                finish()

                                ShowToast("장바구니에 상품을 추가하였습니다.")
                            }else {
                                ConnectionError()
                            }
                        }
                    }))
            }else if(multiOption == 1) {
                var datas = (recycler.adapter as ProductOptionAdapter).getData
                var o_idx_list = ArrayList<String>()
                var o_cnt_list = ArrayList<String>()
                var o_price_list = ArrayList<String>()
                if(spinnerOne.selectedItemPosition == 0) {
                    ShowToast("옵션을 선택해주세요.")
                }else {
                    for (i in 0..datas.size) {
                        if(i == datas.size) {

                            disposable.add(apiService.SINGLE_ITEM_ADD_CART_API(getIdx,id_user,
                                o_idx_list,o_cnt_list,o_price_list)
                                .subscribeOn(io)
                                .observeOn(thread)
                                .subscribeWith(object : DisposableSingleObserver<OrderTempAPI>() {
                                    override fun onError(e: Throwable?) {

                                    }

                                    override fun onSuccess(t: OrderTempAPI) {
                                        if(t.success) {

                                            finish()

                                            ShowToast("장바구니에 상품을 추가하였습니다.")
                                        }else {
                                            ConnectionError()
                                        }
                                    }
                                }))

                        }else {
                            o_idx_list.add(datas.get(i).get("idx")!!)
                            o_cnt_list.add(datas.get(i).get("cnt")!!)
                            var cal_price = "${(datas.get(i).get("price")!!.toInt() + datas.get(i).get("add_price")!!.toInt()) * datas.get(i).get("cnt")!!.toInt()}"
                            o_price_list.add(cal_price)
                        }
                    }
                }
            }else {
                if(spinnerOne.selectedItemPosition == 0 || spinnerTwo.selectedItemPosition == 0){
                    ShowToast("옵션을 선택해주세요.")
                }else {
                    var datas = (recycler.adapter as ProductOptionAdapter).getData
                    var o_idx_list = ArrayList<String>()
                    var o_cnt_list = ArrayList<String>()
                    var o_price_list = ArrayList<String>()
                    var sub_o_idx_list = ArrayList<String>()

                    for (i in 0..datas.size) {
                        if(i == datas.size) {

                            disposable.add(apiService.MULTI_ITEM_ADD_CART_API(getIdx,id_user,
                                o_idx_list,sub_o_idx_list,o_cnt_list,o_price_list)
                                .subscribeOn(io)
                                .observeOn(thread)
                                .subscribeWith(object : DisposableSingleObserver<OrderTempAPI>() {
                                    override fun onError(e: Throwable?) {

                                    }

                                    override fun onSuccess(t: OrderTempAPI) {

                                        if(t.success) {

                                            finish()

                                            ShowToast("장바구니에 상품을 추가하였습니다.")
                                        }else {
                                            ConnectionError()
                                        }
                                    }
                                }))

                        }else {
                            o_idx_list.add(datas.get(i).get("p_idx")!!)
                            o_cnt_list.add(datas.get(i).get("cnt")!!)
                            var cal_price = "${(datas.get(i).get("price")!!.toInt() + datas.get(i).get("add_price")!!.toInt()) * datas.get(i).get("cnt")!!.toInt()}"
                            o_price_list.add(cal_price)
                            sub_o_idx_list.add(datas.get(i).get("idx")!!)
                        }
                    }

                }
            }
        }

        buyBtn.setOnClickListener {
            if(multiOption == 0) {
                var datas = (recycler.adapter as ProductOptionAdapter).getData
                var o_idx = datas.get(0).get("idx")
                var o_cnt = datas.get(0).get("cnt")

                disposable.add(apiService.ORDER_NONE_TEMP_API(getIdx,id_user,o_idx,o_cnt,totalPrice.toString())
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<OrderTempAPI>() {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: OrderTempAPI) {
                            if(t.success) {

                                finish()
                                startActivity(Intent(applicationContext,PrePaidActivity::class.java)
                                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                    .putExtra("o_code",t.o_code))
                            }else {
                                ConnectionError()
                            }
                        }
                    }))
            }else if(multiOption == 1) {
                var datas = (recycler.adapter as ProductOptionAdapter).getData
                var o_idx_list = ArrayList<String>()
                var o_cnt_list = ArrayList<String>()
                var o_price_list = ArrayList<String>()
                if(spinnerOne.selectedItemPosition == 0) {
                    ShowToast("옵션을 선택해주세요.")
                }else {
                    for (i in 0..datas.size) {
                        if(i == datas.size) {

                            disposable.add(apiService.ORDER_SINGLE_TEMP_API(getIdx,id_user,
                                o_idx_list,o_cnt_list,o_price_list)
                                .subscribeOn(io)
                                .observeOn(thread)
                                .subscribeWith(object : DisposableSingleObserver<OrderTempAPI>() {
                                    override fun onError(e: Throwable?) {

                                    }

                                    override fun onSuccess(t: OrderTempAPI) {
                                        if(t.success) {

                                            finish()
                                            startActivity(Intent(applicationContext,PrePaidActivity::class.java)
                                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                                .putExtra("o_code",t.o_code))
                                        }else {
                                            ConnectionError()
                                        }
                                    }
                                }))

                        }else {
                            o_idx_list.add(datas.get(i).get("idx")!!)
                            o_cnt_list.add(datas.get(i).get("cnt")!!)
                            var cal_price = "${(datas.get(i).get("price")!!.toInt() + datas.get(i).get("add_price")!!.toInt()) * datas.get(i).get("cnt")!!.toInt()}"
                            o_price_list.add(cal_price)
                        }
                    }
                }

            }else {
                if(spinnerOne.selectedItemPosition == 0 || spinnerTwo.selectedItemPosition == 0){
                    ShowToast("옵션을 선택해주세요.")
                }else {
                    var datas = (recycler.adapter as ProductOptionAdapter).getData
                    var o_idx_list = ArrayList<String>()
                    var o_cnt_list = ArrayList<String>()
                    var o_price_list = ArrayList<String>()
                    var sub_o_idx_list = ArrayList<String>()

                    for (i in 0..datas.size) {
                        if(i == datas.size) {

                            disposable.add(apiService.ORDER_MULTI_TEMP_API(getIdx,id_user,
                                o_idx_list,sub_o_idx_list,o_cnt_list,o_price_list)
                                .subscribeOn(io)
                                .observeOn(thread)
                                .subscribeWith(object : DisposableSingleObserver<OrderTempAPI>() {
                                    override fun onError(e: Throwable?) {

                                    }

                                    override fun onSuccess(t: OrderTempAPI) {

                                        if(t.success) {

                                            finish()
                                            startActivity(Intent(applicationContext,PrePaidActivity::class.java)
                                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                                .putExtra("o_code",t.o_code))
                                        }else {
                                            ConnectionError()
                                        }
                                    }
                                }))

                        }else {
                            o_idx_list.add(datas.get(i).get("p_idx")!!)
                            o_cnt_list.add(datas.get(i).get("cnt")!!)
                            var cal_price = "${(datas.get(i).get("price")!!.toInt() + datas.get(i).get("add_price")!!.toInt()) * datas.get(i).get("cnt")!!.toInt()}"
                            o_price_list.add(cal_price)
                            sub_o_idx_list.add(datas.get(i).get("idx")!!)
                        }
                    }

                }
            }

        }
    }

    fun getCounterCheck() {
        disposable.add(apiService.OPTION_COUNT_CHECK_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OptionCountCheckAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OptionCountCheckAPI) {
                    if(t.success) {
                        multiOption = t.type
                        layoutSetting()
                    }
                }
            }))
    }

    fun layoutSetting() {
        if(multiOption == 0) {
            spinner1Layer.visibility = View.GONE
            spinner2Layer.visibility = View.GONE
            optionZero()
        }else if(multiOption == 1) {
            spinner1Layer.visibility = View.VISIBLE
            spinner2Layer.visibility = View.GONE
            optionSingle()
        }else if(multiOption == 2) {
            spinner1Layer.visibility = View.VISIBLE
            spinner2Layer.visibility = View.VISIBLE
            optionMultiItem()

        }

    }

    fun optionZero() {
        disposable.add(apiService.OPTION_ZERO_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OptionZeroAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OptionZeroAPI) {
                    if(t.success) {


                        var mapItem = mapOf<String,String>("idx" to "${t.o_idx}",
                            "option_name" to "${t.o_name}",
                            "price" to "${t.price}",
                            "max_cnt" to "${t.o_max_cnt}",
                            "cnt" to "1",
                            "add_price" to "${t.add_price}",
                            "p_idx" to "")
                        optionListItem.add(mapItem)
                        var adapter = ProductOptionAdapter(applicationContext,this@ProductOptionActivity,optionListItem,multiOption,"")
                        recycler.adapter = adapter
                        adapter.notifyDataSetChanged()
                        calcurateTotalPrice(optionListItem)
                    }else {
                        ConnectionError()
                    }
                }
            }))
    }

    fun optionSingle() {

        disposable.add(apiService.OPTION_SINGLE_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OptionItemAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OptionItemAPI) {
                    if(t.success) {
                        optionFirst.add(t.option_title)

                        for (i in 0..t.response.size) {
                            if( i == t.response.size) {
                                spinnerOne.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,optionFirst)
                                spinnerSelectItem(t.response as ArrayList<OptionItemAPI.OptionItemList>,t.option_title,"")
                            }else {
                                optionFirst.add(t.response.get(i).o_name)
                            }
                        }
                    }
                }
            }))
    }

    fun optionMultiItem() {

        disposable.add(apiService.OPTION_SINGLE_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OptionItemAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OptionItemAPI) {
                    if(t.success) {
                        firstOptionData = t.response as ArrayList<OptionItemAPI.OptionItemList>
                        optionFirst.add(t.option_title)

                        for (i in 0..t.response.size) {
                            if( i == t.response.size) {
                                spinnerOne.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,optionFirst)
                                disposable.add(apiService.OPTION_Multi_API(getIdx,t.response.get(0).o_idx)
                                    .subscribeOn(io)
                                    .observeOn(thread)
                                    .subscribeWith(object : DisposableSingleObserver<OptionItemAPI>() {
                                        override fun onError(e: Throwable?) {
                                            ConnectionError()
                                        }

                                        override fun onSuccess(sec_t: OptionItemAPI) {
                                            if(sec_t.success){
                                                optionSecond.add(sec_t.option_title)
                                                spinnerTwo.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,optionSecond)
                                                spinnerSelectItem(t.response as ArrayList<OptionItemAPI.OptionItemList>,t.option_title,sec_t.option_title)
                                            }else {
                                                ConnectionError()
                                            }
                                        }
                                    }))
                              //  spinnerSelectItem(t.response as ArrayList<OptionItemAPI.OptionItemList>,t.option_title)
                            }else {
                                optionFirst.add(t.response.get(i).o_name)
                            }
                        }
                    }
                }
            }))


    }

    fun spinnerSelectItem(items : ArrayList<OptionItemAPI.OptionItemList>, title : String, secTitle : String) {
        spinnerOne.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(multiOption == 1) {
                    if(position == 0) {
                        optionListItem.clear()
                        var adaper = ProductOptionAdapter(applicationContext,this@ProductOptionActivity,optionListItem,multiOption,title)
                        recycler.adapter = adaper
                        adaper.notifyDataSetChanged()
                        calcurateTotalPrice(optionListItem)
                    }else {

                        var mapItem = mapOf<String,String>("idx" to "${items.get(position-1).o_idx}",
                            "option_name" to "${items.get(position-1).o_name}",
                            "price" to "${items.get(position-1).price}",
                            "max_cnt" to "${items.get(position-1).o_max_cnt}",
                            "cnt" to "1",
                            "add_price" to "${items.get(position-1).add_price}",
                            "p_idx" to "")
                        if(singleDuplicationCheck(mapItem)) {
                            var position = getPosition(mapItem)
                            optionListItem.removeAt(position)
                            optionListItem.add(position,mapItem)

                        }else {
                            optionListItem.add(mapItem)
                        }

                        var adapter = ProductOptionAdapter(applicationContext,this@ProductOptionActivity,optionListItem,multiOption,title)
                        recycler.adapter = adapter
                        adapter.notifyDataSetChanged()
                        calcurateTotalPrice(optionListItem)
                    }
                }else if(multiOption == 2) {

                    if(position == 0) {
                        optionSecond.clear()
                        optionSecond.add(secTitle)
                        spinnerTwo.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,optionSecond)
                        spinnerTwo.setSelection(0)
                    }else {
                        optionSecond.clear()

                        disposable.add(apiService.OPTION_Multi_API(getIdx,items.get(position-1).o_idx)
                            .subscribeOn(io)
                            .observeOn(thread)
                            .subscribeWith(object : DisposableSingleObserver<OptionItemAPI> () {
                                override fun onSuccess(t: OptionItemAPI) {

                                    if(t.success) {
                                        optionSecond.add(t.option_title)
                                        for (i in 0..t.response.size) {
                                            if(i == t.response.size) {
                                                spinnerTwo.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,optionSecond)
                                                spinnerTwo.setSelection(0)
                                                multiSpinnerSelect(items.get(position-1),
                                                    t.response as ArrayList<OptionItemAPI.OptionItemList>,title,secTitle)
                                            }else {
                                                optionSecond.add(t.response.get(i).o_name)
                                            }
                                        }
                                    }
                                }

                                override fun onError(e: Throwable?) {
                                    ConnectionError()
                                }

                            }))
                    }


                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun multiSpinnerSelect(firstItem : OptionItemAPI.OptionItemList, secItems : ArrayList<OptionItemAPI.OptionItemList>, firstTitle : String, secTitle: String) {
        spinnerTwo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position != 0) {
                    var mapItem = mapOf<String,String>("idx" to "${secItems.get(position-1).o_idx}",
                        "option_name" to "${firstItem.o_name}/${secItems.get(position-1).o_name}",
                        "price" to "${secItems.get(position-1).price}",
                        "max_cnt" to "${secItems.get(position-1).o_max_cnt}",
                        "cnt" to "1",
                        "add_price" to "${secItems.get(position-1).add_price + firstItem.add_price}",
                        "p_idx" to firstItem.o_idx)
                    if(singleDuplicationCheck(mapItem)) {
                        var position = getPosition(mapItem)
                        optionListItem.removeAt(position)
                        optionListItem.add(position,mapItem)

                    }else {
                        optionListItem.add(mapItem)
                    }

                    var adapter = ProductOptionAdapter(applicationContext,this@ProductOptionActivity,optionListItem,multiOption,"$firstTitle/$secTitle")
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                    calcurateTotalPrice(optionListItem)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun calcurateTotalPrice(data : ArrayList<Map<String,String>>) {


        totalPrice = 0
        for (i in 0..data.size) {
            if( i == data.size) {
                if(totalPrice == 0) {
                    when (multiOption) {
                        0 -> {

                        }

                        1-> {
                            spinnerOne.setSelection(0)
                        }

                        2 -> {
                            spinnerOne.setSelection(0)
                            spinnerTwo.setSelection(0)
                        }
                    }
                }
                totalPriceLabel.setText(PriceUtil.set("$totalPrice"))
            }else {
                totalPrice = totalPrice + ((data.get(i).get("price")!!.toInt() + data.get(i).get("add_price")!!.toInt()) * data.get(i).get("cnt")!!.toInt())
            }
        }
    }

    fun singleDuplicationCheck(map : Map<String,String>) : Boolean {
        for (i in 0..optionListItem.size ) {

            if( i == optionListItem.size) {
                return false
            }else {

                if(optionListItem.get(i).get("option_name")!!.toString().equals(map.get("option_name")!!.toString())){
                    return true
                }
            }
        }
        return false
    }

    fun getPosition(map : Map<String,String>) : Int {
        for (i in 0..optionListItem.size ) {

            if( i == optionListItem.size) {
                return -1
            }else {

                if(optionListItem.get(i).get("option_name")!!.toString().equals(map.get("option_name")!!.toString())){
                    return i
                }
            }
        }
        return -1
    }

}
