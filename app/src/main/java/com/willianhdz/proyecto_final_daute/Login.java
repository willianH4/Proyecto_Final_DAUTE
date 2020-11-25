package com.willianhdz.proyecto_final_daute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText edtUsuario, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usua = edtUsuario.getText().toString();
                String passw = edtPassword.getText().toString();

                if (usua.length()==0){
                    edtUsuario.setError("Campo obligatorio");
                }else if (passw.length()==0){
                    edtPassword.setError("Campo obligatorio");
                }else {
                    validar_usuario(getApplicationContext(), usua, passw);
                    vaciarcampos();
                    // Toast.makeText(getApplicationContext(), "Registro Actualizado", Toast.LENGTH_SHORT).show();
                }

                }

        });


    }

    private void validar_usuario(final Context context, final String correo_usu, final String clave_usu) {
        StringRequest request = new StringRequest(Request.Method.POST, Setting_VAR.URL_Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()){
                            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(com.willianhdz.proyecto_final_daute.Login.this, "Usuario y contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "ERROR Login \n" +
                        "Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este método se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                /*
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");

                 */
                map.put("usuario", correo_usu);
                map.put("password", clave_usu);
                return map;
            }
        };
        com.willianhdz.proyecto_final_daute.MySingleton.getInstance(context).addToRequestQueue(request);
    }
    private void vaciarcampos() {
        edtUsuario.setText(null);
        edtPassword.setText(null);

    }





}