package com.sochic.sochic.SplashFolder

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.sochic.sochic.BrandFolder.BrandHomeActivity
import com.sochic.sochic.HomeFolder.HomeActivity
import com.sochic.sochic.LoginFolder.LoginActivity
import com.sochic.sochic.LoginFolder.PassResetActivity
import com.sochic.sochic.ProductFolder.ProductMainActivity
import com.sochic.sochic.R
import com.sochic.sochic.SplashFolder.API.SplashAPI
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.ScImage
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ScActivity() {

    var settingsShrepref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    private val PREF_NAME = "sc_popup_today_check"
    val KEY_TODAY_DATE_ANDTIME = "TimeToday"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        disposable.add(apiService.SPLASH_API()
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object  : DisposableSingleObserver<SplashAPI> () {
                override fun onError(e: Throwable?) {

                }

                override fun onSuccess(t: SplashAPI) {
                    if(t.success) {
                        ScImage.image(t.image,imageView)
                    }
                }
            }))
        printHashKey(applicationContext)
        supportActionBar!!.hide()

        if(intent.action != "android.intent.action.VIEW") {
            actApps()
        }else {
            FirebaseDynamicLinks.getInstance().getDynamicLink(intent).addOnSuccessListener {

                if(it != null) {
                    var getUri = it.link
                    var getType  = getUri.getQueryParameter("type")
                    var getIdx = getUri.getQueryParameter("idx")
                    Log.d("object",getType)
                    Log.d("object",getIdx)
                    if(getType.toString().equals("find")) {
                        finish()
                        startActivity(Intent(applicationContext,PassResetActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            .putExtra("idx",getIdx))
                    }else if(getType.toString().equals("product")) {
                        finish()
                        if(LoginCheck()) {
                            startActivity(Intent(applicationContext,ProductMainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                .putExtra("idx",getIdx))
                        }else {
                            startActivity(Intent(applicationContext,LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                        }
                    }else if(getType.toString().equals("brand")) {
                        finish()
                        if(LoginCheck()) {
                            startActivity(Intent(applicationContext,BrandHomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                .putExtra("id_user",getIdx))
                        }else {
                            startActivity(Intent(applicationContext,LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                        }
                    }
                }else {


                }

            }.addOnFailureListener {

            }
        }





    }

    fun actApps() {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        var TodayDate_String = dateFormat.format(date)


        settingsShrepref = getSharedPreferences(PREF_NAME, 0)
        editor = settingsShrepref!!.edit()

        val Val = settingsShrepref!!.getString(KEY_TODAY_DATE_ANDTIME, "")
        if (Val != TodayDate_String) {

            try {
                Handler().postDelayed({
                    finish()
                    goTo(StartPopupActivity::class.java)
                }, 1000)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {


            if (LoginCheck()) {
                Handler().postDelayed({
                    finish()
                    goTo(HomeActivity::class.java)
                }, 1000)
            } else {
                Handler().postDelayed({
                    finish()
                    goTo(LoginActivity::class.java)
                }, 1000)
            }


        }
    }
}
