package com.example.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class HistorialAdapter extends BaseAdapter {

    Context context;
    List<ModeloPeticionMaterial> list;
    LayoutInflater inflater;

    public HistorialAdapter(Context context, List<ModeloPeticionMaterial> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageMaterial;
        TextView nombreMaterial;
        TextView cantidadMaterial;
        TextView fechaSalida;
        TextView fechaEntrada;
        TextView status;
        TextView descripcion;
        ImageButton botonEliminar;
        LinearLayout layoutEliminar;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_historial, viewGroup, false);

        ModeloPeticionMaterial c = list.get(i);

        nombreMaterial = itemView.findViewById(R.id.txtNombreMaterial);
        cantidadMaterial = itemView.findViewById(R.id.txtCantidad);
        imageMaterial = itemView.findViewById(R.id.img_Historial);
        fechaSalida = itemView.findViewById(R.id.txtFechaSalida);
        fechaEntrada = itemView.findViewById(R.id.txtFechaEntrada);
        status = itemView.findViewById(R.id.txtStatus);
        descripcion = itemView.findViewById(R.id.txtDescripcion);
        layoutEliminar = itemView.findViewById(R.id.layoutEliminar);

        botonEliminar = (ImageButton) itemView.findViewById(R.id.ibtnEliminar);
        botonEliminar.setTag(i);


        nombreMaterial.setText(c.getNombreMaterial());
        cantidadMaterial.setText(c.getCantidad() + " piezas");
        if(c.getStatus().equals("pendiente")){
            imageMaterial.setImageResource(R.drawable.naranja);
        }
        else if(c.getStatus().equals("aceptado")){
            imageMaterial.setImageResource(R.drawable.verde);

            if(c.getVolver().equals("volver")){
                layoutEliminar.getLayoutParams().height=1;
                layoutEliminar.requestLayout();
            }
        }
        else if(c.getStatus().equals("devuelto")){
            imageMaterial.setImageResource(R.drawable.azul);
        }
        else{
            imageMaterial.setImageResource(R.drawable.rojo);
        }
        fechaSalida.setText("Salida: " + c.getFechaSalida());
        fechaEntrada.setText("Devolución: " + c.getFechaDevuelto());
        status.setText(c.getStatus());
        descripcion.setText(c.getDescripcion());


        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewer) {
                ((ListView) viewGroup).performItemClick(viewer, i, 0);
            }
        });

        return itemView;

    }
}
