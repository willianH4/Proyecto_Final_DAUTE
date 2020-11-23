package com.willianhdz.proyecto_final_daute.ui.producto;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.app_crud_mysql.MySingleton;
import com.willianhdz.proyecto_final_daute.R;
import com.willianhdz.proyecto_final_daute.Setting_VAR;
import com.willianhdz.proyecto_final_daute.ui.dts.dto_categorias;
import com.willianhdz.proyecto_final_daute.ui.dts.dto_productos;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Producto extends Fragment {

    private TextInputLayout ti_id, ti_nombre_prod, ti_descripcion, ti_stock,
            ti_precio, ti_unidadmedida;
    private EditText et_id, et_nombre_prod, et_descripcion, et_stock,
            et_precio, et_unidadmedida;
    private Spinner sp_estadoProductos, sp_fk_categoria;
    private TextView tv_fechahora;
    private Button btnSave, btnNew, btnEditar, btnEliminar, btnBuscar;
    ProgressDialog progressDialog;
    ArrayList<String> lista = null;
    ArrayList<dto_categorias> listaCategorias; //Va a representar la información que se va a mostrar en el combo
    //Arreglos para efectuar pruebas de carga de opciones en spinner.
    String elementos[] = {"Uno", "Dos", "Tres", "Cuatro", "Cinco"};
    final String[] elementos1 =new String[]{
            "Seleccione",
            "1",
            "2",
            "3",
            "4",
            "5"
    };
    String idcategoria = "";
    String nombrecategoria = "";
    int conta = 0;
    String datoStatusProduct = "";
    //Instancia DTO
    dto_productos dto = new dto_productos();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_producto, container,
                false);
        ti_id = view.findViewById(R.id.ti_id);
        ti_nombre_prod = view.findViewById(R.id.ti_nombre_prod);
        ti_descripcion = view.findViewById(R.id.ti_descripcion);
        et_id = view.findViewById(R.id.et_id);
        et_nombre_prod = view.findViewById(R.id.et_nombre_prod);
        et_descripcion = view.findViewById(R.id.et_descripcion);
        et_stock = view.findViewById(R.id.et_stock);
        et_precio = view.findViewById(R.id.et_precio);
        et_unidadmedida = view.findViewById(R.id.et_unidadmedida);
        sp_fk_categoria = view.findViewById(R.id.sp_fk_categoria);
        tv_fechahora = view.findViewById(R.id.tv_fechahora);
        tv_fechahora.setText(timedate());
        btnSave = view.findViewById(R.id.btnSave);
        btnNew = view.findViewById(R.id.btnNew);
        btnEditar = view.findViewById(R.id.btnEditarPro);
        btnBuscar = view.findViewById(R.id.btnbuscarPro);
        btnEliminar = view.findViewById(R.id.btnEliminarPro);
        sp_estadoProductos = view.findViewById(R.id.sp_estadoProductos);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.estadoProductos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_estadoProductos.setOnItemSelectedListener(new
                         AdapterView.OnItemSelectedListener() {
                             @Override
                             public void onItemSelected(AdapterView<?> parent, View view, int
                                     position, long id) {
                                 if(sp_estadoProductos.getSelectedItemPosition()>0)
                                     datoStatusProduct = sp_estadoProductos.getSelectedItem().toString();
                                 else{
                                     datoStatusProduct = "";
                                 }
//Toast.makeText(getContext(), ""+datoStatusProduct,Toast.LENGTH_SHORT).show();
                                     }
                                     @Override
                                     public void onNothingSelected(AdapterView<?> parent) {
                                     }
                                 });
//Llamo al método para que muestre los datos de la busqueda al carga la actividad.
        fk_categorias(getContext());
//ArrayAdapter<String> adaptador = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, obtenerListaCategorias());
//ArrayAdapter<String> adaptador =new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, elementos);
//sp_fk_categoria.setAdapter(adaptador);
//Evento del spinner creado para extraer la información seleccionada en cada item/opción.
                sp_fk_categoria.setOnItemSelectedListener(new
              AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> parent, View view, int
                          position, long id) {
                      if(conta>=1 && sp_fk_categoria.getSelectedItemPosition()>0){
                          String item_spinner= sp_fk_categoria.getSelectedItem().toString();
        //Hago una busqueda en la cadena seleccionada en elspinner para separar el idcategoria y el nombre de la dto_categorias
        //Esto es necesario, debido a que lo que debe enviarse aguardar a la base de datos es únicamente el idcategoria.
                          String s[] = item_spinner.split("~");
        //Dato ID CATEGORIA
                          idcategoria = s[0].trim();
                          //Con trim eliminoespacios al inicio y final de la cadena para enviar limpio el ID CATEGORIA.
        //Dato NOMBRE DE LA CATEGORIA
                          nombrecategoria = s[1];
                          Toast toast = Toast.makeText(getContext(), "Id cat: " + idcategoria + "\n" + "Nombre Categoria: "+nombrecategoria,Toast.LENGTH_SHORT);
                          toast.setGravity(Gravity.CENTER, 0, 0);toast.show();
                      }else{
                          idcategoria = "";
                          nombrecategoria = "";
                      }
                      conta++;
                  }
                  @Override
                  public void onNothingSelected(AdapterView<?> parent) {
                  }
              });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*int id = Integer.parseInt(et_id.getText().toString());
String nombre = et_nombre_prod.getText().toString();
String descripcion = et_descripcion.getText().toString();
double stock =
Double.parseDouble(et_stock.getText().toString());
double precio =
Double.parseDouble(et_precio.getText().toString());
String unidad = et_unidadmedida.getText().toString();*/
                String id = et_id.getText().toString();
                String nombre = et_nombre_prod.getText().toString();
                String descripcion = et_descripcion.getText().toString();
                String stock = et_stock.getText().toString();
                String precio = et_precio.getText().toString();
                String unidad = et_unidadmedida.getText().toString();
                if(id.length() == 0){
                    et_id.setError("Campo obligatorio.");
                }else if(nombre.length() == 0){
                    et_nombre_prod.setError("Campo obligatorio.");
                }else if(descripcion.length() == 0){
                    et_descripcion.setError("Campo obligatorio.");
                }else if(stock.length() == 0){
                    et_stock.setError("Campo obligatorio.");
                }else if(precio.length() == 0){
                    et_precio.setError("Campo obligatorio.");
                }else if(unidad.length() == 0){
                    et_unidadmedida.setError("Campo obligatorio.");
                }else if(sp_estadoProductos.getSelectedItemPosition() == 0){
                    Toast.makeText(getContext(), "Debe seleccionar el estado del dto_productos.", Toast.LENGTH_SHORT).show();
                }else if(sp_fk_categoria.getSelectedItemPosition() > 0){
//Toast.makeText(getContext(), "Good...",Toast.LENGTH_SHORT).show();
                    String esta = sp_estadoProductos.getSelectedItem().toString();
                    save_productos(getContext(), id, nombre, descripcion, stock, precio, unidad, esta, idcategoria);
                }else{
                    Toast.makeText(getContext(), "Debe seleccionar la dto_categorias.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_product();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_id.getText().toString();
                String nombre = et_nombre_prod.getText().toString();
                String descripcion = et_descripcion.getText().toString();
                String stock = et_stock.getText().toString();
                String precio = et_precio.getText().toString();
                String unidad = et_unidadmedida.getText().toString();
                if(id.length() == 0){
                    et_id.setError("Campo obligatorio.");
                }else if(nombre.length() == 0){
                    et_nombre_prod.setError("Campo obligatorio.");
                }else if(descripcion.length() == 0){
                    et_descripcion.setError("Campo obligatorio.");
                }else if(stock.length() == 0){
                    et_stock.setError("Campo obligatorio.");
                }else if(precio.length() == 0){
                    et_precio.setError("Campo obligatorio.");
                }else if(unidad.length() == 0){
                    et_unidadmedida.setError("Campo obligatorio.");
                }else if(sp_estadoProductos.getSelectedItemPosition() == 0){
                    Toast.makeText(getContext(), "Debe seleccionar el estado del dto_productos.", Toast.LENGTH_SHORT).show();
                }else if(sp_fk_categoria.getSelectedItemPosition() > 0){
//Toast.makeText(getContext(), "Good...",Toast.LENGTH_SHORT).show();
                    String esta = sp_estadoProductos.getSelectedItem().toString();
                    update_productos(getContext(), id, nombre, descripcion, stock, precio, unidad, esta, idcategoria);
                }else{
                    Toast.makeText(getContext(), "Debe seleccionar la dto_categorias.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo_bu = et_id.getText().toString();
                if (codigo_bu.length()==0){
                    et_id.setError("Campo obligatorio");
                }else {
                    buscarproducto("http://192.168.1.20/service2020/buscar_producto_id.php?codigo=" + codigo_bu + "");
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo_de = et_id.getText().toString();
                if (codigo_de.length()==0){
                    et_id.setError("Campo obligatorio");
                }else{
                    delete_productos(getContext(), codigo_de);
                    //Toast.makeText(getContext(), "Eliminación exitosa ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private String timedate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        String fecha = sdf.format(cal.getTime());
        return fecha;
    }
    //public ArrayList<dto_categorias> fk_categorias(final Context context){
    public void fk_categorias(final Context context){
        listaCategorias = new ArrayList<dto_categorias>();
        lista = new ArrayList<String>();
        lista.add("Seleccione Categoria");
        String url = Setting_VAR.URL_consultaAllCategorias;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            int totalEncontrados = array.length();
//Toast.makeText(context, "Total:"+totalEncontrados, Toast.LENGTH_SHORT).show();
                            dto_categorias obj_categorias = null;
//dto_categorias obj_categorias = new dto_categorias();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject categoriasObject =
                                        array.getJSONObject(i);
                                int id_categoria =
                                        categoriasObject.getInt("id_categoria");
                                String nombre_categoria =
                                        categoriasObject.getString("nom_categoria");
                                int estado_categoria =
                                        Integer.parseInt(categoriasObject.getString("estado_categoria"));
//Encapsulo registro por registro encontrado dentro del objeto de manera temporal
                                obj_categorias = new dto_categorias(id_categoria, nombre_categoria, estado_categoria);
/*obj_categorias.setId_categoria(id_categoria);
obj_categorias.setNom_categoria(nombre_categoria);
obj_categorias.setEstado_categoria(estado_categoria);*/
//Agrego todos los registros en el arraylist
                                listaCategorias.add(obj_categorias);
//Saco la información del arraylist y personalizo la forma en que deseo se muestren los datos en el spinner y
//Selecciono que datos se van a mostrar del resultado.
                                        lista.add(listaCategorias.get(i).getId_categoria()+" ~ "+listaCategorias.get(i).getNom_categoria());
//Creo un adaptador para cargar la lista preparada anteriormente.
//ArrayAdapter<String> adaptador = newArrayAdapter(getContext(), android.R.layout.simple_spinner_item,obtenerListaCategorias());
                                ArrayAdapter<String> adaptador =new ArrayAdapter<String> (getContext(),android.R.layout.simple_spinner_item, lista);
//Cargo los datos en el Spinner
                                sp_fk_categoria.setAdapter(adaptador);
//Muestro datos en LogCat para verificar larespuesta obtenida desde el servidor.
                                        Log.i("Id Categoria",
                                        String.valueOf(obj_categorias.getId_categoria()));
                                Log.i("Nombre Categoria",
                                        obj_categorias.getNom_categoria());
                                Log.i("Estado Categoria",
                                        String.valueOf(obj_categorias.getEstado_categoria()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error. Compruebe su acceso a Internet.", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
    /*private void save_productos(final Context context, final int id_prod,
    final String nom_prod, final String des_prod,
    final double stock, final double precio, final
    String uni_medida, final int estado_prod,
    final int dto_categorias){*/
    private void save_productos(final Context context, final String id_prod,
                                final String nom_prod, final String des_prod,
                                final String stock, final String precio,
                                final String uni_medida, final String estado_prod,
                                final String categoria){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Setting_VAR.URL_registrar_productos, new Response.Listener<String>() {
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
                    }else if(estado.equals("3")){
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
        }){
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id_prod", id_prod);
                map.put("nom_prod", nom_prod);
                map.put("des_prod", des_prod);
                map.put("stock", stock);
                map.put("precio", precio);
                map.put("uni_medida", uni_medida);
                map.put("estado_prod", estado_prod);
                map.put("categoria", categoria);
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void update_productos(final Context context, final String id_prod,
                                final String nom_prod, final String des_prod,
                                final String stock, final String precio,
                                final String uni_medida, final String estado_prod,
                                final String categoria){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Setting_VAR.URL_update_productos, new Response.Listener<String>() {
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
                    }else if(estado.equals("3")){
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
        }){
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id_prod", id_prod);
                map.put("nom_prod", nom_prod);
                map.put("des_prod", des_prod);
                map.put("stock", stock);
                map.put("precio", precio);
                map.put("uni_medida", uni_medida);
                map.put("estado_prod", estado_prod);
                map.put("categoria", categoria);
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void buscarproducto(String Url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i =0; i<response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        et_nombre_prod.setText(jsonObject.getString("nom_producto"));
                        et_descripcion.setText(jsonObject.getString("des_producto"));
                        et_stock.setText(jsonObject.getString("stock"));
                        et_precio.setText(jsonObject.getString("precio"));
                        et_unidadmedida.setText(jsonObject.getString("unidad_de_medida"));

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

    private void delete_productos(final Context context, final String id_prod){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Setting_VAR.URL_delete_productos, new Response.Listener<String>() {
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
                    }else if(estado.equals("3")){
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
        }){
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id_producto", id_prod);

                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }



    private void new_product() {
        et_id.setText(null);
        et_nombre_prod.setText(null);
        et_descripcion.setText(null);
        et_stock.setText(null);
        et_precio.setText(null);
        et_unidadmedida.setText(null);
        tv_fechahora.setText(null);
        sp_estadoProductos.setSelection(0);
        sp_fk_categoria.setSelection(0);
    }
    //No utilizo este método en nada por el momento
    public ArrayList<String> obtenerListaCategorias() {
//ArrayList<String> lista = new ArrayList<String>();
        lista = new ArrayList<String>();
        lista.add("Seleccione Categoria");
        for(int i=0;i<=listaCategorias.size();i++){
            lista.add(listaCategorias.get(i).getId_categoria()+" ~ "+listaCategorias.get(i).getNom_categoria());
        }
        return lista;
    }



}