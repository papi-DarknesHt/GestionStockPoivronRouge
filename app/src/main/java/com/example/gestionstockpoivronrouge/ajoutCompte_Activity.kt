package com.example.gestionstockpoivronrouge

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gestionstockpoivronrouge.database.AppDatabase
import kotlinx.coroutines.launch
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.repository.CompteRepository
import com.example.gestionstockpoivronrouge.viewmodel.CompteViewModel

class ajoutCompte_Activity : AppCompatActivity() {

    // Initialisation correcte du ViewModel en utilisant la Factory interne
    private val compteViewModel: CompteViewModel by viewModels {
        val dao = AppDatabase.getDatabase(this).compteDao()
        val repository = CompteRepository(dao)
        CompteViewModel.Factory(repository) // Utilisation de la Factory interne
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajoutcompte)

        // Récupérer les éléments de l'interface
        val editTextNom = findViewById<EditText>(R.id.edittextnom)
        val editTextPrenom = findViewById<EditText>(R.id.editTextprenom)
        val editTextEmail = findViewById<EditText>(R.id.edittextemail)
        val editTextPassword = findViewById<EditText>(R.id.edittextpassword)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnAjout = findViewById<Button>(R.id.btajoutcompte)

        btnAjout.setOnClickListener {
            val nom = editTextNom.text.toString().trim()
            val prenom = editTextPrenom.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Vérifier quel radio button est sélectionné
            val statut = when (radioGroup.checkedRadioButtonId) {
                R.id.rbtstatutAdm -> "Admin"
                R.id.rbtstatutManager -> "Manager"
                else -> ""
            }

            // Vérification des champs
            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || statut.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Création de l'objet Compte
            val compte = Compte(
                nom = nom,
                prenom = prenom,
                email = email,
                statut = statut,
                password = password
            )

            // Ajouter le compte via ViewModel
            lifecycleScope.launch {
                compteViewModel.ajouterCompte(compte) { success, message ->
                    runOnUiThread {
                        Toast.makeText(this@ajoutCompte_Activity, message, Toast.LENGTH_SHORT).show()
                        if (success) {
                            finish() // Fermer l'activité après ajout réussi
                        }
                    }
                }
            }
        }
    }
}
