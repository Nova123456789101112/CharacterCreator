//package com.example.charactercreator;
/*
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

import Global.info;
import POJO.chara;

public class modificar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
*/
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


import java.util.Calendar;

import Global.info;
import POJO.chara;

public class modificar extends AppCompatActivity {

    private EditText etNombre, etApellido, etFechaNac, etEspecie, etNacionalidad, etAfiliacion, etDescripcion;
    private CheckBox chboxRelacion;
    private RadioGroup radioGroupSexo;
    private Button btnGuardar;

    Toolbar toolbar;

    int pos;

    chara chara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);


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
                limpiarCampos();
            }
        });

        // Configuración del evento de clic en el campo 'et_fecha_entrega'
        etFechaNac.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onclick_fecha_nac(); // Llama al método para mostrar el selector de fecha
            }
        });


        // Obtener posición del intent
        Intent recibo = getIntent();
        pos = recibo.getExtras().getInt("posicion");

        // Llenar datos desde la lista usando la clase datos (POJO)
        chara = info.lista.get(pos); // Asumiendo que info.lista contiene objetos de tipo datos

        //llenar campos con los datos guardados
        etNombre.setText(chara.getNombre());
        etApellido.setText(chara.getApellido());
        etFechaNac.setText(chara.getFechaNacimiento());
        chboxRelacion.setChecked(chara.isEstaEnUnaRelacion());
        etEspecie.setText(chara.getEspecie());
        etNacionalidad.setText(chara.getNacionalidad());
        etAfiliacion.setText(chara.getAfiliacion());
        etDescripcion.setText(chara.getDescripcionBreve());

        RadioButton selectedRadioButton = findViewById(chara.getSexo());
        selectedRadioButton.toggle();
    }

    private void guardarDatos() {
        // Obtener valores del formulario
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String fechaNacimiento = etFechaNac.getText().toString();
        boolean estaEnRelacion = chboxRelacion.isChecked();
        String especie = etEspecie.getText().toString();
        String nacionalidad = etNacionalidad.getText().toString();
        String afiliacion = etAfiliacion.getText().toString();
        String descripcion = etDescripcion.getText().toString();

        // Obtener sexo seleccionado
        int sexo;
        int selectedSexoId = radioGroupSexo.getCheckedRadioButtonId();
        if (selectedSexoId != -1) {
            sexo = selectedSexoId;
        }

        // Actualizar datos
        chara.setNombre(nombre);
        chara.setApellido(apellido);
        chara.setFechaNacimiento(fechaNacimiento);
        chara.setEstaEnUnaRelacion(estaEnRelacion);
        chara.setEspecie(especie);
        chara.setNacionalidad(nacionalidad);
        chara.setAfiliacion(afiliacion);
        chara.setDescripcionBreve(descripcion);

        // Mostrar mensaje de confirmación
        Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show();


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
