package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtName : EditText
    private lateinit var edtEmail: EditText
    private lateinit var editPassword : EditText
    private lateinit var btnSighUp : Button
    private lateinit var mDbRef:DatabaseReference

    private lateinit var  mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sig_up)

        supportActionBar?.hide()
        mAuth= FirebaseAuth.getInstance()

        edtName =findViewById(R.id.edt_name)
        edtEmail =findViewById(R.id.edt_email)
        editPassword =findViewById(R.id.edt_password)
        btnSighUp =findViewById(R.id.btnSignUp)

        btnSighUp.setOnClickListener{
            val name=edtName.text.toString()
            val email =edtEmail.text.toString()
            val password=editPassword.text.toString()

            signUp(name,email,password)
        }
    }

    private fun signUp(name:String,email:String ,password: String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    // code for jumping home
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                   Toast.makeText(this@SignUp,"Some error occurred",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name:String, email:String, uid:String){
        mDbRef=FirebaseDatabase.getInstance().reference

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}