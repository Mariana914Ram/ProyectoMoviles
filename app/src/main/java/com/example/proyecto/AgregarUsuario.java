import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class AgregarUsuario extends AppCompatActivity {

    EditText nombre, apellidos, correo, password, repetPassword;
    CheckBox tipoA, tipoB;
    TextView tvAlmacen;
    boolean[] selectAlmacen;
    ArrayList<Integer> almacenList = new ArrayList<>();
    String[] almacenArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        nombre = (EditText) findViewById(R.id.et_nombre);
        apellidos = (EditText) findViewById(R.id.et_apellidos);
        correo = (EditText) findViewById(R.id.et_correoGuardar);
        password = (EditText) findViewById(R.id.et_contraGuardar);
        repetPassword = (EditText) findViewById(R.id.et_confirmarContra);
        tipoA = (CheckBox) findViewById(R.id.cbxAdmin);
        tipoB = (CheckBox) findViewById(R.id.cbxNormal);
        tvAlmacen = (TextView) findViewById(R.id.tvAlmacen);


        String texto = abrirArchivo("archivoAlmacenes.txt");
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();

        int contador = 0;
        String cadena = "";
        if(!texto.equals("")){
            String[] almacen = texto.split("\n\n");
            for(int i=0; i<almacen.length; i++){
                String[] parteAlmacen = almacen[i].split("\n");
                String nombre_temp = "";
                for(int j=0; j<parteAlmacen.length; j++) {

                    if (parteAlmacen[j].contains("nombre: ")) {
                        nombre_temp = parteAlmacen[j];
                        nombre_temp = nombre_temp.replace("nombre: ", "");

                        cadena = cadena + nombre_temp;
                        if(i+1 != almacen.length){
                            cadena = cadena + " ";
                        }
                        contador++;
                    }
                }
            }
        }

        almacenArray = cadena.split(" ");
        Toast.makeText(this, almacenArray[0]+"", Toast.LENGTH_SHORT).show();


        selectAlmacen = new boolean[almacenArray.length];
        tvAlmacen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AgregarUsuario.this
                );

                builder.setTitle("Selecciona los almacenes");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(almacenArray, selectAlmacen, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b){
                            almacenList.add(i);
                            Collections.sort(almacenList);
                        }
                        else{
                            almacenList.remove(Integer.valueOf(i));;
                        }
                    }
                });


                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for(int j=0; j<almacenList.size(); j++){
                            stringBuilder.append(almacenArray[almacenList.get(j)]);

                            if(j != almacenList.size()-1){
                                stringBuilder.append(", ");
                            }
                        }
                        tvAlmacen.setText(stringBuilder.toString());
                    }
                });


                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


                builder.setNeutralButton("Limpiar todo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectAlmacen.length; j++){
                            selectAlmacen[j] = false;
                            almacenList.clear();
                            tvAlmacen.setText("");
                        }
                    }
                });


                builder.show();
            }
        });
    }






    public void registrarUsuario(View view){
        boolean verificar = true;

        if(!tipoA.isChecked() && !tipoB.isChecked()){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Debe escoger un tipo de usuario", Snackbar.LENGTH_LONG);
            snackbar.show();
            verificar = false;
        }
        if(nombre.getText().toString().isEmpty()){
            nombre.setError("Campo requerido");
            verificar = false;
        }
        if(apellidos.getText().toString().isEmpty()){
            apellidos.setError("Campo requerido");
            verificar = false;
        }
        if(correo.getText().toString().isEmpty()){
            correo.setError("Campo requerido");
            verificar = false;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Campo requerido");
            verificar = false;
        }
        if(repetPassword.getText().toString().isEmpty()){
            repetPassword.setError("Campo requerido");
            verificar = false;
        }
        if(!password.getText().toString().equals(repetPassword.getText().toString())){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "La contraseña y la confirmación deben ser iguales", Snackbar.LENGTH_LONG);
            snackbar.show();
            verificar = false;
        }


        if(verificar){
            int id = 0;
            String texto = "";
            String abrir = abrirArchivo("archivoUsuarios.txt");
            texto = abrir;

            if(abrir == ""){
                texto = texto + "id: " + id + "\n";
            }else {
                String[] modelos = abrir.split("\n\n");
                for(int i=0; i<modelos.length; i++){
                    String[] parts = modelos[i].split("\n");
                    String id_temp = "";
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), parts[0], Snackbar.LENGTH_LONG);
                    snackbar.show();
                    for(int j=0; j<parts.length; j++){

                        if(parts[j].contains("id: ")){
                            id_temp = parts[j];
                            id_temp = id_temp.replace("id: ", "");
                            if(id < Integer.parseInt(id_temp.trim())){
                                id = Integer.parseInt(id_temp.trim());
                            }
                        }
                    }
                }
                id++;
                texto = texto + "\nid: " + id + "\n";

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), id+"", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
            
            texto = texto + "correo: " + correo.getText().toString().trim() + "\n";
            texto = texto + "contrasena: " + password.getText().toString().trim() + "\n";
            texto = texto + "nombre: " + nombre.getText().toString().trim() + "\n";
            texto = texto + "apellidos: " + apellidos.getText().toString().trim() + "\n";
            if(tipoA.isChecked()){
                texto = texto + "tipo: admin" + "\n";
            }else{
                texto = texto + "tipo: normal" + "\n";
            }
            guardarArchivo(texto);


            Intent intent = new Intent(AgregarUsuario.this, vista_usuario.class);
            startActivity(intent);
            finish();
        }
    }



    private void guardarArchivo(String texto){
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("archivoUsuarios.txt", Activity.MODE_PRIVATE));
            archivo.write(texto);
            archivo.flush();
            archivo.close();
        } catch (IOException e){
            Toast.makeText(AgregarUsuario.this, "Error al escribir en el archivo", Toast.LENGTH_LONG).show();
        }
    }




    private String abrirArchivo(String file){
        String listaArchivos[] = fileList();
        if(existeArchivo(listaArchivos, file)){
            try {
                //1. Crear la instancia para asociar el archivo a leer
                InputStreamReader archivoInterno = new InputStreamReader(openFileInput(file));

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
    
}