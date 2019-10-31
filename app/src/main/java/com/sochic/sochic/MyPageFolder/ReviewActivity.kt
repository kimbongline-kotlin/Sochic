package com.sochic.sochic.MyPageFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.MyPageFolder.API.ReviewAPI
import com.sochic.sochic.MyPageFolder.Adapter.ReviewAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_order_cancel.*
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.activity_review.backBtn
import kotlinx.android.synthetic.main.activity_review.recycler

class ReviewActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }


        var item = ArrayList<String> ()
        item.add("전체 조회")
        item.add("최근 1주일")
        item.add("최근 1개월")
        item.add("최근 3개월")
        item.add("최근 6개월")
        item.add("최근 1년")

        dSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,item)

        var sItem = ArrayList<String> ()
        sItem.add("전체보기")
        sItem.add("리뷰 작성")
        sItem.add("리뷰 미작성")
        sSpinner.adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,sItem)

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)


        sSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getItem()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        dSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getItem()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
       getItem()
    }

    override fun onResume() {
        super.onResume()
        getItem()
    }

    fun getItem() {

        disposable.add(apiService.REVIEW_API(id_user,dSpinner.selectedItemPosition.toString(),sSpinner.selectedItemPosition.toString())
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ReviewAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ReviewAPI) {
                    var tempData = ArrayList<ReviewAPI.ReviewList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<ReviewAPI.ReviewList>
                    }
                    var adapter = ReviewAdapter(applicationContext,this@ReviewActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))

    }

}
