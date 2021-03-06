package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Peticion extends AppCompatActivity {

    EditText cantidad;
    RadioButton vuelve, noVuelve, laboratorio, cliente;
    Spinner material;
    TextView disponible, txtFecha, txtFechaSalida;
    RadioGroup grupoFecha;
    LinearLayout layoutFecha;

    ModeloAlmacen almacen;
    String idUser = "", nombreUser = "", tipoUser = "";
    List<ModeloMateriales> list = new ArrayList<>();
    List<ModeloMateriales> listaCompleta = new ArrayList<>();
    String idMaterialSeleccionado = "", stockMaterialSeleccionado = "", nombreMaterialSeleccionado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticion);

        /*guardarArchivo("", "archivoPeticionMaterial.txt");
        String abrir = abrirArchivo("archivoPeticionMaterial.txt");
        Toast.makeText(this, abrir, Toast.LENGTH_LONG).show();*/

        almacen = (ModeloAlmacen) getIntent().getSerializableExtra("almacenInfo");
        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        idUser = preferences.getString("id", "");
        nombreUser = preferences.getString("nombre", "") + " " + preferences.getString("apellidos", "");
        tipoUser = preferences.getString("tipo", "");

        cantidad = (EditText) findViewById(R.id.et_cantidadRequerida);
        vuelve = (RadioButton) findViewById(R.id.rb_vuelve);
        noVuelve = (RadioButton) findViewById(R.id.rb_noVuelve);
        laboratorio = (RadioButton) findViewById(R.id.rb_pruebas);
        cliente = (RadioButton) findViewById(R.id.rb_muestra);
        material = (Spinner) findViewById(R.id.spnMaterial);
        disponible = (TextView) findViewById(R.id.lb_disponible);
        txtFecha = (TextView) findViewById(R.id.lb_fecha);
        txtFechaSalida = (TextView) findViewById(R.id.lb_fechaSalida);
        grupoFecha = (RadioGroup) findViewById(R.id.rdg_grupoFecha);
        layoutFecha = (LinearLayout) findViewById(R.id.layoutFecha);


        String texto = abrirArchivo("archivoMateriales.txt");
        String cadena = "";
        //Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
        if(!texto.equals("")){
            String[] materiales = texto.split("\n\n");
            int contador = 0;
            for(int i=0; i<materiales.length; i++){
                String[] parteMaterial = materiales[i].split("\n");
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
                    list.add(new ModeloMateriales(Integer.parseInt(id_temp), Integer.parseInt(idAlmacen_temp), nombre_temp, Integer.parseInt(stock_temp)));
                    contador++;
                }
            }

            for(int i=0; i<list.size(); i++){
                cadena = cadena + list.get(i).getNombre();
                if(i+1 != list.size()){
                    cadena = cadena + ", ";
                }
            }
            String[] materialesArray = cadena.split(", ");
            ArrayAdapter<String> adapterMateriales = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, materialesArray);
            material.setAdapter(adapterMateriales);
        }


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(c.getTime());
        txtFecha.setText(strDate);
        txtFechaSalida.setText(strDate);


        layoutFecha.getLayoutParams().height=1;
        layoutFecha.requestLayout();

        grupoFecha.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(vuelve.isChecked()){
                    layoutFecha.getLayoutParams().height= LinearLayout.LayoutParams.MATCH_PARENT;
                    layoutFecha.requestLayout();
                }
                else{
                    layoutFecha.getLayoutParams().height=1;
                    layoutFecha.requestLayout();
                }
            }
        });

        String datoRecibido = material.getSelectedItem().toString();
        for(int j=0; j<list.size(); j++){
            if(datoRecibido.equals(list.get(j).getNombre())){
                idMaterialSeleccionado = list.get(j).getId()+"";
                nombreMaterialSeleccionado = list.get(j).getNombre();
                stockMaterialSeleccionado = list.get(j).getStock()+"";
                disponible.setText("Cantidad disponible: " + list.get(j).getStock());
            }
        }
        material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String datoRecibido = material.getSelectedItem().toString();
                for(int j=0; j<list.size(); j++){
                    if(datoRecibido.equals(list.get(j).getNombre())){
                        idMaterialSeleccionado = list.get(j).getId()+"";
                        nombreMaterialSeleccionado = list.get(j).getNombre();
                        stockMaterialSeleccionado = list.get(j).getStock()+"";
                        disponible.setText("Cantidad disponible: " + list.get(j).getStock());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }



    public void abrirCalendario(View view){
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(Peticion.this/*, R.style.DialogTheme*/, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String fecha = day + "/" + month + "/" + year;
                txtFecha.setText(fecha);
            }
        }, 2022, mes, dia);
        dpd.show();
    }



    public void abrirCalendarioSalida(View view){
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(Peticion.this/*, R.style.DialogTheme*/, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String fecha = day + "/" + month + "/" + year;
                txtFechaSalida.setText(fecha);
            }
        }, 2022, mes, dia);
        dpd.show();
    }





    public void registrarPeticion(View view){
        boolean verificar = true;

        if(!vuelve.isChecked() && !noVuelve.isChecked()){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debe escoger si el material va a volver o no va a volver", Snackbar.LENGTH_LONG);
            snackbar.show();
            verificar = false;
        }
        if(cantidad.getText().toString().isEmpty()){
            cantidad.setError("Campo requerido");
            verificar = false;
        }
        if(!laboratorio.isChecked() && !cliente.isChecked()){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debe escoger el motivo de la salida", Snackbar.LENGTH_LONG);
            snackbar.show();
            verificar = false;
        }
        if(Integer.parseInt(cantidad.getText().toString()) > Integer.parseInt(stockMaterialSeleccionado)){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "La cantidad solicitada no puede superar a la disponible", Snackbar.LENGTH_LONG);
            snackbar.show();
            verificar = false;
        }

        if(verificar){
            int idM = 0;
            String abrir = abrirArchivo("archivoPeticionMaterial.txt");
            String texto = abrir;

            if(abrir.equals("")){
                texto = texto + "id: " + idM + "\n";
            }else {
                String[] modelos = abrir.split("\n\n");
                for(int i=0; i<modelos.length; i++){
                    String[] parts = modelos[i].split("\n");
                    String id_temp = "";
                    for(int j=0; j<parts.length; j++){

                        if(parts[j].contains("id: ")){
                            id_temp = parts[j];
                            id_temp = id_temp.replace("id: ", "");
                            if(idM < Integer.parseInt(id_temp.trim())){
                                idM = Integer.parseInt(id_temp.trim());
                            }
                        }
                    }
                }
                idM++;
                texto = texto + "\nid: " + idM + "\n";

            }

            String fechaSeleccionada = "";

            texto = texto + "idUsuario: " + idUser + "\n";
            texto = texto + "idAlmacen: " + almacen.getId() + "\n";
            texto = texto + "idMaterial: " + idMaterialSeleccionado + "\n";
            texto = texto + "nombreUsuario: " + nombreUser + "\n";
            texto = texto + "nombreMaterial: " + nombreMaterialSeleccionado + "\n";
            texto = texto + "cantidad: " + cantidad.getText().toString() + "\n";
            if(vuelve.isChecked()){
                texto = texto + "volver: volver" + "\n";
                fechaSeleccionada = txtFecha.getText().toString();
            }else{
                texto = texto + "volver: noVolver" + "\n";
                fechaSeleccionada = "";
            }

            if(laboratorio.isChecked()){
                texto = texto + "motivo: laboratorio" + "\n";
            }else{
                texto = texto + "motivo: cliente" + "\n";
            }

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = sdf.format(c.getTime());

            texto = texto + "fecha: " + strDate + "\n";
            texto = texto + "fechaSalida: " + txtFechaSalida.getText().toString() + "\n";
            texto = texto + "fechaDevuelto: " + fechaSeleccionada + "\n";
            texto = texto + "status: " + "pendiente" + "\n";
            texto = texto + "descripcion: " + "" + "\n";

            guardarArchivo(texto, "archivoPeticionMaterial.txt");


            cargarDatosEnLista();
            texto = "";

            for(int i=0; i<listaCompleta.size(); i++){
                if(i==0){
                    texto = "id: " + listaCompleta.get(i).getId() + "\n";
                }
                else{
                    texto = texto + "\nid: " + listaCompleta.get(i).getId() + "\n";
                }
                texto = texto + "idAlmacen: " + listaCompleta.get(i).getIdAlmacen() + "\n";
                texto = texto + "nombre: " + listaCompleta.get(i).getNombre() + "\n";

                if(listaCompleta.get(i).getId() == Integer.parseInt(idMaterialSeleccionado)){
                    int stock_temp = listaCompleta.get(i).getStock();
                    int obtenerCantidad = Integer.parseInt(cantidad.getText().toString());
                    stock_temp = stock_temp - obtenerCantidad;
                    texto = texto + "stock: " + stock_temp + "\n";
                }
                else{
                    texto = texto + "stock: " + listaCompleta.get(i).getStock() + "\n";
                }
            }
            guardarArchivo(texto, "archivoMateriales.txt");


            if(tipoUser.equals("admin")){
                Intent intent = new Intent(Peticion.this, vista_responderSolicitudes.class);
                intent.putExtra("almacenInfo", almacen);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(Peticion.this, Historial.class);
                intent.putExtra("almacenInfo", almacen);
                startActivity(intent);
                finish();
            }
        }
    }




    public void cancelar(View view){
        Intent intent = new Intent(Peticion.this, AlmacenInicio.class);
        intent.putExtra("almacenInfo", almacen);
        startActivity(intent);
        finish();
    }



    private void cargarDatosEnLista(){
        listaCompleta.clear();
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

                listaCompleta.add(new ModeloMateriales(Integer.parseInt(id_temp), Integer.parseInt(idAlmacen_temp), nombre_temp, Integer.parseInt(stock_temp)));
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
            Toast.makeText(Peticion.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
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