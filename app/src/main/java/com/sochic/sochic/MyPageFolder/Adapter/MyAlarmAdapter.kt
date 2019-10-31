package com.sochic.sochic.MyPageFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.MyPageFolder.API.MyAlarmAPI
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.SearchKeywordActivity
import com.sochic.sochic.SearchFolder.SearchResultActivity
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_alarm_my_item.view.*
import kotlinx.android.synthetic.main.listitem_category_layer.view.*
import kotlinx.android.synthetic.main.listitem_category_layer.view.txtLabel
import kotlinx.android.synthetic.main.listitem_recent_keyword_item.view.*


class MyAlarmAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData  : ArrayList<MyAlarmAPI.MyAlarmList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<MyAlarmAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_alarm_my_item, parent, false)
    ) {

        fun onBindViewHolder( item : MyAlarmAPI.MyAlarmList) {
            with(itemView) {

                txtLabel.setText(item.contents)
                dateLabel.setText(item.date)



            }

        }



    }



}