package com.sochic.sochic.MyPageFolder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.activity_search_address.*

class SearchAddressActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_address)
        supportActionBar!!.hide()
        backBtn.setOnClickListener {
            finish()
        }

        init_webView()
    }

    fun init_webView() {
        webView.getSettings().setJavaScriptEnabled(true)
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)
        webView.addJavascriptInterface(AndroidBridge(), "Address")
        webView.setWebChromeClient(WebChromeClient())
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.loadUrl("javascript:sample2_execDaumPostcode();")
            }
        }
        webView.loadUrl("http://sochic.app-pick.com/app/daum.html")
    }



    private inner class AndroidBridge() {

        @JavascriptInterface
        fun setAddress(str: String, str2: String, str3: String) {
            val intent = Intent()
            intent.putExtra("arg1", str)
            intent.putExtra("arg2", str2)
            intent.putExtra("arg3", str3)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
