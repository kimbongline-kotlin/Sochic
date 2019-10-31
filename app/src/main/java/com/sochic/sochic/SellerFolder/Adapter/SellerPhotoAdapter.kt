package com.sochic.sochic.SellerFolder.Adapter

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
import com.sochic.sochic.SellerFolder.SellerProductUploadActivity
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


class SellerPhotoAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<File>,
    public var type : Int
) : androidx.recyclerview.widget.RecyclerView.Adapter<SellerPhotoAdapter.ViewHolder>() {
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
                    if(type == 0) {
                        if(getData.size >= 1) {
                            addView.visibility = View.GONE
                            showView.visibility = View.GONE

                        }else {
                            addView.visibility = View.VISIBLE
                            showView.visibility = View.GONE
                            addImageBtn.setOnClickListener {
                                if(type == 0){
                                    (mAct as SellerProductUploadActivity).mainImageAct()
                                }
                                //  (mAct as ReviewWriteActivity).addImageAct()
                            }
                        }
                    }else {
                        addView.visibility = View.VISIBLE
                        showView.visibility = View.GONE
                        addImageBtn.setOnClickListener {
                            if(type == 0){
                                (mAct as SellerProductUploadActivity).mainImageAct()
                            }else if(type == 1) {
                                (mAct as SellerProductUploadActivity).subImageAct()
                            }else if(type == 2) {
                                (mAct as SellerProductUploadActivity).addImageAct()
                            }
                            //  (mAct as ReviewWriteActivity).addImageAct()
                        }
                    }

                }else {
                    addView.visibility = View.GONE
                    showView.visibility = View.VISIBLE
                    ScImage.image(getData.get(adapterPosition),sImageView)

                    deleteBtn.setOnClickListener {
                        getData.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        notifyItemRangeChanged(adapterPosition,getData.size)


                        if(type == 0) {
                            (mAct as SellerProductUploadActivity).mainImageFile = getData
                            (mAct as SellerProductUploadActivity).mainImageView()
                        }else if(type == 1) {
                            (mAct as SellerProductUploadActivity).subImageFile = getData
                            (mAct as SellerProductUploadActivity).subImageView()
                        }else if(type == 2) {
                            (mAct as SellerProductUploadActivity).addImageFile = getData
                            (mAct as SellerProductUploadActivity).addImageView()
                        }

                    }
                }

            }

        }



    }


}