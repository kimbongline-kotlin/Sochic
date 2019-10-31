package com.sochic.sochic.OrderFolder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sochic.sochic.R
import com.sochic.sochic.Util.ScActivity
import kotlinx.android.synthetic.main.activity_order_delivery.*

class OrderDeliveryActivity : ScActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_delivery)
        supportActionBar!!.hide()
        backBtn.setOnClickListener {
            finish()
        }
    }
}
