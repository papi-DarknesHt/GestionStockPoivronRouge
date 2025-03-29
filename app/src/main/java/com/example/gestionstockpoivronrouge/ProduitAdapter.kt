package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionstockpoivronrouge.model.Produit
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel

class ProduitAdapter(
    private val context: Context,
    private var produits: MutableList<Produit>,  // Liste des comptes à afficher
    private val onEditClick: (Produit) -> Unit,  // Callback pour l'édition
    private val onDeleteClick: (Produit) -> Unit, // Callback pour la suppression
    private val produitViewModel: ProduitViewModel
) : RecyclerView.Adapter<ProduitAdapter.ProduitViewHolder>() {
    private var selectedPosition = -1  // Pour savoir quel compte est sélectionné
    // Méthode pour mettre à jour la liste des comptes
    @SuppressLint("NotifyDataSetChanged")
    fun setProduit(newProduit: List<Produit>) {
        produits.clear()
        produits.addAll(newProduit)
        notifyDataSetChanged()
    }
    inner class ProduitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomProduit: TextView = view.findViewById(R.id.nomproduit)
        val codeProduit: TextView = view.findViewById(R.id.codeProduit)
        val categorieProduit: TextView = view.findViewById(R.id.categorieprod)
        val btnEdit: ImageView = view.findViewById(R.id.btnEditproduit)
        val btnDelete: ImageView = view.findViewById(R.id.btnDeleteproduit)
        val linearLayout: LinearLayout = view.findViewById(R.id.layoutActionsprod)
        init {
            // Gérer l'édition et la suppression avec les callbacks
            btnEdit.setOnClickListener {
                val produit = produits[adapterPosition]
                onEditClick(produit)
            }
            btnDelete.setOnClickListener {
                val produit = produits[adapterPosition]
                onDeleteClick(produit)
                produitViewModel.suprimmerProduit(
                    produit,
                    onResult = { success, message ->
                        if (success) {
                            // Si la suppression est réussie, retirer l'élément de la liste de données de l'adaptateur
                            produits.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)

                            Toast.makeText(
                                context,
                                "Produit supprimé avec succès",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // Si la suppression échoue
                            Toast.makeText(
                                context,
                                message ?: "Erreur lors de la suppression",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
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
    override fun onBindViewHolder(holder: ProduitViewHolder, position: Int) {
        val produit = produits[position]
        holder.nomProduit.text = produit.nom
        holder.categorieProduit.text = produit.categorie
        holder.codeProduit.text = produit.code
        // Afficher ou cacher les icônes en fonction de la sélection
        holder.linearLayout.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
        holder.btnEdit.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
        holder.btnDelete.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
        holder.itemView.setBackgroundColor(
            if (position == selectedPosition) context.getColor(R.color.white)
            else context.getColor(android.R.color.transparent)
        )
    }
    // Retourner la taille de la liste des comptes
    override fun getItemCount(): Int = produits.size
}
