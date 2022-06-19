package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String texto = abrirArchivo("archivoUsuarios.txt");
        if(texto.equals("")){
            texto = "id: " + "0" + "\n";
            texto = texto + "correo: " + "supervisor@supervisor.com" + "\n";
            texto = texto + "contrasena: " + "1234567890" + "\n";
            texto = texto + "nombre: " + "Supervisor" + "\n";
            texto = texto + "apellidos: " + "Supervisor" + "\n";
            texto = texto + "tipo: admin" + "\n";

            guardarArchivo(texto, "archivoUsuarios.txt");
        }

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


    private String abrirArchivo(String nombre){
        String listaArchivos[] = fileList();
        if(existeArchivo(listaArchivos, nombre)){
            try {
                //1. Crear la instancia para asociar el archivo a leer
                InputStreamReader archivoInterno = new InputStreamReader(openFileInput(nombre));

                //2. Crear la instancia para leer el contenido del archivo
                BufferedReader leerArchivo = new BufferedReader(archivoInterno);


                String linea = leerArchivo.readLine();    //3. Leer el contenido del archivo y colocarlo en una variable
                String textoLeido = "";   //4. Variable que guarda el contenido

                //5. Ciclo repetitivo para leer contenido del archivo
                while (linea != null){
                    textoLeido += linea + "\n";
                    linea = leerArchivo.readLine();
                }

                //6. Cerrar el archivo
                leerArchivo.close();

                //7. Cerrar el flujo del archivo
                archivoInterno.close();

                return textoLeido;
            } catch (IOException e){
                return "";
            }
        }
        else{
            return "";
        }
    }


    private boolean existeArchivo(String[] archivos, String archivoBuscado){
        for(int i=0; i<archivos.length; i++){
            if(archivoBuscado.equals(archivos[i])){
                return true;
            }
        }
        return false;
    }

    private void guardarArchivo(String texto, String file){
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(file, Activity.MODE_PRIVATE));
            archivo.write(texto);
            archivo.flush();
            archivo.close();
        } catch (IOException e){
            Toast.makeText(MainActivity.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
    }
}
