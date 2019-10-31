package com.sochic.sochic.PayFolder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.sochic.sochic.MyPageFolder.API.AddressAPI
import com.sochic.sochic.MyPageFolder.API.MypageAPI
import com.sochic.sochic.PayFolder.API.*
import com.sochic.sochic.PayFolder.Adapter.PaidCouponAdapter
import com.sochic.sochic.PayFolder.Adapter.PaidDeliveryAdapter
import com.sochic.sochic.PayFolder.Adapter.PaidItemAdapter
import com.sochic.sochic.R
import com.sochic.sochic.SettingFolder.ScWebActivity
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.GlideImageLoader
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.youth.banner.Transformer
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_pre_paid.*

class PrePaidActivity : ScActivity() {

    var get_oCode = ""
    var productTotalPrice : Int = 0
    var deliveryPrice : Int = 0
    var usePointPrice : Int = 0
    var useCpnPrice : Int = 0
    var payTotalPrice : Int = 0
    var myEmail : String = ""
    var myPoint : Int = 0
    var cpnData = ArrayList<MyCouponAPI.MyCouponList>()
    var payType : Int = 0
    var memoData = ArrayList<DeliveryMemoAPI.DeliveryMemoList>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_pre_paid)

        get_oCode = intent.getStringExtra("o_code")

        backBtn.setOnClickListener {
            finish()
        }

        mRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        dRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        couponRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)


        memoItem()
        cpnItem()
        getMyInfo()
        savePoint()
        getDeliveryItem()

        oSameRadioBtn.setOnClickListener {

            if(oSameRadioBtn.isSelected) {
                oSameRadioBtn.isSelected = false
                oNameEdit.setText("")
                oPhoneEdit.setText("")
                oEmailEdit.setText("")
            }else {
                oSameRadioBtn.isSelected = true

                if(dRecycler.adapter != null) {

                    var data = (dRecycler.adapter as PaidDeliveryAdapter).getDatas
                    for (i in 0..data.size-1){
                        if(data.get(i).d_selected) {
                            oNameEdit.setText(data.get(i).d_name)
                            oPhoneEdit.setText(data.get(i).d_phone)
                            oEmailEdit.setText(myEmail)
                        }
                    }
                }else {
                    ShowToast("배송지를 추가해주세요.")
                }
            }
        }


        dAddedBtn.setOnClickListener {
            startActivity(Intent(applicationContext,DelivieryAddActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

        myPointAllUsedBtn.setOnClickListener {
            if(myPointAllUsedBtn.isSelected) {
                usedPointEdit.setText("0")
                usePointPrice = 0
                calcuratePrice()
                myPointAllUsedBtn.isSelected = false
            }else {
                if(deliveryPrice + productTotalPrice - useCpnPrice <= myPoint) {
                    var calPrice = deliveryPrice + productTotalPrice - useCpnPrice
                    usePointPrice = calPrice
                    usedPointEdit.setText(calPrice.toString())
                    calcuratePrice()

                }else {
                    usedPointEdit.setText(myPoint.toString())
                    usePointPrice = myPoint
                    calcuratePrice()

                }
                myPointAllUsedBtn.isSelected = true
            }
        }

        pointUsedBtn.setOnClickListener {
            if(usedPointEdit.text.toString().isEmpty()) {
                usedPointEdit.setText("0")
                usePointPrice = 0
                calcuratePrice()
            }else {

                var editPrice = usedPointEdit.text.toString().toInt()
                if(editPrice > myPoint) {

                    if(deliveryPrice + productTotalPrice - useCpnPrice <= myPoint) {
                        var calPrice = deliveryPrice + productTotalPrice - useCpnPrice
                        usePointPrice = calPrice
                        usedPointEdit.setText(calPrice.toString())
                        calcuratePrice()

                    }else {
                        usedPointEdit.setText(myPoint.toString())
                        usePointPrice = myPoint
                        calcuratePrice()

                    }


                }else {

                    usePointPrice = usedPointEdit.text.toString().toInt()
                    if(deliveryPrice + productTotalPrice - useCpnPrice <= usePointPrice) {
                        var calPrice = deliveryPrice + productTotalPrice - useCpnPrice
                        usePointPrice = calPrice
                        usedPointEdit.setText(calPrice.toString())
                        calcuratePrice()
                    }
                    calcuratePrice()
                }

            }
        }

        cntCheck()

        payTypeOneBtn.setOnClickListener {
            payType =1
            payTypeOneBtn.setTextColor(resources.getColor(R.color.blackColor))
            payTypeOneBtn.setBackgroundResource(R.drawable.category_select_layer)
        }

        payTypeTwoBtn.setOnClickListener {
            ShowToast("준비중입니다.")
        }

        payTypeThirdBtn.setOnClickListener {
            ShowToast("준비중입니다.")
        }

        payTypeThreeBtn.setOnClickListener {
            ShowToast("준비중입니다.")
        }

        payAgreeBtn.setOnClickListener {
            payAgreeRadio.isSelected = !payAgreeRadio.isSelected
        }

        payAgreeRadio.setOnClickListener {
            payAgreeBtn.callOnClick()
        }

        paidBtn.setOnClickListener {
            var dData = (dRecycler.adapter as PaidDeliveryAdapter).getDatas
            var cData = (couponRecycler.adapter as PaidCouponAdapter).getDatas
            if((dRecycler.adapter as PaidDeliveryAdapter).getDatas.size == 0) {
                ShowToast("배송지를 등록해주세요.")
            }else if(!checkSelectedDelivery(dData)) {
                ShowToast("배송지를 선택해주세요.")
            }else if(oNameEdit.text.toString().isEmpty()){
                ShowToast("주문자 이름을 입력해주세요.")
            }else if(oPhoneEdit.text.toString().isEmpty()) {
                ShowToast("주문자 연락처를 입력해주세요.")
            }else if(payType == 0){
                ShowToast("결제 수단을 선택해주세요.")
            }else if(!payAgreeRadio.isSelected){
                ShowToast("결제 약관에 동의해주세요.")
            }else {
                var cIdxList = ArrayList<String>()
                var dIdx = getDeliveryIdx(dData)
                var mIdx = ""
                if(dMemoSpinner.selectedItemPosition == 0) {
                    mIdx = ""
                }else {
                    mIdx = memoData.get(dMemoSpinner.selectedItemPosition - 1).m_idx
                }
                if(cData.size >0) {
                    for (i in 0..cData.size-1){
                        cIdxList.add(cData.get(i).c_idx)
                    }
                }
                disposable.add(apiService.ORDER_PAY_API(get_oCode,
                    payTotalPrice,
                    cIdxList,
                    dIdx,
                    usePointPrice,
                    mIdx,
                    oNameEdit.text.toString(),
                    oPhoneEdit.text.toString(),
                    oEmailEdit.text.toString(),
                    useCpnPrice)
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                finish()
                                startActivity(Intent(applicationContext,PayWebActivity::class.java)
                                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                    .putExtra("o_code",get_oCode))
                            }else {
                                ConnectionError()
                            }
                        }
                    }))

            }
        }
    }

    fun getDeliveryItem() {
        disposable.add(apiService.ORDER_TEMP_DELIVERY_API(get_oCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderTempDeliveryAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderTempDeliveryAPI) {
                    if(t.success) {
                        deliveryPrice = t.d_price
                        calcuratePrice()

                        termShowBtn.setOnClickListener {
                            startActivity(Intent(applicationContext,ScWebActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                .putExtra("title","결제 약관")
                                .putExtra("url",t.url))
                        }
                    }
                }
            }))
    }

    fun cntCheck() {
        disposable.add(apiService.ORDER_TEMP_CHECK_API(get_oCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderTempCheckAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderTempCheckAPI) {
                    if(t.success) {
                        if(t.type == 0)  {
                            singleLayer.visibility = View.GONE
                            multiLayer.visibility = View.VISIBLE
                            multiItem()
                        }else {
                            singleLayer.visibility = View.GONE
                            multiLayer.visibility = View.VISIBLE
                            multiItem()
                        }
                    }else {
                        ConnectionError()
                    }
                }
            }))
    }

    fun singleItem() {

        disposable.add(apiService.ORDER_TEMP_SINGLE_INFO_API(get_oCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderTempInfoAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderTempInfoAPI) {
                    if(t.success) {
                        var data = t.response.get(0)

                        sNameLabel.setText(data.name)
                        sInfoLabel.setText(data.option_name + " ${PriceUtil.set(data.cnt.toString())}개")

                        if(data.sale_confirm) {
                            productTotalPrice = (data.sale_price + data.add_price) * data.cnt
                            saleLabel.setText(PriceUtil.set("${(data.sale_price + data.add_price) * data.cnt}") + "원")
                            priceLabel.setText(PriceUtil.set(data.price.toString()) + "원")
                        }else {
                            productTotalPrice = (data.price + data.add_price) * data.cnt
                            saleLabel.setText(PriceUtil.set("${(data.price + data.add_price) * data.cnt}") + "원")
                            priceLabel.setText("")
                        }

                        if(data.option_confirm) {
                            sOptionChangeBtn.visibility = View.INVISIBLE
                        }else {
                            sOptionChangeBtn.visibility = View.INVISIBLE
                        }

                        calcuratePrice()

                        var items = ArrayList<String>()
                        for (i in 0..data.img_response.size) {
                            if(i == data.img_response.size) {
                                sIndicatorView.count = items.count()
                                sImageView.setBannerStyle(0)
                                sImageView.setImages(items)
                                sImageView.setImageLoader(GlideImageLoader())
                                sImageView.setBannerAnimation(Transformer.Default)
                                sImageView.setIndicatorGravity(6)
                                sImageView.isAutoPlay(false)
                                sImageView.start()

                                sImageView.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                                    override fun onPageScrollStateChanged(state: Int) {

                                    }

                                    override fun onPageScrolled(
                                        position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int
                                    ) {

                                    }

                                    override fun onPageSelected(position: Int) {
                                        sIndicatorView.setSelected(position)
                                    }
                                })
                            }else {
                                items.add(data.img_response.get(i).image)
                            }
                        }
                    }
                }
            }))






    }

    fun multiItem() {
        disposable.add(apiService.ORDER_TEMP_MULTI_INFO_API(get_oCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderTempInfoAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderTempInfoAPI) {
                    var tempData = ArrayList<OrderTempInfoAPI.OrderTempInfoList>()
                    if(t.success) {

                        tempData = t.response as ArrayList<OrderTempInfoAPI.OrderTempInfoList>

                        for (i in 0..t.response.size) {
                            if(i == t.response.size) {
                                mTotalPriceLabel.setText(PriceUtil.set(productTotalPrice.toString()))
                                calcuratePrice()
                            }else {
                                var price : Int = 0
                                if(t.response.get(i).sale_confirm) {
                                    price = (t.response.get(i).sale_price + t.response.get(i).add_price) * t.response.get(i).cnt
                                }else {
                                    price = (t.response.get(i).price + t.response.get(i).add_price) * t.response.get(i).cnt
                                }
                                productTotalPrice = productTotalPrice + price
                            }
                        }
                    }
                    var adapter = PaidItemAdapter(applicationContext,this@PrePaidActivity,tempData)
                    mRecycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))

    }

    fun deliveryItem() {
        disposable.add(apiService.ADDRESS_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<AddressAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: AddressAPI) {
                    var tempData = ArrayList<AddressAPI.AddressList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<AddressAPI.AddressList>
                    }

                    var adapter = PaidDeliveryAdapter(applicationContext,this@PrePaidActivity,tempData)
                    dRecycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))

    }

    fun memoItem() {
        disposable.add(apiService.DELIVERY_MEMO_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<DeliveryMemoAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: DeliveryMemoAPI) {
                    if(t.success) {
                        memoData = t.response as ArrayList<DeliveryMemoAPI.DeliveryMemoList>
                        var items = ArrayList<String>()
                        items.add("배송 메모를 선택해주세요.")
                        for (i in 0..t.response.size) {
                            if( i == t.response.size) {
                                dMemoSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,items)
                            }else {
                                items.add(t.response.get(i).m_title)
                            }
                        }
                    }
                }
            }))
    }

    fun getMyInfo() {
        disposable.add(apiService.MYPAGE_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<MypageAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: MypageAPI) {
                    if(t.success) {
                        myEmail = t.email
                        myPoint = t.point
                        myPointLabel.setText(PriceUtil.set(myPoint.toString()) + "P")
                    }
                }
            }))
    }

    fun savePoint() {
        disposable.add(apiService.ORDER_TEMP_SAVE_POINT_API(get_oCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<OrderTempSavePointAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: OrderTempSavePointAPI) {
                    if(t.success) {
                        savePointLabel.setText(PriceUtil.set(t.point.toString()) + "P")
                    }
                }
            }))
    }

    fun cpnItem() {

        disposable.add(apiService.MY_COUPON_API(id_user,get_oCode)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<MyCouponAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: MyCouponAPI) {
                    var items = ArrayList<String>()
                    items.add("쿠폰목록")

                    var adapter = PaidCouponAdapter(applicationContext,this@PrePaidActivity,cpnData)
                    couponRecycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                    if(t.success) {

                        calcurateCpnPrice()

                        for (i in 0.. t.response.size) {
                            if( i == t.response.size) {
                                couponSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,items)

                                couponSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                    ) {
                                        if(position != 0) {
                                            var tempData = t.response.get(position -1)
                                            if(!checkDuplicationCpn(tempData.c_idx)) {
                                                cpnData.add(tempData)
                                            }
                                            var adapter = PaidCouponAdapter(applicationContext,this@PrePaidActivity,cpnData)
                                            couponRecycler.adapter = adapter
                                            adapter.notifyDataSetChanged()
                                            calcurateCpnPrice()

                                            if(!usedPointEdit.text.toString().isEmpty()) {
                                                usedPointEdit.setText("0")
                                                usePointPrice = 0
                                                myPointAllUsedBtn.isSelected = false
                                                calcurateCpnPrice()
                                            }
                                        }
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {

                                    }
                                }
                            }else {
                                items.add(t.response.get(i).c_name)
                            }
                        }
                    }else {
                        couponSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,items)
                    }
                }
            }))

    }



    fun calcuratePrice() {

        b_productPriceLabel.setText(PriceUtil.set(productTotalPrice.toString()))
        b_deliveryPriceLabel.setText(PriceUtil.set(deliveryPrice.toString()))
        b_cpnUseLabel.setText(PriceUtil.set(useCpnPrice.toString()))
        b_pointUseLabel.setText(PriceUtil.set(usePointPrice.toString()))
        payTotalPrice = productTotalPrice + deliveryPrice - (useCpnPrice + usePointPrice)
        b_totalPriceLabel.setText(PriceUtil.set(payTotalPrice.toString()))

    }

    override fun onResume() {
        super.onResume()
        deliveryItem()
    }

    fun checkDuplicationCpn(idx : String) : Boolean {

        for (i in 0..cpnData.size) {
            if(i == cpnData.size) {
                return false
            }else {
                if(cpnData.get(i).c_idx.toString().equals(idx)) {
                    return  true
                }
            }
        }
        return false
    }

    fun calcurateCpnPrice() {
        useCpnPrice = 0
        for (i in 0..cpnData.size) {
            if(i == cpnData.size) {
                calcuratePrice()
            }else {
                if(cpnData.get(i).c_percent_bool) {
                    useCpnPrice = useCpnPrice + + (productTotalPrice * "${cpnData.get(i).c_value.toFloat() / 100.0f}".toFloat()).toInt()
                }else {
                    useCpnPrice = useCpnPrice + cpnData.get(i).c_value
                }
            }
        }
    }

    fun checkSelectedDelivery(item : ArrayList<AddressAPI.AddressList>) : Boolean {
        for (i in 0..item.size) {
            if(i == item.size) {
                return false
            }else {
                if(item.get(i).d_selected) {
                    return true
                }
            }
        }
        return false
    }

    fun getDeliveryIdx(item : ArrayList<AddressAPI.AddressList>) : String {
        for (i in 0..item.size) {
            if(i == item.size) {
                return ""
            }else {
                if(item.get(i).d_selected) {
                    return item.get(i).d_idx
                }
            }
        }
        return ""
    }
}
