package com.ejemplo.projectuserfirebase

import android.R.attr
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_employes.*


class EmployesActivity : AppCompatActivity() {

    lateinit var progreesDialog: ProgressDialog
    private lateinit var referenceDatabase: DatabaseReference
    var radioCheck: Boolean = false;
    var dateCreate :String = "";
    var selectGenero:Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employes)
        supportActionBar?.hide()

        btnCreateEmployes.setOnClickListener {
         if(!etName.text!!.isNotEmpty()){
             messageToas("El nombre es requerido")
             return@setOnClickListener
         }
         if(!etLastName.text!!.isNotEmpty()){
             messageToas("Los Apellidos son requeridos")
             return@setOnClickListener
         }
         if(!etPhone.text!!.isNotEmpty()){
            messageToas("El numero de telefono es requerido");
            return@setOnClickListener
         }

        if(!etEmail.text!!.isNotEmpty()){
            messageToas("El Correo es requerido");
            return@setOnClickListener
         }
        if(!selectGenero){
            messageToas("Seleccione uno genero");
            return@setOnClickListener
        }
         createUser(etName.text.toString(), etLastName.text.toString().trim(), etPhone.text.toString(), etEmail.text.toString())
        }

        rdg_main_name.setOnCheckedChangeListener { group, checkedId ->
            selectGenero = true
             when(checkedId){
                R.id.rdb_M -> {
                    radioCheck = false
                }
                R.id.rdb_F -> {
                    radioCheck = true
                }
            }
        }

        // back button
        imgBack.setOnClickListener {
            val _intnet = Intent(this, DashboardActivity::class.java)
            startActivity(_intnet)
        }

        etDate.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                etDate.setText(" " + dayOfMonth + " / " + month + " / " + year)
                dateCreate =  etDate.text.toString().replace(" ", "")
            }, year, month, day)
            dpd.datePicker.maxDate  = Calendar.getInstance().timeInMillis
            dpd.show()
        }
    }

    private fun createUser(name:String, lastName:String, phone:String, email:String) {
        progreesDialog = ProgressDialog(this)
        progreesDialog.setTitle("Cargando..")
        progreesDialog.setCancelable(false)
        progreesDialog.show()
        referenceDatabase = FirebaseDatabase.getInstance().getReference("users")
        var uidd =  referenceDatabase.push().key
        val user = Employes(name.trim(), lastName.trim(), radioCheck, phone, email.trim(), true, dateCreate)
        referenceDatabase.child(uidd!!).setValue(user)
            .addOnSuccessListener {
                progreesDialog.dismiss()
               Toast.makeText(this, "Usuario creado", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                progreesDialog.dismiss()
                Toast.makeText(this, "Error al realizar la operacion intente mas tarder", Toast.LENGTH_LONG).show()
            }

        /*
        *   database = FirebaseDatabase.getInstance().getReference("users")
        database.child("users").get().addOnSuccessListener{
            if (it.exists()){
                Toast.makeText(this,"Successfuly Read",Toast.LENGTH_SHORT).show()


            }else{
                Toast.makeText(this,"User Doesn't Exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
        * */

    }

    private fun messageToas(message:String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}