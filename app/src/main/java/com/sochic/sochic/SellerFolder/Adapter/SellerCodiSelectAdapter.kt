package com.sochic.sochic.SellerFolder.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sochic.sochic.R
import com.sochic.sochic.SellerFolder.API.SellerCodiItemAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import kotlinx.android.synthetic.main.listitem_seller_codi_select_item.view.*


class SellerCodiSelectAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<SellerCodiItemAPI.SellerCodiItemList>

) : androidx.recyclerview.widget.RecyclerView.Adapter<SellerCodiSelectAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_seller_codi_select_item, parent, false)
    ) {

        fun onBindViewHolder( item : SellerCodiItemAPI.SellerCodiItemList) {
            with(itemView) {

                ScImage.image(item.img_response.get(0).image,sImageView)
                sNameLabel.setText(item.product_code)
                sOptionLabel.setText(item.product_name)

                radioButton.isSelected = item.open

                itemView.setOnClickListener {
                    getData.get(adapterPosition).open = !getData.get(adapterPosition).open
                    notifyDataSetChanged()
                }

                radioButton.setOnClickListener {
                    itemView.callOnClick()
                }

            }

        }



    }


}