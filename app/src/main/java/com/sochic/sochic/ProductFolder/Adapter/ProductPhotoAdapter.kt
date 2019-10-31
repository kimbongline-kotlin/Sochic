package com.sochic.sochic.ProductFolder.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sochic.sochic.ProductFolder.API.ProductPhotoAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import kotlinx.android.synthetic.main.listitem_feed_grid_item.view.*



class ProductPhotoAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<ProductPhotoAPI.ProductPhotoList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProductPhotoAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_photo_item, parent, false)
    ) {

        fun onBindViewHolder( item : ProductPhotoAPI.ProductPhotoList) {
            with(itemView) {


               ScImage.image(item.image,pImageView)



            }

        }



    }



}