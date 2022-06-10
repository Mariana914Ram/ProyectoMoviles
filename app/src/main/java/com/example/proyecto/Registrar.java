package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Registrar extends AppCompatActivity {

    EditText nombre, apellidos, correo, password, repetPassword;
    CheckBox tipoA, tipoB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        nombre = (EditText) findViewById(R.id.et_nombre);
        apellidos = (EditText) findViewById(R.id.et_apellidos);
        correo = (EditText) findViewById(R.id.et_correoGuardar);
        password = (EditText) findViewById(R.id.et_contraGuardar);
        repetPassword = (EditText) findViewById(R.id.et_confirmarContra);
        tipoA = (CheckBox) findViewById(R.id.cbxAdmin);
        tipoB = (CheckBox) findViewById(R.id.cbxNormal);
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
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "La contraseña y la confirmación deben ser iguales", Snackbar.LENGTH_LONG);
            snackbar.show();
            verificar = false;
        }


        if(verificar){
            int id = 0;
            String texto = "";
            String abrir = abrirArchivo();
            texto = abrir;

            if(abrir == ""){
                texto = texto + "id: " + id + "\n";
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
                            if(id < Integer.parseInt(id_temp.trim())){
                                id = Integer.parseInt(id_temp.trim());
                            }
                        }
                    }
                }
                id++;
                texto = texto + "\nid: " + id + "\n";

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), id+"", Snackbar.LENGTH_LONG);
                snackbar.show();

            }

            String tipo = "";

            texto = texto + "correo: " + correo.getText().toString().trim() + "\n";
            texto = texto + "contrasena: " + password.getText().toString().trim() + "\n";
            texto = texto + "nombre: " + nombre.getText().toString().trim() + "\n";
            texto = texto + "apellidos: " + apellidos.getText().toString().trim() + "\n";
            if(tipoA.isChecked()){
                texto = texto + "tipo: admin" + "\n";
                tipo = "admin";
            }else{
                texto = texto + "tipo: normal" + "\n";
                tipo = "normal";
            }
            guardarArchivo(texto);


            IngresoAplicacion usuario = new IngresoAplicacion(id+"", correo.getText().toString().trim(), password.getText().toString().trim(), nombre.getText().toString().trim(), apellidos.getText().toString().trim(), tipo, true);

            guardarPreferencias(usuario);
            Intent intent = new Intent(Registrar.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



    private void guardarArchivo(String texto){
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("archivoUsuarios.txt", Activity.MODE_PRIVATE));
            archivo.write(texto);
            archivo.flush();
            archivo.close();
        } catch (IOException e){
            Toast.makeText(Registrar.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
    }




    private String abrirArchivo(){
        String listaArchivos[] = fileList();
        if(existeArchivo(listaArchivos, "archivoUsuarios.txt")){
            try {
                //1. Crear la instancia para asociar el archivo a leer
                InputStreamReader archivoInterno = new InputStreamReader(openFileInput("archivoUsuarios.txt"));

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




    private void guardarPreferencias(IngresoAplicacion usr){
        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", usr.getId());
        editor.putString("correo", usr.getCorreo());
        editor.putString("contrasena", usr.getContrasena());
        editor.putString("nombre", usr.getNombre());
        editor.putString("apellidos", usr.getApellidos());
        editor.putString("tipo", usr.getTipo());
        editor.putBoolean("registrado", usr.isRegistrado());
        editor.apply();
    }
}