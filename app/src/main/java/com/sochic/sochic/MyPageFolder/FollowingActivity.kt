package com.sochic.sochic.MyPageFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.MyPageFolder.API.MyFollowingAPI
import com.sochic.sochic.MyPageFolder.Adapter.FollowingAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_following.*

class FollowingActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_following)

        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        getItem()
    }

    fun getItem() {
        disposable.add(apiService.MY_FOLLOWING_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<MyFollowingAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: MyFollowingAPI) {
                    var tempData = ArrayList<MyFollowingAPI.MyFollowingList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<MyFollowingAPI.MyFollowingList>
                    }
                    var adapter = FollowingAdapter(applicationContext,this@FollowingActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))

    }
}
