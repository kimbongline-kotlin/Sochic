package com.sochic.sochic.MyPageFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.sochic.sochic.MyPageFolder.API.FollowingAlarmAPI
import com.sochic.sochic.MyPageFolder.API.MyAlarmAPI
import com.sochic.sochic.MyPageFolder.Adapter.FollowingAlarmAdapter
import com.sochic.sochic.MyPageFolder.Adapter.MyAlarmAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_alarm.*

class AlarmActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        supportActionBar!!.hide()

        tabLayer.addTab(tabLayer.newTab().setText("내 소식"),true)
        tabLayer.addTab(tabLayer.newTab().setText("팔로잉"))

        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        getMyAlarm()

        tabLayer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!!.position == 0) {
                    getMyAlarm()
                }else {
                    getFollowingAlarm()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
    }

    fun getMyAlarm() {
        disposable.add(apiService.MY_ALARM_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<MyAlarmAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: MyAlarmAPI) {
                    var tempData = ArrayList<MyAlarmAPI.MyAlarmList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<MyAlarmAPI.MyAlarmList>
                    }
                    var adapter = MyAlarmAdapter(applicationContext,this@AlarmActivity,tempData)
                    recycler.adapter = adapter
                }
            }))

    }

    fun getFollowingAlarm() {
        disposable.add(apiService.FOLLOWING_ALARM_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<FollowingAlarmAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: FollowingAlarmAPI) {
                    var tempData = ArrayList<FollowingAlarmAPI.FollowingAlarmList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<FollowingAlarmAPI.FollowingAlarmList>
                    }
                    var adapter = FollowingAlarmAdapter(applicationContext,this@AlarmActivity,tempData)
                    recycler.adapter = adapter
                }
            }))

    }
}
