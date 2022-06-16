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

public class vista_producto extends AppCompatActivity {

    ListView ListViewProductos;
    List<ModeloProductos> list = new ArrayList<>();
    ModeloAlmacen almacen;
    String nombreModal = "";
    String precioModal = "";
    String stockModal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_producto);


        ListViewProductos=findViewById(R.id.lstv_productos);

        almacen = (ModeloAlmacen) getIntent().getSerializableExtra("almacenInfo");

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

                if(idAlmacen_temp.equals(almacen.getId()+"")){
                    list.add(new ModeloProductos(Integer.parseInt(id_temp), Integer.parseInt(idAlmacen_temp), nombre_temp, R.drawable.herramientas, precio_temp, Integer.parseInt(stock_temp)));
                    contador++;
                }
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
                                c = list.get(i);
                                editarProducto(c);

                                break;
                            case R.id.ibtnEliminar:
                                c = list.get(i);
                                eliminarProducto(c);

                                break;
                        }
                    }
                });
            }
        }
    }




    public void agregarProducto(View view){

        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_producto.this);
        mydialog.setTitle("Nuevo producto");

        final EditText nombreInput = new EditText(vista_producto.this);
        nombreInput.setInputType(InputType.TYPE_CLASS_TEXT);
        nombreInput.setWidth(1000);
        nombreInput.setHint("Nombre del producto");
        final EditText precioInput = new EditText(vista_producto.this);
        precioInput.setInputType(InputType.TYPE_CLASS_PHONE);
        precioInput.setWidth(1000);
        precioInput.setHint("Precio");
        final EditText stockInput = new EditText(vista_producto.this);
        stockInput.setInputType(InputType.TYPE_CLASS_PHONE);
        stockInput.setWidth(1000);
        stockInput.setHint("Stock");
        final LinearLayout layout = new LinearLayout(vista_producto.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 0);
        layout.addView(nombreInput);
        layout.addView(precioInput);
        layout.addView(stockInput);
        mydialog.setView(layout);

        mydialog.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nombreModal = nombreInput.getText().toString();
                precioModal = precioInput.getText().toString();
                stockModal = stockInput.getText().toString();
                if(nombreModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir un nombre para el producto", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if(precioModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir el precio", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if(stockModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir la cantidad disponible", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    boolean validar = true;
                    int idProducto = 0;
                    String texto = "";
                    String abrir = abrirArchivo("archivoProductos.txt");
                    texto = abrir;
                    if(abrir == ""){
                        texto = texto + "id: " + idProducto + "\n";
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
                                    if(idProducto < Integer.parseInt(id_temp.trim())){
                                        idProducto = Integer.parseInt(id_temp.trim());
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
                        idProducto++;
                        texto = texto + "\nid: " + idProducto + "\n";
                    }
                    if(validar){
                        texto = texto + "idAlmacen: " + almacen.getId() + "\n";
                        texto = texto + "nombre: " + nombreModal + "\n";
                        texto = texto + "precio: " + precioModal + "\n";
                        texto = texto + "stock: " + stockModal + "\n";
                        guardarArchivo(texto, "archivoProductos.txt");


                        Intent intent = new Intent(vista_producto.this, vista_producto.class);
                        intent.putExtra("almacenInfo", almacen);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        dialogInterface.cancel();

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No pueden repetirse los nombres de los productos", Snackbar.LENGTH_LONG);
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





    private void editarProducto(ModeloProductos c){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_producto.this);
        mydialog.setTitle("Editar producto "+ c.getNombre());


        final TextView txtNombre = new TextView(vista_producto.this);
        txtNombre.setText("Nombre del producto: ");
        final EditText nombreInput = new EditText(vista_producto.this);
        nombreInput.setInputType(InputType.TYPE_CLASS_TEXT);
        nombreInput.setWidth(1000);
        nombreInput.setHint("Nombre del producto");
        nombreInput.setText(c.getNombre());
        final TextView txtPrecio = new TextView(vista_producto.this);
        txtPrecio.setText("Precio: ");
        final EditText precioInput = new EditText(vista_producto.this);
        precioInput.setInputType(InputType.TYPE_CLASS_PHONE);
        precioInput.setWidth(1000);
        precioInput.setHint("Precio");
        precioInput.setText(c.getPrecio());
        final TextView txtStock = new TextView(vista_producto.this);
        txtStock.setText("Stock: ");
        final EditText stockInput = new EditText(vista_producto.this);
        stockInput.setInputType(InputType.TYPE_CLASS_PHONE);
        stockInput.setWidth(1000);
        stockInput.setHint("Stock");
        stockInput.setText(c.getStock()+"");
        final LinearLayout layout = new LinearLayout(vista_producto.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 0);
        layout.addView(txtNombre);
        layout.addView(nombreInput);
        layout.addView(txtPrecio);
        layout.addView(precioInput);
        layout.addView(txtStock);
        layout.addView(stockInput);
        mydialog.setView(layout);

        mydialog.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nombreModal = nombreInput.getText().toString();
                precioModal = precioInput.getText().toString();
                stockModal = stockInput.getText().toString();
                if(nombreModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir un nombre para el producto", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if(precioModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir el precio", Snackbar.LENGTH_LONG);
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
                            texto = texto + "precio: " + precioModal + "\n";
                            texto = texto + "stock: " + stockModal + "\n";
                        }
                        else{
                            texto = texto + "nombre: " + list.get(j).getNombre() + "\n";
                            texto = texto + "precio: " + list.get(j).getPrecio() + "\n";
                            texto = texto + "stock: " + list.get(j).getStock() + "\n";
                        }
                    }
                    if(validar){
                        guardarArchivo(texto, "archivoProductos.txt");

                        Intent intent = new Intent(vista_producto.this, vista_producto.class);
                        intent.putExtra("almacenInfo", almacen);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        dialogInterface.cancel();

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No pueden repetirse los nombres de los productos", Snackbar.LENGTH_LONG);
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






    private void eliminarProducto(ModeloProductos c){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_producto.this);
        mydialog = new AlertDialog.Builder(vista_producto.this);
        mydialog.setTitle("Eliminar producto "+ c.getNombre());

        final TextView almacenText = new TextView(vista_producto.this);
        almacenText.setWidth(1000);
        almacenText.setText("¿Estás seguro de eliminar este producto?");
        final LinearLayout layout = new LinearLayout(vista_producto.this);
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
                        texto = texto + "precio: " + list.get(j).getPrecio() + "\n";
                        texto = texto + "stock: " + list.get(j).getStock() + "\n";

                        guardarArchivo(texto, "archivoProductos.txt");

                        Intent intent = new Intent(vista_producto.this, vista_producto.class);
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