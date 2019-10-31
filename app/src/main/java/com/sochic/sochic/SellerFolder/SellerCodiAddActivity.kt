package com.sochic.sochic.SellerFolder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.API.SubCategoryAPI
import com.sochic.sochic.SellerFolder.API.SellerCodiItemAPI
import com.sochic.sochic.SellerFolder.Adapter.SellerCodiSelectAdapter
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.activity_seller_codi_add.*
import kotlinx.android.synthetic.main.activity_seller_codi_add.backBtn

class SellerCodiAddActivity : ScActivity() {

    var subCategoryItem = ArrayList<SubCategoryAPI.SubCategoryList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_codi_add)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }
        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        var categoryOneItem = ArrayList<String>()
        categoryOneItem.add("1차 카테고리")
        categoryOneItem.add("Outer")
        categoryOneItem.add("Top")
        categoryOneItem.add("Pants")
        categoryOneItem.add("Dress")
        categoryOneItem.add("Knit")
        categoryOneItem.add("Shoes")

        oneSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,categoryOneItem)
        var categoryTwoItem = ArrayList<String>()
        categoryTwoItem.add("2차 카테고리")
        twoSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,categoryTwoItem)

        oneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        twoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if(oneSpinner.selectedItemPosition != 0 && twoSpinner.selectedItemPosition != 0 ) {
                    getItem()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        addBtn.setOnClickListener {
            if(recycler.adapter != null) {
                var data = (recycler.adapter as SellerCodiSelectAdapter).getData
                if(data.size == 0) {
                    ShowToast("코디 아이템을 선택해주세요.")
                }else {
                    var images = ArrayList<String>()
                    var codes = ArrayList<String>()
                    var names = ArrayList<String>()
                    for (i in 0.. data.size) {
                        if(i == data.size) {
                            if(images.size == 0) {
                                ShowToast("코디 아이템을 선택해주세요.")
                            }else {
                                ShowToast("코디 아이템이 추가 되었습니다.")
                                val intent = Intent()
                                intent.putExtra("images", images)
                                intent.putExtra("codes",codes)
                                intent.putExtra("names",names)
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }

                        }else {
                            if(data.get(i).open) {
                                images.add(data.get(i).img_response.get(0).image)
                                codes.add(data.get(i).product_code)
                                names.add(data.get(i).product_name)
                            }
                        }
                    }
                }
            }else {
                ShowToast("코디 아이템을 선택해주세요.")
            }
        }
    }

    fun getItem() {

        disposable.add(apiService.SELLER_CODI_ITEM_API(id_user,(oneSpinner.selectedItemPosition -1).toString(),subCategoryItem.get(twoSpinner.selectedItemPosition-1).sub_category_idx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SellerCodiItemAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: SellerCodiItemAPI) {

                    var tempData = ArrayList<SellerCodiItemAPI.SellerCodiItemList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<SellerCodiItemAPI.SellerCodiItemList>
                    }
                    var adapter = SellerCodiSelectAdapter(applicationContext,this@SellerCodiAddActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    fun getSubCategory(idx : String) {
        disposable.add(apiService.SUB_CATEGORY_API(idx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SubCategoryAPI>() {
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
                                twoSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,categoryTwoItem)
                            }else {
                                categoryTwoItem.add(t.response.get(i).name)
                            }
                        }
                    }
                }
            }))
    }
}
