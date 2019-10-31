package com.sochic.sochic.MyPageFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binaryfork.spanny.Spanny
import com.sochic.sochic.MyPageFolder.API.FollowingAlarmAPI
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.SearchKeywordActivity
import com.sochic.sochic.SearchFolder.SearchResultActivity
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import kotlinx.android.synthetic.main.listitem_alarm_following_item.view.*
import kotlinx.android.synthetic.main.listitem_category_layer.view.*
import kotlinx.android.synthetic.main.listitem_category_layer.view.txtLabel
import kotlinx.android.synthetic.main.listitem_recent_keyword_item.view.*


class FollowingAlarmAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<FollowingAlarmAPI.FollowingAlarmList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<FollowingAlarmAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_alarm_following_item, parent, false)
    ) {

        fun onBindViewHolder(item : FollowingAlarmAPI.FollowingAlarmList) {
            with(itemView) {


                ScImage.CircleImage(item.profile_image,pView)
                dateLabel.setText(item.date)
                var spanny = Spanny(item.nickname, StyleSpan(Typeface.BOLD)).append(" 인플루언서의 새 상품 ").append(item.seller_product_name, StyleSpan(Typeface.BOLD)).append(" 이 등록되었습니다.")
                txtLabel.setText(spanny)

            }

        }



    }



}