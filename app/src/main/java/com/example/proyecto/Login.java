package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {

    private EditText mail, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.et_correo);
        password = findViewById(R.id.et_contra);

    }


    public void ingresarMenu(View view){

        if(mail.getText().toString().trim() == "" || password.getText().toString().trim() == ""){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debe llenar el correo y la contraseña", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else{
            String abrir = abrirArchivo();
            if(abrir != ""){
                String nombre_seleccionado = "";
                String id_seleccionado = "";
                String correo_seleccionado = "";
                String contrasena_seleccionado = "";
                String tipo_seleccionado = "";
                String apellido_seleccionado = "";

                String[] modelos = abrir.split("\n\n");
                for(int i=0; i<modelos.length; i++){
                    String[] parts = modelos[i].split("\n");
                    String id_temp = "";
                    String correo_temp = "";
                    String contrasena_temp = "";
                    String nombre_temp = "";
                    String tipo_temp = "";
                    String apellido_temp = "";
                    for(int j=0; j<parts.length; j++){


                        if(parts[j].contains("id: ")){
                            id_temp = parts[j];
                            id_temp = id_temp.replace("id: ", "");
                        }
                        if(parts[j].contains("correo: ")){
                            correo_temp = parts[j];
                            correo_temp = correo_temp.replace("correo: ", "");
                        }
                        if(parts[j].contains("contrasena: ")){
                            contrasena_temp = parts[j];
                            contrasena_temp = contrasena_temp.replace("contrasena: ", "");
                        }
                        if(parts[j].contains("nombre: ")){
                            nombre_temp = parts[j];
                            nombre_temp = nombre_temp.replace("nombre: ", "");
                        }
                        if(parts[j].contains("tipo: ")){
                            tipo_temp = parts[j];
                            tipo_temp = tipo_temp.replace("tipo: ", "");
                        }
                        if(parts[j].contains("apellidos: ")){
                            apellido_temp = parts[j];
                            apellido_temp = apellido_temp.replace("apellidos: ", "");
                        }

                    }
                    if(contrasena_temp.trim().equals(password.getText().toString().trim()) && correo_temp.trim().equals(mail.getText().toString().trim())){
                        id_seleccionado = id_temp.trim();
                        correo_seleccionado = correo_temp.trim();
                        contrasena_seleccionado = contrasena_temp.trim();
                        nombre_seleccionado = nombre_temp.trim();
                        tipo_seleccionado = tipo_temp.trim();
                        apellido_seleccionado = apellido_temp.trim();
                    }
                }

                if(!id_seleccionado.equals("") && !correo_seleccionado.equals("") && !contrasena_seleccionado.equals("") && !nombre_seleccionado.equals("") && !tipo_seleccionado.equals("") && !apellido_seleccionado.equals("")){
                    IngresoAplicacion usuario = new IngresoAplicacion(id_seleccionado, correo_seleccionado, contrasena_seleccionado, nombre_seleccionado, apellido_seleccionado, tipo_seleccionado, true);

                    guardarPreferencias(usuario);
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No se encontró al usuario, verifique sus datos", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
            else{
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No se encontró al usuario, verifique sus datos", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }



    public void registrarU(View view){
        Intent intent = new Intent(this, Registrar.class);
        startActivity(intent);
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


}