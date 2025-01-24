package com.example.charactercreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.DatePickerDialog;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import Global.info;
import POJO.chara;

public class crear extends AppCompatActivity {

    private EditText etNombre, etApellido, etFechaNac, etEspecie, etNacionalidad, etAfiliacion, etDescripcion;
    private CheckBox chboxRelacion;
    private RadioGroup radioGroupSexo;
    private Button btnGuardar;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Inicializar vistas
        etNombre = findViewById(R.id.et_nombre);
        etApellido = findViewById(R.id.et_apellido);
        etFechaNac = findViewById(R.id.et_fechanac);
        etEspecie = findViewById(R.id.et_especie);
        etNacionalidad = findViewById(R.id.et_nacionalidad);
        etAfiliacion = findViewById(R.id.et_afiliacion);
        etDescripcion = findViewById(R.id.et_descripcion);
        chboxRelacion = findViewById(R.id.chbox_relacion);
        radioGroupSexo = findViewById(R.id.radioGroupSexo);
        btnGuardar = findViewById(R.id.btn_guardar);

        // Configurar el botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });

        // Configuración del evento de clic en el campo 'et_fecha_entrega'
        etFechaNac.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onclick_fecha_nac(); // Llama al método para mostrar el selector de fecha
            }
        });
    }

    private void guardarDatos() {
        // Obtener valores del formulario
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String fechaNacimiento = etFechaNac.getText().toString();
        int estaEnRelacion = chboxRelacion.isChecked()?1:0;
        String especie = etEspecie.getText().toString();
        String nacionalidad = etNacionalidad.getText().toString();
        String afiliacion = etAfiliacion.getText().toString();
        String descripcion = etDescripcion.getText().toString();

        // Obtener sexo seleccionado
        int sexo = radioGroupSexo.indexOfChild(radioGroupSexo.findViewById(radioGroupSexo.getCheckedRadioButtonId()));

        // Crear objeto Chara
        chara newchara = new chara(nombre, apellido, fechaNacimiento, estaEnRelacion, especie, nacionalidad, afiliacion, descripcion, sexo);

        info.lista.add(newchara);

        // (Opcional) Aquí podrías guardar el objeto chara en una base de datos o enviarlo a otra Activity
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2/crear.php");

                // Crear la conexión
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);

                // Datos a enviar
                JSONObject json = new JSONObject();
                json.put("nombre", nombre);
                json.put("apellido", apellido);
                json.put("fechaNacimiento", fechaNacimiento);
                json.put("estaEnRelacion", estaEnRelacion);
                json.put("especie", especie);
                json.put("nacionalidad", nacionalidad);
                json.put("afiliacion", afiliacion);
                json.put("descripcion", descripcion);
                json.put("sexo", sexo);

                // Enviar los datos
                OutputStream os = null;

                os = conn.getOutputStream();
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
                            limpiarCampos();
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST){
                    runOnUiThread(() -> Toast.makeText(this, "Faltan datos requeridos", Toast.LENGTH_SHORT).show());
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

    private void onclick_fecha_nac() {
        int dia, mes, ayo;
        Calendar actual = Calendar.getInstance(); // Obtiene la fecha y hora actual
        dia = actual.get(Calendar.DAY_OF_MONTH); // Día actual
        mes = actual.get(Calendar.MONTH); // Mes actual
        ayo = actual.get(Calendar.YEAR); // Año actual

        // Crea y muestra el diálogo de selección de fecha
        DatePickerDialog datPd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                //Ingresa los datos que ingresa el usuario y se los pasa al pojo
                month = month + 1;

                // Crea una cadena con la fecha en formato día/mes/año
                String fecha = " " + dayofMonth + "/" + month + "/" + year;

                // Muestra la fecha seleccionada en el campo 'fecha'
                etFechaNac.setText(fecha);
            }
        }, ayo, mes, dia);
        datPd.show();
    }

    private void limpiarCampos() {
        // Limpiar campos de texto
        etNombre.setText("");
        etApellido.setText("");
        etFechaNac.setText("");
        etEspecie.setText("");
        etNacionalidad.setText("");
        etAfiliacion.setText("");
        etDescripcion.setText("");

        // Desmarcar la casilla de relación
        chboxRelacion.setChecked(false);

        // Desmarcar el RadioGroup de sexo
        radioGroupSexo.clearCheck();
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
