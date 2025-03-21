package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu

class HomeActivity: Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        var btback : Button? = findViewById(R.id.btBack)
        val intentCompte = Intent(this,Gestion_activity ::class.java)
        var btmenu : Button? = findViewById(R.id.menu);
        if (btmenu != null) {
            btmenu.setOnClickListener(View.OnClickListener {
                val popupMenu = PopupMenu(this,btmenu)
                val menuInflater = popupMenu.menuInflater
                menuInflater.inflate(R.menu.menu, popupMenu.menu)
                popupMenu.show()
            })
        }
        var btCompte : Button? = findViewById(R.id.btCompt)
        if (btCompte != null) {
            btCompte.setOnClickListener(View.OnClickListener {
                startActivity(intentCompte)
            })
        }
    }
}