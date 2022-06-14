package com.example.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UsuarioAdapter extends BaseAdapter {

    Context context;
    List<ModeloUsuario> list;

    public UsuarioAdapter(Context context, List<ModeloUsuario> list) {
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
        ImageView imageUsuario;
        TextView nombreUsuario;
        TextView correoUsuario;

        ModeloUsuario c = list.get(i);

        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_usuario, null);
        }
        nombreUsuario = view.findViewById(R.id.txtUsuario);
        imageUsuario = view.findViewById(R.id.img_Usuario);
        correoUsuario = view.findViewById(R.id.txtCorreo);

        nombreUsuario.setText(c.getNombre() + " " + c.getApellidos());
        imageUsuario.setImageResource(R.drawable.user);
        correoUsuario.setText(c.getCorreo());
        return view;
    }
}