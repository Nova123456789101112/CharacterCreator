
package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charactercreator.R;
import Global.info;

public class adaptadoreliminar extends RecyclerView.Adapter<adaptadoreliminar.mieliminar>{

    public Context context;

    // Constructor para inicializar el contexto
    public adaptadoreliminar(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public mieliminar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.holder_eliminar, parent, false);
        return new mieliminar(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mieliminar mielim, int position) {
        final int pos = position;

        // Obtener el nombre de la tarea y fecha de entrega de la lista
        String nombre = info.lista.get(pos).getNombre();
        String afiliacion = info.lista.get(pos).getAfiliacion();

        // Establecer los valores en los TextViews correspondientes
        mielim.tv_nombre.setText(nombre);
        mielim.tv_afiliacion.setText(afiliacion);

        // Configurar el CheckBox para seleccionar y eliminar
        mielim.seleccion.setChecked(false);
        mielim.seleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    info.listaelim.add(info.lista.get(pos)); // Agregar a la lista de eliminación
                } else {
                    info.listaelim.remove(info.lista.get(pos)); // Eliminar de la lista de eliminación
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.lista.size(); // Retorna el tamaño de la lista de elementos
    }

    // ViewHolder interno
    public class mieliminar extends RecyclerView.ViewHolder {
        public TextView tv_nombre, tv_afiliacion;
        public CheckBox seleccion;

        public mieliminar(@NonNull View itemView) {
            super(itemView);
            // Asignar las vistas correspondientes
            tv_nombre = itemView.findViewById(R.id.tv_nombreapeliido);
            tv_afiliacion = itemView.findViewById(R.id.tv_afiliacion);
            seleccion = itemView.findViewById(R.id.chbox_eliminar);
        }
    }
}


