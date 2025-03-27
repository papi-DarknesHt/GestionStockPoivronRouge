package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity

class HomeActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        creation d'un itent pour gestion activity
        val intentCompte = Intent(this,Gestion_activity ::class.java)

        var btCompte : Button? = findViewById(R.id.btCompt)
        if (btCompte != null) {
            btCompte.setOnClickListener(View.OnClickListener {
                startActivity(intentCompte)
            })
        }
    }// fin Oncreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menuHelp ->{

            }
            R.id.menuparametre ->{

            }
            R.id.menulogout ->{
                finish()
            }

        }

        return super.onOptionsItemSelected(item)
    }
}