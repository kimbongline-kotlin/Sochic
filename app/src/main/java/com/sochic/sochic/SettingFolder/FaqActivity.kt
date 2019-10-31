package com.sochic.sochic.SettingFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.R
import com.sochic.sochic.SettingFolder.API.FaqAPI
import com.sochic.sochic.SettingFolder.Adapter.FaqAdapter
import com.sochic.sochic.SettingFolder.Adapter.NoticeAdapter
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_faq.*


class FaqActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
        getItem()
    }

    fun getItem() {
        disposable.add(apiService.FAQ_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<FaqAPI> (){
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: FaqAPI) {
                    var tempData = ArrayList<FaqAPI.FaqList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<FaqAPI.FaqList>
                    }
                    var adapter = FaqAdapter(applicationContext,this@FaqActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))

    }
}
