package com.sochic.sochic.PayFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.PayFolder.API.MyCouponAPI
import com.sochic.sochic.ProductFolder.API.ProductPhotoAPI
import com.sochic.sochic.ProductFolder.ProductOptionActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.*
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_product_option.view.*
import kotlinx.android.synthetic.main.listitem_coupon_item.view.*

import kotlinx.android.synthetic.main.listitem_feed_grid_item.view.*
import kotlinx.android.synthetic.main.listitem_feed_item.view.*
import kotlinx.android.synthetic.main.listitem_option_item.view.*
import kotlinx.android.synthetic.main.listitem_option_item.view.nameLabel


class PaidCouponAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<MyCouponAPI.MyCouponList>

) : androidx.recyclerview.widget.RecyclerView.Adapter<PaidCouponAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_coupon_item, parent, false)
    ) {

        fun onBindViewHolder(item : MyCouponAPI.MyCouponList) {
            with(itemView) {

                nameLabel.setText(item.c_name)
                if(item.c_percent_bool) {
                    valueLabel.setText("${item.c_value}% 할인")
                }else {
                    valueLabel.setText("${PriceUtil.set(item.c_value.toString())}원 할인")
                }

                deleteBtn.setOnClickListener {
                    getDatas.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition,getDatas.size)
                }
            }

        }



    }



}