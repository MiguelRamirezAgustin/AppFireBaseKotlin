package com.ejemplo.projectuserfirebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ejemplo.projectuserfirebase.Utilities.Utils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            if(inputEmail.text!!.isNotEmpty()   && inputPassword.text!!.isNotEmpty()){
                val _email = inputEmail.text.toString().replace(" ", "")
                if(Utils.validateEmal((_email.replace(" ", "")))){
                    auth.signInWithEmailAndPassword(_email, inputPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                var  usrId = auth.currentUser
                                val sharedPreferences = getSharedPreferences("my_aplication",
                                    Context.MODE_PRIVATE)
                                var editor = sharedPreferences.edit()
                                editor.putString("usr_id_splash", usrId.toString())
                                editor.commit()
                              val _intent = Intent(this,DashboardActivity::class.java)
                                startActivity(_intent)
                                finish()
                            } else {
                                showToas("Error al ingresar intente mas tarde")
                            }
                        }
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