package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.myapplication.databinding.ActivityRegistroFacebookBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class Registro_facebook : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroFacebookBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistroFacebookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()

        progressDialog= ProgressDialog(this)

        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.BtnRegistrar.setOnClickListener{
            validarInfo()
        }
    }

    private var facebook= ""
    private var contraseña=""
    private var r_contraseña=""
    private fun validarInfo() {
        facebook= binding.EtFacebook.text.toString().trim()
        contraseña= binding.EtContraseA.text.toString().trim()
        r_contraseña= binding.EtRContraseA.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(facebook).matches()){
            binding.EtFacebook.error="Facebook inválido"
            binding.EtFacebook.requestFocus()
        }
        else if (facebook.isEmpty()){
            binding.EtFacebook.error="Ingrese Facebook"
            binding.EtFacebook.requestFocus()
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

        firebaseAuth.createUserWithEmailAndPassword(facebook, contraseña)
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
        val facebookUsuario= firebaseAuth.currentUser!!.email
        val uidUsuario= firebaseAuth.uid

        val hashMap= HashMap<String, Any>()
        hashMap["nombres"]=""
        hashMap["codigoTelefono"]=""
        hashMap ["telefono"]=""
        hashMap ["urlImagenPer"]=""
        hashMap ["proveedor"]="Facebook"
        hashMap ["escribiendo"]=""
        hashMap ["tiempo"]= tiempo
        hashMap ["online"]=true
        hashMap["facebook"]="${facebookUsuario}"
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