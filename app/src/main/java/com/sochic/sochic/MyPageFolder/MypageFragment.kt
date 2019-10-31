package com.sochic.sochic.MyPageFolder


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sochic.sochic.MyPageFolder.API.MypageAPI
import com.sochic.sochic.OrderFolder.OrderListActivity
import com.sochic.sochic.R
import com.sochic.sochic.SettingFolder.FaqActivity
import com.sochic.sochic.SettingFolder.NoticeActivity
import com.sochic.sochic.SettingFolder.SettingActivity
import com.sochic.sochic.Util.PriceUtil
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.fragment_mypage.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MypageFragment : Fragment() {

    lateinit var mAct : Activity
    lateinit var mCon : Context
    lateinit var kAct : ScActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAct = activity!!
        mCon = context!!
        kAct = (mAct as ScActivity)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        settingBtn.setOnClickListener {

            kAct.goTo(SettingActivity::class.java)
        }

        alarmBtn.setOnClickListener {
            kAct.goTo(AlarmActivity::class.java )
        }
        getData()

        profileView.setOnClickListener {
            kAct.goTo(ProfileActivity::class.java)
        }
        followLabel.setOnClickListener {
            kAct.goTo(FollowingActivity::class.java)
        }

        inviteBtn.setOnClickListener {
            kAct.goTo(InviteActivity::class.java)
        }

        pointBtn.setOnClickListener {
            kAct.goTo(PointActivity::class.java)
        }

        couponBtn.setOnClickListener {
            kAct.goTo(CouponActivity::class.java)
        }

        noticeBtn.setOnClickListener {
            kAct.goTo(NoticeActivity::class.java)
        }

        faqBtn.setOnClickListener {
            kAct.goTo(FaqActivity::class.java)
        }

        addressBtn.setOnClickListener {
            kAct.goTo(AddressActivity::class.java)
        }

        cartBtn.setOnClickListener {
            startActivity(Intent(context,MyProductActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra("type","1"))
        }

        saveItemBtn.setOnClickListener {
            startActivity(Intent(context,MyProductActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra("type","2"))
        }

        orderBtn.setOnClickListener {
            kAct.goTo(OrderListActivity::class.java)
        }

        reviewBtn.setOnClickListener {
            kAct.goTo(ReviewActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
    fun getData() {
        kAct.disposable.add(kAct.apiService.MYPAGE_API(kAct.id_user)
            .subscribeOn(kAct.io)
            .observeOn(kAct.thread)
            .subscribeWith(object : DisposableSingleObserver<MypageAPI> () {
                override fun onError(e: Throwable?) {
                    kAct.ConnectionError()
                }

                override fun onSuccess(t: MypageAPI) {
                    if(t.success) {

                        ScImage.CircleImage(t.profile_image,profileView)
                        nicknameLabel.setText(t.nickname)
                        followLabel.setText("팔로잉 ${t.following_number}")
                        pointLabel.setText(PriceUtil.set("${t.point}") + "P")
                        preCntLabel.setText(PriceUtil.set("${t.pre_cnt}"))
                        paidCntLabel.setText(PriceUtil.set("${t.paid_cnt}"))
                        deliveryPreCntLabel.setText(PriceUtil.set("${t.delivery_pre_cnt}"))
                        deliveryIngCntLabel.setText(PriceUtil.set("${t.delivery_inf_cnt}"))
                        deliveryComCntLabel.setText(PriceUtil.set("${t.delievry_com_cnt}"))
                    }
                }
            }))
    }
}
