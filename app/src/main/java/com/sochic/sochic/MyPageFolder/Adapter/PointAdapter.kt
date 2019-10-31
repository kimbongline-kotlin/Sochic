package com.sochic.sochic.MyPageFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.MyPageFolder.API.PointAPI
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.SearchKeywordActivity
import com.sochic.sochic.SearchFolder.SearchResultActivity
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_category_layer.view.*
import kotlinx.android.synthetic.main.listitem_point_item.view.*
import kotlinx.android.synthetic.main.listitem_recent_keyword_item.view.*


class PointAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<PointAPI.PointList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<PointAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_point_item, parent, false)
    ) {

        fun onBindViewHolder( item : PointAPI.PointList) {
            with(itemView) {


                dateLabel.setText(item.date)
                titleLabel.setText(item.title)
                contentsLabel.setText(item.contents)
                pointLabel.setText(item.point + "P")


            }

        }



    }



}