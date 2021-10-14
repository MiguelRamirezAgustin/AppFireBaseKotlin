package com.ejemplo.projectuserfirebase.EmployesApp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ejemplo.projectuserfirebase.DashboardActivity
import com.ejemplo.projectuserfirebase.R
import com.ejemplo.projectuserfirebase.Utilities.Utils
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.inputPassword
import kotlinx.android.synthetic.main.activity_login_employes.*

class LoginEmployesActivity : AppCompatActivity() {

    private lateinit var referenceDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_employes)
        supportActionBar?.hide()


        btnLoginEmployes.setOnClickListener {
            if(inputEmailEmployes.text!!.isNotEmpty()   && inputPasswordEmployes.text!!.isNotEmpty()){
                val _email = inputEmailEmployes.text.toString().trim()
                if(Utils.validateEmal((_email))){
                    referenceDatabase = FirebaseDatabase.getInstance().getReference("users")
                    referenceDatabase.orderByChild("email").equalTo(_email)
                    referenceDatabase.orderByChild("password").equalTo(inputPasswordEmployes.text.toString())
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if(dataSnapshot.exists()){
                                    println("Datos consultados-->"+ dataSnapshot)
                                    var key = ""
                                    dataSnapshot!!.children.forEach {
                                        key = it.key.toString()
                                    }
                                    println("Datos consultados-->"+ key)
                                    val _intent = Intent(applicationContext, CheckEmployesActivity::class.java)
                                    _intent.putExtra("key", key)
                                    startActivity(_intent)
                                    finish()
                                }else{
                                    showToas("Error al ingresar el usuario no existe")
                                }
                            }
                            override fun onCancelled(databaseError: DatabaseError) {
                                showToas("Error al ingresar intente mas tarde")
                            }
                        })
                }else{
                    showToas("El correo es invalido")
                }
            }else{
                showToas("Los campos son requeridos")
            }
        }
    }

    // fun reusable toas
    private fun showToas(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}