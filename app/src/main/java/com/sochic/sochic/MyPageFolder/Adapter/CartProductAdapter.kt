package com.sochic.sochic.MyPageFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binaryfork.spanny.Spanny
import com.sochic.sochic.MyPageFolder.API.CartAPI
import com.sochic.sochic.MyPageFolder.MyProductActivity
import com.sochic.sochic.ProductFolder.ProductMainActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.listitem_cart_product_item.view.*



class CartProductAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<CartAPI.CartList.CartItemList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<CartProductAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_cart_product_item, parent, false)
    ) {

        fun onBindViewHolder( item : CartAPI.CartList.CartItemList) {
            with(itemView) {


                sOptionChangeBtn.visibility = View.GONE
                sNameLabel.setText(item.name)
                sOptionLabel.setText(item.option_name)
                if(item.add_price != 0) {
                    sOptionLabel.setText(Spanny(item.option_name,StyleSpan(Typeface.BOLD)).append(" (옵션가 : +${PriceUtil.set(item.add_price.toString())}원)",StyleSpan(Typeface.NORMAL)))
                }
                sCntLabel.setText(PriceUtil.set(item.cnt.toString()) + "개")

                ScImage.image(item.img_response.get(0).image,sImageView)

                var price : Int = 0
                if(item.sale_confirm) {
                    if(item.add_price == 0 ) {
                        price =(item.sale_price )
                        sOpriceLabel.setText(PriceUtil.set(item.price.toString()) + "원")
                        sPriceLabel.setText(PriceUtil.set(price.toString()) + "원")
                    }else {
                        price =(item.sale_price )
                        sOpriceLabel.setText(PriceUtil.set((item.price + item.add_price).toString()) + "원")
                        sPriceLabel.setText(PriceUtil.set((price + item.add_price).toString()) + "원")
                    }

                }else {
                    if(item.add_price == 0 ){
                        price =(item.price )
                        sOpriceLabel.setText("")
                        sPriceLabel.setText(PriceUtil.set(price.toString()) + "원")
                    }else {
                        price =(item.price + item.add_price)
                        sOpriceLabel.setText("")
                        sPriceLabel.setText(PriceUtil.set(price.toString()) + "원")
                    }

                }

                deleteBtn.setOnClickListener {
                    kAct.disposable.add(kAct.apiService.CART_REMOVE_API(kAct.id_user,
                        item.sub_o_index)
                        .subscribeOn(kAct.io)
                        .observeOn(kAct.thread)
                        .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                            override fun onError(e: Throwable?) {
                                kAct.ConnectionError()
                            }

                            override fun onSuccess(t: TrueFalseAPI) {
                                if(t.success) {
                                    getData.removeAt(adapterPosition)
                                    notifyItemRemoved(adapterPosition)
                                    notifyItemRangeChanged(adapterPosition,getData.size)
                                    (mAct as MyProductActivity).showCart()
                                }else {
                                    kAct.ConnectionError()
                                }
                            }
                        }))
                }

                itemView.setOnClickListener {
                    mAct.startActivity(Intent(mCon,ProductMainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("idx",item.idx_goods))
                }

            }

        }



    }



}