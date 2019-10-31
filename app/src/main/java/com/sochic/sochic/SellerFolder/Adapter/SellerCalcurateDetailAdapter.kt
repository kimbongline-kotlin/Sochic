package com.sochic.sochic.SellerFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binaryfork.spanny.Spanny
import com.jakewharton.rxbinding2.widget.RxTextView
import com.sochic.sochic.OrderFolder.API.OrderAPI
import com.sochic.sochic.OrderFolder.Adapter.MyOrderProductAdapter
import com.sochic.sochic.OrderFolder.OrderCancelActivity
import com.sochic.sochic.OrderFolder.OrderDeliveryActivity
import com.sochic.sochic.OrderFolder.OrderDetailActivity
import com.sochic.sochic.OrderFolder.OrderExchangeActivity
import com.sochic.sochic.R
import com.sochic.sochic.SellerFolder.*
import com.sochic.sochic.SellerFolder.API.SellerCalcurateAPI
import com.sochic.sochic.SellerFolder.API.SellerCalcurateDetailAPI
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_calcurate_detail_item.view.*

import kotlinx.android.synthetic.main.listitem_cart_user_item.view.recycler
import kotlinx.android.synthetic.main.listitem_my_order_item.view.*
import kotlinx.android.synthetic.main.listitem_option_apply_item.view.*


class SellerCalcurateDetailAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<SellerCalcurateDetailAPI.SellerCalcurateDetailList>


) : androidx.recyclerview.widget.RecyclerView.Adapter<SellerCalcurateDetailAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_calcurate_detail_item, parent, false)
    ) {

        fun onBindViewHolder(item : SellerCalcurateDetailAPI.SellerCalcurateDetailList) {
            with(itemView) {

                oCodeLabel.setText(item.o_code)
                productOcodeLabel.setText(item.product_o_code)
                divisionLabel.setText(item.division)
                nameLabel.setText(item.name)
                o_nameLabel.setText(item.o_name)
                dateOneLabel.setText(item.date_1)
                dateTwoLabel.setText(item.date_2)
                dateThreeLabel.setText(item.date_3)
                PriceLabel.setText(PriceUtil.set(item.price.toString()))
                FeesLabel.setText(PriceUtil.set(item.fees.toString()))
                mooFeesLabel.setText(PriceUtil.set(item.moo_fees.toString()))
                if(item.calcurate_confirm) {

                    confirmLabel.setText("정산완료")
                }else {
                    confirmLabel.setText("정산대기")
                }


            }

        }



    }


}