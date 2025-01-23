package com.example.charactercreator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Global.info;
import POJO.chara;
import adaptadores.adaptadoreliminar;
import adaptadores.adaptadorver;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import adaptadores.adaptadorver;


public class eliminar extends AppCompatActivity {


    RecyclerView rv;
    Toolbar toolbar;
    Button btn_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_eliminar = findViewById(R.id.btn_eliminar);
        btn_eliminar.setOnClickListener(v -> eliminarElemento());

        rv =findViewById(R.id.rv_personajes);
        adaptadoreliminar adapt_elim = new adaptadoreliminar(this);
        adapt_elim.context=this;
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rv.setAdapter(adapt_elim);
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

    // Método para eliminar los elementos seleccionados
    private void eliminarElemento() {
        if (info.listaelim.isEmpty()) {
            // Mostrar un mensaje si no hay elementos seleccionados
            Toast.makeText(this, "No se seleccionaron elementos para eliminar", Toast.LENGTH_SHORT).show();
        } else {
            // Eliminar los elementos
            for (int i = 0; i < info.listaelim.size(); i++) {
                chara chara_elim = info.listaelim.get(i);
                info.lista.remove(chara_elim);  // Eliminar de la lista local
            }
            info.listaelim.clear();  // Limpiar la lista de elementos eliminados
            rv.getAdapter().notifyDataSetChanged();  // Actualizar la vista del RecyclerView
            Toast.makeText(this, "Elementos eliminados", Toast.LENGTH_SHORT).show();
        }
    }
}