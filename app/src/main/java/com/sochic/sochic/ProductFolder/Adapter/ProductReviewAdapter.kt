package com.sochic.sochic.ProductFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.ProductFolder.API.ProductReviewAPI
import com.sochic.sochic.ProductFolder.API.ProductReviewPhotoAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.*
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

import kotlinx.android.synthetic.main.listitem_product_review_item.view.*


class ProductReviewAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<ProductReviewAPI.ProductReviewList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProductReviewAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_product_review_item, parent, false)
    ) {

        fun onBindViewHolder(item : ProductReviewAPI.ProductReviewList) {
            with(itemView) {

                if(item.img_response.size > 0) {
                    ScImage.image(item.img_response.get(0).image,pImageView)
                }

                rNameLabel.setText(item.name)
                rNickDateLabel.setText(item.nickname + "|" + item.created_date)
                ratingbar.setStar(item.rate)
                rTextLabel.setText(item.contents)


                recycler.layoutManager = LinearLayoutManager(mCon,RecyclerView.HORIZONTAL,false)
                recycler.addItemDecoration(SpacesItemDecoration(kAct.convertDpToPixel(5.0f,mCon).toInt(),5))
                var adapter = ProductHoriReviewAdapter(mCon,mAct,
                    item.img_response as ArrayList<ProductReviewPhotoAPI.ProductReviewPhotoList>
                )
                recycler.adapter = adapter
                adapter.notifyDataSetChanged()
                moreBtn.setOnClickListener {
                    if(recycler.visibility == View.VISIBLE) {
                        moreBtn.setText("더보기")
                        pImageView.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                    }else {
                        moreBtn.setText("닫기")
                        pImageView.visibility = View.GONE
                        recycler.visibility = View.VISIBLE
                    }
                }


            }

        }



    }



}