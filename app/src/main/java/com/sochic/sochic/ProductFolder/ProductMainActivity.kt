package com.sochic.sochic.ProductFolder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.HomeFolder.API.HomeItemAPI
import com.sochic.sochic.HomeFolder.Adapter.HomeGridFeedAdapter
import com.sochic.sochic.HomeFolder.Adapter.HomeListFeedAdapter
import com.sochic.sochic.ProductFolder.API.*
import com.sochic.sochic.ProductFolder.Adapter.ProductContactAdapter
import com.sochic.sochic.ProductFolder.Adapter.ProductHoriReviewAdapter
import com.sochic.sochic.ProductFolder.Adapter.ProductPhotoAdapter
import com.sochic.sochic.ProductFolder.Adapter.ProductReviewAdapter
import com.sochic.sochic.R
import com.sochic.sochic.Util.*
import com.sochic.sochic.Util.ApiMannager.LikeAPI
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.youth.banner.Transformer
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_product_main.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductMainActivity : ScActivity() {

    var oneItemUrl : String = "https://www.naver.com"
    var getIdx : String = ""
    var photoList = ArrayList<ProductPhotoAPI.ProductPhotoList>()
    var reviewPhotoList = ArrayList<ProductReviewPhotoAPI.ProductReviewPhotoList>()
    var reviewList = ArrayList<ProductReviewAPI.ProductReviewList>()
    var twoItemUrl : String = ""
    var contactList = ArrayList<ProductContactAPI.ProductContactList>()
    var threeItemUrl = ""
    var sevenCodiIttem = ArrayList<HomeItemAPI.HomeItemList>()
    var sellerIdx : String = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_main)
        supportActionBar!!.hide()

        getIdx = intent.getStringExtra("idx")

        oneArrow.isSelected = false
        twoArrow.isSelected = false
        threeArrow.isSelected = false
        fourArrow.isSelected = true
        fiveArrow.isSelected = true
        sixArrow.isSelected = true
        sevenArrow.isSelected = true

        backBtn.setOnClickListener {
            finish()
        }



        oneWebView.settings.javaScriptEnabled = true
        oneWebView.settings.useWideViewPort = true
        oneWebView.settings.loadWithOverviewMode = true
        oneWebView.visibility = View.VISIBLE
        oneWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view!!.loadUrl(request!!.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        tHoriRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.HORIZONTAL,false)
        tHoriRecycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(5.0f,applicationContext).toInt(),5))
        tVertiRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        fourWebView.settings.javaScriptEnabled = true
        fourWebView.settings.useWideViewPort = true
        fourWebView.settings.loadWithOverviewMode = true
        fourWebView.visibility = View.GONE
        fourWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view!!.loadUrl(request!!.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        fiveRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        sixWebView.settings.javaScriptEnabled = true
        sixWebView.settings.useWideViewPort = true
        sixWebView.settings.loadWithOverviewMode = true
        sixWebView.visibility = View.GONE
        sixWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view!!.loadUrl(request!!.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        oneBtn.setOnClickListener {

            if(oneArrow.isSelected) {
                oneWebView.visibility = View.VISIBLE
                showOneLayer(oneItemUrl)
            }else {
                oneWebView.visibility = View.GONE
            }
            oneArrow.isSelected = !oneArrow.isSelected
        }




        twoBtn.setOnClickListener {
            if(twoArrow.isSelected) {
                twoRecycler.visibility = View.VISIBLE


                showTwoLayer()
            }else {
                twoRecycler.visibility = View.GONE
            }
            twoArrow.isSelected = !twoArrow.isSelected

        }

        twoGridBtn.isSelected = true


        twoGridBtn.setOnClickListener {
            twoGridBtn.isSelected = !twoGridBtn.isSelected
            showTwoLayer()
        }

        threeBtn.setOnClickListener {
            if(threeArrow.isSelected) {
                threeLayer.visibility = View.VISIBLE
                showThreeLayer()
            }else {
                threeLayer.visibility = View.GONE
            }
            threeArrow.isSelected = !threeArrow.isSelected
        }


        allReviewBtn.setOnClickListener {
            startActivity(Intent(applicationContext,ReviewMoreActivity::class.java)
                .putExtra("idx",getIdx)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

        fourBtn.setOnClickListener {
            if(fourArrow.isSelected) {
                fourWebView.visibility = View.VISIBLE
                showFourLayer(twoItemUrl)
            }else {
                fourWebView.visibility = View.GONE
            }
            fourArrow.isSelected = !fourArrow.isSelected
        }

        fiveBtn.setOnClickListener {
            if(fiveArrow.isSelected) {
                fiveLayer.visibility = View.VISIBLE
                showFiveLayer()
            }else {
                fiveLayer.visibility = View.GONE
            }
            fiveArrow.isSelected = !fiveArrow.isSelected
        }

        sixBtn.setOnClickListener {
            if(sixArrow.isSelected) {
                sixWebView.visibility = View.VISIBLE
                showSixLayer(threeItemUrl)
            }else {
                sixWebView.visibility = View.GONE
            }

            sixArrow.isSelected = !sixArrow.isSelected
        }

        sevenBtn.setOnClickListener {
            if(sevenArrow.isSelected) {
                sevenRecycler.visibility = View.VISIBLE
                showSeveinLayer()
            }else {
                sevenRecycler.visibility = View.GONE
            }

            sevenArrow.isSelected = !sevenArrow.isSelected
            showSeveinLayer()
        }

        sevenGridBtn.isSelected = true

        sevenGridBtn.setOnClickListener {
            sevenGridBtn.isSelected = !sevenGridBtn.isSelected
            showSeveinLayer()
        }

        contactWriteBtn.setOnClickListener {
            startActivity(Intent(applicationContext,ProductContactWriteActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra("idx",getIdx))
        }

        getItemDetail()
        couponCheck()

        buyBtn.setOnClickListener {

            startActivity(Intent(applicationContext,ProductOptionActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra("idx",getIdx))
        }

        sellerCheck()

    }

    fun sellerCheck() {
        disposable.add(apiService.SELLER_CHECK_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: TrueFalseAPI) {
                    if(t.success) {
                        buyBtn.visibility = View.GONE
                    }else {
                        buyBtn.visibility = View.VISIBLE
                    }
                }
            }))
    }

    override fun onResume() {
        super.onResume()
        getInfoComment()
        getPhoto()
        getReviewPhoto()
        getReview()
        getInfoNotice()
        getContact()
      //  getDelivery()
    }

    fun couponCheck() {
        disposable.add(apiService.PRODUCT_COUPON_CHECK_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: TrueFalseAPI) {
                    if(t.success) {
                        couponView.visibility = View.VISIBLE
                        cpnDownBtn.setOnClickListener {
                            startActivity(Intent(applicationContext,ProductCouponActivity::class.java)
                                .putExtra("idx",getIdx)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                        }
                    }else {
                        couponView.visibility = View.GONE
                    }
                }
            }))
    }

    //상품 정보
    fun getItemDetail() {
        disposable.add(apiService.PRODUCT_DETAIL_API(id_user,getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ProductDetailAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(item: ProductDetailAPI) {
                    ScImage.CircleImage(item.profile_image,profileView)


                    getCodiItem(item.seller_id)
                    var items = ArrayList<String>()
                    for (i in 0..item.img_response.size) {
                        if(i == item.img_response.size) {

                            sImageView.setBannerStyle(0)
                            sImageView.setImageLoader(GlideImageLoader())
                            sImageView.setImages(items)
                            sImageView.setBannerAnimation(Transformer.Default)
                            sImageView.setIndicatorGravity(6)
                            sImageView.isAutoPlay(false)
                            sImageView.start()

                        }else {
                            items.add(item.img_response.get(i).image)
                        }
                    }

                    nickLabel.setText(item.nickname)
                    likeLabel.setText(PriceUtil.set("${item.heart_cnt}"))
                    titleLabel.setText(item.name)
                    textLabel.setText(item.info)

                    if(item.heart_cnt == 0) {
                        likeLabel.setText("")
                    }

                    likeBtn.isSelected = item.heart_confirm
                    saveBtn.isSelected = item.bookmark_confirm

                    if(item.follow_confirm) {
                        followBtn.setBackgroundColor(resources.getColor(R.color.blackColor))
                        followBtn.setText("팔로잉")
                        followBtn.setTextColor(resources.getColor(R.color.whiteColor))
                    }else {
                        followBtn.setBackgroundResource(R.drawable.grayborder_white_box)
                        followBtn.setText("팔로우")
                        followBtn.setTextColor(resources.getColor(R.color.borderLineColor))
                    }

                    sellerIdx = item.seller_id
                    getDelivery()
                    likeBtn.setOnClickListener {
                        disposable.add(apiService.LIKE_API(id_user,item.idx)
                            .subscribeOn(io)
                            .observeOn(thread)
                            .subscribeWith(object : DisposableSingleObserver<LikeAPI> () {
                                override fun onError(e: Throwable?) {
                                    ConnectionError()
                                }

                                override fun onSuccess(t: LikeAPI) {
                                    if(t.success) {

                                        getItemDetail()

                                    }else {
                                        ConnectionError()
                                    }
                                }
                            }))
                    }

                    shareBtn.setOnClickListener {
                        shortUrl(getIdx,"product",item.name,item.info,item.img_response.get(0).image,object : ShareReturn {
                            override fun onError(th: Throwable) {


                            }

                            override fun onSuccess(str: String) {
                                shareItem(item.name,str)
                            }
                        })
                    }

                    saveBtn.setOnClickListener {
                        disposable.add(apiService.BOOKMARK_API(id_user,item.idx)
                            .subscribeOn(io)
                            .observeOn(thread)
                            .subscribeWith(object : DisposableSingleObserver<LikeAPI> () {
                                override fun onError(e: Throwable?) {
                                    ConnectionError()
                                }

                                override fun onSuccess(t: LikeAPI) {
                                    if(t.success) {
                                        getItemDetail()
                                    }
                                }
                            }))
                    }

                    if(id_user.toString().equals(item.seller_id)) {
                        followBtn.visibility = View.GONE
                    }else {
                        followBtn.visibility = View.VISIBLE
                    }

                    followBtn.setOnClickListener {
                        disposable.add(apiService.FOLLOW_API(id_user,item.seller_id)
                            .subscribeOn(io)
                            .observeOn(thread)
                            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                                override fun onError(e: Throwable?) {
                                    ConnectionError()
                                }

                                override fun onSuccess(t: TrueFalseAPI) {
                                    if(t.success) {
                                        getItemDetail()
                                    }else {
                                        ConnectionError()
                                    }
                                }
                            }))
                    }

                    if(item.sale_confirm) {
                        priceLabel.setText(PriceUtil.set("${item.price}") + "원")
                        saleLabel.setText(PriceUtil.set("${item.sale_price}") + "원")
                    }else {
                        saleLabel.setText(PriceUtil.set("${item.price}") + "원")
                        priceLabel.setText("")
                    }

                    if(item.type == 0) {
                        timerLabel.visibility = View.VISIBLE


                        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        formatter.setLenient(false)


                        val endTime = item.open_date
                        var milliseconds: Long = 0

                        val mCountDownTimer: CountDownTimer

                        val endDate: Date
                        var startTime = item.server_date
                        var startDate : Date
                        var startMilliSeconds : Long = 0
                        try {
                            endDate = formatter.parse(endTime)
                            milliseconds = endDate.getTime()

                            startDate = formatter.parse(startTime)
                            startMilliSeconds = startDate.time
                        } catch (e: ParseException) {
                            e.printStackTrace()
                        }


                        mCountDownTimer = object : CountDownTimer(milliseconds,1000) {
                            override fun onFinish() {


                            }

                            override fun onTick(millisUntilFinished: Long) {
                                startMilliSeconds = startMilliSeconds - 1;
                                var serverUptimeSeconds = (millisUntilFinished - startMilliSeconds) / 1000
                                var hoursLeft = String.format("%02d", (serverUptimeSeconds % 86400) / 3600);
                                var minLeft = String.format("%02d", ((serverUptimeSeconds % 86400) % 3600) / 60);
                                var secLeft = String.format("%02d", ((serverUptimeSeconds % 86400) % 3600) % 60);
                                timerLabel.setText("$hoursLeft:$minLeft:$secLeft")
                            }
                        }.start()
                    }else {
                        timerLabel.visibility = View.INVISIBLE

                    }
                }
            }))
    }

    fun getInfoComment() {
        disposable.add(apiService.PRODUCT_INFO_COMMENT_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ProductInfoCommentAPI>(){
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ProductInfoCommentAPI) {
                    if(t.success) {
                        oneItemUrl = t.url
                        showOneLayer(oneItemUrl)
                    }
                }
            }))
    }

    fun getPhoto() {
        disposable.add(apiService.PRODUCT_PHOTO_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ProductPhotoAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ProductPhotoAPI) {
                    if(t.success) {
                        photoList = t.response as ArrayList<ProductPhotoAPI.ProductPhotoList>
                        showTwoLayer()
                    }
                }
            }))
    }

    fun getReviewPhoto() {
        disposable.add(apiService.PRODUCT_REVIEW_PHOTO_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ProductReviewPhotoAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ProductReviewPhotoAPI) {
                    if(t.success) {
                        reviewPhotoList = t.response as ArrayList<ProductReviewPhotoAPI.ProductReviewPhotoList>
                        showThreeLayer()
                    }
                }
            }))
    }

    fun getCodiItem(idx : String)  {
        disposable.add(apiService.CODI_ITEM_API(id_user,idx,getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<HomeItemAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: HomeItemAPI) {
                    if(t.success) {
                        sevenCodiIttem = t.response as ArrayList<HomeItemAPI.HomeItemList>
                    }
                }
            }))
    }

    fun getReview() {
        disposable.add(apiService.PRODUCT_REVIEW_MAX_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ProductReviewAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ProductReviewAPI) {
                    if(t.success) {
                        reviewList = t.response as ArrayList<ProductReviewAPI.ProductReviewList>
                    }
                    showThreeLayer()
                }
            }))
    }

    fun getInfoNotice() {
        disposable.add(apiService.PRODUCT_INFO_NOTICE_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ProductInfoNoticeAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ProductInfoNoticeAPI) {
                    if(t.success) {
                        twoItemUrl = t.url
                    }
                }
            }))
    }

    fun getContact() {
        disposable.add(apiService.PRODUCT_CONTACT_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ProductContactAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ProductContactAPI) {
                    if(t.success) {
                        contactList = t.response as ArrayList<ProductContactAPI.ProductContactList>
                    }
                }
            }))
    }

    fun getDelivery() {
        disposable.add(apiService.PRODUCT_DELIVERY_API(getIdx)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<ProductDeliveryAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: ProductDeliveryAPI) {
                    if(t.success) {
                        threeItemUrl = t.url
                    }
                }
            }))
    }

    //상품 설명 및 코멘트
    fun showOneLayer(url : String) {
        oneWebView.loadUrl(url)
    }

    //사진
    fun showTwoLayer() {

        if(!twoGridBtn.isSelected) {

            twoRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
            if(twoRecycler.itemDecorationCount != 0) {
                twoRecycler.removeItemDecorationAt(0)
            }
            twoRecycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(5.0f,applicationContext).toInt(),1))

        }else {
            twoRecycler.layoutManager = GridLayoutManager(applicationContext,3)
            if(twoRecycler.itemDecorationCount != 0) {
                twoRecycler.removeItemDecorationAt(0)
            }
            twoRecycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(5.0f,applicationContext).toInt(),3))
        }
        var adapter = ProductPhotoAdapter(applicationContext,this,photoList)
        twoRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun showSeveinLayer() {
        if(!sevenGridBtn.isSelected){
            sevenRecycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
            if(sevenRecycler.itemDecorationCount != 0) {
                sevenRecycler.removeItemDecorationAt(0)
            }

            var adapter = HomeListFeedAdapter(applicationContext,this,sevenCodiIttem)
            sevenRecycler.adapter = adapter
            adapter.notifyDataSetChanged()

        }else {
            sevenRecycler.layoutManager = GridLayoutManager(applicationContext,3)
            if(sevenRecycler.itemDecorationCount != 0){
                sevenRecycler.removeItemDecorationAt(0)
            }
            sevenRecycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(5.0f,applicationContext).toInt(),3))

            var adapter = HomeGridFeedAdapter(applicationContext,this,sevenCodiIttem)
            sevenRecycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    //리뷰
    fun showThreeLayer() {
        var adapter = ProductHoriReviewAdapter(applicationContext,this,reviewPhotoList)
        tHoriRecycler.adapter = adapter
        adapter.notifyDataSetChanged()

        var rAdapter = ProductReviewAdapter(applicationContext,this,reviewList)
        tVertiRecycler.adapter = rAdapter
        rAdapter.notifyDataSetChanged()
    }

    //상품정보고시
    fun showFourLayer(url : String) {
        fourWebView.loadUrl(url)
    }

    //Q&A
    fun showFiveLayer() {
        var adapter = ProductContactAdapter(applicationContext,this,contactList)
        fiveRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    //배송/교환/반품정보
    fun showSixLayer(url : String) {
        sixWebView.loadUrl(url)
    }
}
