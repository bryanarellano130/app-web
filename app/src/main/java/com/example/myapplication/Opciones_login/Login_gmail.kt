package com.example.myapplication.Opciones_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.Registro_gmail
import com.example.myapplication.databinding.ActivityLoginGmailBinding

class Login_gmail : AppCompatActivity() {
    private lateinit var binding: ActivityLoginGmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginGmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.TxtRegistrar.setOnClickListener{
            startActivity(Intent(this@Login_gmail, Registro_gmail:: class.java))
        }
    }
}