package com.sochic.sochic.BrandFolder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.sochic.sochic.BrandFolder.API.BrandInfoAPI
import com.sochic.sochic.BrandFolder.API.BrandProfileAPI
import com.sochic.sochic.HomeFolder.API.HomeItemAPI
import com.sochic.sochic.HomeFolder.Adapter.HomeGridFeedAdapter
import com.sochic.sochic.ProductFolder.API.ProductReviewAPI
import com.sochic.sochic.ProductFolder.Adapter.ProductReviewAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import com.sochic.sochic.Util.ShareReturn
import com.sochic.sochic.Util.SpacesItemDecoration
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_brand_home.*


class BrandHomeActivity : ScActivity() {

    var getIdx : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand_home)
        supportActionBar!!.hide()

        getIdx = intent.getStringExtra("id_user")

        backBtn.setOnClickListener {
            finish()
        }

        productRecycler.layoutManager = GridLayoutManager(applicationContext,3)
        productRecycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(3.0f,applicationContext).toInt(),3))

        reviewRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)


        tabLayer.addTab(tabLayer.newTab().setText("PRODUCT"),true)
        tabLayer.addTab(tabLayer.newTab().setText("REVIEW"))
        tabLayer.addTab(tabLayer.newTab().setText("INFO"))

        tabLayer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.position == 0) {
                    initLayer()
                    productRecycler.visibility = View.VISIBLE

                }else if(tab.position == 1) {
                    initLayer()
                    reviewRecycler.visibility = View.VISIBLE

                }else {
                    initLayer()
                    infoLayer.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        initLayer()
        productRecycler.visibility = View.VISIBLE

        productShow()
        reviewShow()
        infoShow()

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {


                if(Math.abs(verticalOffset) - appBarLayout!!.totalScrollRange == 0) {

                    var anim = AlphaAnimation(0.0f,1.0f)
                    anim.duration = 500
                    topProfileView.visibility = View.VISIBLE
                    topProfileView.animation = anim

                }else {

                    topProfileView.visibility = View.GONE

                }
            }
        })

        getData()

    }

    fun getData() {
        disposable.add(apiService.BRAND_PROFILE_API(id_user,getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<BrandProfileAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: BrandProfileAPI) {
                    if(t.success) {
                        ScImage.CircleImage(t.profile_image,inProfileView)
                        ScImage.CircleImage(t.profile_image,profileView)
                        nickLabel.setText(t.nickname)
                        inNickLabel.setText(t.nickname)
                        infoLabel.setText(t.info)

                        if(t.follow_confirm) {
                            followBtn.setBackgroundColor(resources.getColor(R.color.blackColor))
                            followBtn.setText("팔로잉")
                            followBtn.setTextColor(resources.getColor(R.color.whiteColor))
                        }else {
                            followBtn.setBackgroundResource(R.drawable.grayborder_white_box)
                            followBtn.setText("팔로우")
                            followBtn.setTextColor(resources.getColor(R.color.borderLineColor))
                        }

                       followBtn.setOnClickListener {
                            disposable.add(apiService.FOLLOW_API(id_user,getIdx)
                                .subscribeOn(io)
                                .observeOn(thread)
                                .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                                    override fun onError(e: Throwable?) {
                                        ConnectionError()
                                    }

                                    override fun onSuccess(t: TrueFalseAPI) {
                                        if(t.success) {
                                            getData()
                                        }else {
                                            ConnectionError()
                                        }
                                    }
                                }))
                        }

                        shareBtn.setOnClickListener {
                            shortUrl(getIdx,"brand",t.nickname,t.info,t.profile_image,object : ShareReturn {
                                override fun onError(th: Throwable) {

                                }

                                override fun onSuccess(str: String) {
                                    shareItem(t.nickname,str)
                                }
                            })
                        }

                        instaBtn.setOnClickListener {
                            val uri = Uri.parse("http://instagram.com/_u/${t.insta_url}")
                            val likeIng = Intent(Intent.ACTION_VIEW, uri)

                            likeIng.setPackage("com.instagram.android")

                            try {
                                startActivity(likeIng)
                            } catch (e: Exception) {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("http://instagram.com/${t.insta_url}")
                                    )
                                )
                            }

                        }
                    }
                }
            }))
    }

    fun initLayer() {
        productRecycler.visibility = View.GONE
        reviewRecycler.visibility = View.GONE
        infoLayer.visibility = View.GONE
    }

    fun productShow() {
        disposable.add(apiService.BRAND_PRODUCT_ITEM_API(id_user,getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<HomeItemAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: HomeItemAPI) {
                    var tempData = ArrayList<HomeItemAPI.HomeItemList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<HomeItemAPI.HomeItemList>
                    }
                    var adapter = HomeGridFeedAdapter(applicationContext,this@BrandHomeActivity,tempData)
                    productRecycler.adapter = adapter
                    adapter.notifyDataSetChanged()

                }
            }))
    }

    fun reviewShow() {
        disposable.add(apiService.BRAND_REVIEW_ITEM_API(id_user,getIdx)
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
                    var adaper = ProductReviewAdapter(applicationContext,this@BrandHomeActivity,tempData)
                    reviewRecycler.adapter = adaper
                    adaper.notifyDataSetChanged()
                }
            }))
    }

    fun infoShow() {
        disposable.add(apiService.BRAND_INFO_API(id_user,getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<BrandInfoAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: BrandInfoAPI) {
                    if(t.success) {
                        topSizeLabel.setText(t.top_size )
                        heightLabel.setText(t.height+ " cm")
                        bottomSizeLabel.setText(t.bottom_size)
                        footSizeLabel.setText(t.foot_size + "mm")
                        bodyFitLabel.setText(t.body_fit)
                        shopNameLabel.setText(t.shop_name)
                        shopCeoLabel.setText(t.shop_ceo_name)
                        shopNumberLabel.setText(t.shop_number)
                        shopTelNumberLabel.setText(t.shop_tel_number)
                        shopAddressLabel.setText(t.shop_address)
                        csTimeLabel.setText(t.center_cs_time)
                        csNumberLabel.setText(t.center_phone_number)
                        csEmailLabel.setText(t.center_address)

                        callBtn.setOnClickListener {
                            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + t.center_phone_number)))
                        }

                        emailBtn.setOnClickListener {
                            setClipboard(applicationContext,t.center_address,"이메일 주소가 복사되었습니다.")
                        }
                    }
                }
            }))
    }
}
