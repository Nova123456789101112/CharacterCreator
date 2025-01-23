package com.example.charactercreator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import POJO.user;

public class registrarse extends AppCompatActivity {

    Button btn_registrarse;
    EditText et_username,et_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrarse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_registrarse = findViewById(R.id.btn_registrarse);
        et_password = findViewById(R.id.et_pass);
        et_username = findViewById(R.id.et_user);

        btn_registrarse.setOnClickListener(v -> Registrarse());

    }

    void Registrarse (){
        // Obtenemos los datos del usuario y la contraseña
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        // Verificamos que los campos no estén vacíos
        if (!username.isEmpty() && !password.isEmpty()) {
            // Creamos una instancia de User con los datos ingresados
            user user = new user(username, password);

            realizarRegistro(username,password);

            //Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();

            //Intent cambio = new Intent(this, MainActivity.class);
            //startActivity(cambio);




            // Cerramos la actividad actual (MainActivity)
            //finish();
        } else {
            // Mostramos un mensaje si los campos están vacíos
            Toast.makeText(this, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
        }



    }


    private void realizarRegistro(String usuario, String contra) {
        new Thread(() -> {
            try {
                // URL del archivo PHP
                URL url = new URL("http://10.0.2.2/registro.php"); // Cambia por la URL de tu servidor

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
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show());
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error de conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

}