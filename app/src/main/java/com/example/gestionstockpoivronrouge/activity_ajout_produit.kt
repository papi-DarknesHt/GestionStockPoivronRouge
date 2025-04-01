package com.example.gestionstockpoivronrouge

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.model.Produit
import com.example.gestionstockpoivronrouge.repository.ProduitRepository
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class activity_ajout_produit : AppCompatActivity() {
    private val produitViewModel: ProduitViewModel by viewModels {
        val dao = AppDatabase.getDatabase(this).produitDao()
        val repository = ProduitRepository(dao)
        ProduitViewModel.FactoryProduit(repository)
    }

    private val IMAGE_REQUEST_CODE = 1001
    private var selectedImagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajout_produit)

        val editTextNomProduit = findViewById<EditText>(R.id.nomProduit)
        val editTextCodeBar = findViewById<EditText>(R.id.codeProd)
        val spinnerCategorie = findViewById<Spinner>(R.id.spinnerCategorieProduit)
        val btnAjout = findViewById<Button>(R.id.btajouterProd)
        val imageViewProduit = findViewById<ImageView>(R.id.imageProduit)
        val selectImageButton = findViewById<Button>(R.id.selectImageButton)

        val optionCategorie = listOf("Vivres", "Légumes", "Grains", "Pois", "Farine/Céréales", "Viandes", "Épices", "Poissons", "Autres")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, optionCategorie)
        spinnerCategorie.adapter = adapter

        val produit = intent.getParcelableExtra<Produit>("produit")
        val produitId = produit?.id ?: -1

        if (produit != null) {
            editTextNomProduit.setText(produit.nom)
            editTextCodeBar.setText(produit.code)
            val position = optionCategorie.indexOf(produit.categorie)
            if (position >= 0) {
                spinnerCategorie.setSelection(position)
            }
            if (!produit.imagePath.isNullOrEmpty()) {
                val bitmap = BitmapFactory.decodeFile(produit.imagePath)
                imageViewProduit.setImageBitmap(bitmap)
                selectedImagePath = produit.imagePath
            }
//            btnAjout.text = "Modifier"
        }

        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        btnAjout.setOnClickListener {
            val nomProd = editTextNomProduit.text.toString().trim()
            val codeProd = editTextCodeBar.text.toString().trim()
            val categorieProd = spinnerCategorie.selectedItem.toString().trim()

            if (nomProd.isEmpty() || codeProd.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val produit = Produit(
                id = if (produitId != -1) produitId else 0,
                nom = nomProd,
                code = codeProd,
                categorie = categorieProd,
                imagePath = selectedImagePath // 🔥 On stocke uniquement le chemin de l'image
            )

            lifecycleScope.launch {
                if (produitId == -1) {
                    produitViewModel.ajouterProduits(produit) { success, message ->
                        runOnUiThread {
                            Toast.makeText(this@activity_ajout_produit, message, Toast.LENGTH_SHORT).show()
                            if (success) {
                                setResult(Activity.RESULT_OK, Intent())
                                finish()
                            }
                        }
                    }
                } else {
                    produitViewModel.modifierProduit(produit) { success, message ->
                        runOnUiThread {
                            Toast.makeText(this@activity_ajout_produit, message, Toast.LENGTH_SHORT).show()
                            if (success) {
                                setResult(Activity.RESULT_OK, Intent())
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            findViewById<ImageView>(R.id.imageProduit).setImageBitmap(bitmap)
            selectedImagePath = saveImageToInternalStorage(bitmap)
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val file = File(filesDir, "image_${System.currentTimeMillis()}.jpg")
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos) // Compression à 50% pour optimiser
        fos.flush()
        fos.close()
        return file.absolutePath // 🔥 Retourne le chemin du fichier
    }
}