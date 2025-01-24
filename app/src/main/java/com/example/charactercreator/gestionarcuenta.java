package com.example.charactercreator;

import  static Global.userinfo.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

public class gestionarcuenta extends AppCompatActivity {

    Toolbar toolbar;
    TextView username;
    EditText password;

    Button btn_guardarCambios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestionarcuenta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = findViewById(R.id.tv_username);
        password = findViewById(R.id.et_password);
        btn_guardarCambios = findViewById(R.id.btn_guardarCambios);
        username.setText("User: " + Usuario.getUsername());
        //password.setText("Contraseña: " + lista.get(0).getPassword());
        btn_guardarCambios.setOnClickListener(v -> cambiarContrasena(Usuario.getUsername(),password.getText().toString().trim()));

    }

    //Opciones de la toolbar para iniciar otras activities desde el menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.crear) {
            Intent cambio = new Intent(this, crear.class);
            startActivity(cambio);
        }
        if (item.getItemId() == R.id.galeria) {
            Intent cambio = new Intent(this, galeria.class);
            startActivity(cambio);
        }
        if (item.getItemId() == R.id.eliminar) {
            Intent cambio = new Intent(this, eliminar.class);
            startActivity(cambio);
        }
        if (item.getItemId() == R.id.gestionarcuenta) {
            Intent cambio = new Intent(this, gestionarcuenta.class);
            startActivity(cambio);
        }
        if (item.getItemId() == R.id.salir) {
            Intent cambio = new Intent(this, MainActivity.class);
            startActivity(cambio);
        }

        return super.onOptionsItemSelected(item);
    }
    //Crea opciones del menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void cambiarContrasena(String usuario, String contra) {
        new Thread(() -> {
            try {
                // URL del archivo PHP
                URL url = new URL("http://10.0.2.2/cambiarcontra.php"); // Cambia por la URL de tu servidor

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