package com.example.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AlmacenNormalAdapter extends BaseAdapter {

    Context context;
    List<ModeloAlmacen> list;
    LayoutInflater inflater;

    public AlmacenNormalAdapter(Context context, List<ModeloAlmacen> list) {
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


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_normal_almacen, viewGroup, false);

        ModeloAlmacen c = list.get(i);


        imageAlmacen = itemView.findViewById(R.id.img_Almacen);
        nombreAlmacen = itemView.findViewById(R.id.txtAlmacen);


        nombreAlmacen.setText(c.getNombre());
        imageAlmacen.setImageResource(c.getImagen());

        return itemView;
    }
}