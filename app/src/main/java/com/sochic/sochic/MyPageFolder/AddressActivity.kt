package com.sochic.sochic.MyPageFolder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.MyPageFolder.API.AddressAPI
import com.sochic.sochic.PayFolder.Adapter.PaidDeliveryAdapter
import com.sochic.sochic.PayFolder.DelivieryAddActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_address.*


class AddressActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        supportActionBar!!.hide()
        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        addedBtn.setOnClickListener {
            startActivity(Intent(applicationContext,DelivieryAddActivity::class.java))
        }


    }

    override fun onResume() {
        super.onResume()
        getItem()

    }

    fun getItem() {
        disposable.add(apiService.ADDRESS_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<AddressAPI> () {
                override fun onSuccess(t: AddressAPI) {
                    var tempData = ArrayList<AddressAPI.AddressList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<AddressAPI.AddressList>
                    }

                            var adapter = PaidDeliveryAdapter(applicationContext,this@AddressActivity,tempData)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable?) {
                    ConnectionError()
                }
            }))

    }
}
