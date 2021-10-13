package com.ejemplo.projectuserfirebase

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ejemplo.projectuserfirebase.Activitys.ReusableActivity
import com.ejemplo.projectuserfirebase.Utilities.Utils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_new_user.*
import kotlinx.android.synthetic.main.activity_new_user.etEmail
import kotlinx.android.synthetic.main.activity_new_user.etPassword

class NewUserActivity : AppCompatActivity() {
    // inportacion de Metodo de firebase
    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        supportActionBar?.hide()
        // Initialize Firebase Auth
        auth= FirebaseAuth.getInstance();

        btnCreateUser.setOnClickListener {
            if(etPassword.text!!.isNotEmpty() && etEmail.text!!.isNotEmpty()){ //->Validacion de campos email y password
                val _email = etEmail.text.toString().replace(" ", "")
                if(Utils.validateEmal((_email.replace(" ", "")))){
                   auth.createUserWithEmailAndPassword(_email, etPassword.text.toString()).addOnCompleteListener { task ->
                       if(task.isSuccessful){
                           val user = auth.currentUser
                           println("usuario Creado "+ user)
                           etEmail.setText("");
                           etPassword.setText("");
                           val _intent = Intent(this, ReusableActivity::class.java)
                           _intent.putExtra("user", user!!.email )
                           startActivity(_intent)
                       }
                   }.addOnFailureListener { exception ->
                       //Error al crear usuario
                       println("Error al crear usuario---------->"+ exception)
                       Toast.makeText(applicationContext,"Error al realizar la operacion",Toast.LENGTH_LONG).show();
                   }
               }else{
                   Toast.makeText(this,"Correo invalido", Toast.LENGTH_LONG).show();
               }
            }else{
                Toast.makeText(this,"Los campis sin requeridos", Toast.LENGTH_LONG).show();
            }
        }


        imgBack.setOnClickListener{
            val _intent = Intent(this, DashboardActivity::class.java)
            startActivity(_intent)
            finish()
        }

    }


}