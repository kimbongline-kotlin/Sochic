package com.sochic.sochic.LoginFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.R
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_category_layer.view.*


class RegisterCategoryAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : List<String>,
    public var boolDatas : ArrayList<Boolean>
) : androidx.recyclerview.widget.RecyclerView.Adapter<RegisterCategoryAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.count()



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position),boolDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_category_layer, parent, false)
    ) {

        fun onBindViewHolder(item : String, check : Boolean) {
            with(itemView) {

                txtLabel.setText(item)
                txtLabel.isSelected = boolDatas.get(position)
                if(boolDatas.get(position)) {
                    txtLabel.setTextColor(resources.getColor(R.color.blackColor))
                }else {
                    txtLabel.setTextColor(resources.getColor(R.color.borderLineColor))
                }

                itemView.setOnClickListener {
                    boolDatas.set(position,!boolDatas.get(position))
                    notifyDataSetChanged()
                }
            }

        }



    }



}