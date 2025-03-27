package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gestionstockpoivronrouge.ui.theme.GestionStockPoivronRougeTheme

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val home = HomeActivity()
        val intentHome = Intent(this, HomeActivity::class.java)
//        var mail: EditText? = null; var password: EditText? = null
        val btlogin: Button? = findViewById<Button>(R.id.connection)
        if (btlogin != null) {
            btlogin.setOnClickListener(View.OnClickListener {
                val mail = findViewById<View>(R.id.mail) as EditText
                val password = findViewById<View>(R.id.password) as EditText
                val email: String = mail!!.getText().toString()
                val psd: String = password!!.getText().toString()
                mail.setText("")
                password.setText("")
                if (email == "napoleonwagnerson@gmail.com" || psd == "admin") {
                    Toast.makeText(this@MainActivity, "Connection reussi", Toast.LENGTH_SHORT).show()
                    startActivity(intentHome)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Email ou Password Incorrect !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }


    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GestionStockPoivronRougeTheme {
        Greeting("Android")
    }
}