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

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        intent.putExtra("buscador", "");
        startActivity(intent);
    }


    public void solicitud_materiales(View view){
        String texto = abrirArchivo("archivoMateriales.txt");
        if(texto.equals("")){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Para crear una petición, deben existir materiales almacenados", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else{

            if(!texto.equals("")){
                String[] material = texto.split("\n\n");
                int contador = 0;
                for(int i=0; i<material.length; i++){
                    String[] parteMaterial = material[i].split("\n");
                    String idAlmacen_temp = "";
                    for(int j=0; j<parteMaterial.length; j++){

                        if(parteMaterial[j].contains("idAlmacen: ")){
                            idAlmacen_temp = parteMaterial[j];
                            idAlmacen_temp = idAlmacen_temp.replace("idAlmacen: ", "");
                        }

                    }

                    if(idAlmacen_temp.equals(almacen.getId()+"")){
                        contador++;
                    }
                }


                if(contador > 0){
                    Intent intent = new Intent(this, Peticion.class);
                    intent.putExtra("almacenInfo", almacen);
                    startActivity(intent);
                }

                else{
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Para crear una petición, deben existir materiales almacenados", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }
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
}