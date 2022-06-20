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

public class Historial extends AppCompatActivity {

    ListView ListViewPeticionesMateriales;
    List<ModeloPeticionMaterial> list = new ArrayList<>();
    List<ModeloPeticionMaterial> listReverso = new ArrayList<>();
    List<ModeloPeticionMaterial> listaCompleta = new ArrayList<>();
    List<ModeloMateriales> listMateriales = new ArrayList<>();
    ModeloAlmacen almacen;
    String idUser = "";
    String tipoUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        ListViewPeticionesMateriales=findViewById(R.id.lstv_historial);

        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        idUser = preferences.getString("id", "");
        tipoUser = preferences.getString("tipo", "");

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

                if(idAlmacen_temp.equals(almacen.getId()+"") && idUsuario_temp.equals(idUser)){
                    list.add(new ModeloPeticionMaterial(Integer.parseInt(id_temp), Integer.parseInt(idUsuario_temp), Integer.parseInt(idAlmacen_temp), Integer.parseInt(idMaterial_temp), nombreUsuario_temp, nombreMaterial_temp, Integer.parseInt(cantidad_temp), volver_temp, motivo_temp, fecha_temp, fechaSalida_temp, fechaDevuelto_temp, status_temp, descripcion_temp));
                    contador++;
                }
            }

            if(contador > 0){

                for(int i=list.size()-1; i>=0; i--){
                    listReverso.add(list.get(i));
                }

                HistorialAdapter adapter = new HistorialAdapter(this, listReverso);
                ListViewPeticionesMateriales.setAdapter(adapter);

                ListViewPeticionesMateriales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int viewId = view.getId();
                        ModeloPeticionMaterial c;

                        switch (viewId){
                            case R.id.ibtnEliminar:
                                c = listReverso.get(i);

                                if(c.getStatus().equals("pendiente")){
                                    modalEliminar(c);
                                }
                                else{
                                    eliminarHistorial(c);
                                }

                                break;
                        }
                    }
                });
            }
        }
    }


    private void modalEliminar(ModeloPeticionMaterial c){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(Historial.this);
        mydialog = new AlertDialog.Builder(Historial.this);
        mydialog.setTitle("Eliminar solicitud de material");

        final TextView almacenText = new TextView(Historial.this);
        almacenText.setWidth(1000);
        almacenText.setText("Esta solicitud no ha sido aceptada ni rechazada ¿Está seguro que desea eliminarla?");
        final LinearLayout layout = new LinearLayout(Historial.this);
        layout.setPadding(40, 20, 40, 0);
        layout.addView(almacenText);
        mydialog.setView(layout);

        mydialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {

                cargarDatosEnListaMaterial();
                String texto = "";
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

                eliminarHistorial(c);
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






    private void eliminarHistorial(ModeloPeticionMaterial c){
        cargarDatosEnLista();

        String texto = "";
        for(int i=0; i<listaCompleta.size(); i++){
            if(c.getId() != listaCompleta.get(i).getId()){
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
                texto = texto + "status: " + listaCompleta.get(i).getStatus() + "\n";
                texto = texto + "descripcion: " + listaCompleta.get(i).getDescripcion() + "\n";
            }
        }
        guardarArchivo(texto, "archivoPeticionMaterial.txt");

        Intent intent = new Intent(Historial.this, Historial.class);
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
            Toast.makeText(Historial.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
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
}