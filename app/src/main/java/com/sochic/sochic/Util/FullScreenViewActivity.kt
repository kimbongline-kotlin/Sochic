package com.sochic.sochic.Util

import android.os.Bundle
import com.sochic.sochic.R
import kotlinx.android.synthetic.main.activity_full_screen_view.*

class FullScreenViewActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_view)

        supportActionBar!!.hide()
        var adapter = FullScreenImageAdapter(this,intent.getStringArrayListExtra("images"))
        viewpager.adapter = adapter
    }
}
