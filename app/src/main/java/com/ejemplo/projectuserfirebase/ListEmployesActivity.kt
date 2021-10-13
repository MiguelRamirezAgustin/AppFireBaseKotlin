package com.ejemplo.projectuserfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ListEmployesActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_employes)
        supportActionBar?.hide()
        getUser()
    }

    private fun getUser(){
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child("users")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                println(error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot!!.children
                children.forEach {
                    println("Nombre de usuarios----"+it.toString())
                }
            }
        })

    }

}