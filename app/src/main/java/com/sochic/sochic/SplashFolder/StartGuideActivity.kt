package com.sochic.sochic.SplashFolder

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.sochic.sochic.R
import com.sochic.sochic.SplashFolder.API.GuideAPI
import com.sochic.sochic.SplashFolder.Adapter.StartGuideAdapter
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_start_guide.*


class StartGuideActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_guide)
        supportActionBar!!.hide()

        startBtn.setOnClickListener {
            finish()

        }

        getData()
    }

    fun getData() {
        disposable.add(apiService.GUIDE_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<GuideAPI> () {

                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: GuideAPI) {
                    if(t.success) {
                        var list = ArrayList<String>()
                        for (i in 0..t.response.size) {
                            if(i == t.response.size) {

                                var adpater = StartGuideAdapter(this@StartGuideActivity,list)

                                viewpager.adapter = adpater
                                viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                                    override fun onPageScrollStateChanged(state: Int) {

                                    }

                                    override fun onPageScrolled(
                                        position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int
                                    ) {

                                    }

                                    override fun onPageSelected(position: Int) {
                                        sIndicatorView.setSelected(position)
                                        if(position == t.response.size-1) {
                                            startBtn.visibility = View.VISIBLE
                                        }else {
                                            startBtn.visibility = View.INVISIBLE
                                        }
                                    }
                                })
                                sIndicatorView.count = t.response.size

                            }else {
                                list.add(t.response.get(i).image)
                             //   list.add(t.response.get(i).image)
                            }
                        }
                    }
                }
            }))
    }
}
