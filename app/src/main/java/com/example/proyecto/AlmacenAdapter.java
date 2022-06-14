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
import android.widget.Toast;

import java.util.List;

public class AlmacenAdapter extends BaseAdapter {

    Context context;
    List<ModeloAlmacen> list;
    LayoutInflater inflater;

    public AlmacenAdapter(Context context, List<ModeloAlmacen> list) {
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
        ImageView imageAlmacen;
        TextView nombreAlmacen;
        ImageButton botonEditar, botonEliminar;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_almacen, viewGroup, false);

        ModeloAlmacen c = list.get(i);


        imageAlmacen = itemView.findViewById(R.id.img_Almacen);
        nombreAlmacen = itemView.findViewById(R.id.txtAlmacen);
        botonEditar = (ImageButton) itemView.findViewById(R.id.ibtnEditar);
        botonEditar.setTag(i);
        botonEliminar = (ImageButton) itemView.findViewById(R.id.ibtnEliminar);
        botonEliminar.setTag(i);

        nombreAlmacen.setText(c.getNombre());
        imageAlmacen.setImageResource(c.getImagen());


        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewer) {
                Toast.makeText(context, "HOLA", Toast.LENGTH_SHORT).show();
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
