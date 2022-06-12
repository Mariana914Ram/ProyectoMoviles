package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class vista_almacen extends AppCompatActivity {

    ListView ListViewAlmacen;
    List<ModeloAlmacen> list;
    ModeloAlmacen[] array;
    String idUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_almacen);

        ListViewAlmacen=findViewById(R.id.lstv_almacenes);

        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        idUser = preferences.getString("id", "");

        if(idUser.equals("")){
            Toast.makeText(getBaseContext(), "ERROR", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        else{

            String texto = abrirArchivo("archivoAlmacenes.txt");
            if(texto == ""){
                //Ir a vista de que no existe el almacén
            }else {
                String[] almacen = texto.split("\n\n");
                int contador = 0;
                for(int i=0; i<almacen.length; i++){
                    String[] parteAlmacen = almacen[i].split("\n");
                    String id_temp = "";
                    String imagen_temp = "";
                    String nombre_temp = "";
                    for(int j=0; j<parteAlmacen.length; j++){


                        if(parteAlmacen[j].contains("id: ")){
                            id_temp = parteAlmacen[j];
                            id_temp = id_temp.replace("id: ", "");
                        }
                        if(parteAlmacen[j].contains("imagen: ")){
                            imagen_temp = parteAlmacen[j];
                            imagen_temp = imagen_temp.replace("imagen: ", "");
                        }
                        if(parteAlmacen[j].contains("nombre: ")){
                            nombre_temp = parteAlmacen[j];
                            nombre_temp = nombre_temp.replace("nombre: ", "");
                        }


                        String textoAlmacenUsuario = abrirArchivo("archivoAlmacenesUsuarios.txt");
                        if(!textoAlmacenUsuario.equals("")){
                            String[] almacenesUsuarios = textoAlmacenUsuario.split("\n\n");
                            for (int k = 0; k < almacenesUsuarios.length; k++) {
                                String[] parts = almacenesUsuarios[i].split("\n");
                                String idUsuarioAlmacenUsuario = "";
                                String idAlmacenAlmacenUsuario = "";
                                for (int l = 0; l < parts.length; l++) {

                                    if(parteAlmacen[l].contains("idUsuario: ")){
                                        idUsuarioAlmacenUsuario = parteAlmacen[j];
                                        idUsuarioAlmacenUsuario = idUsuarioAlmacenUsuario.replace("idUsuario: ", "");
                                    }
                                    if(parteAlmacen[l].contains("idAlmacen: ")){
                                        idAlmacenAlmacenUsuario = parteAlmacen[j];
                                        idAlmacenAlmacenUsuario = idAlmacenAlmacenUsuario.replace("idUsuario: ", "");
                                    }
                                }
                                if(id_temp.equals(idAlmacenAlmacenUsuario) && idUsuarioAlmacenUsuario.equals(idUser)){
                                    array[contador] = new ModeloAlmacen(Integer.parseInt(id_temp), nombre_temp, Integer.parseInt(imagen_temp));
                                    contador++;
                                }
                            }
                        }

                    }
                }


                if(contador > 0){
                    AlmacenAdapter adapter = new AlmacenAdapter(this, GetData());
                    ListViewAlmacen.setAdapter(adapter);

                    ListViewAlmacen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ModeloAlmacen c = list.get(i);
                            Toast.makeText(getBaseContext(), c.getNombre(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    //Vista de Benites
                }

            }
        }

    }

    private List<ModeloAlmacen> GetData(){
        list = new ArrayList<>();

        for(int i=0; i<array.length; i++){
            list.add(new ModeloAlmacen(array[i].getId(), array[i].getNombre(), R.drawable.almacen));
        }
        return list;
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




    public void agregarAlmacen(View view){

    }
}