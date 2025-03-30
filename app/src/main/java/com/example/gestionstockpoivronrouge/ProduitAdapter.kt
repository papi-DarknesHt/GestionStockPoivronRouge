package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import android.graphics.BitmapFactory
import com.example.gestionstockpoivronrouge.model.Produit
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel
import java.io.File

class ProduitAdapter(
    private val context: Context,
    private var produits: MutableList<Produit>,  // Liste des comptes à afficher
    private val onEditClick: (Produit) -> Unit,  // Callback pour l'édition
    private val onDeleteClick: (Produit) -> Unit, // Callback pour la suppression
    private val produitViewModel: ProduitViewModel
) : RecyclerView.Adapter<ProduitAdapter.ProduitViewHolder>() {

    private var selectedPosition = -1  // Pour savoir quel produit est sélectionné

    // Méthode pour mettre à jour la liste des produits
    @SuppressLint("NotifyDataSetChanged")
    fun setProduit(newProduit: List<Produit>) {
        produits.clear()
        produits.addAll(newProduit)
        notifyDataSetChanged()
    }

    inner class ProduitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomProduit: TextView = view.findViewById(R.id.nomProduit)
        val codeProduit: TextView = view.findViewById(R.id.codeProduit)
        val categorieProduit: TextView = view.findViewById(R.id.categorieProduit)
        val btnEdit: ImageView = view.findViewById(R.id.btnEditProduit)
        val btnDelete: ImageView = view.findViewById(R.id.btnDeleteProduit)
        val linearLayout: LinearLayout = view.findViewById(R.id.layoutActionsProd)
        val imageProduit: ImageView = view.findViewById(R.id.imageProduit)  // ImageView pour afficher l'image du produit

        init {
            // Gérer l'édition et la suppression avec les callbacks
            btnEdit.setOnClickListener {
                val produit = produits[adapterPosition]
                onEditClick(produit)

            }

            btnDelete.setOnClickListener {
                val produit = produits[adapterPosition]
                onDeleteClick(produit)
                showDeleteConfirmationDialog(produit)
            }

            // Lorsqu'un produit est cliqué, on sélectionne la position et on met à jour l'affichage
            view.setOnClickListener {
                if (it !is ImageView) {  // Ne pas changer la sélection si l'on clique sur un bouton
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(previousPosition)  // Masquer les icônes de l'ancien produit sélectionné
                    notifyItemChanged(selectedPosition)  // Afficher les icônes du nouveau produit sélectionné
                }
            }
        }

        private fun showDeleteConfirmationDialog(produit: Produit) {
            val dialog = AlertDialog.Builder(context)
                .setTitle("Confirmation de suppression")
                .setMessage("Êtes-vous sûr de vouloir supprimer ce produit ?")
                .setPositiveButton("Oui") { _, _ ->
                    produitViewModel.supprimerProduit(produit, onResult = { success, message ->
                        if (success) {
                            produits.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            Toast.makeText(
                                context,
                                "Produit supprimé avec succès",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                message ?: "Erreur lors de la suppression",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
                .setNegativeButton("Non") { dialogInterface: DialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create()

            dialog.show()
        }
    }

    // Créer un ViewHolder pour chaque item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduitViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_gestion_produit, parent, false)
        return ProduitViewHolder(view)
    }

    // Lier les données à la vue
    override fun onBindViewHolder(holder: ProduitViewHolder, position: Int) {
        val produit = produits[position]
        holder.nomProduit.text = produit.nom
        holder.categorieProduit.text = produit.categorie
        holder.codeProduit.text = produit.code

        // Afficher ou cacher les icônes en fonction de la sélection
        holder.linearLayout.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
        holder.btnEdit.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
        holder.btnDelete.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE

        // Modifier la couleur de fond de l'élément sélectionné
        holder.itemView.setBackgroundColor(
            if (position == selectedPosition) context.getColor(R.color.white)
            else context.getColor(android.R.color.transparent)
        )

        // Charger l'image dans l'ImageView si une image URI est présente
        // Si une image est présente
        if (!produit.imagePath.isNullOrEmpty()) {
            Glide.with(context)
                .load(File(produit.imagePath))
                .placeholder(R.drawable.ic_placeholder_image)
                .error(R.drawable.ic_error)
                .into(holder.imageProduit)
        } else {
            holder.imageProduit.setImageResource(R.drawable.ic_placeholder_image)
        }


    }

    // Retourner la taille de la liste des produits
    override fun getItemCount(): Int = produits.size
}
