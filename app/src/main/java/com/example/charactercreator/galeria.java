package com.example.charactercreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import POJO.chara;
import adaptadores.adaptadorver;
import Global.info;


public class galeria extends AppCompatActivity {

    RecyclerView rv;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_galeria);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //poblarLista();

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        rv =findViewById(R.id.rv_personajes);
        adaptadorver av = new adaptadorver(this);
        av.context=this;
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rv.setAdapter(av);
        rv.setLayoutManager(llm);
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

    @Override
    public void onResume(){
        super.onResume();
        rv.removeAllViews();
        poblarLista();
    }

    private void poblarLista() {
        info.lista.clear();
        new Thread(() -> {
            try {
                // URL del archivo PHP
                URL url = new URL("http://10.0.2.2/galeria.php"); // Cambia por la URL de tu servidor

                // Crear la conexión
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);

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
                            Gson gson = new Gson();
                            Type listType = new TypeToken<ArrayList<chara>>() {}.getType();
                            info.lista.addAll(gson.fromJson(message, listType));
                            rv.getAdapter().notifyDataSetChanged();

                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR){
                    runOnUiThread(() -> Toast.makeText(this, "No se pudo conectar a la base de datos", Toast.LENGTH_SHORT).show());
                }

                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error de conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}