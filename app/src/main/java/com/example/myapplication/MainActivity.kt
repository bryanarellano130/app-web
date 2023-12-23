package com.example.myapplication

import android.adservices.customaudience.TrustedBiddingData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.Fragmentos.FragmentAnuncios
import com.example.myapplication.Fragmentos.FragmentChats
import com.example.myapplication.Fragmentos.FragmentCuenta
import com.example.myapplication.Fragmentos.FragmentInicio
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()
        comprobarSesion()

        verFragmentInicio()

        binding.BottomNV.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.Item_Inicio->{
                    verFragmentInicio()
                    true
                }
                R.id.Item_Chats->{
                    verFragmentChats()
                    true
                }
                R.id.Item_Anuncios->{
                    verFragmentAnuncios()
                    true
                }
                R.id.Item_Cuenta->{
                    verFragmentCuenta()
                    true
                }
                else->{
                    false
                }
            }

        }


        }

    private fun comprobarSesion(){
        if (firebaseAuth.currentUser== null){
            startActivity(Intent(this,OpcionesLogin::class.java))
            finishAffinity()
        }
    }
    private fun verFragmentInicio(){
        binding.TituloRL.text="Inicio"
        val fragment= FragmentInicio()
        val fragmentTransition= supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment.id, fragment, "FragmentInicio")
        fragmentTransition.commit()

    }
    private fun verFragmentChats(){
        binding.TituloRL.text="Chats"
        val fragment= FragmentChats()
        val fragmentTransition= supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment.id, fragment, "FragmentChats")
        fragmentTransition.commit()

    }
    private fun verFragmentAnuncios(){
        binding.TituloRL.text="Anuncios"
        val fragment= FragmentAnuncios()
        val fragmentTransition= supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment.id, fragment, "FragmentAnuncios")
        fragmentTransition.commit()

    }
    private fun verFragmentCuenta(){
        binding.TituloRL.text="Cuenta"
        val fragment= FragmentCuenta()
        val fragmentTransition= supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment.id, fragment, "FragmentCuenta")
        fragmentTransition.commit()

    }
    }
