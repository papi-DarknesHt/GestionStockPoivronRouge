package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import androidx.activity.ComponentActivity

class HomeActivity: Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var btback : Button? = findViewById(R.id.btBack)
        var btmenu : Button? = findViewById(R.id.menu);
        if (btmenu != null) {
            btmenu.setOnClickListener(View.OnClickListener {
                val popupMenu = PopupMenu(this,btmenu)
                val menuInflater = popupMenu.menuInflater
                menuInflater.inflate(R.menu.menu, popupMenu.menu)
                popupMenu.show()
            })
        }
        if (btback != null){
            btback.setOnClickListener(View.OnClickListener {
                finish()
            })
        }
    }
}