package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.gestionstockpoivronrouge.model.Compte

class GestionAdapter(
    private val Gcontext: Context,        // Contexte de l'application
    private var comptes: MutableList<Compte>  // Liste des comptes à afficher
): ArrayAdapter<Compte>(Gcontext, 0, comptes) {

    // Méthode pour mettre à jour la liste des comptes
    fun setComptes(newCompte: List<Compte>) {
        comptes.clear()
        comptes.addAll(newCompte)  // Ajout des nouveaux comptes à la liste
        Log.d("Gestion Compte", "La liste a été mise à jour")
        notifyDataSetChanged()  // Notifie l'adaptateur que les données ont changé
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Utilisation de la vue convertView pour réutiliser les vues et économiser de la mémoire
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(Gcontext)
                .inflate(R.layout.item_gestion_compte, parent, false) // Inflation de la vue
        }

        val compte = getItem(position) ?: return itemView!!  // Si l'élément est nul, retourner la vue

        // Récupération des éléments de la vue
        val id = itemView!!.findViewById<TextView>(R.id.idCompte)
        val nom = itemView.findViewById<TextView>(R.id.nomCompte)
        val prenom = itemView.findViewById<TextView>(R.id.prenomCompte)
        val email = itemView.findViewById<TextView>(R.id.emailCompte)
        val statut = itemView.findViewById<TextView>(R.id.statuCompte)

        // Mise à jour des informations du compte dans les TextViews
        id.text = compte.id.toString()
        nom.text = compte.nom
        prenom.text = compte.prenom
        email.text = compte.email
        statut.text = compte.statut

        return itemView
    }

    // Méthode pour obtenir le nombre d'éléments dans la liste
    override fun getCount(): Int = comptes.size

    // Méthode pour obtenir l'élément à une position spécifique
    override fun getItem(position: Int): Compte? {
        return if (position in 0 until comptes.size) {
            comptes[position]  // Retourne l'élément à la position donnée
        } else {
            null
        }
    }
}