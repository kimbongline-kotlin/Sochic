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
import com.sochic.sochic.SellerFolder.API.SellerCodiItemAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import kotlinx.android.synthetic.main.listitem_seller_codi_item.view.*


class SellerCodiAdapter(
    public val context: Context,
    public var activity: Activity,
    public var images : ArrayList<String>,
    public var codes : ArrayList<String>,
    public var names : ArrayList<String>

) : androidx.recyclerview.widget.RecyclerView.Adapter<SellerCodiAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = images.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder()


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_seller_codi_item, parent, false)
    ) {

        fun onBindViewHolder( ) {
            with(itemView) {

                ScImage.image(images.get(adapterPosition),sImageView)
                sNameLabel.setText(codes.get(adapterPosition))
                sOptionLabel.setText(names.get(adapterPosition))

                deleteBtn.setOnClickListener {
                    images.removeAt(adapterPosition)
                    codes.removeAt(adapterPosition)
                    names.removeAt(adapterPosition)
                    (mAct as SellerProductUploadActivity).codyItemList = codes
                    notifyDataSetChanged()
                }

            }

        }



    }


}