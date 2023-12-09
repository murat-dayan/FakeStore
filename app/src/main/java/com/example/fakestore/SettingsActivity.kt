package com.example.fakestore

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.fakestore.databinding.ActivitySettingsBinding
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var dbh:DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        dbh= DatabaseHelper(this)


        binding.toolbarSA.title = getString(R.string.settings)
        binding.toolbarSA.setBackgroundColor(getColor(R.color.main_color))
        setSupportActionBar(binding.toolbarSA)
        var mode= SqlDao().getNightMode(dbh)
        if (mode ==0){
            binding.buttonNightMode.setText(R.string.nightMode)
        }else{
            binding.buttonNightMode.setText(R.string.normalMode)
        }



        binding.buttonNightMode.setOnClickListener {
            updateAppLanguage()
            if (mode ==0){
                mode=1
                SqlDao().updateNightMode(dbh,mode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.buttonNightMode.setText(R.string.normalMode)
            }else{
                mode =0
                SqlDao().updateNightMode(dbh,mode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.buttonNightMode.setText(R.string.nightMode)

            }


        }

        
        binding.buttonTranslateEN.setOnClickListener {

            settingLanguage()
            updateAppLanguage()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun getCurrentLanguage(): String {
        val language= SqlDao().getLanguage(dbh)
        return language
    }

    private fun updateAppLanguage() {
        val currentLanguage = getCurrentLanguage()
        val locale = Locale(currentLanguage)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        println("updated language")
    }

    // Uygulama dilini kaydetmek için kullanılacak fonksiyon
    private fun saveLanguagePreference(language: String) {
        SqlDao().updateLanguage(dbh,language)
    }

    private fun settingLanguage(){
        val currentLanguage = getCurrentLanguage()
        val newLanguage = if (currentLanguage == "en") {
            "tr"
        } else {
            "en"
        }

        // Dil değişikliğini kaydet
        saveLanguagePreference(newLanguage)

        // Uygulama dilini güncelle
        updateAppLanguage()
    }



}