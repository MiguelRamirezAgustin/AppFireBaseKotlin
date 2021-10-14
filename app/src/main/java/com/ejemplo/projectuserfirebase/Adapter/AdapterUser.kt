package com.ejemplo.projectuserfirebase.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejemplo.projectuserfirebase.Model.Employes
import com.ejemplo.projectuserfirebase.R
import kotlinx.android.synthetic.main.itemuseradapter.view.*

class AdapterUser(
    private var items:List<Employes>,
    private var context:Context): RecyclerView.Adapter<AdapterUser.ViewHolder>() {



    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemuseradapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val model = items.get(position)
        holder.nameUser.text = model.name
        holder.lastNameUser.text = model.lastName

        holder.nameUser.setOnClickListener {
            println("Nombre de usuarios----"+model.key)
        }
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val nameUser = view.textName
        val lastNameUser = view.textLastName
    }
}