package com.sochic.sochic.PayFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.MyPageFolder.API.AddressAPI
import com.sochic.sochic.PayFolder.DeliveryEditActivity
import com.sochic.sochic.ProductFolder.API.ProductPhotoAPI
import com.sochic.sochic.ProductFolder.ProductOptionActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.*
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import io.reactivex.observers.DisposableSingleObserver

import kotlinx.android.synthetic.main.listitem_delivery_item.view.*



class PaidDeliveryAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<AddressAPI.AddressList>

) : androidx.recyclerview.widget.RecyclerView.Adapter<PaidDeliveryAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_delivery_item, parent, false)
    ) {

        fun onBindViewHolder(item : AddressAPI.AddressList) {
            with(itemView) {

                modifyBtn.setOnClickListener {
                    mAct.startActivity(Intent(mCon,DeliveryEditActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .putExtra("idx",item.d_idx))
                }

                nameLabel.isSelected = item.d_selected
                nameLabel.setText(item.d_name)
                infoLabel.setText(item.d_address)

                nameLabel.setOnClickListener {


                    for(i in 0..getDatas.size) {
                        if(i == getDatas.size ) {

                            kAct.disposable.add(kAct.apiService.ADDRESS_SELECT_API(kAct.id_user,item.d_idx)
                                .subscribeOn(kAct.io)
                                .observeOn(kAct.thread)
                                .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                                    override fun onError(e: Throwable?) {
                                        kAct.ConnectionError()
                                    }

                                    override fun onSuccess(t: TrueFalseAPI?) {
                                        getDatas.get(adapterPosition).d_selected = true
                                        notifyDataSetChanged()
                                    }
                                }))
                        }else {
                            getDatas.get(i).d_selected = false
                        }
                    }
                }

            }

        }



    }



}