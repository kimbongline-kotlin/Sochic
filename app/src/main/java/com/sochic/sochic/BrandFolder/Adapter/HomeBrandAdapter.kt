package com.sochic.sochic.BrandFolder.Adapter


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sochic.sochic.BrandFolder.API.BrandAPI
import com.sochic.sochic.BrandFolder.BrandHomeActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.listitem_brand_item.view.*


class HomeBrandAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getDatas : ArrayList<BrandAPI.BrandList>

) : androidx.recyclerview.widget.RecyclerView.Adapter<HomeBrandAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_brand_item, parent, false)
    ) {

        fun onBindViewHolder(item : BrandAPI.BrandList) {
            with(itemView) {

                profileView.setOnClickListener {
                    mAct.startActivity(Intent(mCon, BrandHomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .putExtra("id_user",item.seller_id))
                }

                nickLabel.setText("${item.nickname}")
                ScImage.CircleImage(item.profile_image,profileView)
                recycler.layoutManager = LinearLayoutManager(mCon,RecyclerView.HORIZONTAL,false)
                var adapter = HomeBrandSubAdapter(mCon,mAct,
                    item.product_response as ArrayList<BrandAPI.BrandList.BrandProductList>
                )
                recycler.adapter = adapter
                adapter.notifyDataSetChanged()

                if(item.follow_confirm) {
                    followBtn.setBackgroundColor(resources.getColor(R.color.blackColor))
                    followBtn.setText("팔로잉")
                    followBtn.setTextColor(resources.getColor(R.color.whiteColor))
                }else {
                    followBtn.setBackgroundResource(R.drawable.grayborder_white_box)
                    followBtn.setText("팔로우")
                    followBtn.setTextColor(resources.getColor(R.color.borderLineColor))
                }

                followBtn.setOnClickListener {
                    kAct.disposable.add(kAct.apiService.FOLLOW_API(kAct.id_user,item.seller_id)
                        .subscribeOn(kAct.io)
                        .observeOn(kAct.thread)
                        .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                            override fun onError(e: Throwable?) {
                                kAct.ConnectionError()
                            }

                            override fun onSuccess(t: TrueFalseAPI) {
                                if(t.success) {
                                    getDatas.get(adapterPosition).follow_confirm = !getDatas.get(adapterPosition).follow_confirm
                                    notifyItemChanged(adapterPosition)
                                }else {
                                    kAct.ConnectionError()
                                }
                            }
                        }))
                }

            }

        }



    }



}