package com.sochic.sochic.MyPageFolder.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.MyPageFolder.API.CartAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import kotlinx.android.synthetic.main.listitem_cart_user_item.view.*


class CartUserAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<CartAPI.CartList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<CartUserAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_cart_user_item, parent, false)
    ) {

        fun onBindViewHolder( item : CartAPI.CartList) {
            with(itemView) {


                nickLabel.setText(item.nickname)
                ScImage.CircleImage(item.profile_image,profileView)

                recycler.layoutManager = LinearLayoutManager(mCon,RecyclerView.VERTICAL,false)
                var adapter = CartProductAdapter(mCon,mAct, item.cart_response as ArrayList<CartAPI.CartList.CartItemList>)
                recycler.adapter = adapter
                adapter.notifyDataSetChanged()

                var totalPrice : Int = 0
                var salePrice : Int = 0
                var payPrice : Int = 0
                var data = item.cart_response
                for (i in 0..data.size) {
                    if(i == data.size) {

                        priceInfoLabel.setText("상품 ${PriceUtil.set(totalPrice.toString())}원 + 배송비 ${PriceUtil.set(item.delivery_price.toString())}원")
                        payPriceLabel.setText(PriceUtil.set((totalPrice + item.delivery_price).toString()))
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



    }



}