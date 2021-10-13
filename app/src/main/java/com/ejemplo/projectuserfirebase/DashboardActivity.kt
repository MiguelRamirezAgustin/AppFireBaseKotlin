package com.ejemplo.projectuserfirebase

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()

        btnUser.setOnClickListener {
            val _intent = Intent(this, NewUserActivity::class.java);
            startActivity(_intent)
        }


        // sesion
        imgSesion.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Esta seguro de cerrar la sesion ?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, id ->
                    finish()
                    val sharedPreferences = getSharedPreferences(
                        "my_aplication",
                        Context.MODE_PRIVATE
                    )
                    var editor = sharedPreferences.edit()
                    editor.putString("usr_id_splash", "")
                    editor.commit()
                    val _intent = Intent(this, LoginActivity::class.java)
                    startActivity(_intent)
                    finish()
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            val alert = dialogBuilder.create()
            alert.setTitle("Aviso")
            alert.show()
        }


        btnEmployee.setOnClickListener{
            val _intent = Intent(this, EmployesActivity::class.java)
            startActivity(_intent)
        }

        btnEmployeeList.setOnClickListener {
            val _intent = Intent(this, ListEmployesActivity::class.java)
            startActivity(_intent)
        }
    }

}