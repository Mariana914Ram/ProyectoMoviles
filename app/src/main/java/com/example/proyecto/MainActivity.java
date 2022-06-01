package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent;
                if(nuevoUsuario()){
                    intent = new Intent(MainActivity.this, ActivityMenu.class);

                }else{
                    intent = new Intent(MainActivity.this, Login.class);
                }
                //Ejecutar Activity correspondiente
                startActivity(intent);

                //Eliminar el Activity actual
                finish();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 2000);
    }


    //SharedPreferences permite almacenar valores de configuración dentro del dispositivo
    private boolean nuevoUsuario(){
        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        return preferences.getBoolean("registrado", false);
    }
}