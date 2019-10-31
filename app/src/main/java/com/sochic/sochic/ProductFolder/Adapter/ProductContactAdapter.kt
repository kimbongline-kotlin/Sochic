package com.sochic.sochic.ProductFolder.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.ProductFolder.API.ProductContactAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.*


class ProductContactAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<ProductContactAPI.ProductContactList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProductContactAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_product_contact_item, parent, false)
    ) {

        fun onBindViewHolder( item : ProductContactAPI.ProductContactList) {
            with(itemView) {

                if(item.answer_confirm) {
                    typeLabel.setBackgroundColor(resources.getColor(R.color.blackColor))
                    typeLabel.setTextColor(resources.getColor(R.color.whiteColor))
                    typeLabel.setText("답변완료")
                    answerLayer.visibility = View.VISIBLE
                    answerLabel.setText(item.answer)
                }else {
                    typeLabel.setBackgroundColor(resources.getColor(R.color.borderLineColor))
                    typeLabel.setTextColor(resources.getColor(R.color.whiteColor))
                    typeLabel.setText("답변대기")
                    answerLayer.visibility = View.GONE
                }


                titleLabel.setText(item.title)
                nickDateLabel.setText(item.nickname + "|" + item.date)
                questionLabel.setText(item.question)

                if(item.open) {
                    contactLayer.visibility = View.VISIBLE
                }else {
                    contactLayer.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    getDatas.get(adapterPosition).open = !getDatas.get(adapterPosition).open
                    for( i in 0..getDatas.size) {
                        if(i == getDatas.size) {
                            notifyDataSetChanged()
                        }else {
                            if (i != adapterPosition) {
                                getDatas.get(i).open = false
                            }
                        }
                    }
                }
            }

        }



    }



}