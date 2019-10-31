package com.sochic.sochic.OrderFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binaryfork.spanny.Spanny
import com.sochic.sochic.OrderFolder.API.OrderAPI
import com.sochic.sochic.OrderFolder.OrderCancelActivity
import com.sochic.sochic.OrderFolder.OrderDeliveryActivity
import com.sochic.sochic.OrderFolder.OrderDetailActivity
import com.sochic.sochic.OrderFolder.OrderExchangeActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.listitem_cart_user_item.view.recycler
import kotlinx.android.synthetic.main.listitem_my_order_item.view.*


class MyOrderAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<OrderAPI.OrderList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_my_order_item, parent, false)
    ) {

        fun onBindViewHolder( item : OrderAPI.OrderList) {
            with(itemView) {

                leftLabel.setText(item.o_code +" " + item.date)

                recycler.layoutManager = LinearLayoutManager(mCon,RecyclerView.VERTICAL,false)
                var adapter = MyOrderProductAdapter(mCon,mAct,
                    item.o_response as ArrayList<OrderAPI.OrderList.OrderItemList>
                ,"")
                recycler.adapter = adapter

                itemView.setOnClickListener {
                    mAct.startActivity(Intent(mCon,OrderDetailActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("idx",item.o_code)
                        .putExtra("subIdx", item.sub_o_code))
                }

                when (item.order_confirm) {
                    0 -> {
                        rightBtns.setText(Spanny("주문취소",UnderlineSpan()))
                        stateBtn.setText("입금대기")

                        rightBtns.setOnClickListener {
                            mAct.startActivity(Intent(mCon,OrderCancelActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("idx",item.o_code)
                                .putExtra("subIdx", item.sub_o_code)
                            )
                        }
                    }

                    1 -> {
                        rightBtns.setText(Spanny("주문취소",UnderlineSpan()))
                        stateBtn.setText("결제완료")
                        rightBtns.setOnClickListener {
                            mAct.startActivity(Intent(mCon,OrderCancelActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("idx",item.o_code)
                                .putExtra("subIdx", item.sub_o_code))
                        }
                    }

                    2-> {
                        rightBtns.setText(Spanny("주문취소",UnderlineSpan()))
                        stateBtn.setText("배송준비")
                        rightBtns.setOnClickListener {
                            mAct.startActivity(Intent(mCon,OrderCancelActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("idx",item.o_code)
                                .putExtra("subIdx", item.sub_o_code))
                        }
                    }

                    3 -> {
                        rightBtns.setText(Spanny("배송조회",UnderlineSpan()))
                        stateBtn.setText("배송중")
                        rightBtns.setOnClickListener {
                            mAct.startActivity(Intent(mCon,OrderDeliveryActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                        }
                    }

                    4 -> {
                        rightBtns.setText(Spanny("교환/반품",UnderlineSpan()))
                        stateBtn.setText("배송완료")

                        rightBtns.setOnClickListener {
                            mAct.startActivity(Intent(mCon,OrderExchangeActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("idx",item.o_code)
                                .putExtra("subIdx", item.sub_o_code))
                        }
                    }

                    5 -> {
                        rightBtns.setText("")
                        stateBtn.setText("구매확정")
                    }
                    6 -> {
                        rightBtns.setText("")
                        stateBtn.setText("취소완료")
                    }

                    7 -> {
                        rightBtns.setText("")
                        stateBtn.setText("교환완료")
                    }

                    8 -> {
                        rightBtns.setText("")
                        stateBtn.setText("반품완료")
                    }

                    9 -> {
                        rightBtns.setText("")
                        stateBtn.setText("취소접수")
                    }

                    10 -> {
                        rightBtns.setText("")
                        stateBtn.setText("교환접수")
                    }

                    11 -> {
                        rightBtns.setText("")
                        stateBtn.setText("반품접수")
                    }
                }
            }

        }



    }


}