package com.example.gestionstockpoivronrouge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionstockpoivronrouge.model.Stock

class ActivityStockAdapter(
    private val context: Context,
   private var stocks: MutableList<Stock>,  // Liste des comptes à afficher
   private val onEditClick: (Stock) -> Unit,  // Callback pour l'édition
   private val onDeleteClick: (Stock) -> Unit // Callback pour la suppression
) : RecyclerView.Adapter<ActivityStockAdapter.StockViewHolder>() {
    private var selectedPosition = -1  // Pour savoir quel Stock est sélectionné

    // Méthode pour mettre à jour la liste des comptes
    fun setStocks(newStock: List<Stock>) {
        stocks.clear()
        stocks.addAll(newStock)  // Ajout des nouveaux comptes à la liste
        notifyDataSetChanged()  // Notifie l'adaptateur que les données ont changé
    }

    // ViewHolder pour chaque élément dans le RecyclerView
    inner class StockViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idStock: TextView = view.findViewById(R.id.nomCompte)
        val IdCompteStock: TextView = view.findViewById(R.id.prenomCompte)
        val idProduitStock: TextView = view.findViewById(R.id.TextViewCodeProduitStock)
        val idQuteStock : TextView = view.findViewById(R.id.TextViewQteStock)
        val btnEdit: ImageView = view.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = view.findViewById(R.id.btnDelete)
        val linearLayout: LinearLayout = view.findViewById(R.id.layoutActionsprod)

        init {
            // Gérer l'édition et la suppression avec les callbacks
            btnEdit.setOnClickListener {
                val stock = stocks[adapterPosition]
                onEditClick(stock) // Appel du callback d'édition
            }

            btnDelete.setOnClickListener {
                val stock = stocks[adapterPosition]
                onDeleteClick(stock) // Appel du callback de suppression
            }

            // Lorsqu'un stock est cliqué, on sélectionne la position et on met à jour l'affichage
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_gestion_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stocks[position]

        holder.idStock.text = stock.id.toString().trim()
        holder.idProduitStock.text = stock.id_produit.toString().trim()
        holder.IdCompteStock.text = stock.idCompte.toString().trim()
        holder.idQuteStock.text = stock.qte.toString().trim()
        // ... autres bind ...

        // Afficher ou cacher les icônes en fonction de la sélection
        holder.linearLayout.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
        holder.btnEdit.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
        holder.btnDelete.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
    }


    /*@Override
    fun onBindViewHolder(holder: CompteViewHolder, position: Int) {
        val compte = comptes[position]

        holder.nom.text = compte.nom
        holder.prenom.text = compte.prenom
        holder.email.text = compte.email
        holder.statut.text = compte.statut

        // Afficher ou masquer les icônes Modifier et Supprimer
        holder.btnEdit.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
        holder.btnDelete.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
    }*/

    // Retourner la taille de la liste des comptes
    override fun getItemCount(): Int = stocks.size



}