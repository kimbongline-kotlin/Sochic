package com.sochic.sochic.SettingFolder.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binaryfork.spanny.Spanny
import com.sochic.sochic.R
import com.sochic.sochic.SettingFolder.API.NoticeAPI
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_notice_item.view.*


class NoticeAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<NoticeAPI.NoticeList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_notice_item, parent, false)
    ) {

        fun onBindViewHolder(item : NoticeAPI.NoticeList) {
            with(itemView) {

                dateLabel.setText(item.date)
                titleLabel.setText(item.title)
                contentsLabel.setText(item.contents)

                if(item.open) {
                    arrowIcon.rotation = 180.0f
                    aLayer.visibility = View.VISIBLE
                    titleLabel.setText(Spanny.spanText(item.title, StyleSpan(Typeface.BOLD)))
                }else {
                    arrowIcon.rotation = 0.0f
                    aLayer.visibility = View.GONE
                    titleLabel.setText(Spanny.spanText(item.title, StyleSpan(Typeface.NORMAL)))
                }

                itemView.setOnClickListener {
                    if(!item.open) {
                        for (i in 0..getData.size) {
                            if(i == getData.size) {
                                getData.get(adapterPosition).open = true
                                notifyDataSetChanged()
                            }else {
                                getData.get(i).open = false
                            }
                        }


                    }else {
                        for (i in 0..getData.size) {
                            if(i == getData.size) {
                                notifyDataSetChanged()
                            }else {
                                getData.get(i).open = false
                            }
                        }
                    }
                }


            }

        }



    }



}