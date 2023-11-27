package com.example.fakestore

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.example.fakestore.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var dbh:DatabaseHelper
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        auth= FirebaseAuth.getInstance()



        dbh= DatabaseHelper(this@SplashActivity)
        SqlDao().addPrefs(dbh,"",0)


        checkAppLanguage()

        Handler().postDelayed({
            userLoggingControl()
        }, 300)


    }

    fun userLoggingControl(){
        auth.addAuthStateListener {
            val user= auth.currentUser
            if (user != null){
                val intent= Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }

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
        val language=SqlDao().getLanguage(dbh)
        return language
    }

    // Uygulama açıldığında dil ayarını kontrol etmek için kullanılacak fonksiyon
     fun checkAppLanguage() {
        val savedLanguage = loadLanguagePreference()
        if (savedLanguage != null) {
            val locale = Locale(savedLanguage)
            setAppLanguage(locale)
            println("cehecksplshdegişti")
        }
        println("checksplash degiştii")
    }
}