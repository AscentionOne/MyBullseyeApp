package com.kenchen.bullseye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kenchen.bullseye.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        title = getString(R.string.about_page_title)

        // back button hit
        binding.goBackButton?.setOnClickListener {
            finish()
        }
    }
}