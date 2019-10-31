package com.sochic.sochic.HomeFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.HomeFolder.API.HomeItemAPI
import com.sochic.sochic.ProductFolder.ProductMainActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.GlideImageLoader
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

import kotlinx.android.synthetic.main.listitem_feed_grid_item.view.*
import kotlinx.android.synthetic.main.listitem_feed_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeGridFeedAdapter(
    public val context: Context,
    public var activity: Activity,

    public var getDatas : ArrayList<HomeItemAPI.HomeItemList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<HomeGridFeedAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_feed_grid_item, parent, false)
    ) {

        fun onBindViewHolder(item : HomeItemAPI.HomeItemList) {
            with(itemView) {


               ScImage.image(item.img_response.get(0).image,pImageView)

                if(item.type == 0) {
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    formatter.setLenient(false)


                    val endTime = item.open_date
                    var milliseconds: Long = 0

                    val mCountDownTimer: CountDownTimer

                    val endDate: Date
                    var startTime = item.server_date
                    var startDate : Date
                    var startMilliSeconds : Long = 0
                    try {
                        endDate = formatter.parse(endTime)
                        milliseconds = endDate.getTime()

                        startDate = formatter.parse(startTime)
                        startMilliSeconds = startDate.time
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }


                    mCountDownTimer = object : CountDownTimer(milliseconds,1000) {
                        override fun onFinish() {


                        }

                        override fun onTick(millisUntilFinished: Long) {
                            startMilliSeconds = startMilliSeconds - 1;
                            var serverUptimeSeconds = (millisUntilFinished - startMilliSeconds) / 1000
                            var hoursLeft = String.format("%02d", (serverUptimeSeconds % 86400) / 3600);
                            var minLeft = String.format("%02d", ((serverUptimeSeconds % 86400) % 3600) / 60);
                            var secLeft = String.format("%02d", ((serverUptimeSeconds % 86400) % 3600) % 60);
                            sTimerLabel.setText("$hoursLeft:$minLeft:$secLeft")
                        }
                    }.start()
                    sTimerLabel.visibility = View.VISIBLE
                }else {
                    sTimerLabel.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    mAct.startActivity(Intent(mCon,ProductMainActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .putExtra("idx",item.idx))
                }
            }

        }



    }



}