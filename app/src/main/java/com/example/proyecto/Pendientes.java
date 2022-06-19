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

public class Pendientes extends AppCompatActivity {

    ListView ListViewPeticionesMateriales;
    List<ModeloPeticionMaterial> list = new ArrayList<>();
    List<ModeloPeticionMaterial> listaCompleta = new ArrayList<>();
    List<ModeloMateriales> listMateriales = new ArrayList<>();
    ModeloAlmacen almacen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendientes);


        ListViewPeticionesMateriales=findViewById(R.id.lstv_pendientes);

        almacen = (ModeloAlmacen) getIntent().getSerializableExtra("almacenInfo");

        String texto = abrirArchivo("archivoPeticionMaterial.txt");
        //Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
        if(!texto.equals("")){
            String[] material = texto.split("\n\n");
            int contador = 0;
            for(int i=0; i<material.length; i++){
                String[] parteMaterial = material[i].split("\n");
                String id_temp = "";
                String idUsuario_temp = "";
                String idAlmacen_temp = "";
                String idMaterial_temp = "";
                String nombreUsuario_temp = "";
                String nombreMaterial_temp = "";
                String cantidad_temp = "";
                String volver_temp = "";
                String motivo_temp = "";
                String fecha_temp = "";
                String fechaSalida_temp = "";
                String fechaDevuelto_temp = "";
                String status_temp = "";
                String descripcion_temp = "";

                for(int j=0; j<parteMaterial.length; j++){

                    if(parteMaterial[j].contains("id: ")){
                        id_temp = parteMaterial[j];
                        id_temp = id_temp.replace("id: ", "");
                    }
                    if(parteMaterial[j].contains("idUsuario: ")){
                        idUsuario_temp = parteMaterial[j];
                        idUsuario_temp = idUsuario_temp.replace("idUsuario: ", "");
                    }
                    if(parteMaterial[j].contains("idAlmacen: ")){
                        idAlmacen_temp = parteMaterial[j];
                        idAlmacen_temp = idAlmacen_temp.replace("idAlmacen: ", "");
                    }
                    if(parteMaterial[j].contains("idMaterial: ")){
                        idMaterial_temp = parteMaterial[j];
                        idMaterial_temp = idMaterial_temp.replace("idMaterial: ", "");
                    }
                    if(parteMaterial[j].contains("nombreUsuario: ")){
                        nombreUsuario_temp = parteMaterial[j];
                        nombreUsuario_temp = nombreUsuario_temp.replace("nombreUsuario: ", "");
                    }
                    if(parteMaterial[j].contains("nombreMaterial: ")){
                        nombreMaterial_temp = parteMaterial[j];
                        nombreMaterial_temp = nombreMaterial_temp.replace("nombreMaterial: ", "");
                    }
                    if(parteMaterial[j].contains("cantidad: ")){
                        cantidad_temp = parteMaterial[j];
                        cantidad_temp = cantidad_temp.replace("cantidad: ", "");
                    }
                    if(parteMaterial[j].contains("volver: ")){
                        volver_temp = parteMaterial[j];
                        volver_temp = volver_temp.replace("volver: ", "");
                    }
                    if(parteMaterial[j].contains("motivo: ")){
                        motivo_temp = parteMaterial[j];
                        motivo_temp = motivo_temp.replace("motivo: ", "");
                    }
                    if(parteMaterial[j].contains("fecha: ")){
                        fecha_temp = parteMaterial[j];
                        fecha_temp = fecha_temp.replace("fecha: ", "");
                    }
                    if(parteMaterial[j].contains("fechaSalida: ")){
                        fechaSalida_temp = parteMaterial[j];
                        fechaSalida_temp = fechaSalida_temp.replace("fechaSalida: ", "");
                    }
                    if(parteMaterial[j].contains("fechaDevuelto: ")){
                        fechaDevuelto_temp = parteMaterial[j];
                        fechaDevuelto_temp = fechaDevuelto_temp.replace("fechaDevuelto: ", "");
                    }
                    if(parteMaterial[j].contains("status: ")){
                        status_temp = parteMaterial[j];
                        status_temp = status_temp.replace("status: ", "");
                    }
                    if(parteMaterial[j].contains("descripcion: ")){
                        descripcion_temp = parteMaterial[j];
                        descripcion_temp = descripcion_temp.replace("descripcion: ", "");
                    }
                }

                if(idAlmacen_temp.equals(almacen.getId()+"") && status_temp.equals("aceptado")){
                    list.add(new ModeloPeticionMaterial(Integer.parseInt(id_temp), Integer.parseInt(idUsuario_temp), Integer.parseInt(idAlmacen_temp), Integer.parseInt(idMaterial_temp), nombreUsuario_temp, nombreMaterial_temp, Integer.parseInt(cantidad_temp), volver_temp, motivo_temp, fecha_temp, fechaSalida_temp, fechaDevuelto_temp, status_temp, descripcion_temp));
                    contador++;
                }
            }


            if(contador > 0){
                PendientesAdapter adapter = new PendientesAdapter(this, list);
                ListViewPeticionesMateriales.setAdapter(adapter);

                ListViewPeticionesMateriales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int viewId = view.getId();
                        ModeloPeticionMaterial c;

                        switch (viewId){
                            case R.id.ibtnAceptar:
                                c = list.get(i);
                                modalAceptar(c);

                                break;
                        }
                    }
                });
            }
        }
    }



    private void modalAceptar(ModeloPeticionMaterial c){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(Pendientes.this);
        mydialog = new AlertDialog.Builder(Pendientes.this);
        mydialog.setTitle("Aceptar salida de material");

        final TextView almacenText = new TextView(Pendientes.this);
        almacenText.setWidth(1000);
        almacenText.setText("¿El usuario " + c.getNombreUsuario() + " ha devuelto las " + c.getCantidad() + " piezas prestadas");
        final LinearLayout layout = new LinearLayout(Pendientes.this);
        layout.setPadding(40, 20, 40, 0);
        layout.addView(almacenText);
        mydialog.setView(layout);

        mydialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                botonAceptar(c);
            }
        });

        mydialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        mydialog.show();
    }




    private void botonAceptar(ModeloPeticionMaterial c){
        cargarDatosEnLista();

        String texto = "";
        for(int i=0; i<listaCompleta.size(); i++){
            if(i==0){
                texto = texto + "id: " + listaCompleta.get(i).getId() + "\n";
            }
            else{
                texto = texto + "\nid: " + listaCompleta.get(i).getId() + "\n";
            }
            texto = texto + "idUsuario: " + listaCompleta.get(i).getIdUsuario() + "\n";
            texto = texto + "idAlmacen: " + listaCompleta.get(i).getIdAlmacen() + "\n";
            texto = texto + "idMaterial: " + listaCompleta.get(i).getIdMaterial() + "\n";
            texto = texto + "nombreUsuario: " + listaCompleta.get(i).getNombreUsuario() + "\n";
            texto = texto + "nombreMaterial: " + listaCompleta.get(i).getNombreMaterial() + "\n";
            texto = texto + "cantidad: " + listaCompleta.get(i).getCantidad() + "\n";
            texto = texto + "volver: " + listaCompleta.get(i).getVolver() + "\n";
            texto = texto + "motivo: " + listaCompleta.get(i).getMotivo() + "\n";
            texto = texto + "fecha: " + listaCompleta.get(i).getFecha() + "\n";
            texto = texto + "fechaSalida: " + listaCompleta.get(i).getFechaSalida() + "\n";
            texto = texto + "fechaDevuelto: " + listaCompleta.get(i).getFechaDevuelto() + "\n";
            if(listaCompleta.get(i).getId() == c.getId()){
                texto = texto + "status: " + "devuelto" + "\n";
                texto = texto + "descripcion: " + "Ha devuelto el material prestado" + "\n";
            }
            else{
                texto = texto + "status: " + listaCompleta.get(i).getStatus() + "\n";
                texto = texto + "descripcion: " + listaCompleta.get(i).getDescripcion() + "\n";
            }
        }
        guardarArchivo(texto, "archivoPeticionMaterial.txt");



        cargarDatosEnListaMaterial();
        texto = "";
        for(int i=0; i<listMateriales.size(); i++){
            if(i==0){
                texto = "id: " + listMateriales.get(i).getId() + "\n";
            }
            else{
                texto = texto + "\nid: " + listMateriales.get(i).getId() + "\n";
            }
            texto = texto + "idAlmacen: " + listMateriales.get(i).getIdAlmacen() + "\n";
            texto = texto + "nombre: " + listMateriales.get(i).getNombre() + "\n";
            if(c.getIdMaterial() == listMateriales.get(i).getId()){
                int stock_regresado = listMateriales.get(i).getStock() + c.getCantidad();
                texto = texto + "stock: " + stock_regresado + "\n";
            }
            else{
                texto = texto + "stock: " + listMateriales.get(i).getStock() + "\n";
            }

        }
        guardarArchivo(texto, "archivoMateriales.txt");


        Intent intent = new Intent(Pendientes.this, Pendientes.class);
        intent.putExtra("almacenInfo", almacen);
        startActivity(intent);
        finish();
    }




    private void cargarDatosEnLista(){
        listaCompleta.clear();

        String texto = abrirArchivo("archivoPeticionMaterial.txt");
        if(!texto.equals("")){
            String[] material = texto.split("\n\n");
            for(int i=0; i<material.length; i++){
                String[] parteMaterial = material[i].split("\n");
                String id_temp = "";
                String idUsuario_temp = "";
                String idAlmacen_temp = "";
                String idMaterial_temp = "";
                String nombreUsuario_temp = "";
                String nombreMaterial_temp = "";
                String cantidad_temp = "";
                String volver_temp = "";
                String motivo_temp = "";
                String fecha_temp = "";
                String fechaSalida_temp = "";
                String fechaDevuelto_temp = "";
                String status_temp = "";
                String descripcion_temp = "";

                for(int j=0; j<parteMaterial.length; j++){

                    if(parteMaterial[j].contains("id: ")){
                        id_temp = parteMaterial[j];
                        id_temp = id_temp.replace("id: ", "");
                    }
                    if(parteMaterial[j].contains("idUsuario: ")){
                        idUsuario_temp = parteMaterial[j];
                        idUsuario_temp = idUsuario_temp.replace("idUsuario: ", "");
                    }
                    if(parteMaterial[j].contains("idAlmacen: ")){
                        idAlmacen_temp = parteMaterial[j];
                        idAlmacen_temp = idAlmacen_temp.replace("idAlmacen: ", "");
                    }
                    if(parteMaterial[j].contains("idMaterial: ")){
                        idMaterial_temp = parteMaterial[j];
                        idMaterial_temp = idMaterial_temp.replace("idMaterial: ", "");
                    }
                    if(parteMaterial[j].contains("nombreUsuario: ")){
                        nombreUsuario_temp = parteMaterial[j];
                        nombreUsuario_temp = nombreUsuario_temp.replace("nombreUsuario: ", "");
                    }
                    if(parteMaterial[j].contains("nombreMaterial: ")){
                        nombreMaterial_temp = parteMaterial[j];
                        nombreMaterial_temp = nombreMaterial_temp.replace("nombreMaterial: ", "");
                    }
                    if(parteMaterial[j].contains("cantidad: ")){
                        cantidad_temp = parteMaterial[j];
                        cantidad_temp = cantidad_temp.replace("cantidad: ", "");
                    }
                    if(parteMaterial[j].contains("volver: ")){
                        volver_temp = parteMaterial[j];
                        volver_temp = volver_temp.replace("volver: ", "");
                    }
                    if(parteMaterial[j].contains("motivo: ")){
                        motivo_temp = parteMaterial[j];
                        motivo_temp = motivo_temp.replace("motivo: ", "");
                    }
                    if(parteMaterial[j].contains("fecha: ")){
                        fecha_temp = parteMaterial[j];
                        fecha_temp = fecha_temp.replace("fecha: ", "");
                    }
                    if(parteMaterial[j].contains("fechaSalida: ")){
                        fechaSalida_temp = parteMaterial[j];
                        fechaSalida_temp = fechaSalida_temp.replace("fechaSalida: ", "");
                    }
                    if(parteMaterial[j].contains("fechaDevuelto: ")){
                        fechaDevuelto_temp = parteMaterial[j];
                        fechaDevuelto_temp = fechaDevuelto_temp.replace("fechaDevuelto: ", "");
                    }
                    if(parteMaterial[j].contains("status: ")){
                        status_temp = parteMaterial[j];
                        status_temp = status_temp.replace("status: ", "");
                    }
                    if(parteMaterial[j].contains("descripcion: ")){
                        descripcion_temp = parteMaterial[j];
                        descripcion_temp = descripcion_temp.replace("descripcion: ", "");
                    }
                }

                listaCompleta.add(new ModeloPeticionMaterial(Integer.parseInt(id_temp), Integer.parseInt(idUsuario_temp), Integer.parseInt(idAlmacen_temp), Integer.parseInt(idMaterial_temp), nombreUsuario_temp, nombreMaterial_temp, Integer.parseInt(cantidad_temp), volver_temp, motivo_temp, fecha_temp, fechaSalida_temp, fechaDevuelto_temp, status_temp, descripcion_temp));
            }
        }
    }





    private void cargarDatosEnListaMaterial(){
        listMateriales.clear();

        String texto = abrirArchivo("archivoMateriales.txt");
        if(!texto.equals("")){
            String[] material = texto.split("\n\n");
            for(int i=0; i<material.length; i++){
                String[] parteMaterial = material[i].split("\n");
                String id_temp = "";
                String idAlmacen_temp = "";
                String nombre_temp = "";
                String stock_temp = "";
                for(int j=0; j<parteMaterial.length; j++){


                    if(parteMaterial[j].contains("id: ")){
                        id_temp = parteMaterial[j];
                        id_temp = id_temp.replace("id: ", "");
                    }
                    if(parteMaterial[j].contains("idAlmacen: ")){
                        idAlmacen_temp = parteMaterial[j];
                        idAlmacen_temp = idAlmacen_temp.replace("idAlmacen: ", "");
                    }
                    if(parteMaterial[j].contains("nombre: ")){
                        nombre_temp = parteMaterial[j];
                        nombre_temp = nombre_temp.replace("nombre: ", "");
                    }
                    if(parteMaterial[j].contains("stock: ")){
                        stock_temp = parteMaterial[j];
                        stock_temp = stock_temp.replace("stock: ", "");
                    }

                }

                listMateriales.add(new ModeloMateriales(Integer.parseInt(id_temp), Integer.parseInt(idAlmacen_temp), nombre_temp, Integer.parseInt(stock_temp)));
            }
        }
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
            //Toast.makeText(Pendientes.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
    }
}