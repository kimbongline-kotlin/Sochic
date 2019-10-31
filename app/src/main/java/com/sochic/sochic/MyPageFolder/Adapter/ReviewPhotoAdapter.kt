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
import kotlinx.android.synthetic.main.listitem_review_write_item.view.*
import java.io.File


class ReviewPhotoAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<File>
) : androidx.recyclerview.widget.RecyclerView.Adapter<ReviewPhotoAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size + 1



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder()


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_review_write_item, parent, false)
    ) {

        fun onBindViewHolder() {
            with(itemView) {

                if(adapterPosition == getData.size) {
                    addView.visibility = View.VISIBLE
                    showView.visibility = View.GONE
                    addImageBtn.setOnClickListener {
                        (mAct as ReviewWriteActivity).addImageAct()
                    }
                }else {
                    addView.visibility = View.GONE
                    showView.visibility = View.VISIBLE
                    ScImage.image(getData.get(adapterPosition),sImageView)

                    deleteBtn.setOnClickListener {
                        getData.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        notifyItemRangeChanged(adapterPosition,getData.size)
                        if(mAct is ReviewWriteActivity) {
                            (mAct as ReviewWriteActivity).fileList = getData
                        }


                    }
                }

            }

        }



    }


}