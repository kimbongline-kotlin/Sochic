package com.sochic.sochic.MyPageFolder

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.sochic.sochic.MyPageFolder.API.CartAPI
import com.sochic.sochic.MyPageFolder.API.MyProductCntAPI
import com.sochic.sochic.MyPageFolder.Adapter.BookMarkAdapter
import com.sochic.sochic.MyPageFolder.Adapter.CartUserAdapter
import com.sochic.sochic.PayFolder.PrePaidActivity
import com.sochic.sochic.ProductFolder.API.OrderTempAPI
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.API.SubCategorySearchAPI
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.SpacesItemDecoration
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_my_product.*

class MyProductActivity : ScActivity() {

    var cartItem = ArrayList<CartAPI.CartList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_product)
        supportActionBar!!.hide()
        backBtn.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        bookRecycler.layoutManager = GridLayoutManager(applicationContext,2)
        bookRecycler.addItemDecoration(SpacesItemDecoration(convertDpToPixel(10.0f,applicationContext).toInt(),2))



        tabLayer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab!!.position) {
                    0 -> {
                        showCart()
                    }

                    1 -> {
                        showBookMark()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        if(intent.getStringExtra("type").toString().equals("1")) {
            tabLayer.addTab(tabLayer.newTab().setText("장바구니"),true)
            tabLayer.addTab(tabLayer.newTab().setText("위시리스트"))
        }else {
            tabLayer.addTab(tabLayer.newTab().setText("장바구니"))
            tabLayer.addTab(tabLayer.newTab().setText("위시리스트"),true)
        }

        payBtn.setOnClickListener {

            var sellerIdList = ArrayList<String>()
            for (i in 0..cartItem.size) {
                if(i == cartItem.size) {
                    disposable.add(apiService.CART_TEMP_PAY_API(id_user,sellerIdList)
                        .subscribeOn(io)
                        .observeOn(thread)
                        .subscribeWith(object : DisposableSingleObserver<OrderTempAPI>() {
                            override fun onError(e: Throwable?) {
                                ConnectionError()
                            }

                            override fun onSuccess(t: OrderTempAPI) {
                                if(t.success){
                                    finish()
                                    startActivity(
                                        Intent(applicationContext, PrePaidActivity::class.java)
                                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("o_code",t.o_code))
                                }
                            }
                        }))
                }else {
                    sellerIdList.add(cartItem.get(i).seller_id)
                }
            }

            //
            //
        }


        allDeleteBtn.setOnClickListener {
            if(tabLayer.selectedTabPosition == 0) {

                disposable.add(apiService.CART_CLEAR_API(id_user)
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                ShowToast("장바구니가 삭제되었습니다.")

                                showCart()
                            }
                        }
                    }))
            }else {
                disposable.add(apiService.BOOKMARK_CLEAR_API(id_user)
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: TrueFalseAPI) {
                            if(t.success) {
                                ShowToast("위시리스트가 삭제되었습니다.")

                                showBookMark()
                            }
                        }
                    }))
            }
        }

        getCnt()
    }

    fun getCnt() {
        disposable.add(apiService.MY_PRODUCT_CNT_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<MyProductCntAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: MyProductCntAPI) {
                    tabLayer.getTabAt(1)!!.setText("위시리스트(${t.book_cnt})" )
                    tabLayer.getTabAt(0)!!.setText("장바구니(${t.cart_cnt})")
                }
            }))
    }

    fun showCart() {

        recycler.visibility = View.VISIBLE
        bookRecycler.visibility = View.GONE




        disposable.add(apiService.CART_ITEM_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<CartAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: CartAPI) {

                    var tempData = ArrayList<CartAPI.CartList>()
                    if(t.success) {
                        tempData = t.response as ArrayList<CartAPI.CartList>
                        cartItem = tempData
                        totalPrice = 0
                        salePrice = 0
                        prePrice = 0
                        deliveryPrice = 0
                        for (i in 0..cartItem.size -1) {
                            deliveryPrice = deliveryPrice + cartItem.get(i).delivery_price
                            calcurateItem(cartItem.get(i).cart_response as ArrayList<CartAPI.CartList.CartItemList>)
                        }

                        if(tabLayer.selectedTabPosition == 0) {
                            cartLayer.visibility = View.VISIBLE
                            payBtn.visibility = View.VISIBLE
                        }



                    }else {
                        cartLayer.visibility = View.GONE
                        payBtn.visibility = View.GONE
                    }



                    var adapter = CartUserAdapter(applicationContext,this@MyProductActivity,tempData)
                    recycler.adapter = adapter
                    adapter.notifyDataSetChanged()

                    getCnt()
                }
            }))
    }

    fun showBookMark() {
        recycler.visibility = View.GONE
        bookRecycler.visibility = View.VISIBLE
        cartLayer.visibility = View.GONE
        payBtn.visibility = View.GONE

        disposable.add(apiService.MY_BOOKMARK_LIST_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<SubCategorySearchAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: SubCategorySearchAPI) {
                    var tempData = ArrayList<SubCategorySearchAPI.SubCategorySearchList>()
                    if(t.success) {

                        tempData = t.response as ArrayList<SubCategorySearchAPI.SubCategorySearchList>
                    }

                    var adapter = BookMarkAdapter(applicationContext,this@MyProductActivity,tempData)
                    bookRecycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                    getCnt()
                }
            }))




    }
    var totalPrice : Int = 0
    var salePrice : Int = 0
    var prePrice : Int = 0
    var deliveryPrice : Int = 0


    fun calcurateItem( data : ArrayList<CartAPI.CartList.CartItemList>) {



        for (i in 0..data.size) {
            if(i == data.size) {
                prePrice = salePrice + totalPrice
                salePrice = salePrice
                totalPrice = totalPrice + deliveryPrice
                //salePrice = salePrice * -1
                //payPrice = totalPrice - salePrice
                b_deliveryPriceLabel.setText(PriceUtil.set(deliveryPrice.toString()))
                b_productPriceLabel.setText(PriceUtil.set(prePrice.toString()))
                b_pointUseLabel.setText(PriceUtil.set((salePrice * -1).toString()))
                b_totalPriceLabel.setText(PriceUtil.set(totalPrice.toString()))
            }else {
                var item = data.get(i)
                var price : Int = 0
                if(item.sale_confirm) {
                    price =(item.sale_price + item.add_price) * item.cnt
                    totalPrice = totalPrice + price
                    salePrice = salePrice + (((item.price + item.add_price) * item.cnt - (item.sale_price + item.add_price) *item.cnt))

                }else {
                    price =(item.price + item.add_price) * item.cnt
                    totalPrice = totalPrice + price

                }
            }
        }

    }
}
