package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class AgregarUsuario extends AppCompatActivity {

    EditText nombre, apellidos, correo, password, repetPassword;
    CheckBox tipoA, tipoB;
    TextView tvAlmacen;
    boolean[] selectAlmacen;
    ArrayList<Integer> almacenList = new ArrayList<>();
    String[] almacenArray;
    String tipoUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        nombre = (EditText) findViewById(R.id.et_nombre);
        apellidos = (EditText) findViewById(R.id.et_apellidos);
        correo = (EditText) findViewById(R.id.et_correoGuardar);
        password = (EditText) findViewById(R.id.et_contraGuardar);
        repetPassword = (EditText) findViewById(R.id.et_confirmarContra);
        tipoA = (CheckBox) findViewById(R.id.cbxAdmin);
        tipoB = (CheckBox) findViewById(R.id.cbxNormal);
        tvAlmacen = (TextView) findViewById(R.id.tvAlmacen);

        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        tipoUser = preferences.getString("tipo", "");


        String texto = abrirArchivo("archivoAlmacenes.txt");
        //Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();

        String cadena = "";
        if(!texto.equals("")){
            String[] almacen = texto.split("\n\n");
            for(int i=0; i<almacen.length; i++){
                String[] parteAlmacen = almacen[i].split("\n");
                String nombre_temp = "";
                for(int j=0; j<parteAlmacen.length; j++) {

                    if (parteAlmacen[j].contains("nombre: ")) {
                        nombre_temp = parteAlmacen[j];
                        nombre_temp = nombre_temp.replace("nombre: ", "");

                        cadena = cadena + nombre_temp;
                        if(i+1 != almacen.length){
                            cadena = cadena + ", ";
                        }
                    }
                }
            }
        }

        almacenArray = cadena.split(", ");


        selectAlmacen = new boolean[almacenArray.length];
        tvAlmacen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AgregarUsuario.this
                );

                builder.setTitle("Selecciona los almacenes");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(almacenArray, selectAlmacen, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b){
                            almacenList.add(i);
                            Collections.sort(almacenList);
                        }
                        else{
                            almacenList.remove(Integer.valueOf(i));;
                        }
                    }
                });


                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for(int j=0; j<almacenList.size(); j++){
                            stringBuilder.append(almacenArray[almacenList.get(j)]);

                            if(j != almacenList.size()-1){
                                stringBuilder.append(", ");
                            }
                        }
                        tvAlmacen.setText(stringBuilder.toString());
                    }
                });


                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


                builder.setNeutralButton("Limpiar todo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectAlmacen.length; j++){
                            selectAlmacen[j] = false;
                            almacenList.clear();
                            tvAlmacen.setText("");
                        }
                    }
                });


                builder.show();
            }
        });
    }






    public void registrarUsuario(View view){
        boolean verificar = true;

        if(!tipoA.isChecked() && !tipoB.isChecked()){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debe escoger un tipo de usuario", Snackbar.LENGTH_LONG);
            snackbar.show();
            verificar = false;
        }
        if(nombre.getText().toString().isEmpty()){
            nombre.setError("Campo requerido");
            verificar = false;
        }
        if(apellidos.getText().toString().isEmpty()){
            apellidos.setError("Campo requerido");
            verificar = false;
        }
        if(correo.getText().toString().isEmpty()){
            correo.setError("Campo requerido");
            verificar = false;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Campo requerido");
            verificar = false;
        }
        if(repetPassword.getText().toString().isEmpty()){
            repetPassword.setError("Campo requerido");
            verificar = false;
        }
        if(!password.getText().toString().equals(repetPassword.getText().toString())){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "La contrase??a y la confirmaci??n deben ser iguales", Snackbar.LENGTH_LONG);
            snackbar.show();
            verificar = false;
        }
        if(tvAlmacen.getText().equals("")){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes seleccionar al menos un almac??n", Snackbar.LENGTH_LONG);
            snackbar.show();
            verificar = false;
        }


        if(verificar){
            int idU = 0;
            String texto = "";
            String abrir = abrirArchivo("archivoUsuarios.txt");
            texto = abrir;

            if(abrir == ""){
                texto = texto + "id: " + idU + "\n";
            }else {
                String[] modelos = abrir.split("\n\n");
                for(int i=0; i<modelos.length; i++){
                    String[] parts = modelos[i].split("\n");
                    String id_temp = "";
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), parts[0], Snackbar.LENGTH_LONG);
                    snackbar.show();
                    for(int j=0; j<parts.length; j++){

                        if(parts[j].contains("id: ")){
                            id_temp = parts[j];
                            id_temp = id_temp.replace("id: ", "");
                            if(idU < Integer.parseInt(id_temp.trim())){
                                idU = Integer.parseInt(id_temp.trim());
                            }
                        }
                    }
                }
                idU++;
                texto = texto + "\nid: " + idU + "\n";

            }

            texto = texto + "correo: " + correo.getText().toString().trim() + "\n";
            texto = texto + "contrasena: " + password.getText().toString().trim() + "\n";
            texto = texto + "nombre: " + nombre.getText().toString().trim() + "\n";
            texto = texto + "apellidos: " + apellidos.getText().toString().trim() + "\n";
            if(tipoA.isChecked()){
                texto = texto + "tipo: admin" + "\n";
            }else{
                texto = texto + "tipo: normal" + "\n";
            }
            guardarArchivo(texto, "archivoUsuarios.txt");




            //Relacionar usuario a almac??n
            String[] almacenesSeleccionados = tvAlmacen.getText().toString().split(", ");
            for(int k=0; k<almacenesSeleccionados.length; k++){
                texto = "";
                abrir = abrirArchivo("archivoAlmacenesUsuarios.txt");
                texto = abrir;
                int idAU=0;

                if(abrir == ""){
                    texto = texto + "id: " + idAU + "\n";
                }else {
                    String[] modelos = abrir.split("\n\n");
                    for(int i=0; i<modelos.length; i++){
                        String[] parts = modelos[i].split("\n");
                        String id_temp = "";
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), parts[0], Snackbar.LENGTH_LONG);
                        snackbar.show();
                        for(int j=0; j<parts.length; j++){

                            if(parts[j].contains("id: ")){
                                id_temp = parts[j];
                                id_temp = id_temp.replace("id: ", "");
                                if(idAU < Integer.parseInt(id_temp.trim())){
                                    idAU = Integer.parseInt(id_temp.trim());
                                }
                            }
                        }
                    }
                    idAU++;
                    texto = texto + "\nid: " + idAU + "\n";
                }


                abrir = abrirArchivo("archivoAlmacenes.txt");
                boolean almacenEncontrado = false;
                String id_almacenSeleccionado = "";
                if(!abrir.equals("")){
                    String[] modelos = abrir.split("\n\n");
                    for(int i=0; i<modelos.length; i++){
                        String[] parts = modelos[i].split("\n");
                        String id_temp = "";
                        for(int j=0; j<parts.length; j++){

                            if(parts[j].contains("id: ")){
                                id_temp = parts[j];
                                id_temp = id_temp.replace("id: ", "");
                            }
                            if(parts[j].contains("nombre: ")){
                                String nombre_temp = parts[j];
                                nombre_temp = nombre_temp.replace("nombre: ", "");
                                if(nombre_temp.equals(almacenesSeleccionados[k])){
                                    almacenEncontrado = true;
                                }
                            }
                        }

                        if(almacenEncontrado){
                            almacenEncontrado = false;
                            id_almacenSeleccionado = id_temp;
                        }
                    }
                }

                texto = texto + "idUsuario: " + idU + "\n";
                texto = texto + "idAlmacen: " + id_almacenSeleccionado + "\n";
                guardarArchivo(texto, "archivoAlmacenesUsuarios.txt");
            }


            Intent intent = new Intent(AgregarUsuario.this, vista_usuario.class);
            startActivity(intent);
            finish();
        }
    }



    private void guardarArchivo(String texto, String file){
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(file, Activity.MODE_PRIVATE));
            archivo.write(texto);
            archivo.flush();
            archivo.close();
        } catch (IOException e){
            Toast.makeText(AgregarUsuario.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
    }




    private String abrirArchivo(String file){
        String listaArchivos[] = fileList();
        if(existeArchivo(listaArchivos, file)){
            try {
                //1. Crear la instancia para asociar el archivo a leer
                InputStreamReader archivoInterno = new InputStreamReader(openFileInput(file));

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






    //Men??
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

}