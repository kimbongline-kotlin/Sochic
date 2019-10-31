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
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_calcurate_item.view.*
import kotlinx.android.synthetic.main.listitem_cart_user_item.view.recycler
import kotlinx.android.synthetic.main.listitem_my_order_item.view.*
import kotlinx.android.synthetic.main.listitem_option_apply_item.view.*


class SellerCalcurateAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<SellerCalcurateAPI.SellerCalcurateList>


) : androidx.recyclerview.widget.RecyclerView.Adapter<SellerCalcurateAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_calcurate_item, parent, false)
    ) {

        fun onBindViewHolder(item : SellerCalcurateAPI.SellerCalcurateList) {
            with(itemView) {

                dateOneLabel.setText(item.date_1)
                dateTwoLabel.setText(item.date_2)
                CalcuratePriceLabel.setText(PriceUtil.set(item.calcurate_price.toString()))
                PriceLabel.setText(PriceUtil.set(item.price.toString()))
                FeesLabel.setText(PriceUtil.set(item.fees.toString()))
                Benefitlabel.setText(PriceUtil.set(item.benefit.toString()))
                refundLabel.setText(item.refund)
                typeLabel.setText(item.type)
                bankLabel.setText(item.bank)
                bankNumberLabel.setText(item.bank_number)

            }

        }



    }


}