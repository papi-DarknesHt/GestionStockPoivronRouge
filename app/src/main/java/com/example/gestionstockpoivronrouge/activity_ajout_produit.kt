package com.example.gestionstockpoivronrouge

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.model.Produit
import com.example.gestionstockpoivronrouge.repository.ProduitRepository
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel
import kotlinx.coroutines.launch

class activity_ajout_produit: AppCompatActivity() {
    private lateinit var gestionProduit_Activity: Produit_Activity
    private val produitViewModel: ProduitViewModel by viewModels {
        val daoProduit = AppDatabase.getDatabase(this).produitDao()
        val repository = ProduitRepository(daoProduit)
        ProduitViewModel.FactoryProduit(repository) // Utilisation correcte de la Factory interne
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajout_produit)
        ajoutproduit()
//        spinnerCategorie()
    }

//    private fun spinnerCategorie() {
//        val spinnerCategorie : Spinner = findViewById(R.id.spinnerCategorieProduit)
//        val optionCategorie = listOf("Vivre","Legume","Grain","Pois","Farine","Viande","Poisson")
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, optionCategorie)
//        spinnerCategorie.adapter = adapter
//
//        spinnerCategorie.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedItem = parent?.getItemAtPosition(position).toString()
//                // Votre code ici
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // Optionnel : Gérer le cas où rien n'est sélectionné
//            }
//        }
//    }

    private fun ajoutproduit() {
        val editTextNomProduit = findViewById<EditText>(R.id.nomProduit)
        val editTexcodeBar = findViewById<EditText>(R.id.codeProd)
        val btajout= findViewById<Button>(R.id.btajouterProd)

        val spinnerCategorie : Spinner = findViewById(R.id.spinnerCategorieProduit)
        val optionCategorie = listOf("Vivre","Legume","Grain","Pois","Farine","Viande","Poisson")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, optionCategorie)
        spinnerCategorie.adapter = adapter

        btajout.setOnClickListener{
            val nomProd = editTextNomProduit.text.toString().trim()
            val codeProd = editTexcodeBar.text.toString().trim()
            val categorieprod = spinnerCategorie.selectedItem.toString().trim()

            // Vérification des champs
            if (nomProd.isEmpty() || codeProd.isEmpty() || categorieprod.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val produit = Produit(
                code = codeProd,
                nom = nomProd,
                categorie = categorieprod
            )
            // Ajouter le compte via ViewModel
            lifecycleScope.launch {
                produitViewModel.ajouterProduits(produit) { success, message ->
                    runOnUiThread {
                        Toast.makeText(this@activity_ajout_produit, message, Toast.LENGTH_SHORT).show()
                        if (success) {
                            finish() // Fermer l'activité après ajout réussi
                        }
                    }
                }
            }

        }

    }
}