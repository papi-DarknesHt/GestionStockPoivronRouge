package com.example.gestionstockpoivronrouge

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.repository.ProduitRepository
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel

class Produit_Activity : AppCompatActivity() {

    private lateinit var gestionProduit_Activity: Produit_Activity
    private val produitViewModel: ProduitViewModel by viewModels {
        val daoProduit = AppDatabase.getDatabase(this).produitDao()
        val repository = ProduitRepository(daoProduit)
        ProduitViewModel.FactoryProduit(repository) // Utilisation correcte de la Factory interne
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityproduit)

    }
}