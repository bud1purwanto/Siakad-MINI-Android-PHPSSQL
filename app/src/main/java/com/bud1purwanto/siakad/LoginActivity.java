package com.bud1purwanto.siakad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText nim;
    private Button btnMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Session.Login == true || Session.NIM != null){
            startActivity(new Intent(getApplicationContext(), Beranda.class));
        }

        nim = findViewById(R.id.nim);
        btnMasuk = findViewById(R.id.btnMasuk);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Beranda.class);
                Session.Login = true;
                Session.NIM = nim.getText().toString();
                i.putExtra("nim", nim.getText().toString());
                startActivity(i);
                finish();

            }
        });
    }
}
