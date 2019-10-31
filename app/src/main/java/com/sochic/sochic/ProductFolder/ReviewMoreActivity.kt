package com.sochic.sochic.ProductFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.ProductFolder.API.ProductReviewAPI
import com.sochic.sochic.ProductFolder.Adapter.ProductReviewAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_review_more.*

class ReviewMoreActivity : ScActivity() {

    var getIdx : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_more)

        supportActionBar!!.hide()

        getIdx = intent.getStringExtra("idx")
        backBtn.setOnClickListener {
            finish()
        }



        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        getData()

    }

    fun getData() {
        disposable.add(apiService.PRODUCT_REVIEW_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ProductReviewAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ProductReviewAPI) {
                    var tempData = ArrayList<ProductReviewAPI.ProductReviewList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<ProductReviewAPI.ProductReviewList>
                    }
                    var adapter = ProductReviewAdapter(applicationContext,this@ReviewMoreActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }
}
