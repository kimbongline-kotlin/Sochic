package com.sochic.sochic.LoginFolder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.log.Logger
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton
import com.sochic.sochic.HomeFolder.HomeActivity
import com.sochic.sochic.LoginFolder.API.LoginAPI
import com.sochic.sochic.R
import com.sochic.sochic.SplashFolder.StartGuideActivity
import com.sochic.sochic.Util.ScActivity
import com.sochic.sochic.Util.UserFolder.User
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.jvm.internal.Intrinsics

class LoginActivity : ScActivity() {

    private val OAUTH_CLIENT_ID = "i_0P8h1tiE0INBKgHTjI"
    private val OAUTH_CLIENT_NAME = "소식"
    private val OAUTH_CLIENT_SECRET = "LDxIFVEYya"

    var kakaoCallBacck: SessionCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()
        this.kakaoCallBacck = SessionCallback()
        Session.getCurrentSession().addCallback(this.kakaoCallBacck)

        goTo(StartGuideActivity::class.java)
        registerBtn.setOnClickListener {
            goTo(AgreeActivity::class.java)
        }

        instaBtn.setOnClickListener {
          //  goTo(SocialRegisterActivity::class.java)
        }

        findBtn.setOnClickListener {
            goTo(FindActivity::class.java)
        }

        kakaoBtn.setOnClickListener {

            com_kakao_login.callOnClick()
        }

        loginBtn.setOnClickListener {
//            finish()
//            goTo(HomeActivity::class.java)

            if(emailEdit.text.toString().isEmpty()) {
                ShowToast("이메일을 입력해주세요.")
            }else if(passEdit.text.toString().isEmpty()) {
                ShowToast("비밀번호를 입력해주세요.")
            }else {
                disposable.add(apiService.LOGIN_API(emailEdit.text.toString(),
                    passEdit.text.toString(),
                    "AOS",FirebaseInstanceId.getInstance().token.toString())
                    .subscribeOn(io)
                    .observeOn(thread)
                    .subscribeWith(object : DisposableSingleObserver<LoginAPI>(){
                        override fun onError(e: Throwable?) {
                            ConnectionError()
                        }

                        override fun onSuccess(t: LoginAPI) {
                            if(t.success) {
                                errorLabel.setText("")
                                errorLabel.visibility = View.INVISIBLE
                                var user = User(t.id_user)
                                appController.prefManager.storeUser(user)
                                finish()
                                goTo(HomeActivity::class.java)
                            }else{
                                errorLabel.setText("비밀번호가 일치하지않습니다.")
                                errorLabel.visibility = View.VISIBLE
                            }
                        }
                    }))


            }
        }

        initData()
        initView()
    }

    inner/* compiled from: LoginActivity.kt */ class SessionCallback : ISessionCallback {
        override fun onSessionOpened() {
            this@LoginActivity.requestMe()
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Toast.makeText(this@LoginActivity.applicationContext, exception.toString(), Toast.LENGTH_LONG)
                Logger.e(exception)
            }
        }
    }

    fun requestMe() {
        val keys = ArrayList<String>()
        keys.add("properties.nickname")
        keys.add("kakao_account.email")


        UserManagement.getInstance().me(keys,object : MeV2ResponseCallback() {
            override fun onSuccess(result: MeV2Response) {

                Log.d("object",result.toString())
                var kId = "k" + result.id
                //var kNIckname = result.properties.get("email")
                checkKakao(kId)


            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                redirectSignupActivity()
            }

        })

    }

    fun redirectSignupActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (!Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



    lateinit var mOAuthLoginButton: OAuthLoginButton
    @SuppressLint("HandlerLeak")

    lateinit var mOAuthLoginInstance: OAuthLogin
    val mOAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {

            val accessToken: String
            if (success) {
                accessToken = moAuthLoginInstance.getAccessToken(applicationContext)
                ProfileTask().execute(accessToken)
            } else {
            }
        }

    }

    var moAuthLoginButton: OAuthLoginButton
        get() {
            val oAuthLoginButton = this.mOAuthLoginButton
            return oAuthLoginButton
        }
        set(value) {
            this.mOAuthLoginButton = value
        }

    var moAuthLoginInstance: OAuthLogin
        get() {
            val oAuthLogin = this.mOAuthLoginInstance
            return oAuthLogin
        }
        set(value) {

            this.mOAuthLoginInstance = value
        }

    inner class ProfileTask : AsyncTask<String, Void, String>() {
        private var result = ""

        fun getResult(): String {
            return this.result
        }

        fun setResult(valu: String) {
            Intrinsics.checkParameterIsNotNull(valu, "valu")
            this.result = valu
        }

        override fun doInBackground(vararg strings: String): String? {
            Intrinsics.checkParameterIsNotNull(strings, "strings")
            val token = strings
            var header: StringBuilder = StringBuilder()
            header.append("Bearer ")
            header.append(token.get(0))
            header = header
            try {
                var con: HttpURLConnection? = URL("https://openapi.naver.com/v1/nid/me").openConnection() as HttpURLConnection?
                    ?: throw TypeCastException("null cannot be cast to non-null type java.net.HttpURLConnection")
                val br: BufferedReader
                con = con
                con!!.requestMethod = "GET"
                con.setRequestProperty("Authorization", header.toString())
                if (con.responseCode == 200) {
                    br = BufferedReader(InputStreamReader(con.inputStream))
                } else {
                    br = BufferedReader(InputStreamReader(con.errorStream))
                }
                val response = StringBuffer()
                response.append(br.readLine())
                val stringBuffer = response.toString()
                this.result = stringBuffer
                br.close()

                return this.result
            } catch (e: Exception) {
                println(e)

            }
            return "error"

        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            try {

                val `object` = JSONObject(this.result)
                if (Intrinsics.areEqual(`object`.getString("resultcode"), "00")) {
                    val jsonObject = JSONObject(`object`.getString("response"))
                    val naverId = StringBuilder()
                    naverId.append("n")
                    naverId.append(jsonObject.getString("id"))
                    var nId = naverId.toString()
                    checkNaver(nId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun initData() {
        var instance: OAuthLogin? = OAuthLogin.getInstance()
        this.mOAuthLoginInstance = instance!!
        instance = this.mOAuthLoginInstance
        val context = applicationContext
        instance.init(context, this.OAUTH_CLIENT_ID, this.OAUTH_CLIENT_SECRET, this.OAUTH_CLIENT_NAME)
    }

    private fun initView() {
        val findViewById = findViewById<View>(R.id.buttonOAuthLoginImg)

        this.mOAuthLoginButton = findViewById as OAuthLoginButton
        val oAuthLoginButton = this.mOAuthLoginButton
        oAuthLoginButton.setOAuthLoginHandler(this.mOAuthLoginHandler)
        naverBtn.setOnClickListener {

            mOAuthLoginInstance.startOauthLoginActivity(this,this.mOAuthLoginHandler)
        }
    }

    fun checkKakao(snsId : String ) {
        disposable.add(apiService.KAKAO_CHECK_API(snsId,"AOS","")
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<LoginAPI>() {
                override fun onError(e: Throwable?) {

                    ConnectionError()
                }

                override fun onSuccess(t: LoginAPI) {

                    if(t.success) {
                        var user = User(t.id_user)
                        appController.prefManager.storeUser(user)
                        finish()
                        goTo(HomeActivity::class.java)

                    }else{
                        var intent = Intent(applicationContext,AgreeActivity::class.java)
                        intent.putExtra("type","k")
                        intent.putExtra("snsId", snsId)

                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(intent)
                    }
                }
            }))
    }

    fun checkNaver(snsId : String ) {
        disposable.add(apiService.NAVER_CHECK_API(snsId,"AOS","")
            .subscribeOn(io)
            .observeOn(thread)
            .subscribeWith(object : DisposableSingleObserver<LoginAPI>() {
                override fun onError(e: Throwable?) {

                    ConnectionError()
                }

                override fun onSuccess(t: LoginAPI) {

                    if(t.success) {
                        var user = User(t.id_user)
                        appController.prefManager.storeUser(user)
                        finish()
                        goTo(HomeActivity::class.java)

                    }else{
                        var intent = Intent(applicationContext,AgreeActivity::class.java)
                        intent.putExtra("type","n")
                        intent.putExtra("snsId", snsId)

                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(intent)
                    }
                }
            }))
    }
}
