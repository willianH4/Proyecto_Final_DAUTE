package com.willianhdz.proyecto_final_daute.ui.producto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.willianhdz.proyecto_final_daute.MainActivity;
import com.willianhdz.proyecto_final_daute.MySingleton;
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

public class Edit_Producto extends AppCompatActivity {
    private TextInputLayout ti_id, ti_nombre_prod, ti_descripcion, ti_stock,
            ti_precio, ti_unidadmedida;
    private EditText et_id, et_nombre_prod, et_descripcion, et_stock,
            et_precio, et_unidadmedida;
    private Spinner sp_estadoProductos, sp_fk_categoria;
    private TextView tv_fechahora;
    private Button  btnEditar, btnSalir;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_producto);

        ti_id = findViewById(R.id.ti_id);
        ti_nombre_prod = findViewById(R.id.ti_nombre_prod);
        ti_descripcion = findViewById(R.id.ti_descripcion);
        et_id = findViewById(R.id.et_id);
        et_nombre_prod = findViewById(R.id.et_nombre_prod);
        et_descripcion = findViewById(R.id.et_descripcion);
        et_stock = findViewById(R.id.et_stock);
        et_precio = findViewById(R.id.et_precio);
        et_unidadmedida = findViewById(R.id.et_unidadmedida);
        sp_fk_categoria = findViewById(R.id.sp_fk_categoria);
        tv_fechahora = findViewById(R.id.tv_fechahora);
        tv_fechahora.setText(timedate());

        btnEditar = findViewById(R.id.btnEditarPro);
        btnSalir = findViewById(R.id.btnSalir);
        sp_estadoProductos = findViewById(R.id.sp_estadoProductos);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
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
                                        fk_categorias(getApplicationContext());
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
                                                                      Toast toast = Toast.makeText(getApplicationContext(), "Id cat: " + idcategoria + "\n" + "Nombre Categoria: "+nombrecategoria,Toast.LENGTH_SHORT);
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

        String senal = "";
        String codigo = "";
        String nombre = "";

        String descrip = "";
        String stock = "";
        String precio = "";
        String unidad = "";
        String cate = "";
        String fecha = "";
        String estad = "";


        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                codigo = bundle.getString("codigo");
                senal = bundle.getString("senal");
                nombre = bundle.getString("nombre");
                descrip = bundle.getString("des");
                stock = bundle.getString("stock");
                precio = bundle.getString("precio");
                unidad = bundle.getString("unidad");
                cate = bundle.getString("cate");
                estad = bundle.getString("estado");

                if (senal.equals("1")){
                    et_id.setText(codigo);
                    et_nombre_prod.setText(nombre);
                    et_descripcion.setText(descrip);
                    et_stock.setText(stock);
                    et_precio.setText(precio);
                    et_unidadmedida.setText(unidad);
                    sp_estadoProductos.setSelection(1+Integer.parseInt(estad));
                    sp_fk_categoria.setSelection(Integer.parseInt(cate));


                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

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
                    Toast.makeText(getApplicationContext(), "Debe seleccionar el estado del dto_productos.", Toast.LENGTH_SHORT).show();
                }else if(sp_fk_categoria.getSelectedItemPosition() > 0){
//Toast.makeText(getContext(), "Good...",Toast.LENGTH_SHORT).show();
                    String esta = sp_estadoProductos.getSelectedItem().toString();
                    update_productos(getApplicationContext(), id, nombre, descripcion, stock, precio, unidad, esta, idcategoria);
                    Intent intent = new Intent(com.willianhdz.proyecto_final_daute.ui.producto.Edit_Producto.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la dto_categorias.", Toast.LENGTH_SHORT).show();
                }



            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.willianhdz.proyecto_final_daute.ui.producto.Edit_Producto.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
                                ArrayAdapter<String> adaptador =new ArrayAdapter<String> (getApplicationContext(),android.R.layout.simple_spinner_item, lista);
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

}