package com.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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

public class vista_material extends AppCompatActivity {

    ListView ListViewMateriales;
    List<ModeloMateriales> list = new ArrayList<>();
    ModeloAlmacen almacen;
    String nombreModal = "";
    String stockModal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_material);


        ListViewMateriales=findViewById(R.id.lstv_materiales);

        almacen = (ModeloAlmacen) getIntent().getSerializableExtra("almacenInfo");

        String texto = abrirArchivo("archivoMateriales.txt");
        //Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
        if(!texto.equals("")){
            String[] material = texto.split("\n\n");
            int contador = 0;
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

                if(idAlmacen_temp.equals(almacen.getId()+"")){
                    list.add(new ModeloMateriales(Integer.parseInt(id_temp), Integer.parseInt(idAlmacen_temp), nombre_temp, Integer.parseInt(stock_temp.trim())));
                    contador++;
                }
            }


            if(contador > 0){
                MaterialAdapter adapter = new MaterialAdapter(this, list);
                ListViewMateriales.setAdapter(adapter);

                ListViewMateriales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int viewId = view.getId();
                        ModeloMateriales c;

                        switch (viewId){
                            case R.id.ibtnEditar:
                                c = list.get(i);
                                editarMaterial(c);

                                break;
                            case R.id.ibtnEliminar:
                                c = list.get(i);
                                eliminarMaterial(c);

                                break;
                        }
                    }
                });
            }
        }
    }




    public void agregarMaterial(View view){

        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_material.this);
        mydialog.setTitle("Nuevo material");

        final EditText nombreInput = new EditText(vista_material.this);
        nombreInput.setInputType(InputType.TYPE_CLASS_TEXT);
        nombreInput.setWidth(1000);
        nombreInput.setHint("Nombre del material");
        final EditText stockInput = new EditText(vista_material.this);
        stockInput.setInputType(InputType.TYPE_CLASS_PHONE);
        stockInput.setWidth(1000);
        stockInput.setHint("Stock");
        final LinearLayout layout = new LinearLayout(vista_material.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 0);
        layout.addView(nombreInput);
        layout.addView(stockInput);
        mydialog.setView(layout);

        mydialog.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nombreModal = nombreInput.getText().toString();
                stockModal = stockInput.getText().toString();
                if(nombreModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir un nombre para el material", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if(stockModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir la cantidad disponible", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    boolean validar = true;
                    int idMaterial = 0;
                    String texto = "";
                    String abrir = abrirArchivo("archivoMateriales.txt");
                    texto = abrir;
                    if(abrir == ""){
                        texto = texto + "id: " + idMaterial + "\n";
                    }else {
                        String[] modelos = abrir.split("\n\n");
                        for(int k=0; k<modelos.length; k++){
                            String[] parts = modelos[k].split("\n");
                            String id_temp = "";
                            String nombre_temp = "";
                            String idAlmacen_temp = "";
                            for(int j=0; j<parts.length; j++){

                                if(parts[j].contains("id: ")){
                                    id_temp = parts[j];
                                    id_temp = id_temp.replace("id: ", "");
                                    if(idMaterial < Integer.parseInt(id_temp.trim())){
                                        idMaterial = Integer.parseInt(id_temp.trim());
                                    }
                                }
                                if(parts[j].contains("nombre: ")){
                                    nombre_temp = parts[j];
                                    nombre_temp = nombre_temp.replace("nombre: ", "");
                                }
                                if(parts[j].contains("idAlmacen: ")){
                                    idAlmacen_temp = parts[j];
                                    idAlmacen_temp = idAlmacen_temp.replace("idAlmacen: ", "");
                                }
                            }
                            if(nombre_temp.equals(nombreModal) && idAlmacen_temp.equals(almacen.getId()+"")){
                                validar = false;
                            }
                        }
                        idMaterial++;
                        texto = texto + "\nid: " + idMaterial + "\n";
                    }
                    if(validar){
                        texto = texto + "idAlmacen: " + almacen.getId() + "\n";
                        texto = texto + "nombre: " + nombreModal + "\n";
                        texto = texto + "stock: " + stockModal + "\n";
                        guardarArchivo(texto, "archivoMateriales.txt");


                        Intent intent = new Intent(vista_material.this, vista_material.class);
                        intent.putExtra("almacenInfo", almacen);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        dialogInterface.cancel();

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No pueden repetirse los nombres de los materiales", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

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





    private void editarMaterial(ModeloMateriales c){
        cargarDatosEnLista();

        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_material.this);
        mydialog.setTitle("Editar material "+ c.getNombre());


        final TextView txtNombre = new TextView(vista_material.this);
        txtNombre.setText("Nombre del material: ");
        final EditText nombreInput = new EditText(vista_material.this);
        nombreInput.setInputType(InputType.TYPE_CLASS_TEXT);
        nombreInput.setWidth(1000);
        nombreInput.setHint("Nombre del material");
        nombreInput.setText(c.getNombre());
        final TextView txtStock = new TextView(vista_material.this);
        txtStock.setText("Stock: ");
        final EditText stockInput = new EditText(vista_material.this);
        stockInput.setInputType(InputType.TYPE_CLASS_PHONE);
        stockInput.setWidth(1000);
        stockInput.setHint("Stock");
        stockInput.setText(c.getStock()+"");
        final LinearLayout layout = new LinearLayout(vista_material.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 0);
        layout.addView(txtNombre);
        layout.addView(nombreInput);
        layout.addView(txtStock);
        layout.addView(stockInput);
        mydialog.setView(layout);

        mydialog.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nombreModal = nombreInput.getText().toString();
                stockModal = stockInput.getText().toString();
                if(nombreModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir un nombre para el material", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if(stockModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir la cantidad disponible", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    String texto = "";
                    boolean validar = true;
                    for(int j=0; j<list.size(); j++){
                        if(j == 0){
                            texto = texto + "id: " + list.get(j).getId() + "\n";
                        }
                        else{
                            texto = texto + "\nid: " + list.get(j).getId() + "\n";
                        }
                        texto = texto + "idAlmacen: " + list.get(j).getIdAlmacen() + "\n";

                        if(list.get(j).getNombre().equals(nombreModal) && (list.get(j).getIdAlmacen()+"").equals(almacen.getId()+"") && !(list.get(j).getId()+"").equals(c.getId()+"")){
                            validar = false;
                        }

                        if(list.get(j).getId() == c.getId()){
                            texto = texto + "nombre: " + nombreModal + "\n";
                            texto = texto + "stock: " + stockModal + "\n";
                        }
                        else{
                            texto = texto + "nombre: " + list.get(j).getNombre() + "\n";
                            texto = texto + "stock: " + list.get(j).getStock() + "\n";
                        }
                    }
                    if(validar){
                        guardarArchivo(texto, "archivoMateriales.txt");

                        Intent intent = new Intent(vista_material.this, vista_material.class);
                        intent.putExtra("almacenInfo", almacen);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        dialogInterface.cancel();

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No pueden repetirse los nombres de los materiales", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
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






    private void eliminarMaterial(ModeloMateriales c){

        cargarDatosEnLista();

        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_material.this);
        mydialog = new AlertDialog.Builder(vista_material.this);
        mydialog.setTitle("Eliminar material "+ c.getNombre());

        final TextView almacenText = new TextView(vista_material.this);
        almacenText.setWidth(1000);
        almacenText.setText("¿Estás seguro de eliminar este material?");
        final LinearLayout layout = new LinearLayout(vista_material.this);
        layout.setPadding(40, 20, 40, 0);
        layout.addView(almacenText);
        mydialog.setView(layout);

        mydialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String texto = "";
                for(int j=0; j<list.size(); j++){
                    if(list.get(j).getId() != c.getId()){
                        if(j == 0){
                            texto = texto + "id: " + list.get(j).getId() + "\n";
                        }
                        else{
                            texto = texto + "\nid: " + list.get(j).getId() + "\n";
                        }
                        texto = texto + "idAlmacen: " + list.get(j).getIdAlmacen() + "\n";
                        texto = texto + "nombre: " + list.get(j).getNombre() + "\n";
                        texto = texto + "stock: " + list.get(j).getStock() + "\n";

                        guardarArchivo(texto, "archivoMateriales.txt");

                        Intent intent = new Intent(vista_material.this, vista_material.class);
                        intent.putExtra("almacenInfo", almacen);
                        startActivity(intent);
                        finish();
                    }
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



    private void cargarDatosEnLista(){
        list.clear();

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

                list.add(new ModeloMateriales(Integer.parseInt(id_temp), Integer.parseInt(idAlmacen_temp), nombre_temp, Integer.parseInt(stock_temp)));
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
            Toast.makeText(vista_material.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
    }
}