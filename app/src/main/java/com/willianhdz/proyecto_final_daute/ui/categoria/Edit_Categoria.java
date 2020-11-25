package com.willianhdz.proyecto_final_daute.ui.categoria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.willianhdz.proyecto_final_daute.MainActivity;
import com.willianhdz.proyecto_final_daute.MySingleton;
import com.willianhdz.proyecto_final_daute.R;
import com.willianhdz.proyecto_final_daute.Setting_VAR;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Edit_Categoria extends AppCompatActivity {
    private EditText edtCode, edtNombre;
    private Spinner spinner;
    private Button btnUpdate, btnSalir;
    String datoSelect = "";
    int posi =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__categoria);

        //referencias
        edtCode = findViewById(R.id.edtCategoriaUp);
        edtNombre = findViewById(R.id.edtNombreCategoriaUp);
        spinner = findViewById(R.id.sp_estadoUp);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnSalir = findViewById(R.id.btnSalir);

       btnSalir.setOnClickListener(view -> {
           Intent intent = new Intent(Edit_Categoria.this, MainActivity.class);
           startActivity(intent);
       });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.estadoCategorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItemPosition()>0){
                    datoSelect = spinner.getSelectedItem().toString();
                } else {
                    datoSelect = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String senal = "";
        String codigo = "";
        String nombre = "";
        String estad = "";
        int suma = 0;

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                codigo = bundle.getString("codigo");
                senal = bundle.getString("senal");
                nombre = bundle.getString("nombre");
                estad = bundle.getString("estado");

                if (senal.equals("1")){
                    edtCode.setText(codigo);
                    edtNombre.setText(nombre);
                    suma = Integer.parseInt(estad);
                     spinner.setSelection(suma);
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtCode.getText().toString();
                String nombre = edtNombre.getText().toString();

                if (id.length() == 0){
                    edtCode.setError("Por favor introduzca el Id");
                } else if (nombre.length() == 0){
                    edtNombre.setError("Por favor escriba el nombre de la categoria");
                } else if (spinner.getSelectedItemPosition() > 0){
                    //this action save in the BD
                    String est = (String) spinner.getSelectedItem();
                    udpate_server(getApplicationContext(), Integer.parseInt(id), nombre, Integer.parseInt(est));
                } else {
                    Toast.makeText(getApplicationContext(), "Seleccione un estado para la categoria", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.willianhdz.proyecto_final_daute.ui.categoria.Edit_Categoria.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void udpate_server(final Context context, final int id_cat_up, final String nom_cat_up, final int esta_cat_up) {
        StringRequest request = new StringRequest(Request.Method.POST, Setting_VAR.URL_update_categoria,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject requestJSON = null;
                        try {
                            requestJSON = new JSONObject(response.toString());
                            String estado = requestJSON.getString("estado");
                            String mensaje = requestJSON.getString("mensaje");
                            if(estado.equals("1")){
                                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(context, "Registro almacenado en MySQL.", Toast.LENGTH_SHORT).show();
                            }else if(estado.equals("2")){
                                Toast.makeText(context, ""+mensaje, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "No se puedo guardar. \n" +
                        "Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este método se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id_cat", String.valueOf(id_cat_up));
                map.put("nom_cat", nom_cat_up);
                map.put("estado", String.valueOf(esta_cat_up));
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}