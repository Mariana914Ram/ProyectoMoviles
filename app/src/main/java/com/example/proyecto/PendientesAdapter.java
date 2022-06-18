package com.example.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class PendientesAdapter  extends BaseAdapter {

    Context context;
    List<ModeloPeticionMaterial> list;
    LayoutInflater inflater;

    public PendientesAdapter(Context context, List<ModeloPeticionMaterial> list) {
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
        ImageButton botonAceptar;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_pendientes, viewGroup, false);

        ModeloPeticionMaterial c = list.get(i);

        nombreMaterial = itemView.findViewById(R.id.txtNombreMaterial);
        cantidadMaterial = itemView.findViewById(R.id.txtCantidad);
        imageMaterial = itemView.findViewById(R.id.img_Pendiente);
        fechaSalida = itemView.findViewById(R.id.txtFechaSalida);
        fechaEntrada = itemView.findViewById(R.id.txtFechaEntrada);

        botonAceptar = (ImageButton) itemView.findViewById(R.id.ibtnAceptar);
        botonAceptar.setTag(i);


        nombreMaterial.setText(c.getNombreMaterial());
        cantidadMaterial.setText(c.getCantidad() + " piezas");
        imageMaterial.setImageResource(R.drawable.productos);
        fechaSalida.setText("Salida: " + c.getFechaSalida());
        fechaEntrada.setText("Devolución: " + c.getFechaDevuelto());


        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewer) {
                ((ListView) viewGroup).performItemClick(viewer, i, 0);
            }
        });

        return itemView;

    }
}