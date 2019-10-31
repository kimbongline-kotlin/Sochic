package com.sochic.sochic.SplashFolder

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.sochic.sochic.HomeFolder.HomeActivity
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.LoginFolder.LoginActivity
import com.sochic.sochic.R
import com.sochic.sochic.SplashFolder.API.PopupAPI
import com.sochic.sochic.Util.GlideImageLoader
import com.youth.banner.BannerConfig
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_start_popup.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StartPopupActivity : ScActivity() {


    var settingsShrepref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    private val PREF_NAME = "sc_popup_today_check"
    val KEY_TODAY_DATE_ANDTIME = "TimeToday"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_popup)
        supportActionBar!!.hide()


        /**
         * 팝업 설정및 불러오기.
         */
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        var TodayDate_String = dateFormat.format(date)


        settingsShrepref = getSharedPreferences(PREF_NAME, 0)
        editor = settingsShrepref!!.edit()

        val Val = settingsShrepref!!.getString(KEY_TODAY_DATE_ANDTIME, "")


        closeBtn.setOnClickListener {
            if(todayRadioBtn.isSelected) {
                editor!!.putString(KEY_TODAY_DATE_ANDTIME, TodayDate_String)
                editor!!.commit()
            }

            finish()
            if(LoginCheck()) {
                goTo(HomeActivity::class.java)
            }else {
                goTo(LoginActivity::class.java)
            }

        }

        todayRadioBtn.setOnClickListener {
            todayRadioBtn.isSelected = !todayRadioBtn.isSelected
        }
        getData()
    }

    fun getData() {
        disposable.add(apiService.POPUP_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<PopupAPI>() {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: PopupAPI) {
                    if(t.success) {

                        var list = ArrayList<String>()
                        for (i in 0..t.response.size) {
                            if(i == t.response.size) {


                                sImageView.setImageLoader(GlideImageLoader())
                                sImageView.setImages(list)
                                sImageView.isAutoPlay(false)
                                sImageView.setBannerStyle(BannerConfig.NOT_INDICATOR)
                                sImageView.start()

                                sIndicatorView.count = t.response.size
                                sImageView.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                                    override fun onPageSelected(position: Int) {
                                        sIndicatorView.setSelected(position)
                                    }

                                    override fun onPageScrollStateChanged(state: Int) {

                                    }

                                    override fun onPageScrolled(
                                        position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int
                                    ) {

                                    }
                                })
                            }else {
                                list.add(t.response.get(i).image)
                            }
                        }

                    }
                }
            }))
    }
}
