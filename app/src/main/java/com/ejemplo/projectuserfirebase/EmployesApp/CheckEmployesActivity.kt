package com.ejemplo.projectuserfirebase.EmployesApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ejemplo.projectuserfirebase.Model.EmployesCheck
import com.ejemplo.projectuserfirebase.R
import kotlinx.android.synthetic.main.activity_check_employes.*
import java.text.SimpleDateFormat
import java.util.*

import com.google.firebase.database.*


class CheckEmployesActivity : AppCompatActivity() {

    private lateinit var referenceDatabase: DatabaseReference
    private lateinit var hours:String
    private lateinit var checkDay:String
    private lateinit var keyUser:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_employes)
        supportActionBar?.hide()
       keyUser = intent.getStringExtra("key").toString()

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val _hours = SimpleDateFormat("hh:mm:ss a")
        checkDay = sdf.format(Date())
        System.out.println(" C DATE is  " + checkDay)
        hours = _hours.format(Date())
        textDate.setText(checkDay)
        println("Hora--->" + hours)

        // check Go
        btnChekGo.setOnClickListener {
            referenceDatabase = FirebaseDatabase.getInstance().getReference("users")
            var uidd = referenceDatabase.push().key
            val user = EmployesCheck(keyUser, checkDay, hours, "", false)
            referenceDatabase.child("Hours").child(uidd!!).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Check exitoso", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Check fallido", Toast.LENGTH_LONG).show()
                }
        }

        // check exit
        btnChekExit.setOnClickListener {
            referenceDatabase = FirebaseDatabase.getInstance().getReference("users")
            referenceDatabase.child("Hours").child("-Mlx7xbdtGyBKxXGtajx").child("checkExit")
                .setValue(hours)
                .addOnSuccessListener {
                    Toast.makeText(this, "Check exitoso", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Check fallido", Toast.LENGTH_LONG).show()
                }
        }

        linearHeaders.setOnClickListener {
            referenceDatabase = FirebaseDatabase.getInstance().getReference("users")
            referenceDatabase.child("Hours").orderByChild("dateCheck").equalTo("13/10/2021")
            referenceDatabase.child("Hours").orderByChild("key").equalTo("-MlvX6rVw3876JX36itj")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()){
                        println("Datos consultados-->"+ dataSnapshot)
                        dataSnapshot.children.forEach {
                            var id =  it.child("checkExit").getValue().toString()
                            if(id == ""){
                                println("Datos consultados forEach-vacio->")
                            }else{
                                println("Datos consultados forEach-datos->")
                            }
                        }
                        Toast.makeText(applicationContext, "ok ok ok dataSnapshot ", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext, "ok no dataSnapshot ", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(applicationContext, "Check fallido", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}