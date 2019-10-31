package com.sochic.sochic.SettingFolder.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.R
import com.sochic.sochic.SettingFolder.API.ContactAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import kotlinx.android.synthetic.main.listitem_contact_item.view.*
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.answerLabel
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.answerLayer
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.nickDateLabel
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.questionLabel
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.titleLabel
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.typeLabel


class ContactAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<ContactAPI.ContactList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_contact_item, parent, false)
    ) {

        fun onBindViewHolder( item : ContactAPI.ContactList) {
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
                if(item.nickname.toString().equals("")){
                    nickDateLabel.setText(item.date)
                }else {
                    nickDateLabel.setText(item.nickname + "|" + item.date)
                }

                questionLabel.setText(item.question)

                if(item.image.toString().isEmpty()) {
                    sView.visibility = View.GONE
                }else {
                    sView.visibility = View.VISIBLE
                    ScImage.image(item.image,sView)
                }

                if(item.open) {
                    contentsLayer.visibility = View.VISIBLE
                }else {
                    contentsLayer.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    if(item.open) {
                        for (i in 0..getDatas.size) {
                            if(i == getDatas.size) {
                                notifyDataSetChanged()
                            }else {
                                getDatas.get(i).open = false
                            }
                        }
                    }else {
                        for (i in 0..getDatas.size) {
                            if(i == getDatas.size) {
                                getDatas.get(adapterPosition).open = true

                                notifyDataSetChanged()
                            }else {
                                getDatas.get(i).open = false
                            }
                        }
                    }

                }
            }

        }



    }



}