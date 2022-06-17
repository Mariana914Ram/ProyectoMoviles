package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView disponible, txtFecha;
    RadioGroup grupoFecha;
    LinearLayout layoutFecha;

    ModeloAlmacen almacen;
    String idUser = "";
    List<ModeloMateriales> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticion);

        almacen = (ModeloAlmacen) getIntent().getSerializableExtra("almacenInfo");
        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        idUser = preferences.getString("id", "");

        cantidad = (EditText) findViewById(R.id.et_cantidadRequerida);
        vuelve = (RadioButton) findViewById(R.id.rb_vuelve);
        noVuelve = (RadioButton) findViewById(R.id.rb_noVuelve);
        laboratorio = (RadioButton) findViewById(R.id.rb_pruebas);
        cliente = (RadioButton) findViewById(R.id.rb_muestra);
        material = (Spinner) findViewById(R.id.spnMaterial);
        disponible = (TextView) findViewById(R.id.lb_disponible);
        txtFecha = (TextView) findViewById(R.id.lb_fecha);
        grupoFecha = (RadioGroup) findViewById(R.id.rdg_grupoFecha);
        layoutFecha = (LinearLayout) findViewById(R.id.layoutFecha);


        String texto = abrirArchivo("archivoMateriales.txt");
        String cadena = "";
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
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
}