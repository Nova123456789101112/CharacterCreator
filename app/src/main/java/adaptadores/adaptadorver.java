package adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static Global.info.lista;

import com.example.charactercreator.R;
import com.example.charactercreator.modificar;


public class adaptadorver extends RecyclerView.Adapter<adaptadorver.Miactivy> {

    public Context context;

    public adaptadorver(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Miactivy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.holder_ver, parent, false);
        return new Miactivy(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Miactivy holder, int position) {
        // Configura los datos para cada ítem en la lista
        holder.tv_nombreapellido.setText(lista.get(position).getNombre() + " " + lista.get(position).getApellido());
        holder.tv_afiliacion.setText("Afiliacion: " + lista.get(position).getAfiliacion());


        // Evento de clic para ir a modificar
        holder.itemView.setOnClickListener(v -> {
            Intent modificar = new Intent(context, modificar.class);
            modificar.putExtra("posicion", position);
            context.startActivity(modificar);
        });


    }

    @Override
    public int getItemCount() {
        // devolver el tamaño de la lista
        return lista.size();
    }

    // ViewHolder interno
    public class Miactivy extends RecyclerView.ViewHolder {
        public TextView tv_nombreapellido, tv_afiliacion;

        public Miactivy(@NonNull View itemView) {
            super(itemView);
            tv_nombreapellido = itemView.findViewById(R.id.tv_nombreapeliido);
            tv_afiliacion = itemView.findViewById(R.id.tv_afiliacion);
        }
    }
}
