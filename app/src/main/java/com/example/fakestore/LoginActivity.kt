package com.example.fakestore

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.fakestore.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)

        auth = FirebaseAuth.getInstance()



        binding.editTextEmail.setSingleLine(true)
        binding.editTextPassword.setSingleLine(true)
        binding.editTextPassword.inputType= InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.editTextEmail.inputType= InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        closeLottieWhenKeyboardOpen()





        binding.buttonLogIn.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                signIn(email, password)
            } else {
                Toast.makeText(this, "lütfen boş yerleri doldurunuz", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonSignUp.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signUp(email, password)
            } else {
                Toast.makeText(this, "lütfen boş yerleri doldurunuz", Toast.LENGTH_LONG).show()
            }
        }

        binding.forgetPasswordText.setOnClickListener {
            val builder= AlertDialog.Builder(this)
            builder.setTitle("FAKESTORE")
            builder.setMessage("Şifre sıfırlama bağlantısı e mail adresinize gönderilecektir.")
            builder.setPositiveButton("Gönder"){ dialog,which->
                val email = binding.editTextEmail.text.toString().trim()

                if (email.isEmpty()) {
                    Toast.makeText(this, "Lütfen e-posta adresinizi girin", Toast.LENGTH_SHORT).show()
                } else {
                    resetPassword(email)
                }
            }
            builder.setNegativeButton("İptal"){dialog,which->
            }
            val dialog= builder.create()
            dialog.show()
        }


    }

    fun resetPassword(email:String){

        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this,"Şifre sıfırlama bağlantısı gönderildi",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Şifre sıfırlama bağlantısı başarısız",Toast.LENGTH_LONG).show()
            }
        }

    }





    fun signIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = auth.currentUser
                println(user?.email)
                val intent= Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("userMail",user?.email)
                startActivity(intent)
                finish()
            } else {
                val errorMessage = it.exception?.message ?: "An unknown error occurred"
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }

    }

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {
                Toast.makeText(this, "Kayıt Başarılı", Toast.LENGTH_LONG).show()
            } else {
                val errorMessage = it.exception?.message ?: "An unknown error occurred"

                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }

        }
    }

    fun closeLottieWhenKeyboardOpen() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val rect = Rect()
                binding.root.getWindowVisibleDisplayFrame(rect)
                val screenHeight = binding.root.height
                val keypadHeight = screenHeight - rect.bottom

                if (keypadHeight > screenHeight * 0.15) {
                    // Klavye açıldığında yapılacak işlemler
                    binding.lottieView.visibility = View.GONE
                } else {
                    // Klavye kapandığında yapılacak işlemler
                    binding.lottieView.visibility = View.VISIBLE
                }
            }
        })
    }
}