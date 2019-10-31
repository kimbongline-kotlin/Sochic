package com.sochic.sochic.SellerFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.sochic.sochic.ProductFolder.API.ProductReviewAPI
import com.sochic.sochic.ProductFolder.Adapter.ProductReviewAdapter
import com.sochic.sochic.R
import com.sochic.sochic.SellerFolder.Adapter.SellerContactAdapter
import com.sochic.sochic.SettingFolder.API.ContactAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_seller_contact_review.*

class SellerContactReviewActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_contact_review)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }


        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        tabLayer.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.position == 0) {
                    getContact()
                }else {
                    getReview()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        tabLayer.addTab(tabLayer.newTab().setText("상품문의"),true)
        tabLayer.addTab(tabLayer.newTab().setText("상품리뷰"))
        getContact()
    }

    fun getContact() {

        disposable.add(apiService.SELLER_CONTACT_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ContactAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ContactAPI) {
                    var tempData = ArrayList<ContactAPI.ContactList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<ContactAPI.ContactList>
                    }

                    var adapter = SellerContactAdapter(applicationContext,this@SellerContactReviewActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    fun getReview() {
        disposable.add(apiService.SELLER_REVIEW_API(id_user)
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

                    var adapter = ProductReviewAdapter(applicationContext,this@SellerContactReviewActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }

    override fun onResume() {
        super.onResume()
        if(tabLayer.selectedTabPosition == 0) {
            getContact()
        }else {
            getReview()
        }
    }
}
