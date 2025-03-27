package com.example.gestionstockpoivronrouge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.repository.CompteRepository
import com.example.gestionstockpoivronrouge.viewmodel.CompteViewModel
import com.example.gestionstockpoivronrouge.viewmodel.CompteViewModelFactory


class Gestion_activity: AppCompatActivity() {

    private  lateinit var compteListeAdapter: GestionAdapter
    private  lateinit var CompteViewModel: CompteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestioncompte)
        afficherListe()
    }

    private fun afficherListe() {
        compteListeAdapter = GestionAdapter(this, mutableListOf())
        val listCompte = findViewById<ListView>(R.id.listviewCompte)

        listCompte.adapter = compteListeAdapter
        val daocompte = AppDatabase.getDatabase(this).compteDao()
        val repository = CompteRepository(daocompte)
        val factory = CompteViewModelFactory(repository)
        CompteViewModel = viewModels<CompteViewModel> { factory }.value
        CompteViewModel.allComptes.observe(this, Observer { comptes->
            comptes?.let {
                compteListeAdapter.setComptes(it)
            }

        })


//        listCompte.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
//            if(position>=0) {
//                val clickedCompte = listCompte.getItemAtPosition(position) as Compte
//                    val intent = Intent(this, DetailCompte_Activity::class.java)
//                    intent.putExtra("id", clickedCompte.id)
//                    intent.putExtra("nom", clickedCompte.nom)
//                    intent.putExtra("prenom", clickedCompte.prenom)
//                    intent.putExtra("email", clickedCompte.email)
//                    intent.putExtra("statut", clickedCompte.statut)
//                    startActivity(intent)
//
//            }else{
//                Log.e("Gestion Compte","Position invalide: $position")
//            }
//        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
//            menuInflater.Inflate(R.menu.)
        super.onCreateContextMenu(menu, v, menuInfo)
    }
    
//    option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.produit_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ajouter_produit ->{
                var intentAjoutCompte= Intent(this, ajoutCompte_Activity ::class.java)
                startActivity(intentAjoutCompte)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}