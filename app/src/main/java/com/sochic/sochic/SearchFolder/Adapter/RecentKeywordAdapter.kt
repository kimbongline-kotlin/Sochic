package com.sochic.sochic.SearchFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.SearchKeywordActivity
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_category_layer.view.*
import kotlinx.android.synthetic.main.listitem_recent_keyword_item.view.*


class RecentKeywordAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<String>

) : androidx.recyclerview.widget.RecyclerView.Adapter<RecentKeywordAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.count()



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_recent_keyword_item, parent, false)
    ) {

        fun onBindViewHolder(item : String) {
            with(itemView) {


                keywordLabel.setText(item)

                deleteBtn.setOnClickListener {
                    getDatas.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition,getDatas.size)
                    (mAct as SearchKeywordActivity).removeItem(getDatas)
                }

                itemView.setOnClickListener {
                    (mAct as SearchKeywordActivity).goToResult(item)
                }
            }

        }



    }



}