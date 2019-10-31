package com.sochic.sochic.SettingFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.activity_sc_web.*

class ScWebActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sc_web)
        supportActionBar!!.hide()

        backBtn.setOnClickListener {
            finish()
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {

                view!!.loadUrl(request!!.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        webView.loadUrl(intent.getStringExtra("url"))
        titleLabel.setText(intent.getStringExtra("title"))
    }
}
