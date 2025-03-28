package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionstockpoivronrouge.model.Compte

class GestionAdapter(
    private val context: Context,
    private var comptes: MutableList<Compte>,  // Liste des comptes à afficher
    private val onEditClick: (Compte) -> Unit,  // Callback pour l'édition
    private val onDeleteClick: (Compte) -> Unit // Callback pour la suppression
) : RecyclerView.Adapter<GestionAdapter.CompteViewHolder>() {

    private var selectedPosition = -1  // Pour savoir quel compte est sélectionné

    // Méthode pour mettre à jour la liste des comptes
    fun setComptes(newCompte: List<Compte>) {
        comptes.clear()
        comptes.addAll(newCompte)  // Ajout des nouveaux comptes à la liste
        notifyDataSetChanged()  // Notifie l'adaptateur que les données ont changé
    }

    // ViewHolder pour chaque élément dans le RecyclerView
    inner class CompteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nom: TextView = view.findViewById(R.id.nomCompte)
        val prenom: TextView = view.findViewById(R.id.prenomCompte)
        val email: TextView = view.findViewById(R.id.emailCompte)
        val statut: TextView = view.findViewById(R.id.statuCompte)
        val btnEdit: ImageView = view.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = view.findViewById(R.id.btnDelete)

        init {
            // Gérer l'édition et la suppression avec les callbacks
            btnEdit.setOnClickListener {
                val compte = comptes[adapterPosition]
                onEditClick(compte) // Appel du callback d'édition
            }

            btnDelete.setOnClickListener {
                val compte = comptes[adapterPosition]
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompteViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_gestion_compte, parent, false)
        return CompteViewHolder(view)
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

    override fun onBindViewHolder(holder: CompteViewHolder, position: Int) {
        val compte = comptes[position]

        holder.nom.text = compte.nom
        // ... autres bind ...

        // Afficher ou cacher les icônes en fonction de la sélection
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
    override fun getItemCount(): Int = comptes.size
}
