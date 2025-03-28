package com.example.gestionstockpoivronrouge

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.repository.ProduitRepository
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel

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
    }
}