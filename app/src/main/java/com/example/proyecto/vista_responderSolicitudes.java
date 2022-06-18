package com.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class vista_responderSolicitudes extends AppCompatActivity {

    ListView ListViewPeticionesMateriales;
    List<ModeloPeticionMaterial> list = new ArrayList<>();
    List<ModeloPeticionMaterial> listaCompleta = new ArrayList<>();
    List<ModeloMateriales> listMateriales = new ArrayList<>();
    ModeloAlmacen almacen;
    String motivoRechazoModal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_responder_solicitudes);


        ListViewPeticionesMateriales=findViewById(R.id.lstv_peticion);

        almacen = (ModeloAlmacen) getIntent().getSerializableExtra("almacenInfo");

        String texto = abrirArchivo("archivoPeticionMaterial.txt");
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
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

                if(idAlmacen_temp.equals(almacen.getId()+"") && status_temp.equals("pendiente")){
                    list.add(new ModeloPeticionMaterial(Integer.parseInt(id_temp), Integer.parseInt(idUsuario_temp), Integer.parseInt(idAlmacen_temp), Integer.parseInt(idMaterial_temp), nombreUsuario_temp, nombreMaterial_temp, Integer.parseInt(cantidad_temp), volver_temp, motivo_temp, fecha_temp, fechaSalida_temp, fechaDevuelto_temp, status_temp, descripcion_temp));
                    contador++;
                }
            }


            if(contador > 0){
                ResponderSolicitudesAdapter adapter = new ResponderSolicitudesAdapter(this, list);
                ListViewPeticionesMateriales.setAdapter(adapter);

                ListViewPeticionesMateriales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int viewId = view.getId();
                        ModeloPeticionMaterial c;

                        switch (viewId){
                            case R.id.ibtnInfo:
                                c = list.get(i);
                                modalInformacion(c);

                                break;
                            case R.id.ibtnAceptar:
                                c = list.get(i);
                                modalAceptar(c);

                                break;
                            case R.id.ibtnRechazar:
                                c = list.get(i);
                                modalRechazar(c);

                                break;
                        }
                    }
                });
            }
        }

    }




    private void modalInformacion(ModeloPeticionMaterial c){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_responderSolicitudes.this);
        mydialog = new AlertDialog.Builder(vista_responderSolicitudes.this);
        mydialog.setTitle("Información de la solicitud");

        //ALMACÉN
        final TextView almacenText1 = new TextView(vista_responderSolicitudes.this);
        almacenText1.setText("Almacén: ");
        almacenText1.setTypeface(null, Typeface.BOLD_ITALIC);
        final TextView almacenText2 = new TextView(vista_responderSolicitudes.this);
        almacenText2.setText(almacen.getNombre());
        final LinearLayout layoutAlmacen = new LinearLayout(vista_responderSolicitudes.this);
        layoutAlmacen.setOrientation(LinearLayout.HORIZONTAL);
        layoutAlmacen.setPadding(40, 20, 40, 0);
        layoutAlmacen.addView(almacenText1);
        layoutAlmacen.addView(almacenText2);

        //USUARIO
        final TextView usuarioText1 = new TextView(vista_responderSolicitudes.this);
        usuarioText1.setText("Nombre del solicitante: ");
        usuarioText1.setTypeface(null, Typeface.BOLD_ITALIC);
        final TextView usuarioText2 = new TextView(vista_responderSolicitudes.this);
        usuarioText2.setText(c.getNombreUsuario());
        final LinearLayout layoutUsuario = new LinearLayout(vista_responderSolicitudes.this);
        layoutUsuario.setOrientation(LinearLayout.HORIZONTAL);
        layoutUsuario.setPadding(40, 20, 40, 0);
        layoutUsuario.addView(usuarioText1);
        layoutUsuario.addView(usuarioText2);

        //MATERIAL
        final TextView materialText1 = new TextView(vista_responderSolicitudes.this);
        materialText1.setText("Herramienta seleccionada: ");
        materialText1.setTypeface(null, Typeface.BOLD_ITALIC);
        final TextView materialText2 = new TextView(vista_responderSolicitudes.this);
        materialText2.setText(c.getNombreMaterial());
        final LinearLayout layoutMaterial = new LinearLayout(vista_responderSolicitudes.this);
        layoutMaterial.setOrientation(LinearLayout.HORIZONTAL);
        layoutMaterial.setPadding(40, 20, 40, 0);
        layoutMaterial.addView(materialText1);
        layoutMaterial.addView(materialText2);

        //CANTIDAD
        final TextView cantidadText1 = new TextView(vista_responderSolicitudes.this);
        cantidadText1.setText("Cantidad solicitada: ");
        cantidadText1.setTypeface(null, Typeface.BOLD_ITALIC);
        final TextView cantidadText2 = new TextView(vista_responderSolicitudes.this);
        cantidadText2.setText(c.getCantidad()+" piezas");
        final LinearLayout layoutCantidad = new LinearLayout(vista_responderSolicitudes.this);
        layoutCantidad.setOrientation(LinearLayout.HORIZONTAL);
        layoutCantidad.setPadding(40, 20, 40, 0);
        layoutCantidad.addView(cantidadText1);
        layoutCantidad.addView(cantidadText2);

        //FECHA
        final TextView fechaText1 = new TextView(vista_responderSolicitudes.this);
        fechaText1.setText("Fecha solicitada: ");
        fechaText1.setTypeface(null, Typeface.BOLD_ITALIC);
        final TextView fechaText2 = new TextView(vista_responderSolicitudes.this);
        fechaText2.setText(c.getFecha());
        final LinearLayout layoutFecha = new LinearLayout(vista_responderSolicitudes.this);
        layoutFecha.setOrientation(LinearLayout.HORIZONTAL);
        layoutFecha.setPadding(40, 20, 40, 0);
        layoutFecha.addView(fechaText1);
        layoutFecha.addView(fechaText2);

        //MOTIVO
        final TextView motivoText1 = new TextView(vista_responderSolicitudes.this);
        motivoText1.setText("Fecha solicitada: ");
        motivoText1.setTypeface(null, Typeface.BOLD_ITALIC);
        final TextView motivoText2 = new TextView(vista_responderSolicitudes.this);
        String motivo="";
        if(c.getMotivo().equals("laboratorio")){
            motivo = "Pruebas de laboratorio";
        }
        else{
            motivo = "Muestras para cliente";
        }
        motivoText2.setText(motivo);
        final LinearLayout layoutMotivo = new LinearLayout(vista_responderSolicitudes.this);
        layoutMotivo.setOrientation(LinearLayout.HORIZONTAL);
        layoutMotivo.setPadding(40, 20, 40, 0);
        layoutMotivo.addView(motivoText1);
        layoutMotivo.addView(motivoText2);

        //FECHA SALIDA
        final TextView fechaSalidaText1 = new TextView(vista_responderSolicitudes.this);
        fechaSalidaText1.setText("Salida: ");
        fechaSalidaText1.setTypeface(null, Typeface.BOLD_ITALIC);
        final TextView fechaSalidaText2 = new TextView(vista_responderSolicitudes.this);
        fechaSalidaText2.setText(c.getFechaSalida());
        final LinearLayout layoutFechaSalida = new LinearLayout(vista_responderSolicitudes.this);
        layoutFechaSalida.setOrientation(LinearLayout.HORIZONTAL);
        layoutFechaSalida.setPadding(40, 40, 40, 0);
        layoutFechaSalida.addView(fechaSalidaText1);
        layoutFechaSalida.addView(fechaSalidaText2);

        //FECHA DEVOLUCIÓN
        final TextView fechaDevolucionText1 = new TextView(vista_responderSolicitudes.this);
        fechaDevolucionText1.setText("Devolución: ");
        fechaDevolucionText1.setTypeface(null, Typeface.BOLD_ITALIC);
        final TextView fechaDevolucionText2 = new TextView(vista_responderSolicitudes.this);
        if(c.getFechaDevuelto().equals("")){
            fechaDevolucionText2.setText("Sin retorno");
        }
        else{
            fechaDevolucionText2.setText(c.getFechaDevuelto());
        }
        final LinearLayout layoutFechaDevolucion = new LinearLayout(vista_responderSolicitudes.this);
        layoutFechaDevolucion.setOrientation(LinearLayout.HORIZONTAL);
        layoutFechaDevolucion.setPadding(40, 20, 40, 0);
        layoutFechaDevolucion.addView(fechaDevolucionText1);
        layoutFechaDevolucion.addView(fechaDevolucionText2);

        final LinearLayout layout = new LinearLayout(vista_responderSolicitudes.this);
        layout.setPadding(40, 20, 40, 0);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(layoutAlmacen);
        layout.addView(layoutUsuario);
        layout.addView(layoutMaterial);
        layout.addView(layoutCantidad);
        layout.addView(layoutFecha);
        layout.addView(layoutMotivo);
        layout.addView(layoutFechaSalida);
        layout.addView(layoutFechaDevolucion);

        mydialog.setView(layout);

        mydialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                modalAceptar(c);
            }
        });

        mydialog.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        mydialog.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                modalRechazar(c);
            }
        });
        mydialog.show();
    }



    private void modalAceptar(ModeloPeticionMaterial c){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_responderSolicitudes.this);
        mydialog = new AlertDialog.Builder(vista_responderSolicitudes.this);
        mydialog.setTitle("Aceptar salida de material");

        final TextView almacenText = new TextView(vista_responderSolicitudes.this);
        almacenText.setWidth(1000);
        almacenText.setText("¿Está seguro de aceptar la salida de este material?");
        final LinearLayout layout = new LinearLayout(vista_responderSolicitudes.this);
        layout.setPadding(40, 20, 40, 0);
        layout.addView(almacenText);
        mydialog.setView(layout);

        mydialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                botonAceptar(c);
            }
        });

        mydialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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
                texto = texto + "status: " + "aceptado" + "\n";
                texto = texto + "descripcion: " + "Su solicitud ha sido aceptada" + "\n";
            }
            else{
                texto = texto + "status: " + listaCompleta.get(i).getStatus() + "\n";
                texto = texto + "descripcion: " + listaCompleta.get(i).getDescripcion() + "\n";
            }
        }
        guardarArchivo(texto, "archivoPeticionMaterial.txt");


        Intent intent = new Intent(vista_responderSolicitudes.this, vista_responderSolicitudes.class);
        intent.putExtra("almacenInfo", almacen);
        startActivity(intent);
        finish();
    }




    private void modalRechazar(ModeloPeticionMaterial c){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_responderSolicitudes.this);
        mydialog = new AlertDialog.Builder(vista_responderSolicitudes.this);
        mydialog.setTitle("Rechazar petición");

        final TextView almacenText = new TextView(vista_responderSolicitudes.this);
        almacenText.setWidth(1000);
        almacenText.setText("¿Cuál es la razón del rechazo de la salida de material?");
        final EditText razonInput = new EditText(vista_responderSolicitudes.this);
        razonInput.setInputType(InputType.TYPE_CLASS_TEXT);
        razonInput.setWidth(1000);
        razonInput.setHint("Razón");
        final LinearLayout layout = new LinearLayout(vista_responderSolicitudes.this);
        layout.setPadding(40, 20, 40, 0);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(almacenText);
        layout.addView(razonInput);
        mydialog.setView(layout);

        mydialog.setPositiveButton("Rechazar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                motivoRechazoModal = razonInput.getText().toString();
                if(motivoRechazoModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Por favor, escriba un motivo del rechazo de la salida", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    botonRechazar(c, motivoRechazoModal);
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
    }




    private void botonRechazar(ModeloPeticionMaterial c, String motivoDescripcion){
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
                texto = texto + "status: " + "rechazado" + "\n";
                texto = texto + "descripcion: " + motivoDescripcion + "\n";
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


        Intent intent = new Intent(vista_responderSolicitudes.this, vista_responderSolicitudes.class);
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
            Toast.makeText(vista_responderSolicitudes.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
    }
}