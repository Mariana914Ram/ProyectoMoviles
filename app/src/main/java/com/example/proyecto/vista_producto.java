package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class vista_producto extends AppCompatActivity {

    ListView ListViewProductos;
    List<ModeloProductos> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_producto);


        ListViewProductos=findViewById(R.id.lstv_productos);

        String texto = abrirArchivo("archivoProductos.txt");
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
        if(!texto.equals("")){
            String[] producto = texto.split("\n\n");
            int contador = 0;
            for(int i=0; i<producto.length; i++){
                String[] parteProducto = producto[i].split("\n");
                String id_temp = "";
                String idAlmacen_temp = "";
                String nombre_temp = "";
                String precio_temp = "";
                String stock_temp = "";
                for(int j=0; j<parteProducto.length; j++){


                    if(parteProducto[j].contains("id: ")){
                        id_temp = parteProducto[j];
                        id_temp = id_temp.replace("id: ", "");
                    }
                    if(parteProducto[j].contains("idAlmacen: ")){
                        idAlmacen_temp = parteProducto[j];
                        idAlmacen_temp = idAlmacen_temp.replace("idAlmacen: ", "");
                    }
                    if(parteProducto[j].contains("nombre: ")){
                        nombre_temp = parteProducto[j];
                        nombre_temp = nombre_temp.replace("nombre: ", "");
                    }
                    if(parteProducto[j].contains("precio: ")){
                        precio_temp = parteProducto[j];
                        precio_temp = precio_temp.replace("precio: ", "");
                    }
                    if(parteProducto[j].contains("stock: ")){
                        stock_temp = parteProducto[j];
                        stock_temp = stock_temp.replace("stock: ", "");
                    }

                }

                list.add(new ModeloProductos(Integer.parseInt(id_temp), Integer.parseInt(idAlmacen_temp), nombre_temp, R.drawable.almacen, Integer.parseInt(precio_temp), Integer.parseInt(stock_temp)));
                contador++;
            }


            if(contador > 0){
                ProductoAdapter adapter = new ProductoAdapter(this, list);
                ListViewProductos.setAdapter(adapter);

                ListViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int viewId = view.getId();
                        ModeloProductos c;

                        switch (viewId){
                            case R.id.ibtnEditar:


                                break;
                            case R.id.ibtnEliminar:


                                break;
                        }
                    }
                });
            }

        }
    }




    public void agregarProducto(View view){

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
            Toast.makeText(vista_producto.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
    }
}