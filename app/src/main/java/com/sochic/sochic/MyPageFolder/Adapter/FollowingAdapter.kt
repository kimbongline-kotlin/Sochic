package com.sochic.sochic.MyPageFolder.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sochic.sochic.MyPageFolder.API.MyFollowingAPI
import com.sochic.sochic.R
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.listitem_following_item.view.*


class FollowingAdapter(
    public val context: Context,
    public var activity: Activity,
    public var getData : ArrayList<MyFollowingAPI.MyFollowingList>
) : androidx.recyclerview.widget.RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    public val mAct = activity
    public val mCon = context
    public var kAct = mAct as ScActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = getData.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getData.get(position))


    }

    inner class ViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.listitem_following_item, parent, false)
    ) {

        fun onBindViewHolder(item : MyFollowingAPI.MyFollowingList) {
            with(itemView) {

                ScImage.CircleImage(item.profile_image,profileView)
                nickLabel.setText(item.nickname)


                if(item.follow_confirm) {
                    followBtn.setBackgroundColor(resources.getColor(R.color.blackColor))
                    followBtn.setText("언팔")
                    followBtn.setTextColor(resources.getColor(R.color.whiteColor))
                }else {
                    followBtn.setBackgroundResource(R.drawable.grayborder_white_box)
                    followBtn.setText("팔로우")
                    followBtn.setTextColor(resources.getColor(R.color.borderLineColor))
                }

                followBtn.setOnClickListener {
                    kAct.disposable.add(kAct.apiService.FOLLOW_API(kAct.id_user,item.id_user)
                        .subscribeOn(kAct.io)
                        .observeOn(kAct.thread)
                        .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI>() {
                            override fun onError(e: Throwable?) {
                                kAct.ConnectionError()
                            }

                            override fun onSuccess(t: TrueFalseAPI) {
                                if(t.success) {

                                    getData.removeAt(adapterPosition)

                                    notifyDataSetChanged()
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