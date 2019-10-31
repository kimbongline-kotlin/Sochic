package com.sochic.sochic.OrderFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.OrderFolder.API.OrderAPI
import com.sochic.sochic.OrderFolder.OrderDetailActivity
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.API.SubCategorySearchAPI
import com.sochic.sochic.SearchFolder.SearchKeywordActivity
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage

import kotlinx.android.synthetic.main.listitem_multi_paid_item.view.*



class MyOrderProductAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<OrderAPI.OrderList.OrderItemList>,
    public var subIdx : String
) : androidx.recyclerview.widget.RecyclerView.Adapter<MyOrderProductAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder( getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_multi_paid_item, parent, false)
    ) {

        fun onBindViewHolder( item : OrderAPI.OrderList.OrderItemList) {
            with(itemView) {


                sOptionChangeBtn.visibility = View.GONE
                deleteBtn.visibility = View.GONE
                sNameLabel.setText(item.name)
                sOptionLabel.setText(item.option_name)
                sCntLabel.setText(PriceUtil.set(item.cnt.toString()) + "개")

                ScImage.image(item.img_response.get(0).image,sImageView)

                var price : Int = 0
                if(item.sale_confirm) {
                    price =(item.sale_price + item.add_price) * item.cnt
                    sOpriceLabel.setText(PriceUtil.set(item.price.toString()) + "원")
                    sPriceLabel.setText(PriceUtil.set(price.toString()) + "원")
                }else {
                    price =(item.price + item.add_price) * item.cnt
                    sOpriceLabel.setText("")
                    sPriceLabel.setText(PriceUtil.set(price.toString()) + "원")
                }


            }

        }



    }



}