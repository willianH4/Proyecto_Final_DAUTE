package com.willianhdz.proyecto_final_daute.ui.categoria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.willianhdz.proyecto_final_daute.MySingleton;
import com.willianhdz.proyecto_final_daute.R;
import com.willianhdz.proyecto_final_daute.Setting_VAR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Categoria extends Fragment implements View.OnClickListener{
    private TextInputLayout ti_idcategoria, ti_namecategoria;
    private EditText et_idcategoria, et_namecategoria;
    public TextView et_categpria;
    private Spinner sp_estado;
    private Button btnSave, btnNew, btnEditar, btnDelete, btnbuscar;
    String datoSelect = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_categoria, container, false);

        ti_idcategoria = root.findViewById(R.id.ti_idcategoria);
        ti_namecategoria = root.findViewById(R.id.ti_namecategoria);
        et_idcategoria = root.findViewById(R.id.et_idcategoria);
        et_namecategoria = root.findViewById(R.id.et_namecategoria);
        sp_estado = root.findViewById(R.id.sp_estado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.estadoCategorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_estado.setAdapter(adapter);
        sp_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sp_estado.getSelectedItemPosition()>0) {
                    datoSelect = (String) sp_estado.getSelectedItem();
                }else{
                    datoSelect = "";
                }
                //Toast.makeText(getContext(), ""+datoSelect, Toast.LENGTH_SHORT).show();
 /*
 if(conta>=1 && sp_especialista.getSelectedItemPosition()>0){
 //Toast toast = Toast.makeText(getApplicationContext(), sp_especialista.getSelectedItem().toString(),
Toast.LENGTH_SHORT);
 //toast.setGravity(Gravity.CENTER, 0, 0);
 //toast.show();
 String dato_SP = sp_especialista.getSelectedItem().toString();
 String s[] = dato_SP.split("~");
 documento = s[0].trim();
 String nombres = s[1];
 }else{
 }
 conta++;
 */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnSave = root.findViewById(R.id.btnSave);
        btnNew = root.findViewById(R.id.btnNew);
        btnEditar = root.findViewById(R.id.btnEditar);
        btnDelete = root.findViewById(R.id.btnEliminar);
        btnbuscar = root.findViewById(R.id.btnbuscar);


        btnSave.setOnClickListener(this);
        btnNew.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnbuscar.setOnClickListener(this);


        return root;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                //save_server(getContext(),);
                String id = et_idcategoria.getText().toString();
                String nombre = et_namecategoria.getText().toString();
                if (id.length() == 0){
                    et_idcategoria.setError("Campo obligatorio");
                }else if(nombre.length() == 0){
                    et_namecategoria.setError("Campo obligatorio");
                    //}else if(!datoSelect.isEmpty()){
                }else if(sp_estado.getSelectedItemPosition() > 0){
                    //Acciones para guardar registro en la base de datos.
                   // Toast.makeText(getContext(), "Registro guardado", Toast.LENGTH_SHORT).show();
                    save_server(getContext(), Integer.parseInt(id), nombre, datoSelect);
                }else{
                    Toast.makeText(getContext(), "Debe seleccionar un estado para la dto_categorias",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnNew:
                new_categories();
                break;
            case R.id.btnbuscar:
                String codigo_bu = et_idcategoria.getText().toString();
                if (codigo_bu.length()==0){
                    et_idcategoria.setError("Campo obligatorio");
                }else {
                    String idbuscar = et_idcategoria.getText().toString();
                    buscarcategoria("https://proyectorsfxresng.000webhostapp.com/service2020/buscar_categoria_id.php?codigo=" + codigo_bu + "");
                }
                break;
            case R.id.btnEditar:
                String codigo = et_idcategoria.getText().toString();
                String nombre_cat = et_namecategoria.getText().toString();

                if (codigo.length()==0){
                    et_idcategoria.setError("Campo obligatorio");
                }else if (nombre_cat.length()==0){
                    et_namecategoria.setError("Campo obligatorio");
                }else if (sp_estado.getSelectedItemPosition()>0 ){
                    String est = (String) sp_estado.getSelectedItem();
                    uddate_server(getContext(), Integer.parseInt(codigo), nombre_cat, est);
                    Toast.makeText(getContext(), "Registro Actualizado", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "seleccione un estado", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btnEliminar:
                String codigo_de = et_idcategoria.getText().toString();
                if (codigo_de.length()==0){
                    et_idcategoria.setError("Campo obligatorio");
                }else{
                    delete_server(getContext(), Integer.parseInt(codigo_de));
                    //Toast.makeText(getContext(), "Eliminación exitosa ", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
        }
    }

    private void save_server(final Context context, final int id_categoria, final String nom_categoria, final String
            estado_categoria) {
        StringRequest request = new StringRequest(Request.Method.POST, Setting_VAR.URL_guardar_categoria,
                new Response.Listener<String>() {
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
                map.put("id", String.valueOf(id_categoria));
                map.put("nombre", nom_categoria);
                map.put("estado", estado_categoria);
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    private void uddate_server(final Context context, final int id_cat_up, final String nom_cat_up, final String esta_cat_up) {
        StringRequest request = new StringRequest(Request.Method.POST, Setting_VAR.URL_update_categoria,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject requestJSON = null;
                        try {
                            requestJSON = new JSONObject(response.toString());
                            String estado = requestJSON.getString("estad");
                            String mensaje = requestJSON.getString("mensaje");
                            if(estado.equals("1")){
                                Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(context, "Registro almacenado en MySQL.", Toast.LENGTH_SHORT).show();
                            }else if(estado.equals("2")){
                                Toast.makeText(getContext(), ""+mensaje, Toast.LENGTH_SHORT).show();
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
                map.put("estado", esta_cat_up);
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    private void delete_server(final Context context, final int id_cat_de) {
        StringRequest request = new StringRequest(Request.Method.POST, Setting_VAR.URL_delete_categoria,
                new Response.Listener<String>() {
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
                Toast.makeText(context, "Error Delete Categoria. \n" +
                        "Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este método se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                map.put("id_categoria", String.valueOf(id_cat_de));
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    private void buscarcategoria(String Url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            JSONObject jsonObject = null;

            for (int i =0; i<response.length(); i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    et_namecategoria.setText(jsonObject.getString("nom_categoria"));
                    et_categpria.setText(jsonObject.getString("estado_categoria"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getContext(), "No se encuentra registro \n Pruebe con otro Id", Toast.LENGTH_SHORT).show();
            }
        }) ;
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }






    private void new_categories() {
        et_idcategoria.setText(null);
        et_namecategoria.setText(null);
        sp_estado.setSelection(0);
        et_categpria.setText(null);
    }

}
