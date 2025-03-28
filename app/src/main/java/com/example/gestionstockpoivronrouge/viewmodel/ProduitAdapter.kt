package com.example.gestionstockpoivronrouge.viewmodel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.gestionstockpoivronrouge.R
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.model.Produit

class ProduitAdapter(
    private val context: Context,        // Contexte de l'application
    private var produits: MutableList<Produit>  // Liste des comptes à afficher
): ArrayAdapter<Produit>(context, 0, produits) {

    fun setProduit(newProduit: List<Produit>) {
        produits.clear()
        produits.addAll(newProduit)  // Ajout des nouveaux comptes à la liste
        Log.d("Gestion Produit", "La liste a été mise à jour")
        notifyDataSetChanged()  // Notifie l'adaptateur que les données ont changé
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Utilisation de la vue convertView pour réutiliser les vues et économiser de la mémoire
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_gestion_produit, parent, false) // Inflation de la vue
        }

        val produit = getItem(position) ?: return itemView!!  // Si l'élément est nul, retourner la vue

        // Récupération des éléments de la vue
        val nomProduit = itemView!!.findViewById<TextView>(R.id.textviewNomProd)
        val codeProduit = itemView.findViewById<TextView>(R.id.textViewCodeProduit)
        val categorie = itemView.findViewById<TextView>(R.id.textViewCategorieProduit)

        // Mise à jour des informations du compte dans les TextViews
        nomProduit.text = produit.nom
        codeProduit.text = produit.code
        categorie.text = produit.categorie
        return itemView
    }


    // Méthode pour obtenir le nombre d'éléments dans la liste
    override fun getCount(): Int = produits.size

    // Méthode pour obtenir l'élément à une position spécifique
    override fun getItem(position: Int): Produit? {
        return if (position in 0 until produits.size) {
            produits[position]  // Retourne l'élément à la position donnée
        } else {
            null
        }
    }
}