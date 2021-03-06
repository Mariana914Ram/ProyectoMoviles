package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
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

public class vista_almacen extends AppCompatActivity {

    ListView ListViewAlmacen;
    LinearLayout layoutAgregarAlmacen;

    List<ModeloAlmacen> list = new ArrayList<>();
    String idUser = "", tipoUser = "";
    String textoModal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_almacen);

        ListViewAlmacen=findViewById(R.id.lstv_almacenes);
        layoutAgregarAlmacen = findViewById(R.id.layoutAgregarAlmacen);

        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        idUser = preferences.getString("id", "");
        tipoUser = preferences.getString("tipo", "");


        if(tipoUser.equals("normal")){
            layoutAgregarAlmacen.getLayoutParams().height=1;
            layoutAgregarAlmacen.requestLayout();
        }

        if(idUser.equals("")){
            Toast.makeText(getBaseContext(), "ERROR", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        else{

            String texto = abrirArchivo("archivoAlmacenes.txt");
            if(texto == ""){
                //Ir a vista de que no existe el almac??n
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
                    }
                    String textoAlmacenUsuario = abrirArchivo("archivoAlmacenesUsuarios.txt");
                    if(!textoAlmacenUsuario.equals("")){
                        String[] almacenesUsuarios = textoAlmacenUsuario.split("\n\n");
                        for (int k = 0; k < almacenesUsuarios.length; k++) {
                            String[] parts = almacenesUsuarios[k].split("\n");
                            String idUsuarioAlmacenUsuario = "";
                            String idAlmacenAlmacenUsuario = "";
                            for (int l = 0; l < parts.length; l++) {
                                if(parts[l].contains("idUsuario: ")){
                                    idUsuarioAlmacenUsuario = parts[l];
                                    idUsuarioAlmacenUsuario = idUsuarioAlmacenUsuario.replace("idUsuario: ", "");
                                }
                                if(parts[l].contains("idAlmacen: ")){
                                    idAlmacenAlmacenUsuario = parts[l];
                                    idAlmacenAlmacenUsuario = idAlmacenAlmacenUsuario.replace("idAlmacen: ", "");
                                }
                            }
                            if(id_temp.equals(idAlmacenAlmacenUsuario) && idUsuarioAlmacenUsuario.equals(idUser)){
                                list.add(new ModeloAlmacen(Integer.parseInt(id_temp), nombre_temp, R.drawable.almacen));
                                contador++;
                            }
                        }
                    }
                }


                if(contador > 0){

                    if(tipoUser.equals("admin")){
                        AlmacenAdapter adapter = new AlmacenAdapter(this, list);
                        ListViewAlmacen.setAdapter(adapter);

                        ListViewAlmacen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                int viewId = view.getId();
                                ModeloAlmacen c;

                                switch (viewId){
                                    case R.id.ibtnEditar:
                                        c = list.get(i);
                                        //Toast.makeText(getBaseContext(), c.getNombre(), Toast.LENGTH_SHORT).show();


                                        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_almacen.this);
                                        mydialog.setTitle("Editar almac??n "+ c.getNombre());

                                        final EditText almacenInput = new EditText(vista_almacen.this);
                                        almacenInput.setInputType(InputType.TYPE_CLASS_TEXT);
                                        almacenInput.setWidth(1000);
                                        almacenInput.setText(c.getNombre());
                                        LinearLayout layout = new LinearLayout(vista_almacen.this);
                                        layout.setPadding(40, 20, 40, 0);
                                        layout.addView(almacenInput);
                                        mydialog.setView(layout);

                                        mydialog.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                textoModal = almacenInput.getText().toString();
                                                if(textoModal.equals("")){
                                                    dialogInterface.cancel();
                                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir un nombre para el almac??n", Snackbar.LENGTH_LONG);
                                                    snackbar.show();
                                                }
                                                else{
                                                    boolean validar = true;
                                                    String texto = "";
                                                    String abrir = abrirArchivo("archivoAlmacenes.txt");
                                                    if(abrir == ""){
                                                        Toast.makeText(vista_almacen.this, "Hubo un error, intente de nuevo", Toast.LENGTH_LONG).show();
                                                    }else {
                                                        String[] modelos = abrir.split("\n\n");
                                                        for(int k=0; k<modelos.length; k++){
                                                            String[] parts = modelos[k].split("\n");
                                                            for(int j=0; j<parts.length; j++){

                                                                if(parts[j].contains("nombre: ")){
                                                                    String nombre_temp = parts[j];
                                                                    nombre_temp = nombre_temp.replace("nombre: ", "");
                                                                    if(!nombre_temp.equals(c.getNombre()) && nombre_temp.equals(textoModal)){
                                                                        validar = false;
                                                                    }
                                                                }
                                                            }
                                                        }

                                                        if(validar){
                                                            for(int k=0; k<modelos.length; k++){
                                                                String[] parts = modelos[k].split("\n");
                                                                for(int j=0; j<parts.length; j++){

                                                                    if(parts[j].contains("id: ")){
                                                                        if(k == 0){
                                                                            texto = texto + parts[j] + "\n";
                                                                        }
                                                                        else{
                                                                            texto = texto + "\n" + parts[j] + "\n";
                                                                        }
                                                                    }
                                                                    if(parts[j].contains("nombre: ")){
                                                                        String nombre_temp = parts[j];
                                                                        nombre_temp = nombre_temp.replace("nombre: ", "");
                                                                        if(nombre_temp.equals(c.getNombre())){
                                                                            texto = texto + "nombre: " + textoModal + "\n";
                                                                        }
                                                                        else{
                                                                            texto = texto + parts[j] + "\n";
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            guardarArchivo(texto, "archivoAlmacenes.txt");

                                                            Intent intent = new Intent(vista_almacen.this, vista_almacen.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                        else{
                                                            dialogInterface.cancel();

                                                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No pueden repetirse los nombres de los almacenes", Snackbar.LENGTH_LONG);
                                                            snackbar.show();
                                                        }
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
                                        break;
                                    case R.id.ibtnEliminar:
                                        c = list.get(i);

                                        mydialog = new AlertDialog.Builder(vista_almacen.this);
                                        mydialog.setTitle("Eliminar almac??n "+ c.getNombre());

                                        final TextView almacenText = new TextView(vista_almacen.this);
                                        almacenText.setWidth(1000);
                                        almacenText.setText("??Est??s seguro de eliminar este almac??n?");
                                        layout = new LinearLayout(vista_almacen.this);
                                        layout.setPadding(40, 20, 40, 0);
                                        layout.addView(almacenText);
                                        mydialog.setView(layout);

                                        mydialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                boolean validar = true;
                                                String texto = "";
                                                String abrir = abrirArchivo("archivoAlmacenes.txt");
                                                if(abrir == ""){
                                                    Toast.makeText(vista_almacen.this, "Hubo un error, intente de nuevo", Toast.LENGTH_LONG).show();
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
                                                    guardarArchivo(texto, "archivoAlmacenes.txt");

                                                    validar = true;
                                                    texto = "";
                                                    abrir = abrirArchivo("archivoAlmacenesUsuarios.txt");
                                                    if(abrir == ""){
                                                        Toast.makeText(vista_almacen.this, "Hubo un error, intente de nuevo", Toast.LENGTH_LONG).show();
                                                    }else {
                                                        modelos = abrir.split("\n\n");
                                                        for(int k=0; k<modelos.length; k++){
                                                            validar = true;
                                                            String[] parts = modelos[k].split("\n");
                                                            String textoTemporal = "";
                                                            for(int j=0; j<parts.length; j++){

                                                                if(parts[j].contains("idAlmacen: ")){
                                                                    String idAlmacen_temp = parts[j];
                                                                    idAlmacen_temp = idAlmacen_temp.replace("idAlmacen: ", "");
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


                                                    Intent intent = new Intent(vista_almacen.this, vista_almacen.class);
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




                                    default:
                                        c = list.get(i);
                                        Intent intent = new Intent(vista_almacen.this, AlmacenInicio.class);
                                        intent.putExtra("almacenInfo", c);
                                        startActivity(intent);

                                        break;
                                }
                            }
                        });
                    }
                    else{
                        AlmacenNormalAdapter adapter = new AlmacenNormalAdapter(this, list);
                        ListViewAlmacen.setAdapter(adapter);

                        ListViewAlmacen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                int viewId = view.getId();
                                ModeloAlmacen c;

                                c = list.get(i);
                                Intent intent = new Intent(vista_almacen.this, AlmacenInicio.class);
                                intent.putExtra("almacenInfo", c);
                                startActivity(intent);

                            }
                        });
                    }


                }
                else{

                }

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
            Toast.makeText(vista_almacen.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
    }



    public void agregarAlmacen(View view){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(vista_almacen.this);
        mydialog.setTitle("Nombre del nuevo almac??n");

        final EditText almacenInput = new EditText(vista_almacen.this);
        almacenInput.setInputType(InputType.TYPE_CLASS_TEXT);
        almacenInput.setWidth(1000);
        final LinearLayout layout = new LinearLayout(vista_almacen.this);
        layout.setPadding(40, 20, 40, 0);
        layout.addView(almacenInput);
        mydialog.setView(layout);

        mydialog.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textoModal = almacenInput.getText().toString();
                if(textoModal.equals("")){
                    dialogInterface.cancel();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debes escribir un nombre para el almac??n", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    boolean validar = true;
                    int idAlmacen = 0;
                    String texto = "";
                    String abrir = abrirArchivo("archivoAlmacenes.txt");
                    texto = abrir;
                    if(abrir == ""){
                        texto = texto + "id: " + idAlmacen + "\n";
                    }else {
                        String[] modelos = abrir.split("\n\n");
                        for(int k=0; k<modelos.length; k++){
                            String[] parts = modelos[k].split("\n");
                            String id_temp = "";
                            String nombre_temp = "";
                            for(int j=0; j<parts.length; j++){

                                if(parts[j].contains("id: ")){
                                    id_temp = parts[j];
                                    id_temp = id_temp.replace("id: ", "");
                                    if(idAlmacen < Integer.parseInt(id_temp.trim())){
                                        idAlmacen = Integer.parseInt(id_temp.trim());
                                    }
                                }
                                if(parts[j].contains("nombre: ")){
                                    nombre_temp = parts[j];
                                    nombre_temp = nombre_temp.replace("nombre: ", "");
                                    if(nombre_temp.equals(textoModal)){
                                        validar = false;
                                    }
                                }
                            }
                        }
                        idAlmacen++;
                        texto = texto + "\nid: " + idAlmacen + "\n";
                    }
                    if(validar){
                        texto = texto + "nombre: " + textoModal + "\n";
                        guardarArchivo(texto, "archivoAlmacenes.txt");



                        abrir = abrirArchivo("archivoAlmacenesUsuarios.txt");
                        texto = abrir;
                        int idAlmacenUsuario = 0;
                        if(abrir == ""){
                            texto = texto + "id: " + idAlmacenUsuario + "\n";
                        }else {
                            String[] modelos = abrir.split("\n\n");
                            for(int k=0; k<modelos.length; k++){
                                String[] parts = modelos[k].split("\n");
                                String id_temp = "";
                                String nombre_temp = "";
                                for(int j=0; j<parts.length; j++){

                                    if(parts[j].contains("id: ")){
                                        id_temp = parts[j];
                                        id_temp = id_temp.replace("id: ", "");
                                        if(idAlmacenUsuario < Integer.parseInt(id_temp.trim())){
                                            idAlmacenUsuario = Integer.parseInt(id_temp.trim());
                                        }
                                    }
                                }
                            }
                            idAlmacenUsuario++;
                            texto = texto + "\nid: " + idAlmacenUsuario + "\n";
                        }
                        texto = texto + "idUsuario: " + Integer.parseInt(idUser) + "\n";
                        texto = texto + "idAlmacen: " + idAlmacen + "\n";
                        guardarArchivo(texto, "archivoAlmacenesUsuarios.txt");


                        Intent intent = new Intent(vista_almacen.this, vista_almacen.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        dialogInterface.cancel();

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No pueden repetirse los nombres de los almacenes", Snackbar.LENGTH_LONG);
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