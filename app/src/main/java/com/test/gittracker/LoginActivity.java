package com.test.gittracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText textInputEditPseudo;
    private TextInputEditText textInputEditPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditPseudo = findViewById(R.id.textInputEditPseudo);
        textInputEditPassword = findViewById(R.id.textInputEditPassword);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(login);
    }

    private final View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);

//            String pseudo = textInputEditPseudo.getText().toString();
//            String password = textInputEditPassword.getText().toString();
//
//            TextInputLayout editTextPseudo = findViewById(R.id.editTextPseudo);
//            TextInputLayout editTextPassword = findViewById(R.id.editTextPassword);
//
//            if (pseudo.equals("")) {
//
//                editTextPseudo.setErrorEnabled(true);
//                editTextPseudo.setError("This field is required");
//            } else editTextPseudo.setErrorEnabled(false);
//
//            if (password.equals("")) {
//                editTextPassword.setErrorEnabled(true);
//                editTextPassword.setError("This field is required");
//            } else editTextPassword.setErrorEnabled(false);
        }
    };
}