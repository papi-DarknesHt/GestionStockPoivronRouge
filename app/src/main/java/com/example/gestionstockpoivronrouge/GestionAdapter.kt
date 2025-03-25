package com.example.gestionstockpoivronrouge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class GestionAdapter(
    var Gcontext: Context,
    var resoure: Int,
    var values:ArrayList<GestionCompte_data>
):ArrayAdapter<GestionCompte_data>(Gcontext,resoure,values) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val compte = values[position]
        val itemView = LayoutInflater.from(Gcontext).inflate(resoure,parent,false)
        val id = itemView.findViewById<TextView>(R.id.idCompte)
        val nom = itemView.findViewById<TextView>(R.id.nomCompte)
        val prenom = itemView.findViewById<TextView>(R.id.prenomCompte)
        val email = itemView.findViewById<TextView>(R.id.emailCompte)
        val statut = itemView.findViewById<TextView>(R.id.statuCompte)
        val image = itemView.findViewById<ImageView>(R.id.imageCompte)
        id.text = compte.id
        nom.text = compte.nom
        prenom.text = compte.prenom
        email.text = compte.email
        statut.text = compte.statut
        //image.setImageResource(compte.image)


        return itemView
    }
}