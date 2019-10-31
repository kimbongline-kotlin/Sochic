package com.sochic.sochic.OrderFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binaryfork.spanny.Spanny
import com.sochic.sochic.OrderFolder.API.OrderAPI
import com.sochic.sochic.OrderFolder.OrderCancelActivity
import com.sochic.sochic.OrderFolder.OrderDeliveryActivity
import com.sochic.sochic.OrderFolder.OrderDetailActivity
import com.sochic.sochic.OrderFolder.OrderExchangeActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage

import kotlinx.android.synthetic.main.listitem_order_data_item.view.*


class OrderDataAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<OrderAPI.OrderList.OrderItemList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<OrderDataAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_order_data_item, parent, false)
    ) {

        fun onBindViewHolder( item : OrderAPI.OrderList.OrderItemList) {
            with(itemView) {


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

                if(item.select) {
                    selectBtn.isSelected = true
                }else {
                    selectBtn.isSelected = false
                }

                selectBtn.setOnClickListener {
                    itemView.callOnClick()
                }

                itemView.setOnClickListener {
                    getData.get(adapterPosition).select = !getData.get(adapterPosition).select
                    notifyItemChanged(adapterPosition)
                    if(mAct is OrderCancelActivity) {
                        ((mAct) as OrderCancelActivity).allCheck(getData)
                    }else if(mAct is OrderExchangeActivity) {
                        ((mAct) as OrderExchangeActivity).allCheck(getData)
                    }
                }
            }

        }



    }

    fun allData(value : Boolean) {
        for (i in 0.. getData.size) {
            if(i == getData.size) {
                notifyDataSetChanged()
            }else {
                getData.get(i).select = value
            }
        }
    }

}