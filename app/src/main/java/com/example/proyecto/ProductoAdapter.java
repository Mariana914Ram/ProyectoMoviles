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

public class ProductoAdapter extends BaseAdapter {

    Context context;
    List<ModeloProductos> list;
    LayoutInflater inflater;

    public ProductoAdapter(Context context, List<ModeloProductos> list) {
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
        ImageView imageProducto;
        TextView nombreProducto;
        TextView precioProducto;
        TextView stockProducto;
        ImageButton botonEditar, botonEliminar;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_usuario, viewGroup, false);

        ModeloProductos c = list.get(i);

        nombreProducto = itemView.findViewById(R.id.txtProducto);
        imageProducto = itemView.findViewById(R.id.img_Producto);
        precioProducto = itemView.findViewById(R.id.txtPrecio);
        stockProducto = itemView.findViewById(R.id.txtStock);

        botonEditar = (ImageButton) itemView.findViewById(R.id.ibtnEditar);
        botonEditar.setTag(i);
        botonEliminar = (ImageButton) itemView.findViewById(R.id.ibtnEliminar);
        botonEliminar.setTag(i);

        nombreProducto.setText(c.getNombre());
        imageProducto.setImageResource(R.drawable.herramientas);
        precioProducto.setText(c.getPrecio()+"");
        stockProducto.setText(c.getStock()+"");


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