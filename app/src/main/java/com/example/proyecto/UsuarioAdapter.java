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

public class UsuarioAdapter extends BaseAdapter {

    Context context;
    List<ModeloUsuario> list;
    LayoutInflater inflater;

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
        ImageButton botonEditar, botonEliminar;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_usuario, viewGroup, false);

        ModeloUsuario c = list.get(i);

        nombreUsuario = itemView.findViewById(R.id.txtUsuario);
        imageUsuario = itemView.findViewById(R.id.img_Usuario);
        correoUsuario = itemView.findViewById(R.id.txtCorreo);

        botonEditar = (ImageButton) itemView.findViewById(R.id.ibtnEditar);
        botonEditar.setTag(i);
        botonEliminar = (ImageButton) itemView.findViewById(R.id.ibtnEliminar);
        botonEliminar.setTag(i);

        nombreUsuario.setText(c.getNombre() + " " + c.getApellidos());
        imageUsuario.setImageResource(R.drawable.user);
        correoUsuario.setText(c.getCorreo());


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