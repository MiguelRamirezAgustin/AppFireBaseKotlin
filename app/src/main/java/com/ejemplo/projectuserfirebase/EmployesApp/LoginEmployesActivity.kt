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
                var email = inputEmailEmployes.text.toString().trim()
                println("valida correo  "+ email )
                if(Utils.validateEmal((email))){
                    println("Datos para consulta login Correo: "+ email +" Password: "+inputPasswordEmployes.text.toString())
                    referenceDatabase = FirebaseDatabase.getInstance().getReference("users")
                    referenceDatabase.orderByChild("email").equalTo(email)
                    referenceDatabase.orderByChild("password").equalTo(inputPasswordEmployes.text.toString())
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if(dataSnapshot.exists()){
                                    println("Datos consultados Login "+ dataSnapshot)
                                    var key = ""
                                    dataSnapshot!!.children.forEach {
                                        if(it.child("email").getValue().toString().trim() == email){
                                            key = it.key.toString()
                                            println("Datos consultados-->"+ key)
                                            val _intent = Intent(applicationContext, CheckEmployesActivity::class.java)
                                            _intent.putExtra("key", key)
                                            startActivity(_intent)
                                            finish()
                                            return
                                        }
                                    }

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