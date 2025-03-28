package com.example.gestionstockpoivronrouge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionstockpoivronrouge.model.Produit

class ProduitAdapter(
    private val context: Context,
    private var produits: MutableList<Produit>,  // Liste des comptes à afficher
    private val onEditClick: (Produit) -> Unit,  // Callback pour l'édition
    private val onDeleteClick: (Produit) -> Unit // Callback pour la suppression
) : RecyclerView.Adapter<ProduitAdapter.ProduitViewHolder>() {

    private var selectedPosition = -1  // Pour savoir quel compte est sélectionné

    // Méthode pour mettre à jour la liste des comptes
    fun setComptes(newProduit: List<Produit>) {
        produits.clear()
        produits.addAll(newProduit)  // Ajout des nouveaux comptes à la liste
        notifyDataSetChanged()  // Notifie l'adaptateur que les données ont changé
    }

    // ViewHolder pour chaque élément dans le RecyclerView
    inner class ProduitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomProduit: TextView = view.findViewById(R.id.nomproduit)
        val codeProduit: TextView = view.findViewById(R.id.codeProduit)
        val categorieProduit: TextView = view.findViewById(R.id.categorieprod)
        val btnEdit: ImageView = view.findViewById(R.id.btnEditproduit)
        val btnDelete: ImageView = view.findViewById(R.id.btnDeleteproduit)

        init {
            // Gérer l'édition et la suppression avec les callbacks
            btnEdit.setOnClickListener {
                val compte = produits[adapterPosition]
                onEditClick(compte) // Appel du callback d'édition
            }

            btnDelete.setOnClickListener {
                val compte = produits[adapterPosition]
                onDeleteClick(compte) // Appel du callback de suppression
            }

            // Lorsqu'un compte est cliqué, on sélectionne la position et on met à jour l'affichage
            view.setOnClickListener {
                btnEdit
                // Si l'élément cliqué n'est pas un bouton ImageView
                if (it !is ImageView) {
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(previousPosition)  // Masquer les icônes de l'ancien compte sélectionné
                    notifyItemChanged(selectedPosition)  // Afficher les icônes du nouveau compte sélectionné
                }
            }

        }
    }

    // Créer un ViewHolder pour chaque item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduitViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_gestion_produit, parent, false)
        return ProduitViewHolder(view)
    }

    // Lier les données du compte avec les vues de l'item
    /*@SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CompteViewHolder, position: Int) {
        val compte = comptes[position]

        holder.nom.text = compte.nom
        holder.prenom.text = compte.prenom
        holder.email.text = compte.email
        holder.statut.text = compte.statut

        // Afficher ou cacher les icônes en fonction de la sélection
        holder.btnEdit.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
        holder.btnDelete.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
    }*/

    override fun onBindViewHolder(holder: ProduitViewHolder, position: Int) {
        val produit = produits[position]

        holder.nomProduit.text = produit.nom
        holder.categorieProduit.text = produit.categorie
        holder.codeProduit.text = produit.code
        // Afficher ou cacher les icônes en fonction de la sélection
        holder.btnEdit.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
        holder.btnDelete.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
    }


    // Retourner la taille de la liste des comptes
    override fun getItemCount(): Int = produits.size
}
