package com.sochic.sochic.MyPageFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.sochic.sochic.MyPageFolder.API.MyPointAPI
import com.sochic.sochic.MyPageFolder.API.PointAPI
import com.sochic.sochic.MyPageFolder.Adapter.PointAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_point.*

class PointActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point)
        supportActionBar!!.hide()

        var item = ArrayList<String> ()
        item.add("전체 조회")
        item.add("최근 1주일")
        item.add("최근 1개월")
        item.add("최근 3개월")
        item.add("최근 6개월")
        item.add("최근 1년")

        getData()
        durationSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,item)

        tabLayer.addTab(tabLayer.newTab().setText("전체"),true)
        tabLayer.addTab(tabLayer.newTab().setText("적립"))
        tabLayer.addTab(tabLayer.newTab().setText("사용"))

        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        tabLayer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                getListData()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        durationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getListData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        getListData()
    }



    fun getListData() {
        disposable.add(apiService.MY_POINT_LIST_API(id_user,durationSpinner.selectedItemPosition,
            tabLayer.selectedTabPosition)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<PointAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: PointAPI) {
                    var tempData = ArrayList<PointAPI.PointList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<PointAPI.PointList>
                    }
                    var adapter = PointAdapter(applicationContext,this@PointActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    fun getData() {
        disposable.add(apiService.MY_POINT_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<MyPointAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: MyPointAPI) {
                    if(t.success) {
                        myPointLabel.setText(PriceUtil.set(t.my_point.toString()) + "P")
                    }
                }
            }))
    }
}
