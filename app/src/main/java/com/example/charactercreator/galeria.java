package com.example.charactercreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import adaptadores.adaptadorver;


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

}