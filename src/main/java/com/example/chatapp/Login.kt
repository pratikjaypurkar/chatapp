package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var editPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSighUp : Button

    private lateinit var  mAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()


        edtEmail =findViewById(R.id.edt_email)
        editPassword =findViewById(R.id.edt_password)
        btnLogin =findViewById(R.id.btnLogin)
        btnSighUp =findViewById(R.id.btnSignUp)

        btnSighUp.setOnClickListener{
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }


        btnLogin.setOnClickListener{
            val email= edtEmail.text.toString()
            val password=editPassword.text.toString()

            login(email,password)
        }


    }

    private fun login(email: String , password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for login in
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@Login,"Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }
}