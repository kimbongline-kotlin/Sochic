package com.sochic.sochic.MyPageFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.sochic.sochic.MyPageFolder.API.CouponAPI
import com.sochic.sochic.MyPageFolder.Adapter.CouponAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_coupon.*


class CouponActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)
        supportActionBar!!.hide()

        var item = ArrayList<String> ()
        item.add("전체 조회")
        item.add("최근 1주일")
        item.add("최근 1개월")
        item.add("최근 3개월")
        item.add("최근 6개월")
        item.add("최근 1년")

        durationSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,item)

        tabLayer.addTab(tabLayer.newTab().setText("사용 가능한 쿠폰"),true)
        tabLayer.addTab(tabLayer.newTab().setText("완료/지난 쿠폰"))


        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)

        tabLayer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {

                    0 -> {
                        dLayer.visibility = View.VISIBLE

                        possibleItem()
                    }

                    1 -> {
                        dLayer.visibility = View.GONE
                        imossibleItem()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
        possibleItem()

        durationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                possibleItem()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun possibleItem()
    {
        disposable.add(apiService.COUPON_API(id_user,durationSpinner.selectedItemPosition)
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

                    var adapter = CouponAdapter(applicationContext,this@CouponActivity,tempData,0)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))

    }

    fun imossibleItem()
    {
        disposable.add(apiService.DOWN_COUPON_API(id_user,0)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<CouponAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: CouponAPI) {

                    var tempData = ArrayList<CouponAPI.CouponList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<CouponAPI.CouponList>
                    }

                    var adapter = CouponAdapter(applicationContext,this@CouponActivity,tempData,1)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))

    }
}
