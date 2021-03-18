package com.test.gittracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.test.gittracker.Utils.readStream;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText textInputEditPseudo;
    private TextInputEditText textInputEditToken;
    private TextInputLayout editTextPseudo;
    private TextInputLayout editTextToken;
    private TextView textViewToken;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditPseudo = findViewById(R.id.textInputEditPseudo);
        textInputEditToken = findViewById(R.id.textInputEditToken);

        editTextPseudo = findViewById(R.id.editTextPseudo);
        editTextToken = findViewById(R.id.editTextToken);


        textViewToken = findViewById(R.id.textViewToken);
        textViewToken.setOnClickListener(showAlertDialog);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(login);
    }

    private final View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String pseudo = textInputEditPseudo.getText().toString();
            String password = textInputEditToken.getText().toString();

            editTextPseudo.setErrorEnabled(pseudo.equals(""));
            editTextToken.setErrorEnabled(password.equals(""));

//            String username = editTextPseudo.getEditText().toString();
//            String token = editTextToken.getEditText().toString();

            String username = "Naegon";
            String token = "38b79f839e6258ba8fcff68e6325d193de370cac";

            new Thread(() -> {
                URL url;
                try {
                    url = new URL("https://api.github.com/user");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    String basicAuth = "Basic " + Base64.encodeToString((username + ":" + token).getBytes(), Base64.NO_WRAP);
                    urlConnection.setRequestProperty ("Authorization", basicAuth);
                    try {
                        InputStream in;

                        try {
                            in = new BufferedInputStream(urlConnection.getInputStream());
                        } catch (FileNotFoundException e) {
                            Toast.makeText(getApplicationContext(), "Failed authentification", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String s = readStream(in);

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        UserClass loggedUser;
                        try {
                            JSONObject result = new JSONObject(s);
                            editor.putString("login", username);
                            editor.putString("token", token);
                            editor.apply();

                            loggedUser = new UserClass(
                                    result.getString("login"),
                                    result.getString("avatar_url"),
                                    result.getString("type"),
                                    result.getString("company"),
                                    Integer.parseInt(result.getString("public_repos")),
                                    Integer.parseInt(result.getString("total_private_repos")),
                                    Integer.parseInt(result.getString("followers")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("logged_user", loggedUser);
                        startActivity(intent);
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    };

    private final View.OnClickListener showAlertDialog = v -> {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle("How to get your token ?");
        alertDialog.setMessage("You need to go to github and request a token there");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Got it!", (dialog, which) -> dialog.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Go to Github", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/settings/tokens"));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        alertDialog.show();
    };
}
