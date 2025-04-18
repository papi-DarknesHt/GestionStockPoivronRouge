package com.example.gestionstockpoivronrouge

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.viewmodel.CompteViewModel
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel

class GestionAdapter(
    private val context: Context,
    private var comptes: MutableList<Compte>,  // Liste des comptes à afficher
    private val onEditClick: (Compte) -> Unit,  // Callback pour l'édition
    private val onDeleteClick: (Compte) -> Unit, // Callback pour la suppression
    private val compteViewModel: CompteViewModel
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
        val layoutActions: View = view.findViewById(R.id.layoutActions)  // Référence au LinearLayout des actions

        init {
            // Gérer l'édition et la suppression avec les callbacks
            btnEdit.setOnClickListener {
                val compte = comptes[adapterPosition]
                onEditClick(compte) // Appel du callback d'édition
            }

            btnDelete.setOnClickListener {
                val compte = comptes[adapterPosition]
                onDeleteClick(compte) // Appel du callback de suppression
                val dialog = AlertDialog.Builder(context)
                    .setTitle("Confirmation de suppression")
                    .setMessage("Êtes-vous sûr de vouloir supprimer ce compte ?")
                    .setPositiveButton("Oui") { _, _ ->
                        compteViewModel.supprimerCompte(
                            compte,
                            onResult = { success, message ->
                                if (success) {
                                    // Si la suppression est réussie, retirer l'élément de la liste de données de l'adaptateur
                                    comptes.removeAt(adapterPosition)
                                    notifyItemRemoved(adapterPosition)

                                    Toast.makeText(
                                        context,
                                        "compte supprimé avec succès",
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
                    }.setNegativeButton("Non") { dialogInterface: DialogInterface, _ ->
                        // Si l'utilisateur annule, fermer le dialog
                        dialogInterface.dismiss()
                    }
                    .create()

                // Afficher le dialog
                dialog.show()

            }

            // Lorsqu'un compte est cliqué, on sélectionne la position et on met à jour l'affichage
            view.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)  // Masquer les icônes de l'ancien compte sélectionné
                notifyItemChanged(selectedPosition)  // Afficher les icônes du nouveau compte sélectionné
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
    override fun onBindViewHolder(holder: CompteViewHolder, position: Int) {
        val compte = comptes[position]

        holder.nom.text = compte.nom
        holder.prenom.text = compte.prenom
        holder.email.text = compte.email
        holder.statut.text = compte.statut

        // Afficher ou masquer le LinearLayout des actions en fonction de la position sélectionnée
        holder.layoutActions.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE
    }

    // Retourner la taille de la liste des comptes
    override fun getItemCount(): Int = comptes.size
}