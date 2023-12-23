package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.myapplication.databinding.ActivityRegistroGmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Registro_gmail : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroGmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistroGmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()

        progressDialog= ProgressDialog(this)

        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.BtnRegistrar.setOnClickListener{
            validarInfo()
        }
    }

    private var gmail= ""
    private var contraseña=""
    private var r_contraseña=""
    private fun validarInfo() {
        gmail= binding.EtGmail.text.toString().trim()
        contraseña= binding.EtContraseA.text.toString().trim()
        r_contraseña= binding.EtRContraseA.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(gmail).matches()){
            binding.EtGmail.error="Gmail inválido"
            binding.EtGmail.requestFocus()
        }
        else if (gmail.isEmpty()){
            binding.EtGmail.error="Ingrese Gmail"
            binding.EtGmail.requestFocus()
        }
        else if (contraseña.isEmpty()){
            binding.EtContraseA.error="Ingrese contraseña"
            binding.EtRContraseA.requestFocus()
        }
        else if (r_contraseña.isEmpty()){
            binding.EtRContraseA.error="Repita la contraseña"
            binding.EtRContraseA.requestFocus()
        }
        else if (contraseña!=r_contraseña){
            binding.EtRContraseA.error="La contraseña no coincide"
            binding.EtRContraseA.requestFocus()
        }

        else{
            registrarUsuario()
        }

    }
    private fun registrarUsuario() {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(gmail, contraseña)
            .addOnSuccessListener {
                llenarInfoBD()

            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this,"No se registro el usuario debido a ${e.message}",
                    Toast.LENGTH_SHORT).show()

            }
    }

    private fun llenarInfoBD() {
        progressDialog.setMessage("Guardando información")

        val tiempo= Constantes.obtenerTiempoDis()
        val gmailUsuario= firebaseAuth.currentUser!!.email
        val uidUsuario= firebaseAuth.uid

        val hashMap= HashMap<String, Any>()
        hashMap["nombres"]=""
        hashMap["codigoTelefono"]=""
        hashMap ["telefono"]=""
        hashMap ["urlImagenPer"]=""
        hashMap ["proveedor"]="Gmail"
        hashMap ["escribiendo"]=""
        hashMap ["tiempo"]= tiempo
        hashMap ["online"]=true
        hashMap["gmail"]="${gmailUsuario}"
        hashMap ["uid"]="${uidUsuario}"
        hashMap ["fechaNacimiento"]=""

        val ref= FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(uidUsuario!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this,MainActivity::class.java))
                finishAffinity()

            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"No se registro debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }
}