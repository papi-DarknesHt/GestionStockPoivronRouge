package com.example.gestionstockpoivronrouge

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gestionstockpoivronrouge.model.Produit
import com.example.gestionstockpoivronrouge.model.Stock
import com.example.gestionstockpoivronrouge.viewmodel.StockViewModel
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel

class ActivityStockAdapter(
    private val context: Context,
    private var stocks: MutableList<Stock>,
    private val onEditClick: (Stock) -> Unit,
    private val onDeleteClick: (Stock) -> Unit,
    private val stockViewModel: StockViewModel,
    private val produitViewModel: ProduitViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ActivityStockAdapter.StockViewHolder>() {

    private val produitsCache = mutableMapOf<Int, Produit?>()
    private var selectedPosition = -1  // Position de l'élément sélectionné

    fun setStocks(newStock: List<Stock>) {
        stocks.clear()
        stocks.addAll(newStock)
        notifyDataSetChanged()
    }

    inner class StockViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageProduit: ImageView = view.findViewById(R.id.imageProduitStock)
        val nomProduit: TextView = view.findViewById(R.id.nomProduitStock)
        val codeProduit: TextView = view.findViewById(R.id.codeProduitStock)
        val quantiteProduit: TextView = view.findViewById(R.id.quantiteProduitStock)
        val btnPlus: ImageView = view.findViewById(R.id.btnIncreaseStock)
        val btnMinus: ImageView = view.findViewById(R.id.btnDecreaseStock)
        val btnEdit: ImageView = view.findViewById(R.id.btnEditStock)
        val btnDelete: ImageView = view.findViewById(R.id.btnDeleteStock)
        val layoutActions: LinearLayout = view.findViewById(R.id.layoutActionsStock)

        init {
            // Augmenter la quantité
            btnPlus.setOnClickListener {
                val stock = stocks[adapterPosition]
                modifierQuantite(stock, 1)
            }

            // Diminuer la quantité
            btnMinus.setOnClickListener {
                val stock = stocks[adapterPosition]
                if (stock.qte > 0) {
                    modifierQuantite(stock, -1)
                } else {
                    Toast.makeText(context, "Stock insuffisant", Toast.LENGTH_SHORT).show()
                }
            }

            // Bouton Modifier
            btnEdit.setOnClickListener {
                val stock = stocks[adapterPosition]
                onEditClick(stock)
            }

            // Bouton Supprimer
            // Bouton Supprimer
            btnDelete.setOnClickListener {
                val stock = stocks[adapterPosition]
                showDeleteConfirmationDialog(stock)
            }

            // Sélection de l'élément
            view.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)  // Masquer actions du précédent
                notifyItemChanged(selectedPosition)  // Afficher actions du nouveau
            }
        }

        // Modifier la quantité du stock
        private fun modifierQuantite(stock: Stock, variation: Int) {
            val nouvelleQuantite = stock.qte + variation
            stock.qte = nouvelleQuantite

            // Mise à jour dans la base de données
            stockViewModel.modifierStock(stock) { success, message ->
                if (success) {
                    notifyItemChanged(adapterPosition)
                    Toast.makeText(context, "Stock mis à jour", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, message ?: "Erreur", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Boîte de dialogue de confirmation pour suppression
        private fun showDeleteConfirmationDialog(stock: Stock) {
            AlertDialog.Builder(context)
                .setTitle("Confirmation de suppression")
                .setMessage("Voulez-vous vraiment supprimer ce stock ?")
                .setPositiveButton("Oui") { _, _ ->
                    stockViewModel.supprimerStock(stock) { success, message ->
                        if (success) {
                            stocks.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            Toast.makeText(context, "Stock supprimé", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, message ?: "Erreur", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton("Non") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_gestion_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stocks[position]

        // Valeurs par défaut
        holder.nomProduit.text = "Chargement..."
        holder.codeProduit.text = "Code: --"
        holder.imageProduit.setImageResource(R.drawable.ic_placeholder_image)

        // Vérifier si le produit est déjà en cache
        if (produitsCache.containsKey(stock.id_produit)) {
            val produit = produitsCache[stock.id_produit]
            afficherProduit(holder, produit)
        } else {
            // Observer les données du produit si pas dans le cache
            produitViewModel.getProduitById(stock.id_produit).observe(lifecycleOwner, Observer { produit ->
                produitsCache[stock.id_produit] = produit // Ajouter au cache
                afficherProduit(holder, produit)
            })
        }

        // Affichage de la quantité
        holder.quantiteProduit.text = "Quantité: ${stock.qte}"

        // Afficher/Masquer les icônes Modifier & Supprimer selon la sélection
        holder.layoutActions.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
    }

    private fun afficherProduit(holder: StockViewHolder, produit: Produit?) {
        if (produit != null) {
            holder.nomProduit.text = produit.nom ?: "Produit inconnu"
            holder.codeProduit.text = "Code: ${produit.code ?: "N/A"}"

            if (!produit.imagePath.isNullOrEmpty()) {
                Glide.with(context).load(produit.imagePath).into(holder.imageProduit)
            } else {
                holder.imageProduit.setImageResource(R.drawable.ic_placeholder_image)
            }
        } else {
            holder.nomProduit.text = "Produit inconnu"
            holder.codeProduit.text = "Code: N/A"
            holder.imageProduit.setImageResource(R.drawable.ic_placeholder_image)
        }
    }

    fun updateProduit(produit: Produit) {
        val index = stocks.indexOfFirst { it.id_produit == produit.id }
        if (index != -1) {
            notifyItemChanged(index)
        }
    }

    override fun getItemCount(): Int = stocks.size
}
