import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class vista_usuario extends AppCompatActivity {

    ListView ListViewUsuario;
    List<ModeloUsuario> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuario);


        ListViewUsuario=findViewById(R.id.lstv_usuarios);

        String texto = abrirArchivo("archivoUsuarios.txt");
        if(texto == ""){
            //Ir a vista de que no existe el almacén
        }else {
            String[] usuario = texto.split("\n\n");
            int contador = 0;
            for(int i=0; i<usuario.length; i++){
                String[] parteUsuario = usuario[i].split("\n");
                String id_temp = "";
                String correo_temp = "";
                String nombre_temp = "";
                String apellidos_temp = "";
                for(int j=0; j<parteUsuario.length; j++){


                    if(parteUsuario[j].contains("id: ")){
                        id_temp = parteUsuario[j];
                        id_temp = id_temp.replace("id: ", "");
                    }
                    if(parteUsuario[j].contains("nombre: ")){
                        nombre_temp = parteUsuario[j];
                        nombre_temp = nombre_temp.replace("nombre: ", "");
                    }
                    if(parteUsuario[j].contains("correo: ")){
                        correo_temp = parteUsuario[j];
                        correo_temp = correo_temp.replace("correo: ", "");
                    }
                    if(parteUsuario[j].contains("apellidos: ")){
                        apellidos_temp = parteUsuario[j];
                        apellidos_temp = apellidos_temp.replace("apellidos: ", "");
                    }

                }

                list.add(new ModeloUsuario(Integer.parseInt(id_temp), correo_temp, "", nombre_temp, apellidos_temp, "", R.drawable.almacen));
                contador++;
            }


            if(contador > 0){
                UsuarioAdapter adapter = new UsuarioAdapter(this, list);
                ListViewUsuario.setAdapter(adapter);

                ListViewUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ModeloUsuario c = list.get(i);
                        Toast.makeText(getBaseContext(), c.getNombre(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                //Vista de Benites
            }

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "HOLA!!!!", Snackbar.LENGTH_LONG);
            snackbar.show();

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
}