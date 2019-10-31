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
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_cart_user_item.view.recycler
import kotlinx.android.synthetic.main.listitem_my_order_item.view.*
import kotlinx.android.synthetic.main.listitem_option_apply_item.view.*


class OptionApplyAdapter(
    public val context: Context,
    public var activity: Activity,
    public var oNames : ArrayList<String>,
    public var oItems : ArrayList<String>,
    public var oAdded : ArrayList<Int>,
    public var oMax : ArrayList<Int>

) : androidx.recyclerview.widget.RecyclerView.Adapter<OptionApplyAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = oNames.count()



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder()


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_option_apply_item, parent, false)
    ) {

        fun onBindViewHolder() {
            with(itemView) {

                optNameLabel.setText(oNames.get(adapterPosition))
                optItemLabel.setText(oItems.get(adapterPosition))
                addPriceEdit.setText("${oAdded.get(adapterPosition)}")
                cntEdit.setText("${oMax.get(adapterPosition)}")

                RxTextView.textChanges(addPriceEdit).subscribe {
                    if(it.toString().equals("")){

                    }else {
                        oAdded.set(adapterPosition,it.toString().toInt())
                    }
                }
                RxTextView.textChanges(cntEdit).subscribe {
                    if(it.toString().equals("")){

                    }else {
                        oMax.set(adapterPosition,it.toString().toInt())
                        if(it.toString().toInt() == 0) {
                            stateLabel.setText("품절")
                        }else {
                            stateLabel.setText("판매중")
                        }
                        (mAct as SellerProductUploadActivity).maxCntCalcurate(oMax)
                    }

                }
                if(oMax.get(adapterPosition) == 0) {
                    stateLabel.setText("품절")
                }else {
                    stateLabel.setText("판매중")
                }

                deleteBtn.setOnClickListener {
                    (mAct as SellerProductUploadActivity).singleEditChange(oItems.get(adapterPosition))
                    oNames.removeAt(adapterPosition)
                    oItems.removeAt(adapterPosition)
                    oAdded.removeAt(adapterPosition)
                    oMax.removeAt(adapterPosition)
                    notifyDataSetChanged()

                }
            }

        }



    }


}