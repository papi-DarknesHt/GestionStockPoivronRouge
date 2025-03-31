package com.example.gestionstockpoivronrouge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionstockpoivronrouge.viewmodel.CompteViewModel
import com.example.gestionstockpoivronrouge.repository.CompteRepository
import com.example.gestionstockpoivronrouge.dao.CompteDao
import com.example.gestionstockpoivronrouge.database.AppDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var compteDao: CompteDao
    private lateinit var repository: CompteRepository
    private val viewModel: CompteViewModel by viewModels {
        CompteViewModel.Factory(repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation de la base de données
        compteDao = AppDatabase.getDatabase(applicationContext).compteDao()
        repository = CompteRepository(compteDao)

        val mail = findViewById<EditText>(R.id.mail)
        val password = findViewById<EditText>(R.id.password)
        val btlogin = findViewById<Button>(R.id.connection)

        btlogin.setOnClickListener {
            val email = mail.text.toString()
            val psd = password.text.toString()

            if (email.isEmpty() || psd.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.authentifier(email, psd) { compte ->
                when {
                    // Cas spécial : connexion en tant qu'admin par défaut
                    email == "admin@gmail.com" && psd == "admin" -> {
                        Toast.makeText(this, "Bienvenue, Admin !", Toast.LENGTH_SHORT).show()
                        val intentHome = Intent(this, HomeActivity::class.java).apply {
                            putExtra("user_email", email)
                            putExtra("user_nom", "Administrateur")
                            putExtra("user_statut", "admin") // Statut défini en dur
                        }
                        startActivity(intentHome)
                        finish()
                    }

                    // Connexion normale avec un compte existant dans la base de données
                    compte != null -> {
                        Toast.makeText(this, "Bienvenue, ${compte.nom} !", Toast.LENGTH_SHORT).show()
                        val intentHome = Intent(this, HomeActivity::class.java).apply {
                            putExtra("user_email", compte.email)
                            putExtra("user_nom", compte.nom)
                            putExtra("user_statut", compte.statut)
                        }
                        startActivity(intentHome)
                        finish() // Empêche de revenir en arrière à l’écran de connexion
                    }

                    // Erreur : Email ou mot de passe incorrect
                    else -> {
                        Toast.makeText(this, "Email ou mot de passe incorrect !", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        }
    }

