package com.example.fakestore

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.preference.PreferenceManager.getDefaultSharedPreferences
import androidx.databinding.DataBindingUtil
import com.example.fakestore.databinding.ActivitySettingsBinding
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)


        binding.toolbarSA.title = getString(R.string.settings)
        binding.toolbarSA.setBackgroundColor(getColor(R.color.dark_blue))
        setSupportActionBar(binding.toolbarSA)

        binding.buttonTranslateEN.setOnClickListener {
            val locale = if (getCurrentLanguage() == "en") {
                Locale("tr")
            } else {
                Locale("en")
            }
            updateAppLanguage(locale)
            recreate()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun getCurrentLanguage(): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        return sharedPreferences.getString("language", "en") ?: "en"
    }

    private fun updateAppLanguage(locale: Locale) {
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        saveLanguagePreference(locale)
    }

    // Uygulama dilini kaydetmek için kullanılacak fonksiyon
    private fun saveLanguagePreference(locale: Locale) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()
        editor.putString("language", locale.language)
        editor.apply()
    }

}