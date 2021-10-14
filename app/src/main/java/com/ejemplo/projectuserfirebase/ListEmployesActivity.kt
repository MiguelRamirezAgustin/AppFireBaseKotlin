package com.ejemplo.projectuserfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ejemplo.projectuserfirebase.Adapter.AdapterUser
import com.ejemplo.projectuserfirebase.Model.Employes
import com.google.firebase.database.*

class ListEmployesActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    var listEmployes : MutableList<Employes>  = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_employes)
        supportActionBar?.hide()
        recyclerView = findViewById(R.id.rvListUser)
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
                    println("Nombre de usuarios----"+it.key)
                   val employes = Employes(
                        it.key.toString(),
                        it.child("name").getValue().toString(),
                        it.child("lastName").getValue().toString(),
                        it.child("sexo").getValue().toString().toBoolean(),
                        it.child("phone").getValue().toString(),
                        it.child("email").getValue().toString(),
                        it.child("activo").getValue().toString().toBoolean(),
                        it.child("dateBirth").getValue().toString()
                    )
                    listEmployes.add(employes)
                }
                println("Nombre de usuarios----"+listEmployes)
                linearLayoutManager = LinearLayoutManager(applicationContext)
                recyclerView.layoutManager = linearLayoutManager
                recyclerView.adapter = AdapterUser(listEmployes,applicationContext)
            }
        })

    }

}