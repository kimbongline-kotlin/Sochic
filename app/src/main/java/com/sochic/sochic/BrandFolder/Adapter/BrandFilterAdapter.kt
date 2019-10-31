package com.sochic.sochic.BrandFolder.Adapter


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_filter_select_item.view.*


class BrandFilterAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<String>

) : androidx.recyclerview.widget.RecyclerView.Adapter<BrandFilterAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_filter_select_item, parent, false)
    ) {

        fun onBindViewHolder(item : String) {
            with(itemView) {


                textView18.setText(item)

            }

        }



    }



}