package com.example.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.fakestore.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_favorite)

        binding.toolbarFA.title= getString(R.string.favorites)
        binding.toolbarFA.setBackgroundColor(getColor(R.color.dark_blue))

        setSupportActionBar(binding.toolbarFA)



    }
}