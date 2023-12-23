package com.example.myapplication.Opciones_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.Registro_facebook
import com.example.myapplication.databinding.ActivityLoginFacebookBinding

class Login_facebook : AppCompatActivity() {
    private lateinit var binding: ActivityLoginFacebookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginFacebookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.TxtRegistrar.setOnClickListener{
            startActivity(Intent(this@Login_facebook, Registro_facebook:: class.java))
        }
    }
}