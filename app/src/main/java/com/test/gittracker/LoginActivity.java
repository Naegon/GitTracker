package com.test.gittracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
    private Handler mHandler;

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

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Toast.makeText(getApplicationContext(), "Failed authentification", Toast.LENGTH_LONG).show();
            }
        };
    }

    private final View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String pseudo = textInputEditPseudo.getText().toString();
            String password = textInputEditToken.getText().toString();

            editTextPseudo.setErrorEnabled(pseudo.equals(""));
            editTextToken.setErrorEnabled(password.equals(""));

            String username = editTextPseudo.getEditText().getText().toString();
            String token = editTextToken.getEditText().getText().toString();

            new Thread(() -> {
                URL url;
                try {
                    url = new URL("https://api.github.com/user");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    String basicAuth = "Basic " + Base64.encodeToString((username + ":" + token).getBytes(), Base64.NO_WRAP);
                    urlConnection.setRequestProperty("Authorization", basicAuth);
                    try {
                        InputStream in;
                        try {
                            in = new BufferedInputStream(urlConnection.getInputStream());
                        } catch (FileNotFoundException e) {
                            Message message = mHandler.obtainMessage();
                            message.sendToTarget();
                            e.printStackTrace();
                            return;
                        }

                        String s = readStream(in);

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        try {
                            JSONObject result = new JSONObject(s);

//                            loggedUser = new UserClass(
//                                    result.getString("login"),
//                                    result.getString("avatar_url"),
//                                    result.getString("type"),
//                                    result.getString("company"),
//                                    Integer.parseInt(result.getString("public_repos")),
//                                    Integer.parseInt(result.getString("total_private_repos")),
//                                    Integer.parseInt(result.getString("followers")));

                            editor.putString("login", username);
                            editor.putString("token", token);
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
        alertDialog.setMessage("In order to use the app, you'll need a personnal access token from Github. Just click on the bottom left button and follow the instructions!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Got it!", (dialog, which) -> dialog.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Go to Github", (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/settings/tokens"));
            startActivity(intent);
            dialog.dismiss();
        });
        alertDialog.show();
    };
}
