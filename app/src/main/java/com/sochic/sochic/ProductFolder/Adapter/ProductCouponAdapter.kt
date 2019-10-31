package com.sochic.sochic.ProductFolder.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sochic.sochic.MyPageFolder.API.CouponAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScTransActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.listitem_my_coupon_item.view.*


class ProductCouponAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<CouponAPI.CouponList>,
    public var type : Int
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProductCouponAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScTransActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_my_coupon_item, parent, false)
    ) {

        fun onBindViewHolder(item : CouponAPI.CouponList) {
            with(itemView) {


                if(item.c_percent_bool) {
                    valueLabel.setText(item.c_value.toString() + "%")
                }else {
                    valueLabel.setText(PriceUtil.set(item.c_value.toString()) + "원 할인")
                }

                nameLabel.setText(item.c_name)
                if(item.c_valid_value == 0) {
                    validLabel.setText("")
                }else {
                    validLabel.setText(PriceUtil.set(item.c_valid_value.toString()) + "원 이상 구매시 사용.")
                }

                nickLabel.setText(item.c_nickname)
                dateLabel.setText(item.c_date)

                if(type == 0) {
                    if(item.c_down_cofirm) {
                        rightBtns.setText("다운완료")
                        rightBtns.setTextColor(resources.getColor(R.color.borderLineColor))
                        rightBtns.setBackgroundResource(R.drawable.category_unselect_layer)
                    }else {
                        rightBtns.setText("쿠폰다운")
                        rightBtns.setTextColor(resources.getColor(R.color.blackColor))
                        rightBtns.setBackgroundResource(R.drawable.category_select_layer)
                        rightBtns.setOnClickListener {
                            kAct.disposable.add(kAct.apiService.COUPON_DOWN_API(kAct.id_user,
                                item.c_idx)
                                .subscribeOn(kAct.io)
                                .observeOn(kAct.thread)
                                .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                                    override fun onError(e: Throwable?) {
                                        kAct.ConnectionError()
                                    }

                                    override fun onSuccess(t: TrueFalseAPI) {
                                        if(t.success) {
                                            kAct.ShowToast("쿠폰을 다운받았습니다.")
                                            getData.get(adapterPosition).c_down_cofirm = true
                                            notifyDataSetChanged()
                                        }else {
                                            kAct.ConnectionError()
                                        }
                                    }
                                }))
                        }
                    }
                }else {
                    if(item.c_use_confirm) {
                        rightBtns.setText("사용완료")
                        rightBtns.setTextColor(resources.getColor(R.color.blackColor))
                        rightBtns.setBackgroundResource(R.drawable.category_select_layer)
                    }else {
                        rightBtns.setText("미사용")
                        rightBtns.setTextColor(resources.getColor(R.color.borderLineColor))
                        rightBtns.setBackgroundResource(R.drawable.category_unselect_layer)
                    }
                }

            }

        }



    }



}