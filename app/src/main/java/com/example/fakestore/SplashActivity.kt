package com.example.fakestore

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import androidx.databinding.DataBindingUtil
import com.example.fakestore.databinding.ActivitySplashBinding
import java.util.*

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_splash)


        checkAppLanguage()

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        },500)


    }

    // Uygulama dilini ayarlamak için kullanılacak fonksiyon
    private fun setAppLanguage(locale: Locale) {
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }



    // Uygulama dilini yüklemek için kullanılacak fonksiyon
    private fun loadLanguagePreference(): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        return sharedPreferences.getString("language", null)
    }

    // Uygulama açıldığında dil ayarını kontrol etmek için kullanılacak fonksiyon
    private fun checkAppLanguage() {
        val savedLanguage = loadLanguagePreference()
        if (savedLanguage != null) {
            val locale = Locale(savedLanguage)
            setAppLanguage(locale)
        }
    }
}