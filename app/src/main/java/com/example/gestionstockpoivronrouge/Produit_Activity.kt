package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.repository.ProduitRepository
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel
import androidx.lifecycle.Observer
import com.example.gestionstockpoivronrouge.model.Produit


class Produit_Activity : AppCompatActivity() {

    private lateinit var gestionProduit_Activity: ProduitAdapter
    lateinit var recyclerView: RecyclerView
    val produits: MutableList<Produit> = mutableListOf()
    private val produitViewModel: ProduitViewModel by viewModels {
        val daoProduit = AppDatabase.getDatabase(this).produitDao()
        val repository = ProduitRepository(daoProduit)
        ProduitViewModel.FactoryProduit(repository) // Utilisation correcte de la Factory interne
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityproduit)
        afficherListeProduit()

    }

    private fun afficherListeProduit() {
        val recyclerView = findViewById<RecyclerView>(R.id.listViewProduit)
        gestionProduit_Activity = ProduitAdapter(
            this,
            mutableListOf(),
            onEditClick = { produit ->
                // Logique pour modifier le compte
                /* val intent = Intent(this, EditCompteActivity::class.java)
                 intent.putExtra("compteId", compte.id)
                 startActivity(intent)*/
                showEditDialog(produit)
            },
            onDeleteClick = { produit ->
                // Logique pour supprimer le compte
                /* compteViewModel.delete(compte) // Supposons que vous avez une méthode pour supprimer un compte
                 Toast.makeText(this, "Compte ${compte.nom} supprimé", Toast.LENGTH_SHORT).show()*/
            },
            produitViewModel = produitViewModel
        )
        recyclerView.adapter = gestionProduit_Activity


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = gestionProduit_Activity

        // Observer les comptes depuis le ViewModel
        produitViewModel.allProduit.observe(this, Observer { produits ->
            produits?.let {
                gestionProduit_Activity.setProduit(it)
            }
        })

    }

    @SuppressLint("MissingInflatedId")
    private fun showEditDialog(produit: Produit) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_ajout_produit, null)
        val nomEditText = dialogView.findViewById<EditText>(R.id.nomProduit)
        val codeEditText = dialogView.findViewById<EditText>(R.id.codeProd)
        val spinnerCategorie: Spinner = dialogView.findViewById(R.id.spinnerCategorieProduit)
        val recyclerView: RecyclerView = findViewById(R.id.listViewProduit)  // Assurez-vous que l'ID du RecyclerView est correct
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = gestionProduit_Activity

        val optionCategorie = listOf("Vivre","Legume","Grain","Pois","Farine","Viande","Poisson")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, optionCategorie)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategorie.adapter = adapter

        val categorieposition = optionCategorie.indexOf(produit.categorie)
        Toast.makeText(this, "Categorie selected: ${categorieposition}", Toast.LENGTH_SHORT).show()
        if(categorieposition >= 0){
            spinnerCategorie.setSelection(categorieposition)
        }
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Modifier Produit")
            .setView(dialogView)
            .setPositiveButton("Modifier") { dialog, _ ->
                if(codeEditText.text.toString().isEmpty()){
                    codeEditText.setText(produit.code)
                }
                if(nomEditText.text.toString().isEmpty()){
                    nomEditText.setText(produit.nom)
                }
                // Mettre à jour le produit avec les nouvelles valeurs
                produit.nom = nomEditText.text.toString()
                produit.code = codeEditText.text.toString()
                produit.categorie = spinnerCategorie.selectedItem.toString()

                // Mettre à jour le produit dans la base de données ou le ViewModel
                produitViewModel.modifierProduit(produit) { success, message ->
                    if (success) {
                        Toast.makeText(this, "Produit modifié avec succès", Toast.LENGTH_SHORT).show()

                        // Rafraîchir la liste des produits
                        val position = produits.indexOf(produit)
                        if (position != -1) {
                            produits[position] = produit
                            (recyclerView.adapter as ProduitAdapter).notifyItemChanged(position)
                        }
                    } else {
                        Toast.makeText(this, message ?: "Erreur lors de la modification", Toast.LENGTH_SHORT).show()
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            .setNegativeButton("Annuler") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    // Option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.produit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ajouter_produit -> {
                val intentAjoutProduit = Intent(this, activity_ajout_produit::class.java)
                startActivity(intentAjoutProduit)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}