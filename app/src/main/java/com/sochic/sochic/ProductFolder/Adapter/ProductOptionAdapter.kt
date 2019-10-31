package com.sochic.sochic.ProductFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.ProductFolder.API.ProductPhotoAPI
import com.sochic.sochic.ProductFolder.ProductOptionActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.*
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_product_option.view.*

import kotlinx.android.synthetic.main.listitem_feed_grid_item.view.*
import kotlinx.android.synthetic.main.listitem_feed_item.view.*
import kotlinx.android.synthetic.main.listitem_option_item.view.*


class ProductOptionAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<Map<String,String>>,
    public var type : Int,
    public var optionTitle : String
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProductOptionAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScTransActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_option_item, parent, false)
    ) {

        fun onBindViewHolder(item : Map<String,String>) {
            with(itemView) {


                if(item.get("add_price")!!.toInt() == 0){
                    if(optionTitle.toString().isEmpty()) {
                        nameLabel.setText(item.get("option_name")!!)
                    }else {
                        nameLabel.setText("$optionTitle : "+item.get("option_name")!!)
                    }

                    priceLabel.setText(PriceUtil.set("${item.get("price")!!.toInt() * item.get("cnt")!!.toInt()}") +"원")
                }else {
                    if(optionTitle.toString().isEmpty()) {
                        nameLabel.setText(item.get("option_name")!! + " (추가 금액 + ${PriceUtil.set(item.get("add_price")!!)}원)")
                    }else {
                        nameLabel.setText("$optionTitle : "+item.get("option_name")!! + " (추가 금액 + ${PriceUtil.set(item.get("add_price")!!)}원)")
                    }

                    priceLabel.setText(PriceUtil.set("${(item.get("price")!!.toInt()  + item.get("add_price")!!.toInt()) * item.get("cnt")!!.toInt()}") +"원")
                }

                cntLabel.setText(item.get("cnt"))

                pBtn.setOnClickListener {
                    var nowCnt = item.get("cnt")!!.toInt()
                    if(nowCnt == item.get("max_cnt")!!.toInt()) {
                        kAct.ShowToast("더 이상 재고가 없습니다.")
                    }else {

                        var mapItem = mapOf<String,String>("idx" to item.get("idx")!!,
                            "option_name" to item.get("option_name")!!,
                            "price" to item.get("price")!!,
                            "max_cnt" to "5",
                            "cnt" to "${item.get("cnt")!!.toInt() + 1}",
                            "add_price" to "${item.get("add_price")}",
                            "p_idx" to "${item.get("p_idx")}")
                        getData.removeAt(adapterPosition)
                        getData.add(adapterPosition,mapItem)
                        (mAct as ProductOptionActivity).calcurateTotalPrice(getData)
                        notifyItemChanged(adapterPosition)

                    }

                }

                mBtn.setOnClickListener {
                    var nowCnt = item.get("cnt")!!.toInt()
                    if(nowCnt == 1) {

                    }else {
                        var mapItem = mapOf<String,String>("idx" to item.get("idx")!!,
                            "option_name" to item.get("option_name")!!,
                            "price" to item.get("price")!!,
                            "max_cnt" to "5",
                            "cnt" to "${item.get("cnt")!!.toInt() - 1}",
                            "add_price" to "${item.get("add_price")}",
                            "p_idx" to "${item.get("p_idx")}")
                        getData.removeAt(adapterPosition)
                        getData.add(adapterPosition,mapItem)
                        (mAct as ProductOptionActivity).calcurateTotalPrice(getData)
                        notifyItemChanged(adapterPosition)

                    }
                }

                if(type == 0) {
                    removeBtn.visibility = View.GONE
                }else {
                    removeBtn.visibility = View.VISIBLE
                    removeBtn.setOnClickListener {
                        getData.removeAt(adapterPosition)
                        (mAct as ProductOptionActivity).calcurateTotalPrice(getData)
                        notifyItemRemoved(adapterPosition)
                        notifyItemRangeChanged(adapterPosition,getData.size)
                    }
                }

            }

        }



    }



}