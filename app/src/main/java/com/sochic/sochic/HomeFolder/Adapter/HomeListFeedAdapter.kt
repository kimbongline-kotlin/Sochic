package com.sochic.sochic.HomeFolder.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.BrandFolder.BrandHomeActivity
import com.sochic.sochic.HomeFolder.API.HomeItemAPI
import com.sochic.sochic.ProductFolder.ProductMainActivity
import com.sochic.sochic.R
import com.sochic.sochic.Util.*
import com.sochic.sochic.Util.ApiMannager.LikeAPI
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.listitem_feed_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeListFeedAdapter(
    public val context: Context,
    public var activity: Activity,

    public var getDatas : ArrayList<HomeItemAPI.HomeItemList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<HomeListFeedAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getDatas.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getDatas.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(com.sochic.sochic.R.layout.listitem_feed_item, parent, false)
    ) {

        fun onBindViewHolder(item : HomeItemAPI.HomeItemList) {
            with(itemView) {

                profileView.setOnClickListener {
                    mAct.startActivity(Intent(mCon,BrandHomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .putExtra("id_user",item.id_user))
                }

                ScImage.CircleImage(item.profile_image,profileView)

                var items = ArrayList<String>()
                for (i in 0..item.img_response.size) {
                    if(i == item.img_response.size) {

                        sImageView.setBannerStyle(BannerConfig.NUM_INDICATOR)
                        sImageView.setImageLoader(GlideImageLoader())
                        sImageView.setImages(items)
                        sImageView.setBannerAnimation(Transformer.Default)
                        sImageView.setIndicatorGravity(6)
                        sImageView.isAutoPlay(false)
                        sImageView.start()
                        sImageView.setOnBannerListener {
                            itemView.callOnClick()
                        }
                    }else {
                        items.add(item.img_response.get(i).image)
                    }
                }

                nickLabel.setText(item.nickname)
                likeLabel.setText(PriceUtil.set("${item.heart_cnt}"))
                titleLabel.setText(item.name)
                textLabel.setText(item.info)

                if(item.heart_cnt == 0) {
                    likeLabel.setText("")
                }

                likeBtn.isSelected = item.heart_confirm
                saveBtn.isSelected = item.bookmark_confirm

                likeBtn.setOnClickListener {
                    kAct.disposable.add(kAct.apiService.LIKE_API(kAct.id_user,item.idx)
                        .subscribeOn(kAct.io)
                        .observeOn(kAct.thread)
                        .subscribeWith(object : DisposableSingleObserver<LikeAPI> () {
                            override fun onError(e: Throwable?) {
                                kAct.ConnectionError()
                            }

                            override fun onSuccess(t: LikeAPI) {
                                if(t.success) {

                                    getDatas.get(adapterPosition).heart_confirm = !getDatas.get(adapterPosition).heart_confirm
                                    getDatas.get(adapterPosition).heart_cnt = t.number
                                    notifyItemChanged(adapterPosition)

                                }else {
                                    kAct.ConnectionError()
                                }
                            }
                        }))
                }

                saveBtn.setOnClickListener {
                    kAct.disposable.add(kAct.apiService.BOOKMARK_API(kAct.id_user,item.idx)
                        .subscribeOn(kAct.io)
                        .observeOn(kAct.thread)
                        .subscribeWith(object : DisposableSingleObserver<LikeAPI> () {
                            override fun onError(e: Throwable?) {
                                kAct.ConnectionError()
                            }

                            override fun onSuccess(t: LikeAPI) {
                                if(t.success) {
                                    getDatas.get(adapterPosition).bookmark_confirm = !getDatas.get(adapterPosition).bookmark_confirm
                                    notifyItemChanged(adapterPosition)
                                }
                            }
                        }))
                }
                if(item.type == 0) {
                    timerLabel.visibility = View.VISIBLE


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
                            timerLabel.setText("$hoursLeft:$minLeft:$secLeft")
                        }
                    }.start()
                }else {
                    timerLabel.visibility = View.GONE

                }


                itemView.setOnClickListener {
                    mAct.startActivity(Intent(mCon,ProductMainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("idx",item.idx))
                }

                moreBtn.setOnClickListener {
                    var myLayer = false
                    if(kAct.id_user.toString().equals(item.id_user)) {
                        myLayer = false
                    }

                    kAct.scAlert.moreAlert(mAct, View.OnClickListener {
                        if(it.id == R.id.moreFollowBtn) {

                            kAct.scAlert.simpleDialog.dismiss()
                            kAct.disposable.add(kAct.apiService.FOLLOW_API(kAct.id_user, item.id_user)
                                .subscribeOn(kAct.io)
                                .observeOn(kAct.thread)
                                .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                                    override fun onError(e: Throwable?) {

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
                        }else if(it.id == R.id.moreShareBtn) {

                            kAct.scAlert.simpleDialog.dismiss()
                            kAct.shortUrl(item.idx,"product",item.name,item.info,item.img_response.get(0).image,object : ShareReturn {
                                override fun onError(th: Throwable) {

                                }

                                override fun onSuccess(str: String) {
                                    kAct.shareItem(item.name,str)
                                }
                            })
                        }
                    },myLayer,item.follow_confirm)
                }

            }

        }



    }



}