package com.sochic.sochic.MyPageFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binaryfork.spanny.Spanny
import com.sochic.sochic.MyPageFolder.API.ReviewAPI
import com.sochic.sochic.MyPageFolder.ReviewWriteActivity
import com.sochic.sochic.OrderFolder.OrderCancelActivity
import com.sochic.sochic.OrderFolder.OrderDeliveryActivity
import com.sochic.sochic.OrderFolder.OrderDetailActivity
import com.sochic.sochic.OrderFolder.OrderExchangeActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import kotlinx.android.synthetic.main.listitem_cart_user_item.view.recycler
import kotlinx.android.synthetic.main.listitem_my_order_item.view.*
import kotlinx.android.synthetic.main.listitem_order_data_item.view.*
import kotlinx.android.synthetic.main.listitem_review_data_item.view.*
import kotlinx.android.synthetic.main.listitem_review_data_item.view.sCntLabel
import kotlinx.android.synthetic.main.listitem_review_data_item.view.sImageView
import kotlinx.android.synthetic.main.listitem_review_data_item.view.sNameLabel
import kotlinx.android.synthetic.main.listitem_review_data_item.view.sOpriceLabel
import kotlinx.android.synthetic.main.listitem_review_data_item.view.sOptionLabel
import kotlinx.android.synthetic.main.listitem_review_data_item.view.sPriceLabel


class ReviewItemAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<ReviewAPI.ReviewList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<ReviewItemAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_review_data_item, parent, false)
    ) {

        fun onBindViewHolder( item : ReviewAPI.ReviewList) {
            with(itemView) {

                sNameLabel.setText(item.name)
                sOptionLabel.setText(item.option_name)
                sCntLabel.setText(PriceUtil.set(item.cnt.toString()) + "개")

                ScImage.image(item.image,sImageView)

                var price : Int = 0
                if(item.sale_confirm) {
                    price =(item.sale_price) * item.cnt
                    sOpriceLabel.setText(PriceUtil.set(item.price.toString()) + "원")
                    sPriceLabel.setText(PriceUtil.set(price.toString()) + "원")
                }else {
                    price =(item.price ) * item.cnt
                    sOpriceLabel.setText("")
                    sPriceLabel.setText(PriceUtil.set(price.toString()) + "원")
                }

                rWriteBtn.visibility = View.GONE
                ratingbar.visibility = View.GONE
            }

        }



    }


}