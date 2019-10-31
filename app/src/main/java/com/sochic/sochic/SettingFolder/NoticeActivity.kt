package com.sochic.sochic.SettingFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.R
import com.sochic.sochic.SettingFolder.API.NoticeAPI
import com.sochic.sochic.SettingFolder.Adapter.NoticeAdapter
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_notice.*

class NoticeActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        getItem()
    }

    fun getItem() {
        disposable.add(apiService.NOTICE_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<NoticeAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: NoticeAPI) {
                    var tempData = ArrayList<NoticeAPI.NoticeList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<NoticeAPI.NoticeList>
                    }
                    var adapter = NoticeAdapter(applicationContext,this@NoticeActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()

                }
            }))

    }
}
