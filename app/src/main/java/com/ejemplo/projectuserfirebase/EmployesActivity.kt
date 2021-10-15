package com.ejemplo.projectuserfirebase

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.ejemplo.projectuserfirebase.Model.Employes
import java.util.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_employes.*
import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.database.DatabaseReference


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
         }else{
             if(etPhone.text!!.length <= 9){
                 messageToas("El numero es invalido");
                 return@setOnClickListener
             }
         }

        if(!etEmail.text!!.isNotEmpty()){
            messageToas("El Correo es requerido");
            return@setOnClickListener
         }
        if(!selectGenero){
            messageToas("Seleccione uno genero");
            return@setOnClickListener
        }
        if(!etPasswordEmloyes.text!!.isNotEmpty()){
            messageToas("Ingrese una contraseña");
            return@setOnClickListener
        }
            progreesDialog = ProgressDialog(this)
            progreesDialog.setTitle("Creando..")
            progreesDialog.setCancelable(false)
            progreesDialog.show()
            checkUser(etEmail.text.toString().trim())
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
            var date: Calendar = Calendar.getInstance()
            var thisAYear = date.get(Calendar.YEAR)
            var thisAMonth = date.get(Calendar.MONTH)
            var thisADay = date.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view2, thisYear, thisMonth, thisDay ->
                thisAMonth = thisMonth + 1
                thisADay = thisDay
                thisAYear = thisYear
                dateCreate = "" + thisAMonth + "/" + thisDay + "/" + thisYear
                etDate.setText(dateCreate)
                println("Fecha Seleccionada--------<"+dateCreate)
                /*val newDate:Calendar =Calendar.getInstance()
                newDate.set(thisYear, thisMonth, thisDay)
                println("Fecha Seleccionada--------<"+newDate)
                val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                val strDate = dateFormat.format(dateSelect).toString()
                println("Fecha Seleccionada--------<"+strDate)
                val dtStart = "2010-10-15T09:27:37Z"
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                try {
                    val date = format.parse(dateSelect)
                    println("Fecha Seleccionada-------->>>>>>"+date)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                //myRef.child("datetime").setValue(strDate)*/
            }, thisAYear, thisAMonth, thisADay)
            dpd.datePicker.maxDate  = Calendar.getInstance().timeInMillis
            dpd.show()
        }
    }

    // cehck user db firebase
    private fun checkUser(email:String){
        referenceDatabase = FirebaseDatabase.getInstance().getReference("users")
        referenceDatabase.child("").orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()){
                        println("Datos consultados-->"+ dataSnapshot)
                        messageToas("EL correo ya fue registrado")
                    }else{
                        createUser(etName.text.toString(), etLastName.text.toString().trim(), etPhone.text.toString(), etEmail.text.toString(),etPasswordEmloyes.text.toString())
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    progreesDialog.dismiss()
                    messageToas("Error al reacilizar chek intente nuevamente")
                }
            })
    }


    // create user firebase
    private fun createUser(name:String, lastName:String, phone:String, email:String, password:String) {
        referenceDatabase = FirebaseDatabase.getInstance().getReference("users")
        var uidd =  referenceDatabase.push().key
        val user = Employes("",name.trim(), lastName.trim(), radioCheck, phone, email.trim(), true, dateCreate,password)
        referenceDatabase.child(uidd!!).setValue(user)
            .addOnSuccessListener {
                progreesDialog.dismiss()
                messageToas("Usuario creado exitosamente");
                clear()
                val _intent = Intent(this, ListEmployesActivity::class.java)
                startActivity(_intent)
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

    private fun clear(){
        etName.setText("")
        etLastName.setText("")
        etPhone.setText("")
        etEmail.setText("")
        etPasswordEmloyes.setText("")
        selectGenero = false
    }

    private fun messageToas(message:String){
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

}