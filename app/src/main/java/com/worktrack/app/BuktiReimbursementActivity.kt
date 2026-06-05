package com.worktrack.app

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class BuktiReimbursementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_bukti_reimbursement
        )

        val imgBukti =
            findViewById<ImageView>(
                R.id.imgBukti
            )

        val url =
            intent.getStringExtra(
                "url_bukti"
            )

        Glide.with(this)
            .load(url)
            .into(imgBukti)
    }
}