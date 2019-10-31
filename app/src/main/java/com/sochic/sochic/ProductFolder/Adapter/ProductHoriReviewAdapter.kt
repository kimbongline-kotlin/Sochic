package com.sochic.sochic.ProductFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sochic.sochic.ProductFolder.API.ProductReviewPhotoAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.FullScreenViewActivity
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import kotlinx.android.synthetic.main.listitem_feed_grid_item.view.*



class ProductHoriReviewAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<ProductReviewPhotoAPI.ProductReviewPhotoList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProductHoriReviewAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_horizontal_review_item, parent, false)
    ) {

        fun onBindViewHolder(item : ProductReviewPhotoAPI.ProductReviewPhotoList) {
            with(itemView) {


               ScImage.image(item.image,pImageView)

                pImageView.setOnClickListener {
                    var images = ArrayList<String>()
                    for (img in getDatas) {
                        images.add(img.image)
                    }

                    kAct.startActivity(Intent(mCon,FullScreenViewActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("images",images))
                }

            }

        }



    }



}