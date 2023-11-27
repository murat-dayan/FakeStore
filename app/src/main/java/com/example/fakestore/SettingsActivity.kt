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

        binding.editTextReminder.isFocusable = false
        binding.editTextReminder.isFocusableInTouchMode = false

        binding.toolbarSA.title = getString(R.string.settings)
        binding.toolbarSA.setBackgroundColor(getColor(R.color.main_color))
        setSupportActionBar(binding.toolbarSA)


        binding.checkBox.setOnCheckedChangeListener { compoundButton, b ->
            var mode=0
            if (b){
                mode=1
                SqlDao().updateNightMode(dbh,mode)
            }else{
                SqlDao().updateNightMode(dbh,mode)
            }


            if (b) {
                // Checkbox aktif ise gece moduna geç
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Checkbox aktif değilse gündüz moduna geç
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            settingLanguage()
        }

        
        binding.buttonTranslateEN.setOnClickListener {

            settingLanguage()
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