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

public class MaterialAdapter extends BaseAdapter {

    Context context;
    List<ModeloMateriales> list;
    LayoutInflater inflater;

    public MaterialAdapter(Context context, List<ModeloMateriales> list) {
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
        TextView precioMaterial;
        TextView stockMaterial;
        ImageButton botonEditar, botonEliminar;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_materiales, viewGroup, false);

        ModeloMateriales c = list.get(i);

        nombreMaterial = itemView.findViewById(R.id.txtMaterial);
        imageMaterial = itemView.findViewById(R.id.img_Material);
        stockMaterial = itemView.findViewById(R.id.txtStock);

        botonEditar = (ImageButton) itemView.findViewById(R.id.ibtnEditar);
        botonEditar.setTag(i);
        botonEliminar = (ImageButton) itemView.findViewById(R.id.ibtnEliminar);
        botonEliminar.setTag(i);

        nombreMaterial.setText(c.getNombre());
        imageMaterial.setImageResource(R.drawable.herramientas);
        stockMaterial.setText("Cantidad disponible: " + c.getStock()+"");


        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewer) {
                ((ListView) viewGroup).performItemClick(viewer, i, 0);
            }
        });


        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewer) {
                ((ListView) viewGroup).performItemClick(viewer, i, 0);
            }
        });
        return itemView;

    }
}
