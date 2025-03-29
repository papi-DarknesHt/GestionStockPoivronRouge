package com.example.gestionstockpoivronrouge

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class ActivityStock:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        afficherStock()
    }

    private fun afficherStock() {

    }


    //    menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_stock, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btAjoutStock -> {
                val activity_AjouterStock = Intent(this, Activity_AjouterStock::class.java)
                startActivity(activity_AjouterStock)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}