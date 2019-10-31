package com.sochic.sochic.SellerFolder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.API.SubCategoryAPI
import com.sochic.sochic.SellerFolder.Adapter.OptionApplyAdapter
import com.sochic.sochic.SellerFolder.Adapter.OptionMultiApplyAdapter
import com.sochic.sochic.SellerFolder.Adapter.SellerCodiAdapter
import com.sochic.sochic.SellerFolder.Adapter.SellerPhotoAdapter
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_seller_product_upload.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SellerProductUploadActivity : ScActivity() {


    var subCategoryItem = ArrayList<SubCategoryAPI.SubCategoryList>()
    var mainImageFile = ArrayList<File>()
    var subImageFile = ArrayList<File>()
    var addImageFile = ArrayList<File>()
    var codyItemList = ArrayList<String>()

    private val MAIN_IMAGE_RESULT = 1
    private val SUB_IMAGE_RESULT = 2
    private val ADD_IMAGE_RESULT = 3
    private val INFO_CONTENTS_RESULT = 4
    private val SIZE_CONTENTS_RESULT =5
    private val P_INFO_CONTENTS_RESULT = 6
    private val CODI_ITEM_RESULT = 7
    public var infoHtmls = ""
    public var sizeHtmls = ""
    public var p_infoHtmls = ""
    public var firstOptionNames = ArrayList<String>()
    public var secOptionNames = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_product_upload)
        supportActionBar!!.hide()



        var categoryOneItem = ArrayList<String>()
        categoryOneItem.add("1차 카테고리")
        categoryOneItem.add("Outer")
        categoryOneItem.add("Top")
        categoryOneItem.add("Pants")
        categoryOneItem.add("Dress")
        categoryOneItem.add("Knit")
        categoryOneItem.add("Shoes")
        categoryOneItem.add("Bag")

        var categoryTwoItem = ArrayList<String>()
        categoryTwoItem.add("2차 카테고리")
        cTwoSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,categoryTwoItem)
        cOneSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,categoryOneItem)

        var typeItem = ArrayList<String>()
        typeItem.add("일반상품")
        typeItem.add("타임상품")
        pTypeSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,typeItem)


        pTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 0) {
                    pTypeLayer.visibility = View.GONE
                    pStartTimeEdit.setText("")
                    pEndTimeEdit.setText("")
                    pTimePriceEdit.setText("")
                }else {
                    pTypeLayer.visibility = View.VISIBLE
                    pStartTimeEdit.setText("")
                    pEndTimeEdit.setText("")
                    pTimePriceEdit.setText("")
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        startTimeBtn.setOnClickListener {
            var dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
        "타임세일 시작 시간",
        "확인",
        "취소",
        "초기화"
            );

            var  myDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            dateTimeDialogFragment.setOnButtonClickListener(object : SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener {
                override fun onNegativeButtonClick(date: Date?) {

                }

                override fun onNeutralButtonClick(date: Date?) {

                }

                override fun onPositiveButtonClick(date: Date?) {
                    pStartTimeEdit.setText(myDateFormat.format(date))

                }
            })
            dateTimeDialogFragment.show(getSupportFragmentManager(), "타임세일 시작 시간");
        }
        pStartTimeEdit.setOnClickListener {
            startTimeBtn.callOnClick()
        }

        endTimeBtn.setOnClickListener {
            var dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "타임세일 종료 시간",
                "확인",
                "취소",
                "초기화"
            );

            var  myDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            dateTimeDialogFragment.setOnButtonClickListener(object : SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener {
                override fun onNegativeButtonClick(date: Date?) {

                }

                override fun onNeutralButtonClick(date: Date?) {

                }

                override fun onPositiveButtonClick(date: Date?) {
                    pEndTimeEdit.setText(myDateFormat.format(date))

                }
            })
            dateTimeDialogFragment.show(getSupportFragmentManager(), "타임세일 종료 시간");
        }
        pEndTimeEdit.setOnClickListener {
            endTimeBtn.callOnClick()
        }

        oRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)


        cOneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position != 0) {
                    getSubCategory((position-1).toString())
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        btns.setOnClickListener {
            finish()
        }

        cntHideActBtn.isSelected = true
        optHideBtn.isSelected = true
        sizeHideBtn.isSelected = true
        dAddPayBtn.isSelected = true
        codyHideBtn.isSelected = true

        dAddPayBtn.setOnClickListener {
            selectLayer(dAddPayBtn,dFreePayBtn)

        }

        dFreePayBtn.setOnClickListener {
            dPayEdit.setText("")
            selectLayer(dFreePayBtn,dAddPayBtn)
        }

        codyShowBtn.setOnClickListener {
            selectLayer(codyShowBtn,codyHideBtn)
            codyAddBtn.visibility = View.VISIBLE
            codyItemList.clear()
            codyRecycler.visibility = View.GONE
            codyAddBtn.setOnClickListener {
                startActivityForResult(Intent(applicationContext,SellerCodiAddActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                ,CODI_ITEM_RESULT)
            }
        }

        codyHideBtn.setOnClickListener {
            selectLayer(codyHideBtn,codyShowBtn)
            codyAddBtn.visibility = View.GONE
            codyRecycler.visibility = View.GONE
        }

        sizeActBtn.setOnClickListener {
            selectLayer(sizeActBtn,sizeHideBtn)
            sizeAddView.visibility = View.VISIBLE
            sizeAddBtn.visibility = View.VISIBLE
            sizeHintLabel.visibility = View.VISIBLE
            sizeWebView.visibility = View.GONE
            sizeShowView.visibility = View.GONE
        }

        sizeHideBtn.setOnClickListener {
            selectLayer(sizeHideBtn,sizeActBtn)
            sizeAddView.visibility = View.GONE
            sizeAddBtn.visibility = View.GONE
            sizeHtmls = ""
            sizeHintLabel.visibility = View.VISIBLE
            sizeWebView.visibility = View.GONE
            sizeShowView.visibility = View.GONE
        }

        sizeEditBtn.setOnClickListener {
            startActivityForResult(Intent(applicationContext,EditorActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("html",sizeHtmls),SIZE_CONTENTS_RESULT)

        }

        cntShowActBtn.setOnClickListener {
            selectLayer(cntShowActBtn,cntHideActBtn)
        }

        cntHideActBtn.setOnClickListener {
            selectLayer(cntHideActBtn,cntShowActBtn)
        }

        optShowBtn.setOnClickListener {
            selectLayer(optShowBtn,optHideBtn)
            oSelectLayer.visibility = View.VISIBLE
            oInitial()
            optonItemAction()

        }

        optHideBtn.setOnClickListener {
            selectLayer(optHideBtn,optShowBtn)
            oSelectLayer.visibility = View.GONE
        }


        mainImageRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.HORIZONTAL,false)
        subImageRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.HORIZONTAL,false)
        addImageRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.HORIZONTAL,false)

        var mAdapter = SellerPhotoAdapter(applicationContext,this,mainImageFile,0)
        var sAdapter = SellerPhotoAdapter(applicationContext,this,subImageFile,1)
        var aAdapter = SellerPhotoAdapter(applicationContext,this,addImageFile,2)

        mainImageRecycler.adapter = mAdapter
        subImageRecycler.adapter = sAdapter
        addImageRecycler.adapter = aAdapter


        infoEditBtn.setOnClickListener {
            startActivityForResult(Intent(applicationContext,EditorActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("html",infoHtmls),INFO_CONTENTS_RESULT)
        }

        pInfoEditBtn.setOnClickListener {
            startActivityForResult(Intent(applicationContext,EditorActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("html",p_infoHtmls),P_INFO_CONTENTS_RESULT)

        }

        saveBtn.setOnClickListener {
            if(cOneSpinner.selectedItemPosition == 0) {
                ShowToast("1차 카테고리를 선택해주세요.")

            }else if(cTwoSpinner.selectedItemPosition == 0) {
                ShowToast("2차 카테고리를 선택해주세요.")
            }else if(pNameEdit.text.toString().isEmpty()) {
                ShowToast("상품명을 입력해주세요.")
            }else if(pPriceEdit.text.toString().isEmpty()) {
                ShowToast("판매가를 입력해주세요.")
            }else if(pTypeSpinner.selectedItemPosition == 1 && pStartTimeEdit.text.toString().isEmpty()) {
                ShowToast("시작시간을 입력해주세요.")
            }else if(pTypeSpinner.selectedItemPosition == 1 && pStartTimeEdit.text.toString().isEmpty()) {
                ShowToast("종료시간을 입력해주세요.")
            }else if(pTypeSpinner.selectedItemPosition == 1 && pTimePriceEdit.text.toString().isEmpty()) {
                ShowToast("타임 세일 가격을 입력해주세요.")
            }else if(optHideBtn.isSelected && maxCntEdit.text.toString().isEmpty()) {
                ShowToast("재고수량을 선택해주세요.")
            }else if(optShowBtn.isSelected && optSpinner.selectedItemPosition == 0) {
                ShowToast("옵션 개수를 선택해주세요.")
            }else if(optShowBtn.isSelected && optSpinner.selectedItemPosition == 1 && oOneNameEdit.text.toString().isEmpty()) {
                ShowToast("옵션명1을 입력해주세요.")
            }else if(optShowBtn.isSelected && optSpinner.selectedItemPosition == 1 && oOneItemEdit.text.toString().isEmpty()) {
                ShowToast("옵션값1을 입력해주세요.")
            }else if(oRecycler.visibility == View.GONE){
                ShowToast("옵션 목록을 적용해주세요.")
            }else if(optShowBtn.isSelected && optSpinner.selectedItemPosition == 1 && (maxCntEdit.text.toString().equals("0") || maxCntEdit.text.toString().isEmpty())) {
                ShowToast("재고 수량을 입력해주세요.")
            }else if(optShowBtn.isSelected && optSpinner.selectedItemPosition == 2 && oOneNameEdit.text.toString().isEmpty()) {
                ShowToast("옵션명1을 입력해주세요.")
            }else if(optShowBtn.isSelected && optSpinner.selectedItemPosition == 2 && oOneItemEdit.text.toString().isEmpty()) {
                ShowToast("옵션값1을 입력해주세요.")
            }else if(optShowBtn.isSelected && optSpinner.selectedItemPosition == 2 && oTwoNameEdit.text.toString().isEmpty()) {
                ShowToast("옵션명2를 입력해주세요.")
            }else if(optShowBtn.isSelected && optSpinner.selectedItemPosition == 2 && oTwoItemEdit.text.toString().isEmpty()) {
                ShowToast("옵션값2를 입력해주세요.")
            }else if(oRecycler.visibility == View.GONE){
                ShowToast("옵션 목록을 적용해주세요.")
            }else if(optShowBtn.isSelected && optSpinner.selectedItemPosition == 2 && (maxCntEdit.text.toString().equals("0") || maxCntEdit.text.toString().isEmpty())) {
                ShowToast("재고 수량을 입력해주세요.")
            }else if(mainImageFile.size == 0) {
                ShowToast("대표이미지를 선택해주세요.")
            }else if(addImageFile.size == 0){
                ShowToast("추가 이미지를 한장 이상 더 선택해주세요.")
            }else if(infoHtmls.toString().isEmpty()) {
                ShowToast("상세 설명을 입력해주세요.")
            }else if(sizeActBtn.isSelected && sizeHtmls.toString().isEmpty()) {
                ShowToast("실측 사이즈 정보를 입력해주세요.")
            }else if(p_infoHtmls.toString().isEmpty()) {
                ShowToast("상품정보고시를 입력해주세요.")
            }else if(dAddPayBtn.isSelected && dPayEdit.text.toString().isEmpty()) {
                ShowToast("배송비를 입력해주세요.")
            }else if(dRefundEdit.text.toString().isEmpty()) {
                ShowToast("반품비를 입력해주세요.")
            }else if(dExchangeEdit.text.toString().isEmpty()) {
                ShowToast("교환비를 입력해주세요.")
            }else {

                var p_id_user = id_user
                var p_type = pTypeSpinner.selectedItemPosition.toString()
                var p_name = pNameEdit.text.toString()
                var p_category : String = "${cOneSpinner.selectedItemPosition-1}"
                var p_sub_category_idx : String = "${subCategoryItem.get(cTwoSpinner.selectedItemPosition-1).sub_category_idx}"
                var p_price : Int = pPriceEdit.text.toString().toInt()
                var p_sale_price : Int = 0
                if(pSalePriceEdit.text.toString().isEmpty()) {
                    p_sale_price = 0
                }else {
                    p_sale_price = pSalePriceEdit.text.toString().toInt()
                }
                var p_sale_start_date : String = pStartTimeEdit.text.toString()
                var p_sale_end_date : String = pEndTimeEdit.text.toString()
                var p_inventory : Int = maxCntEdit.text.toString().toInt()
                var p_inventory_view : Int = 0
                if(cntShowActBtn.isSelected) {
                    p_inventory_view = 1
                }
                var p_option_confirm : Int = 0
                if(optShowBtn.isSelected) {
                    p_option_confirm = optSpinner.selectedItemPosition
                }
                var p_option_cnt : Int = 0
                if(optShowBtn.isSelected) {
                    p_option_cnt = optSpinner.selectedItemPosition
                }
                var p_option_first_name : String = oOneNameEdit.text.toString()
                var p_option_sec_name : String = oTwoNameEdit.text.toString()
                var p_option_first_name_list = firstOptionNames
                var p_option_sec_name_list = secOptionNames
                var p_option_add_price = ArrayList<Int>()
                if(oRecycler.visibility == View.VISIBLE) {
                    if(oRecycler.adapter != null) {
                        if(optSpinner.selectedItemPosition == 1) {
                            p_option_add_price = (oRecycler.adapter as OptionApplyAdapter).oAdded
                        }else {
                            p_option_add_price = (oRecycler.adapter as OptionMultiApplyAdapter).oAdded
                        }
                    }
                }
                var p_option_cnt_value = ArrayList<Int>()
                if(oRecycler.visibility == View.VISIBLE) {
                    if(oRecycler.adapter != null) {
                        if(optSpinner.selectedItemPosition == 1) {
                            p_option_cnt_value = (oRecycler.adapter as OptionApplyAdapter).oMax
                        }else {
                            p_option_cnt_value = (oRecycler.adapter as OptionMultiApplyAdapter).oAdded
                        }
                    }
                }

                var p_pimage = mainImageFile.get(0)
                var p_addImage = addImageFile
                var p_subImage = subImageFile
                var p_comment = infoHtmls
                var p_size_view : Int = 0
                if(sizeActBtn.isSelected) {
                    p_size_view = 1
                }else {
                    p_size_view = 0
                }

                var p_size_info : String = sizeHtmls
                var p_info_noti : String = p_infoHtmls
                var p_delivery_price : Int= 0
                if(dPayEdit.text.toString().isEmpty()) {
                    p_delivery_price = 0
                }else {
                    p_delivery_price = dPayEdit.text.toString().toInt()
                }

                var p_exchange_price : Int= 0
                if(dExchangeEdit.text.toString().isEmpty()) {
                    p_exchange_price = 0
                }else {
                    p_exchange_price = dExchangeEdit.text.toString().toInt()
                }

                var p_return_price : Int= 0
                if(dRefundEdit.text.toString().isEmpty()) {
                    p_return_price = 0
                }else {
                    p_return_price = dRefundEdit.text.toString().toInt()
                }
                var p_cody_item = codyItemList

                var builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart("id_user",p_id_user)
                Log.d("object",p_id_user)
                builder.addFormDataPart("type",p_type)
                Log.d("object",p_type)
                builder.addFormDataPart("name",p_name)
                Log.d("object",p_name)
                builder.addFormDataPart("category",p_category)
                Log.d("object",p_category)
                builder.addFormDataPart("sub_category_idx",p_sub_category_idx)
                Log.d("object",p_sub_category_idx)
                builder.addFormDataPart("price",p_price.toString())
                Log.d("object",p_price.toString())
                builder.addFormDataPart("sale_price",p_sale_price.toString())
                Log.d("object",p_sale_price.toString())
                builder.addFormDataPart("sale_start_date",p_sale_start_date)
                Log.d("object",p_sale_start_date)
                builder.addFormDataPart("sale_end_date",p_sale_end_date)
                Log.d("object",p_sale_end_date)

                builder.addFormDataPart("inventory",p_inventory.toString())
                Log.d("object",p_inventory.toString())
                builder.addFormDataPart("inventory_view",p_inventory_view.toString())
                Log.d("object",p_inventory_view.toString())
                builder.addFormDataPart("option_confirm",p_option_confirm.toString())
                Log.d("object",p_option_confirm.toString())

                builder.addFormDataPart("option_cnt",p_option_cnt.toString())
                Log.d("object",p_option_cnt.toString())
                builder.addFormDataPart("option_first_name",p_option_first_name.toString())
                Log.d("object",p_option_first_name.toString())
                builder.addFormDataPart("option_sec_name",p_option_sec_name.toString())
                Log.d("object",p_option_sec_name.toString())

                for (i in 0..p_option_first_name_list.size ) {
                    if(i != p_option_first_name_list.size) {
                        builder.addFormDataPart("option_first_name_list[]",p_option_first_name_list.get(i).toString())
                        Log.d("object",p_option_first_name_list.get(i).toString())
                    }

                }

                for (i in 0..p_option_sec_name_list.size ) {
                    if(i != p_option_sec_name_list.size) {
                        builder.addFormDataPart("option_sec_name_list[]",p_option_sec_name_list.get(i).toString())
                        Log.d("object",p_option_sec_name_list.get(i).toString())
                    }

                }

                for (i in 0..p_option_add_price.size) {

                    if(i != p_option_add_price.size) {
                        builder.addFormDataPart("option_add_price[]",p_option_add_price.get(i).toString())
                        Log.d("object",p_option_add_price.get(i).toString())
                    }


                }

                for (i in 0..p_option_cnt_value.size) {

                    if(i != p_option_add_price.size) {
                        builder.addFormDataPart("option_cnt_value[]",p_option_cnt_value.get(i).toString())
                        Log.d("object",p_option_cnt_value.get(i).toString())
                    }

                }
                builder.addFormDataPart(
                    "pimage",
                    p_pimage.name,
                    RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        p_pimage
                    )
                )

                for (i in 0..p_subImage.size) {
                    if(i != p_subImage.size) {
                        builder.addFormDataPart(
                            "subimage[]",
                            p_subImage.get(i).name,
                            RequestBody.create(
                                MediaType.parse("multipart/form-data"),
                                p_subImage.get(i)
                            )
                        )
                    }

                }

                for (i in 0..p_addImage.size) {

                    if(i != p_addImage.size) {
                        builder.addFormDataPart(
                            "addimage[]",
                            p_addImage.get(i).name,
                            RequestBody.create(
                                MediaType.parse("multipart/form-data"),
                                p_addImage.get(i)
                            )
                        )
                    }

                }
                builder.addFormDataPart("comment",p_comment.toString())
                Log.d("object",p_comment.toString())

                builder.addFormDataPart("size_view",p_size_view.toString())
                Log.d("object",p_size_view.toString())
                builder.addFormDataPart("size_info",p_size_info.toString())
                Log.d("object",p_size_info.toString())
                builder.addFormDataPart("info_noti",p_info_noti.toString())
                Log.d("object",p_info_noti.toString())

                builder.addFormDataPart("delivery_price",p_delivery_price.toString())
                Log.d("object",p_delivery_price.toString())

                builder.addFormDataPart("exchange_price",p_exchange_price.toString())
                Log.d("object",p_exchange_price.toString())

                builder.addFormDataPart("return_price",p_return_price.toString())
                Log.d("object",p_return_price.toString())


                for (i in 0..p_cody_item.size) {
                    if(i != p_cody_item.size) {
                        builder.addFormDataPart("cody_item[]",p_cody_item.get(i))
                        Log.d("object",p_cody_item.get(i).toString())
                    }

                }
                disposable.add(apiService.SELLER_PRODUCT_UPLOAD_API(builder.build())
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                ShowToast("상품이 등록되었습니다.")
                                finish()
                            }else {
                                ConnectionError()
                            }
                        }
                    }))


            }

        }
    }



    fun selectLayer(btn1 : Button, btn2 : Button){
        btn1.isSelected = true
        btn1.setTextColor(resources.getColor(R.color.blackColor))
        btn2.isSelected = false
        btn2.setTextColor(resources.getColor(R.color.borderLineColor))
    }

    fun oInitial() {
        oItemLayer.visibility = View.GONE
        oSingleLayer.visibility = View.GONE
        oMultiLayer.visibility = View.GONE
        oAddedLayer.visibility = View.GONE
        oRecycler.visibility = View.GONE
        oApplyLayer.visibility = View.GONE
    }

    fun getSubCategory(idx : String) {
        disposable.add(apiService.SUB_CATEGORY_API(idx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SubCategoryAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: SubCategoryAPI) {
                    if(t.success) {
                        subCategoryItem.clear()
                        subCategoryItem = t.response as ArrayList<SubCategoryAPI.SubCategoryList>
                        var categoryTwoItem = ArrayList<String>()
                        categoryTwoItem.add("2차 카테고리")

                        for (i in 0..t.response.size) {
                            if(i == t.response.size) {
                                cTwoSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,categoryTwoItem)
                            }else {
                                categoryTwoItem.add(t.response.get(i).name)
                            }
                        }
                    }
                }
            }))
    }

    fun optonItemAction() {
        oOneNameEdit.setText("")
        oOneItemEdit.setText("")
        oTwoNameEdit.setText("")
        oTwoItemEdit.setText("")
        firstOptionNames.clear()
        secOptionNames.clear()
        var oCntItem = ArrayList<String>()
        oCntItem.add("옵션 개수를 선택해주세요.")
        oCntItem.add("1개")
        oCntItem.add("2개")
        optSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,oCntItem)
        optSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 0){
                    oInitial()
                }else if(position == 1){
                    oInitial()
                    oItemLayer.visibility = View.VISIBLE
                    oSingleLayer.visibility = View.VISIBLE
                    oAddedLayer.visibility = View.VISIBLE


                    optSaveBtn.setOnClickListener {
                        if(oOneNameEdit.text.toString().isEmpty()) {
                            ShowToast("옵션명1을 입력해주세요.")
                        }else if(oOneItemEdit.text.toString().isEmpty()) {
                            ShowToast("옵션값1을 입력해주세요.")
                        }else {
                            var lastCharacter = oOneItemEdit.text.toString().substring(oOneItemEdit.text.toString().length-1,oOneItemEdit.text.toString().length)
                            var firstCharacter = oOneItemEdit.text.toString().substring(0,1)
                            if(oOneItemEdit.text.toString().contains(",")){
                                if(lastCharacter.toString().equals(",")){
                                    oOneItemEdit.setText(oOneItemEdit.text.toString().substring(0,oOneItemEdit.text.toString().length-1))
                                }
                                if(firstCharacter.toString().equals(",")){
                                    oOneItemEdit.setText(oOneItemEdit.text.toString().substring(1,oOneItemEdit.text.toString().length))
                                }
                                var tempOptionItem = oOneItemEdit.text.toString().split(",")
                                firstOptionNames = tempOptionItem as ArrayList<String>
                                var oNames = ArrayList<String>()
                                var oItems = ArrayList<String>()
                                var oAddPrices = ArrayList<Int>()
                                var oMaxCnts = ArrayList<Int>()
                                for (i in 0.. tempOptionItem.size) {
                                    if(i == tempOptionItem.size) {

                                        oApplyLayer.visibility = View.VISIBLE
                                        oRecycler.visibility = View.VISIBLE
                                        var adapter = OptionApplyAdapter(applicationContext,this@SellerProductUploadActivity,oNames,oItems,oAddPrices,oMaxCnts)
                                        oRecycler.adapter = adapter
                                        adapter.notifyDataSetChanged()
                                    }else {
                                        oNames.add(oOneNameEdit.text.toString())
                                        oItems.add(tempOptionItem.get(i))
                                        oAddPrices.add(0)
                                        oMaxCnts.add(0)
                                    }
                                }
                            }else {
                                var oNames = ArrayList<String>()
                                var oItems = ArrayList<String>()
                                var oAddPrices = ArrayList<Int>()
                                var oMaxCnts = ArrayList<Int>()
                                oNames.add(oOneNameEdit.text.toString())
                                oItems.add(oOneItemEdit.text.toString())
                                oAddPrices.add(0)
                                oMaxCnts.add(0)

                                oApplyLayer.visibility = View.VISIBLE
                                oRecycler.visibility = View.VISIBLE
                                var adapter = OptionApplyAdapter(applicationContext,this@SellerProductUploadActivity,oNames,oItems,oAddPrices,oMaxCnts)
                                oRecycler.adapter = adapter
                                adapter.notifyDataSetChanged()

                                firstOptionNames = oItems

                            }
                        }
                    }


                }else {
                    oInitial()
                    oItemLayer.visibility = View.VISIBLE
                    oSingleLayer.visibility = View.VISIBLE
                    oMultiLayer.visibility = View.VISIBLE
                    oAddedLayer.visibility = View.VISIBLE

                    optSaveBtn.setOnClickListener {
                        if(oOneNameEdit.text.toString().isEmpty()) {
                            ShowToast("옵션명1을 입력해주세요.")
                        }else if(oOneItemEdit.text.toString().isEmpty()) {
                            ShowToast("옵션값1을 입력해주세요.")
                        }else if(oTwoNameEdit.text.toString().isEmpty()) {
                            ShowToast("옵션명2를 입력해주세요.")
                        }else if(oTwoItemEdit.text.toString().isEmpty()) {
                            ShowToast("옵션값2를 입력해주세요.")
                        }else {
                            var lastCharacter = oOneItemEdit.text.toString().substring(oOneItemEdit.text.toString().length-1,oOneItemEdit.text.toString().length)
                            var firstCharacter = oOneItemEdit.text.toString().substring(0,1)
                            if(lastCharacter.toString().equals(",")){
                                oOneItemEdit.setText(oOneItemEdit.text.toString().substring(0,oOneItemEdit.text.toString().length-1))
                            }
                            if(firstCharacter.toString().equals(",")){
                                oOneItemEdit.setText(oOneItemEdit.text.toString().substring(1,oOneItemEdit.text.toString().length))
                            }
                            var oNames = ArrayList<String>()


                            if(oOneItemEdit.text.toString().contains(",")){
                                var tempOptionItem = oOneItemEdit.text.toString().split(",")
                                oNames = tempOptionItem as ArrayList<String>
                            }else {

                                oNames.add(oOneItemEdit.text.toString())
                            }
                            firstOptionNames = oNames

                            var seclastCharacter = oTwoItemEdit.text.toString().substring(oTwoItemEdit.text.toString().length-1,oTwoItemEdit.text.toString().length)
                            var secfirstCharacter = oTwoItemEdit.text.toString().substring(0,1)
                            if(seclastCharacter.toString().equals(",")){
                                oTwoItemEdit.setText(oTwoItemEdit.text.toString().substring(0,oTwoItemEdit.text.toString().length-1))
                            }
                            if(secfirstCharacter.toString().equals(",")){
                                oTwoItemEdit.setText(oTwoItemEdit.text.toString().substring(1,oTwoItemEdit.text.toString().length))
                            }
                            var oSecNames = ArrayList<String>()

                            if(oTwoItemEdit.text.toString().contains(",")){
                                var tempTwOptionItem = oTwoItemEdit.text.toString().split(",")
                                oSecNames = tempTwOptionItem as ArrayList<String>
                            }else {

                                oSecNames.add(oOneNameEdit.text.toString())

                            }

                            secOptionNames = oSecNames

                            var postName = ArrayList<String>()
                            var postItem = ArrayList<String>()
                            var postAddPrice = ArrayList<Int>()
                            var postMax = ArrayList<Int>()
                            var postType = ArrayList<Int>()
                            for (i in 0..oNames.size) {
                                if(i == oNames.size) {
                                    oApplyLayer.visibility = View.VISIBLE
                                    oRecycler.visibility = View.VISIBLE
                                    var adapter = OptionMultiApplyAdapter(applicationContext,this@SellerProductUploadActivity,postName,postItem,postAddPrice,postMax,postType)
                                    oRecycler.adapter = adapter
                                    adapter.notifyDataSetChanged()
                                }else {
                                    for (k in 0..oSecNames.size-1){
                                        postName.add(oNames.get(i))
                                        postItem.add(oSecNames.get(k))
                                        postAddPrice.add(0)
                                        postMax.add(0)
                                        postType.add(i)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun singleEditChange(txt : String) {
        oOneItemEdit.setText(oOneItemEdit.text.toString().replace(txt,""))
        if(!oOneItemEdit.text.toString().isEmpty()){
            var lastCharacter = oOneItemEdit.text.toString().substring(oOneItemEdit.text.toString().length-1,oOneItemEdit.text.toString().length)
            var firstCharacter = oOneItemEdit.text.toString().substring(0,1)
            if(lastCharacter.toString().equals(",")){
                oOneItemEdit.setText(oOneItemEdit.text.toString().substring(0,oOneItemEdit.text.toString().length-1))
            }
            if(firstCharacter.toString().equals(",")){
                oOneItemEdit.setText(oOneItemEdit.text.toString().substring(1,oOneItemEdit.text.toString().length))
            }
        }else {
            oApplyLayer.visibility = View.GONE
            oRecycler.visibility = View.GONE
        }

    }

    fun mainImageAct() {
        galleryOpen(MAIN_IMAGE_RESULT)
    }

    fun addImageAct() {
        if(addImageFile.size == 9) {
            ShowToast("추가 사진은 최대 9장 까지 등록가능합니다.")
        }else {
            galleryOpen(ADD_IMAGE_RESULT)
        }

    }

    fun subImageAct() {
        if(subImageFile.size == 12) {
            ShowToast("사진 이미지는 최대 12장 까지 등록가능합니다.")
        }else{
            galleryOpen(SUB_IMAGE_RESULT)
        }

    }

    fun galleryOpen(reuslt_code : Int) {

        TedPermission.with(this)
            .setPermissionListener(object: PermissionListener {
                override fun onPermissionGranted() {
                    TakePickture(reuslt_code)
                }

                override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>?) {
                    ShowToast("권한 설정 후 이용해주세요.")
                }


            })
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) // 확인할 권한을 다중 인자로 넣어줍니다.
            .check()

    }

    fun TakePickture(reuslt_code : Int) {
        startActivityForResult(Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI),reuslt_code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MAIN_IMAGE_RESULT && resultCode == Activity.RESULT_OK) {
            var imagePath = data!!.data
            var file = File(getRealPathFromURI(applicationContext,imagePath))
            var path = file.path
            mainImageFile.clear()
            mainImageFile.add(file)
            mainImageView()

        }else if(requestCode == SUB_IMAGE_RESULT && resultCode == Activity.RESULT_OK) {
                var imagePath = data!!.data
                var file = File(getRealPathFromURI(applicationContext,imagePath))
                var path = file.path

                subImageFile.add(file)
                subImageView()


        }else if(requestCode == ADD_IMAGE_RESULT && resultCode == Activity.RESULT_OK) {
            var imagePath = data!!.data
            var file = File(getRealPathFromURI(applicationContext,imagePath))
            var path = file.path

            addImageFile.add(file)
            addImageView()


        }else if(requestCode == INFO_CONTENTS_RESULT && resultCode == Activity.RESULT_OK) {
            infoHintLabel.visibility = View.GONE
            infoWebView.visibility = View.VISIBLE
            infoShowView.visibility = View.VISIBLE
            var htmls = data!!.getStringExtra("html")
            infoHtmls = htmls
            infoWebView.settings.javaScriptEnabled = true
            infoWebView.settings.javaScriptCanOpenWindowsAutomatically = true
            infoWebView.loadData(htmls,"text/html","UTF-8")
        }else if(requestCode == SIZE_CONTENTS_RESULT && resultCode == Activity.RESULT_OK) {
            sizeHintLabel.visibility = View.GONE
            sizeWebView.visibility = View.VISIBLE
            sizeShowView.visibility = View.VISIBLE
            var htmls = data!!.getStringExtra("html")
            sizeHtmls = htmls
            sizeWebView.settings.javaScriptEnabled = true
            sizeWebView.settings.javaScriptCanOpenWindowsAutomatically = true
            sizeWebView.loadData(htmls,"text/html","UTF-8")
        }else if(requestCode == P_INFO_CONTENTS_RESULT && resultCode == Activity.RESULT_OK) {
            pInfoHintLabel.visibility = View.GONE
            pInfoWebView.visibility = View.VISIBLE
            pInfoShowVIew.visibility = View.VISIBLE
            var htmls = data!!.getStringExtra("html")
            p_infoHtmls = htmls
            pInfoWebView.settings.javaScriptEnabled = true
            pInfoWebView.settings.javaScriptCanOpenWindowsAutomatically = true
            pInfoWebView.loadData(htmls,"text/html","UTF-8")
        }else if(requestCode == CODI_ITEM_RESULT && resultCode == Activity.RESULT_OK) {
            var images = data!!.getStringArrayListExtra("images")
            var codes = data!!.getStringArrayListExtra("codes")
            var names = data!!.getStringArrayListExtra("names")
            codyItemList = codes

            codyRecycler.visibility = View.VISIBLE
            codyRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

            var adapter= SellerCodiAdapter(applicationContext,this,images,codes,names)
            codyRecycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }

    }

    fun mainImageView() {
        var adapter = SellerPhotoAdapter(applicationContext,this,mainImageFile,0)
        mainImageRecycler.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    fun subImageView() {
        var adapter = SellerPhotoAdapter(applicationContext,this,subImageFile,1)
        subImageRecycler.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    fun addImageView() {
        var adapter = SellerPhotoAdapter(applicationContext,this,addImageFile,2)
        addImageRecycler.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    fun maxCntCalcurate(item : ArrayList<Int>) {
        var totalMax : Int = 0
        for (i in 0..item.size) {
            if(i == item.size) {
                maxCntEdit.setText(totalMax.toString())
            }else{
                totalMax = totalMax + item.get(i)
            }
        }
    }
}
