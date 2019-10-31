package com.sochic.sochic.SellerFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.sochic.sochic.R
import com.sochic.sochic.SellerFolder.API.SellerCalcurateAPI
import com.sochic.sochic.SellerFolder.API.SellerCalcurateDetailAPI
import com.sochic.sochic.SellerFolder.Adapter.SellerCalcurateAdapter
import com.sochic.sochic.SellerFolder.Adapter.SellerCalcurateDetailAdapter
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver

import kotlinx.android.synthetic.main.activity_seller_calcurate.*


class SellerCalcurateActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_calcurate)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        dRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        tabLayer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!!.position == 0) {
                    allItem()
                }else {
                    detailItem()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
        tabLayer.addTab(tabLayer.newTab().setText("정산내역"),true)
        tabLayer.addTab(tabLayer.newTab().setText("정산상세내역"))


    }

    fun allItem() {

        allLayer.visibility = View.VISIBLE
        detailLayer.visibility = View.GONE
        var dItem = ArrayList<String>()
        dItem.add("전체")
        dItem.add("1주일")
        dItem.add("1개월")
        dItem.add("3개월")
        dItem.add("6개월")
        dItem.add("1년")

        dSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,dItem)
        dSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                getAllItem()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        var sItem = ArrayList<String>()
        sItem.add("전체")
        sItem.add("미정산내역")
        sItem.add("정산완료")

        sSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,sItem)
        sSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                getAllItem()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        getAllItem()
    }

    fun detailItem() {
        allLayer.visibility = View.GONE
        detailLayer.visibility = View.VISIBLE
        var dItem = ArrayList<String>()
        dItem.add("전체")
        dItem.add("1주일")
        dItem.add("1개월")
        dItem.add("3개월")
        dItem.add("6개월")
        dItem.add("1년")
        dSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,dItem)
        dSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                getDetailItem()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        var sItem = ArrayList<String>()
        sItem.add("전체")
        sItem.add("정산대기")
        sItem.add("정산완료")
        sSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,sItem)
        sSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                getDetailItem()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        getDetailItem()
    }

    fun getAllItem() {
        disposable.add(apiService.SELLER_CALCURATE_API(id_user,dSpinner.selectedItemPosition,sSpinner.selectedItemPosition)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SellerCalcurateAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: SellerCalcurateAPI) {

                    var tempData = ArrayList<SellerCalcurateAPI.SellerCalcurateList>()
                    allCalcurateLabel.setText(PriceUtil.set(t.all_calcurate_price.toString()))
                    allPriceLabel.setText(PriceUtil.set(t.all_price.toString()))
                    allFeesLabel.setText(PriceUtil.set(t.all_fees.toString()))
                    if(t.success) {
                        tempData = t.response as ArrayList<SellerCalcurateAPI.SellerCalcurateList>
                    }
                    var adpater = SellerCalcurateAdapter(applicationContext,this@SellerCalcurateActivity,tempData)
                    recycler.adapter = adpater
                    adpater.notifyDataSetChanged()
                }
            }))
    }

    fun getDetailItem() {
        disposable.add(apiService.SELLER_CALCURATE_DETAIL_API(id_user,dSpinner.selectedItemPosition,sSpinner.selectedItemPosition)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SellerCalcurateDetailAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: SellerCalcurateDetailAPI) {

                    var tempData = ArrayList<SellerCalcurateDetailAPI.SellerCalcurateDetailList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<SellerCalcurateDetailAPI.SellerCalcurateDetailList>
                    }
                    var adapter = SellerCalcurateDetailAdapter(applicationContext,this@SellerCalcurateActivity,tempData)
                    dRecycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }
}
