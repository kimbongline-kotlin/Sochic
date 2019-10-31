package com.sochic.sochic.MyPageFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.MyPageFolder.MyProductActivity
import com.sochic.sochic.ProductFolder.ProductMainActivity
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.API.SubCategorySearchAPI
import com.sochic.sochic.Util.ApiMannager.LikeAPI
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.listitem_search_result_item.view.*


class BookMarkAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<SubCategorySearchAPI.SubCategorySearchList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<BookMarkAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_search_result_item, parent, false)
    ) {

        fun onBindViewHolder(item : SubCategorySearchAPI.SubCategorySearchList) {
            with(itemView) {


                deleteBtn.visibility = View.VISIBLE
                ScImage.image(item.image,sImageView)
                nameLabel.setText(item.name)

                if(item.sale_confirm) {
                    priceLabel.setText(PriceUtil.set("${item.price}") + "원")
                    saleLabel.setText(PriceUtil.set("${item.sale_price}") + "원")
                }else {
                    saleLabel.setText(PriceUtil.set("${item.price}") + "원")
                    priceLabel.setText("")
                }

                deleteBtn.setOnClickListener {
                    kAct.disposable.add(kAct.apiService.BOOKMARK_API(kAct.id_user,item.idx)
                        .subscribeOn(kAct.io)
                        .observeOn(kAct.thread)
                        .subscribeWith(object : DisposableSingleObserver<LikeAPI>() {
                            override fun onError(e: Throwable?) {
                                kAct.ConnectionError()
                            }

                            override fun onSuccess(t: LikeAPI) {
                                if(t.success) {
                                   getData.removeAt(adapterPosition)
                                    notifyItemRemoved(adapterPosition)
                                    notifyItemRangeChanged(adapterPosition,getData.size)
                                    if(mAct is MyProductActivity) {
                                        (mAct as MyProductActivity).getCnt()
                                    }
                                }
                            }
                        }))
                }

                itemView.setOnClickListener {
                    mAct.startActivity(Intent(mCon,ProductMainActivity::class.java).putExtra("idx" , item.idx).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                }

            }

        }



    }



}