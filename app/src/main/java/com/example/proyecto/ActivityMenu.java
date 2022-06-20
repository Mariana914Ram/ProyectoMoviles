package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityMenu extends AppCompatActivity {

    LinearLayout layoutUsuarios;
    TextView titulo;

    String tipoUser = "", nombreUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        layoutUsuarios = findViewById(R.id.layoutBotonUsuarios);
        titulo = findViewById(R.id.txtTitulo);

        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        tipoUser = preferences.getString("tipo", "");
        nombreUser = preferences.getString("nombre", "");

        if(nombreUser.equals("")){

        }else{
            titulo.setText("¡Te damos la bienvenida " + nombreUser + "!\nElige una opción");
        }

        if(tipoUser.equals("normal")){
            layoutUsuarios.getLayoutParams().height=1;
            layoutUsuarios.requestLayout();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(tipoUser.equals("admin")){
            getMenuInflater().inflate(R.menu.menu_overflow, menu);
            return super.onCreateOptionsMenu(menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu_overflow_normal, menu);
            return super.onCreateOptionsMenu(menu);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(tipoUser.equals("admin")){
            int opc = item.getItemId();
            switch (opc){
                case R.id.itemAlmacen:
                    vistaAlmacen();
                    break;
                case R.id.itemUsuario:
                    vistaUsuario();
                    break;
                case R.id.itemCerrar:
                    cerrarSesion();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }
        else{
            int opc = item.getItemId();
            switch (opc){
                case R.id.itemAlmacen:
                    vistaAlmacen();
                    break;
                case R.id.itemCerrar:
                    cerrarSesion();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }
    }



    public void opcionCerrarSesion(View view){
        cerrarSesion();
    }
    public void cerrarSesion(){
        SharedPreferences preferencias = getSharedPreferences("user.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public void opcionAlmacen(View view){
        vistaAlmacen();
    }
    public void vistaAlmacen(){
        Intent intent = new Intent(this, vista_almacen.class);
        startActivity(intent);
    }


    public void opcionUsuario(View view){
        vistaUsuario();
    }
    public void vistaUsuario(){
        Intent intent = new Intent(this, vista_usuario.class);
        startActivity(intent);
    }
}