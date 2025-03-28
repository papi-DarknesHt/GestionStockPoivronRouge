package com.example.gestionstockpoivronrouge

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Observer
import com.example.gestionstockpoivronrouge.database.AppDatabase
import kotlinx.coroutines.launch
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.repository.CompteRepository
import com.example.gestionstockpoivronrouge.viewmodel.CompteViewModel

class ajoutCompte_Activity : AppCompatActivity() {

    // Initialisation du ViewModel avec la Factory
    private val compteViewModel: CompteViewModel by viewModels {
        val dao = AppDatabase.getDatabase(this).compteDao()
        val repository = CompteRepository(dao)
        CompteViewModel.Factory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajoutcompte)

        // Récupération des éléments de l'interface
        val editTextNom = findViewById<EditText>(R.id.edittextnom)
        val editTextPrenom = findViewById<EditText>(R.id.editTextprenom)
        val editTextEmail = findViewById<EditText>(R.id.edittextemail)
        val editTextPassword = findViewById<EditText>(R.id.edittextpassword)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnAjout = findViewById<Button>(R.id.btajoutcompte)

        // Vérifier si un ID est passé en paramètre
        val compte = intent.getParcelableExtra<Compte>("compte")
        val compteId = compte?.id ?: -1

        if (compte != null) {
            editTextNom.setText(compte.nom)
            editTextPrenom.setText(compte.prenom)
            editTextEmail.setText(compte.email)
            editTextPassword.setText(compte.password)

            when (compte.statut) {
                "ADMIN" -> radioGroup.check(R.id.rbtstatutAdm)
                "MANAGER" -> radioGroup.check(R.id.rbtstatutManager)
            }

            btnAjout.text = "Modifier le compte" // Mise à jour du texte du bouton
        }

        // Gestion du clic sur le bouton d'ajout/modification
        btnAjout.setOnClickListener {
            val nom = editTextNom.text.toString().trim().uppercase()
            val prenom = editTextPrenom.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Vérification du statut sélectionné
            val statut = when (radioGroup.checkedRadioButtonId) {
                R.id.rbtstatutAdm -> "ADMIN"
                R.id.rbtstatutManager -> "MANAGER"
                else -> ""
            }

            // Vérifier si tous les champs sont remplis
            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || statut.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Création d'un objet Compte
            val compte = Compte(
                id = if (compteId != -1) compteId else 0,
                nom = nom,
                prenom = prenom,
                email = email,
                statut = statut,
                password = password
            )

            lifecycleScope.launch {
                if (compteId == -1) {
                    // Ajout d'un nouveau compte
                    compteViewModel.ajouterCompte(compte) { success, message ->
                        runOnUiThread {
                            Toast.makeText(this@ajoutCompte_Activity, message, Toast.LENGTH_SHORT).show()
                            if (success) finish()
                        }
                    }
                } else {
                    // Modification d'un compte existant
                    compteViewModel.modifierCompte(compte) { success, message ->
                        runOnUiThread {
                            Toast.makeText(this@ajoutCompte_Activity, message, Toast.LENGTH_SHORT).show()
                            if (success) finish()
                        }
                    }
                }
            }
        }
    }
}
