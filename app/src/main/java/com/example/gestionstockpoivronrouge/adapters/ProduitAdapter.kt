package com.example.gestionstockpoivronrouge.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.gestionstockpoivronrouge.R
import com.example.gestionstockpoivronrouge.model.Produit

class ProduitAdapter(
    private val Gcontext: Context,        // Contexte de l'application
    private var produit: MutableList<Produit>  // Liste des comptes à afficher
): ArrayAdapter<Produit>(Gcontext, 0, produit) {

    // Méthode pour mettre à jour la liste des comptes
    fun setProduit(newProduit: List<Produit>) {
        produit.clear()
        produit.addAll(newProduit)  // Ajout des nouveaux Produit à la liste
        Log.d("Gestion Compte", "La liste a été mise à jour")
        notifyDataSetChanged()  // Notifie l'adaptateur que les données ont changé
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Utilisation de la vue convertView pour réutiliser les vues et économiser de la mémoire
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(Gcontext)
                .inflate(R.layout.item_gestion_produit, parent, false) // Inflation de la vue
        }

        val produititem = getItem(position) ?: return itemView!!  // Si l'élément est nul, retourner la vue

        // Récupération des éléments de la vue
        val nom = itemView!!.findViewById<TextView>(R.id.textviewNomProd)
        val categorie = itemView.findViewById<TextView>(R.id.textViewCategorieProduit)
        val codebar = itemView.findViewById<TextView>(R.id.textViewCodeProduit)


        // Mise à jour des informations du compte dans les TextViews
        codebar.text = produititem.code
        nom.text = produititem.nom
        categorie.text = produititem.categorie

        return itemView
    }

    // Méthode pour obtenir le nombre d'éléments dans la liste
    override fun getCount(): Int = produit.size

    // Méthode pour obtenir l'élément à une position spécifique
    override fun getItem(position: Int): Produit? {
        return if (position in 0 until produit.size) {
            produit[position]  // Retourne l'élément à la position donnée
        } else {
            null
        }
    }
}