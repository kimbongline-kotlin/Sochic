package com.sochic.sochic.PayFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.PayFolder.API.OrderTempInfoAPI
import com.sochic.sochic.ProductFolder.API.ProductPhotoAPI
import com.sochic.sochic.ProductFolder.ProductOptionActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.*
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.listitem_multi_paid_item.view.*


class PaidItemAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<OrderTempInfoAPI.OrderTempInfoList>

) : androidx.recyclerview.widget.RecyclerView.Adapter<PaidItemAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_multi_paid_item, parent, false)
    ) {

        fun onBindViewHolder( item : OrderTempInfoAPI.OrderTempInfoList) {
            with(itemView) {

                ScImage.image(item.img_response.get(0).image,sImageView)
                sNameLabel.setText(item.name)
                sOptionLabel.setText(item.option_name)
                if(item.option_confirm) {
                    sOptionChangeBtn.visibility = View.INVISIBLE
                }else {
                    sOptionChangeBtn.visibility = View.INVISIBLE
                }

                sCntLabel.setText(PriceUtil.set(item.cnt.toString()) + "개")
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