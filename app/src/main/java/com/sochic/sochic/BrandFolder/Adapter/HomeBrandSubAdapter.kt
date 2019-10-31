package com.sochic.sochic.BrandFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.BrandFolder.API.BrandAPI
import com.sochic.sochic.ProductFolder.ProductMainActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.GlideImageLoader
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.listitem_brand_sub_item.view.*


import kotlin.concurrent.timer


class HomeBrandSubAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<BrandAPI.BrandList.BrandProductList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<HomeBrandSubAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_brand_sub_item, parent, false)
    ) {

        fun onBindViewHolder(item : BrandAPI.BrandList.BrandProductList) {
            with(itemView) {

                ScImage.image(
                    item.image,sImageView
                )

                itemView.setOnClickListener {
                    mAct.startActivity(Intent(mCon,ProductMainActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .putExtra("idx",item.idx))
                }

            }

        }



    }



}