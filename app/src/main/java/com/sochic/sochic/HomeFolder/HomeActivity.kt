package com.sochic.sochic.HomeFolder

import android.os.Bundle
import com.sochic.sochic.BrandFolder.BrandFragment
import com.sochic.sochic.HomeFolder.Fragment.HomeFragment
import com.sochic.sochic.MyPageFolder.MypageFragment
import com.sochic.sochic.R
import com.sochic.sochic.SearchFolder.SearchFragment
import com.sochic.sochic.SearchFolder.SearchSubFragment
import com.sochic.sochic.SellerFolder.Fragment.SellerMyPageFragment
import com.sochic.sochic.Util.ApiMannager.TrueFalseAPI
import com.sochic.sochic.Util.ScActivity
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : ScActivity() {


    lateinit var fm: androidx.fragment.app.FragmentManager
    lateinit var mFramgent: androidx.fragment.app.Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar!!.hide()

        homeBtn.setOnClickListener {
            val supportFragmentManager = supportFragmentManager
            this.fm = supportFragmentManager
            mFramgent = HomeFragment()
            val ft = fm
                .beginTransaction()
                .replace(R.id.frameLayer, mFramgent )
            if (!fm.isStateSaved) {
                ft.commit()
            } else {
                ft.commitAllowingStateLoss()
            }

            homeIcon.isSelected = true
            brandIcon.isSelected = false
            searchIcon.isSelected = false
            mypageIcon.isSelected = false
        }

        brandBtn.setOnClickListener {
            val supportFragmentManager = supportFragmentManager
            this.fm = supportFragmentManager
            mFramgent = BrandFragment()
            val ft = fm
                .beginTransaction()
                .replace(R.id.frameLayer, mFramgent )
            if (!fm.isStateSaved) {
                ft.commit()
            } else {
                ft.commitAllowingStateLoss()
            }

            homeIcon.isSelected = false
            brandIcon.isSelected = true
            searchIcon.isSelected = false
            mypageIcon.isSelected = false
        }

        searchBtn.setOnClickListener {
            val supportFragmentManager = supportFragmentManager
            this.fm = supportFragmentManager
            mFramgent = SearchFragment()
            val ft = fm
                .beginTransaction()
                .replace(R.id.frameLayer, mFramgent )
            if (!fm.isStateSaved) {
                ft.commit()
            } else {
                ft.commitAllowingStateLoss()
            }

            homeIcon.isSelected = false
            brandIcon.isSelected = false
            searchIcon.isSelected = true
            mypageIcon.isSelected = false
        }

        disposable.add(apiService.SELLER_CHECK_API(id_user)
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<TrueFalseAPI> () {
                override fun onError(e: Throwable?) {
                    ConnectionError()
                }

                override fun onSuccess(t: TrueFalseAPI) {
                    if(t.success) {
                        mypageBtn.setOnClickListener {
                            val supportFragmentManager = supportFragmentManager
                            fm = supportFragmentManager
                            mFramgent = SellerMyPageFragment()
                            val ft = fm
                                .beginTransaction()
                                .replace(R.id.frameLayer, mFramgent )
                            if (!fm.isStateSaved) {
                                ft.commit()
                            } else {
                                ft.commitAllowingStateLoss()
                            }

                            homeIcon.isSelected = false
                            brandIcon.isSelected = false
                            searchIcon.isSelected = false
                            mypageIcon.isSelected = true
                        }
                    }else {
                        mypageBtn.setOnClickListener {
                            val supportFragmentManager = supportFragmentManager
                            fm = supportFragmentManager
                            mFramgent = MypageFragment()
                            val ft = fm
                                .beginTransaction()
                                .replace(R.id.frameLayer, mFramgent )
                            if (!fm.isStateSaved) {
                                ft.commit()
                            } else {
                                ft.commitAllowingStateLoss()
                            }

                            homeIcon.isSelected = false
                            brandIcon.isSelected = false
                            searchIcon.isSelected = false
                            mypageIcon.isSelected = true
                        }
                    }
                }
            }))


        homeBtn.callOnClick()
    }

    public fun searchSubFragment(keyword : String) {
        val supportFragmentManager = supportFragmentManager
        this.fm = supportFragmentManager
        mFramgent = SearchSubFragment()
        val ft = fm
            .beginTransaction()
            .replace(R.id.frameLayer, mFramgent )
        if (!fm.isStateSaved) {
            ft.commit()
            (mFramgent as SearchSubFragment).keyword = keyword
        } else {
            ft.commitAllowingStateLoss()
        }


        homeIcon.isSelected = false
        brandIcon.isSelected = false
        searchIcon.isSelected = true
        mypageIcon.isSelected = false
    }

    public fun searchBackAction() {
        searchBtn.callOnClick()
    }

}
