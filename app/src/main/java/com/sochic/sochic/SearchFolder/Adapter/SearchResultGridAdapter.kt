package com.sochic.sochic.SearchFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.ProductFolder.ProductMainActivity
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.API.SubCategorySearchAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import kotlinx.android.synthetic.main.listitem_feed_grid_item.view.*


import kotlinx.android.synthetic.main.listitem_search_result_item.view.*


class SearchResultGridAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<SubCategorySearchAPI.SubCategorySearchList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<SearchResultGridAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_feed_grid_item, parent, false)
    ) {

        fun onBindViewHolder(item : SubCategorySearchAPI.SubCategorySearchList) {
            with(itemView) {


                sTimerLabel.visibility = View.GONE
                ScImage.image(item.image,pImageView)

                itemView.setOnClickListener {
                    kAct.startActivity(Intent(mCon,ProductMainActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .putExtra("idx",item.idx))
                }

            }

        }



    }



}