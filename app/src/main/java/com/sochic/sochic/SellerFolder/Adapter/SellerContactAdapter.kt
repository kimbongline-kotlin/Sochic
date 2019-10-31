package com.sochic.sochic.SellerFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.ProductFolder.API.ProductContactAPI
import com.sochic.sochic.R
import com.sochic.sochic.SellerFolder.SellerContactWriteActivity
import com.sochic.sochic.SettingFolder.API.ContactAPI
import com.sochic.sochic.Util.*
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.listitem_contact_item.view.*
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.*
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.answerLabel
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.answerLayer
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.nickDateLabel
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.questionLabel
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.titleLabel
import kotlinx.android.synthetic.main.listitem_product_contact_item.view.typeLabel

import kotlinx.android.synthetic.main.listitem_product_review_item.view.*


class SellerContactAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<ContactAPI.ContactList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<SellerContactAdapter.ViewHolder>() {
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
                    writeLayer.visibility = View.GONE
                }else {
                    typeLabel.setBackgroundColor(resources.getColor(R.color.borderLineColor))
                    typeLabel.setTextColor(resources.getColor(R.color.whiteColor))
                    typeLabel.setText("답변대기")
                    answerLayer.visibility = View.GONE
                    writeLayer.visibility = View.VISIBLE
                    answerBtn.setOnClickListener {
                        mAct.startActivity(Intent(mAct,SellerContactWriteActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            .putExtra("idx", item.idx))
                    }
                }

                pNameLabel.visibility = View.VISIBLE

                titleLabel.setText(item.title)
                nickDateLabel.setText( item.date)
                questionLabel.setText(item.question)

                pNameLabel.setText(item.product_name)
                if(item.image.toString().isEmpty()) {
                    sView.visibility = View.GONE
                }else {
                    sView.visibility = View.VISIBLE
                    ScImage.image(item.image,sView)
                }
            }

        }



    }



}