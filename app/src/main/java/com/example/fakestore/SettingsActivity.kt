package com.example.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.fakestore.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_settings)

        binding.toolbarSA.title= getString(R.string.settings)
        binding.toolbarSA.setBackgroundColor(getColor(R.color.dark_blue))

        setSupportActionBar(binding.toolbarSA)


    }
}