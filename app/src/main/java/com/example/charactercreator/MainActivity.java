package com.example.charactercreator;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Global.info;
import Global.userinfo;
import POJO.user;

public class MainActivity extends AppCompatActivity {

    private EditText etUser, etPass;
    private Button btnEntrar, btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos los elementos de la interfaz
        etUser = findViewById(R.id.et_user);
        etPass = findViewById(R.id.et_pass);
        btnEntrar = findViewById(R.id.btn_entrar);
        btnRegistrarse = findViewById(R.id.btn_registrarse);

        // Configuramos el listener para el botón "Entrar"
        btnEntrar.setOnClickListener(v -> {
            // Obtenemos los datos del usuario y la contraseña
            String username = etUser.getText().toString().trim();
            String password = etPass.getText().toString().trim();

            // Verificamos que los campos no estén vacíos
            if (!username.isEmpty() && !password.isEmpty()) {
                realizarLogin(username, password);
                user user = new user(username,password);
                userinfo.lista.add(user);

            } else {
                // Mostramos un mensaje si los campos están vacíos
                Toast.makeText(MainActivity.this, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistrarse.setOnClickListener(v -> irRegistro());
    }

    private void realizarLogin(String usuario, String contra) {
        new Thread(() -> {
            try {
                // URL del archivo PHP
                URL url = new URL("http://10.0.2.2/login.php"); // Cambia por la URL de tu servidor

                // Crear la conexión
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);

                // Datos a enviar
                JSONObject json = new JSONObject();
                json.put("username", usuario);
                json.put("password", contra);

                // Enviar los datos
                OutputStream os = conn.getOutputStream();
                os.write(json.toString().getBytes("UTF-8"));
                os.close();

                // Leer la respuesta
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();

                    JSONObject responseJson = new JSONObject(sb.toString());
                    String status = responseJson.getString("status");
                    String message = responseJson.getString("message");

                    runOnUiThread(() -> {
                        if ("success".equals(status)) {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                            // Navegar a la siguiente actividad
                            Intent intent = new Intent(MainActivity.this, crear.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show());
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error de conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    void irRegistro() {
        Intent intent = new Intent(MainActivity.this, registrarse.class);
        startActivity(intent);
        finish();
    }
}
