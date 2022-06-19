package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlmacenInicio extends AppCompatActivity {

    LinearLayout layoutMaterial, layoutSalidaMaterial;

    String tipoUser = "";
    ModeloAlmacen almacen;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacen_inicio);

        layoutMaterial = findViewById(R.id.layoutMateriales);
        layoutSalidaMaterial = findViewById(R.id.layoutSalidaMateriales);

        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        tipoUser = preferences.getString("tipo", "");

        if(tipoUser.equals("normal")){
            layoutMaterial.getLayoutParams().height=1;
            layoutMaterial.requestLayout();

            layoutSalidaMaterial.getLayoutParams().height=1;
            layoutSalidaMaterial.requestLayout();
        }


        almacen = (ModeloAlmacen) getIntent().getSerializableExtra("almacenInfo");
        titulo = (TextView) findViewById(R.id.txtTituloAlmacen);

        titulo.setText("Bienvenido al almacén " + almacen.getNombre());
    }


    public void materiales(View view){
        Intent intent = new Intent(this, vista_material.class);
        intent.putExtra("almacenInfo", almacen);
        startActivity(intent);
    }


    public void solicitud_materiales(View view){
        Intent intent = new Intent(this, Peticion.class);
        intent.putExtra("almacenInfo", almacen);
        startActivity(intent);
    }


    public void peticion_materiales(View view){
        Intent intent = new Intent(this, vista_responderSolicitudes.class);
        intent.putExtra("almacenInfo", almacen);
        startActivity(intent);
    }


    public void pendientes(View view){
        Intent intent = new Intent(this, Pendientes.class);
        intent.putExtra("almacenInfo", almacen);
        startActivity(intent);
    }


    public void historial(View view){
        Intent intent = new Intent(this, Historial.class);
        intent.putExtra("almacenInfo", almacen);
        startActivity(intent);
    }
}