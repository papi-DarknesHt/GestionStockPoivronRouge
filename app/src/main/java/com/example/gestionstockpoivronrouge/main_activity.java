package com.example.gestionstockpoivronrouge;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class main_activity extends Activity {
    private Button btlogin;
    private EditText mail,password;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    setContentView(R.layout.activity_main);

    btlogin = findViewById(R.id.connection);
    btlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mail = (EditText) findViewById(R.id.mail);
            password = (EditText) findViewById(R.id.password);
            String email = mail.getText().toString();
            String psd = password.getText().toString();
            if(email.equals("admin@gmail.com") && psd.equals("admin")){
                Toast.makeText(main_activity.this,"Connection reussi",Toast.LENGTH_SHORT).show();
//            setContentView(R.layout.activity_home);
            }            else{
                Toast.makeText(main_activity.this,"Email ou Password Incorrect !",Toast.LENGTH_SHORT).show();
            }
        }
    });

    }


}
