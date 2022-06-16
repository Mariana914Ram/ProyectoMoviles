package com.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class vista_usuario extends AppCompatActivity {

    ListView ListViewUsuario;
    List<ModeloUsuario> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuario);


        ListViewUsuario=findViewById(R.id.lstv_usuarios);

        String texto = abrirArchivo("archivoUsuarios.txt");
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
        if(texto == ""){
            //Ir a vista de que no existe el almacén
        }else {
            String[] usuario = texto.split("\n\n");
            int contador = 0;
            for(int i=0; i<usuario.length; i++){
                String[] parteUsuario = usuario[i].split("\n");
                String id_temp = "";
                String correo_temp = "";
                String nombre_temp = "";
                String apellidos_temp = "";
                String tipo_temp = "";
                for(int j=0; j<parteUsuario.length; j++){


                    if(parteUsuario[j].contains("id: ")){
                        id_temp = parteUsuario[j];
                        id_temp = id_temp.replace("id: ", "");
                    }
                    if(parteUsuario[j].contains("nombre: ")){
                        nombre_temp = parteUsuario[j];
                        nombre_temp = nombre_temp.replace("nombre: ", "");
                    }
                    if(parteUsuario[j].contains("correo: ")){
                        correo_temp = parteUsuario[j];
                        correo_temp = correo_temp.replace("correo: ", "");
                    }
                    if(parteUsuario[j].contains("apellidos: ")){
                        apellidos_temp = parteUsuario[j];
                        apellidos_temp = apellidos_temp.replace("apellidos: ", "");
                    }
                    if(parteUsuario[j].contains("tipo: ")){
                        tipo_temp = parteUsuario[j];
                        tipo_temp = tipo_temp.replace("tipo: ", "");
                    }

                }

                list.add(new ModeloUsuario(Integer.parseInt(id_temp), correo_temp, "", nombre_temp, apellidos_temp, tipo_temp, R.drawable.almacen));
                contador++;
            }


            if(contador > 0){
                UsuarioAdapter adapter = new UsuarioAdapter(this, list);
                ListViewUsuario.setAdapter(adapter);

                ListViewUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int viewId = view.getId();
                        ModeloUsuario c;

                        switch (viewId){
                            case R.id.ibtnEditar:
                                c = list.get(i);
                                Intent intent = new Intent(vista_usuario.this, Editar_usuario.class);
                                intent.putExtra("usuarioInfo", c);
                                startActivity(intent);

                                break;
                            case R.id.ibtnEliminar:
                                c = list.get(i);

                                AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_usuario.this);
                                mydialog.setTitle("Eliminar usuario "+ c.getNombre() + " " + c.getApellidos());

                                final TextView almacenText = new TextView(vista_usuario.this);
                                almacenText.setWidth(1000);
                                almacenText.setText("¿Estás seguro de eliminar a este usuario?");
                                LinearLayout layout = new LinearLayout(vista_usuario.this);
                                layout.setPadding(40, 20, 40, 0);
                                layout.addView(almacenText);
                                mydialog.setView(layout);

                                mydialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        boolean validar = true;
                                        String texto = "";
                                        String abrir = abrirArchivo("archivoUsuarios.txt");
                                        if(abrir == ""){
                                            Toast.makeText(vista_usuario.this, "Hubo un error, intente de nuevo", Toast.LENGTH_LONG).show();
                                        }else {
                                            String[] modelos = abrir.split("\n\n");
                                            for(int k=0; k<modelos.length; k++){
                                                validar = true;
                                                String[] parts = modelos[k].split("\n");
                                                String textoTemporal = "";
                                                for(int j=0; j<parts.length; j++){

                                                    if(parts[j].contains("id: ")){
                                                        String id_temp = parts[j];
                                                        id_temp = id_temp.replace("id: ", "");
                                                        if(id_temp.equals(c.getId()+"")){
                                                            validar = false;
                                                        }
                                                    }
                                                    if(parts[j].contains("id: ")){
                                                        if(k == 0){
                                                            textoTemporal = textoTemporal + parts[j] + "\n";
                                                        }
                                                        else{
                                                            textoTemporal = textoTemporal + "\n" + parts[j] + "\n";
                                                        }
                                                    }
                                                    else{
                                                        textoTemporal = textoTemporal + parts[j] + "\n";
                                                    }
                                                }
                                                if(validar){
                                                    texto = texto + textoTemporal;
                                                }
                                            }
                                            guardarArchivo(texto, "archivoUsuarios.txt");

                                            validar = true;
                                            texto = "";
                                            abrir = abrirArchivo("archivoAlmacenesUsuarios.txt");
                                            if(abrir == ""){
                                                Toast.makeText(vista_usuario.this, "Hubo un error, intente de nuevo", Toast.LENGTH_LONG).show();
                                            }else {
                                                modelos = abrir.split("\n\n");
                                                for(int k=0; k<modelos.length; k++){
                                                    validar = true;
                                                    String[] parts = modelos[k].split("\n");
                                                    String textoTemporal = "";
                                                    for(int j=0; j<parts.length; j++){

                                                        if(parts[j].contains("idUsuario: ")){
                                                            String idAlmacen_temp = parts[j];
                                                            idAlmacen_temp = idAlmacen_temp.replace("idUsuario: ", "");
                                                            if(idAlmacen_temp.equals(c.getId())){
                                                                validar = false;
                                                            }
                                                        }
                                                        if(parts[j].contains("id: ")){
                                                            if(k == 0){
                                                                textoTemporal = textoTemporal + parts[j] + "\n";
                                                            }
                                                            else{
                                                                textoTemporal = textoTemporal + "\n" + parts[j] + "\n";
                                                            }
                                                        }
                                                        else{
                                                            textoTemporal = textoTemporal + parts[j] + "\n";
                                                        }
                                                    }
                                                    if(validar){
                                                        texto = texto + textoTemporal;
                                                    }
                                                }
                                                guardarArchivo(texto, "archivoAlmacenesUsuarios.txt");
                                            }


                                            Intent intent = new Intent(vista_usuario.this, vista_usuario.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });

                                mydialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                mydialog.show();
                                break;
                        }
                    }
                });
            }
            else{
                //Vista de Benites
            }

        }
    }



    public void registrarUsuario(View view){
        Intent intent = new Intent(this, AgregarUsuario.class);
        startActivity(intent);
        finish();
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
            Toast.makeText(vista_usuario.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
    }
}