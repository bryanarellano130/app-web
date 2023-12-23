package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.Opciones_login.Login_facebook
import com.example.myapplication.Opciones_login.Login_gmail
import com.example.myapplication.databinding.ActivityOpcionesLoginBinding
import com.google.firebase.auth.FirebaseAuth

class OpcionesLogin : AppCompatActivity() {

    private lateinit var binding: ActivityOpcionesLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth= FirebaseAuth.getInstance()
        comprobarSesion()

        binding.IngresarFacebook.setOnClickListener {
            startActivity(Intent(this@OpcionesLogin, Login_facebook::class.java))

        }
        binding.IngresarGmail.setOnClickListener {
            startActivity(Intent(this@OpcionesLogin, Login_gmail::class.java))
        }
    }

    private fun comprobarSesion(){
        if (firebaseAuth.currentUser !=null){
            startActivity(Intent(this,MainActivity::class.java))
            finishAffinity()
        }
    }
}