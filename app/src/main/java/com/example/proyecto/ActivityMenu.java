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
import android.widget.Toast;

public class ActivityMenu extends AppCompatActivity {

    LinearLayout layoutUsuarios;

    String tipoUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        layoutUsuarios = findViewById(R.id.layoutBotonUsuarios);

        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        tipoUser = preferences.getString("tipo", "");

        if(tipoUser.equals("normal")){
            layoutUsuarios.getLayoutParams().height=1;
            layoutUsuarios.requestLayout();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int opc = item.getItemId();
        switch (opc){
            case R.id.itemAlmacen:
                vistaAlmacen();
            case R.id.itemUsuario:
                vistaUsuario();
            case R.id.itemCerrar:
                cerrarSesion();
                break;
        }
        return super.onOptionsItemSelected(item);
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