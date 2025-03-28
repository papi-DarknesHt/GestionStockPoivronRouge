package com.example.gestionstockpoivronrouge.viewmodel

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
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