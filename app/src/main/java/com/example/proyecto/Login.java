package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Login extends AppCompatActivity {

    private EditText mail, password;
    private CheckBox guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.et_correo);
        password = findViewById(R.id.et_contra);
        guardar = (CheckBox) findViewById(R.id.cb_guardarSesion);
    }


    public void ingresarMenu(View view){
        IngresoAplicacion usuario = new IngresoAplicacion(mail.getText().toString().trim(), password.getText().toString().trim(), true);

        if(guardar.isChecked()){
            guardarPreferencias(usuario);
        }
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void guardarPreferencias(IngresoAplicacion usr){
        SharedPreferences preferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", usr.getCorreo());
        editor.putString("contrasena", usr.getContrasena());
        editor.putBoolean("registrado", usr.isRegistrado());
        editor.apply();
    }
}