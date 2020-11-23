package com.willianhdz.proyecto_final_daute.ui.usuario;

import android.app.AppComponentFactory;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.app_crud_mysql.MainActivity;
import com.example.app_crud_mysql.MySingleton;
import com.example.app_crud_mysql.R;
import com.example.app_crud_mysql.Setting_VAR;
import com.example.app_crud_mysql.ui.dts.dto_usuarios;
import com.example.app_crud_mysql.ui.producto.Edit_Producto;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Edit_Usuario extends AppCompatActivity {

    private TextInputLayout ti_id, ti_nombre_prod, ti_descripcion, ti_stock,
            ti_precio, ti_unidadmedida;
    private EditText et_id, et_nombre_usu, et_apellido_usu, et_correo_usu, et_usuario, et_clave, et_respuesta;

    private Spinner sp_estadoUsu, sp_tipo, sp_pregunta;
    private TextView tv_fechahora;
    private Button btnSave, btnNew, btnEditar, btnEliminar, btnBuscar;
    ProgressDialog progressDialog;
    ArrayList<String> lista = null;
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
    dto_usuarios datos = new dto_usuarios();
    String Pregunt2 = "";
    String Pregunt = "";
    String Tipo = "";
    int conta = 0;
    int nu = 0;
    String datoStatusUsua = "";
    //Instancia DTO
    dto_usuarios dto = new dto_usuarios();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_usuario);
// Inflate the layout for this fragment

        ti_id = findViewById(R.id.ti_id);
        ti_nombre_prod = findViewById(R.id.ti_nombre_prod);
        ti_descripcion = findViewById(R.id.ti_descripcion);

        et_id = findViewById(R.id.et_id_usu);
        et_nombre_usu = findViewById(R.id.et_nombre_usu);
        et_apellido_usu = findViewById(R.id.et_apellido_usu);
        et_correo_usu = findViewById(R.id.et_correo);
        et_usuario = findViewById(R.id.et_usuario);
        et_clave = findViewById(R.id.et_clave);
        et_respuesta = findViewById(R.id.et_respuesta);
        sp_estadoUsu = findViewById(R.id.sp_estadoUsuario);
        sp_tipo = findViewById(R.id.sp_tipo);
        sp_pregunta = findViewById(R.id.sp_preguntas);


        btnNew = findViewById(R.id.btnSalir);
        btnEditar = findViewById(R.id.btnEditarUsu);
        sp_estadoUsu = findViewById(R.id.sp_estadoUsuario);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.estadoUsuarios, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_estadoUsu.setOnItemSelectedListener(new
                         AdapterView.OnItemSelectedListener() {
                             @Override
                             public void onItemSelected(AdapterView<?> parent, View view, int
                                     position, long id) {
                                 if(sp_estadoUsu.getSelectedItemPosition()>0)
                                     datoStatusUsua = sp_estadoUsu.getSelectedItem().toString();
                                 else{
                                     datoStatusUsua = "";
                                 }
//Toast.makeText(getContext(), ""+datoStatusProduct,Toast.LENGTH_SHORT).show();
                                     }
                                     @Override
                                     public void onNothingSelected(AdapterView<?> parent) {
                                     }
                                 });
//Informacion Spinner
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.TipoUsuario, android.R.layout.simple_spinner_item);
        sp_tipo.setAdapter(adapter1);
        sp_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(MainActivityUsuario.this, "Seleccionaste: " + parent.getItemIdAtPosition(position), Toast.LENGTH_SHORT).show();
                String tipo = String.valueOf(parent.getItemIdAtPosition(position));
                int val;
                switch (tipo){
                    case "3":
                        val = 3;
                       Tipo = String.valueOf(val);
                        break;
                    case "2":
                        val = 2;
                        Tipo = String.valueOf(val);
                        break;
                    case "1":
                        val = 1;
                        Tipo = String.valueOf(val);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Preguntas, android.R.layout.simple_spinner_item);
        sp_pregunta.setAdapter(adapter2);
        sp_pregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivityUsuario.this, "Seleccionaste: " + parent.getItemIdAtPosition(position), Toast.LENGTH_SHORT).show();
                String est = String.valueOf(parent.getItemIdAtPosition(position));

                switch (est){
                    case "1":
                        Pregunt = "1 ¿Nombre de tu mama?";
                        break;
                    case "2":
                        Pregunt ="2 ¿Nombre de tu primera mascota?";
                        break;
                    case "3":
                        Pregunt ="3 ¿Cual fue tu primera escuela?";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String senal = "";
        String codigo = "";
        String nombre = "";
        String ape = "";
        String correo = "";
        String usu = "";
        String clave = "";
        String tipo = "";
        String est = "";
        String pregun = "";
        String respu = "";



        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                codigo = bundle.getString("id");
                senal = bundle.getString("senal");
                nombre = bundle.getString("nombre");
                ape = bundle.getString("apellido");
                correo = bundle.getString("correo");
                usu = bundle.getString("usuario");
                clave = bundle.getString("clave");
                tipo = bundle.getString("tipo");
                est = bundle.getString("estado");
                pregun = bundle.getString("pregunta");
                respu = bundle.getString("respuesta");


                if (senal.equals("1")){
                    et_id.setText(codigo);
                    et_nombre_usu.setText(nombre);
                    et_apellido_usu.setText(ape);
                    et_correo_usu.setText(correo);
                    et_usuario.setText(usu);
                    et_clave.setText(clave);
                    sp_estadoUsu.setSelection(1+Integer.parseInt(est));
                    sp_tipo.setSelection(Integer.parseInt(tipo));
                    et_respuesta.setText(respu);
                    sp_pregunta.setSelection(Integer.parseInt(pregun));
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }



        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Intent intent = new Intent(Edit_Usuario.this, MainActivity.class);
        startActivity(intent);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_id.getText().toString();
                String nombre = et_nombre_usu.getText().toString();
                String apellido = et_apellido_usu.getText().toString();
                String correo = et_correo_usu.getText().toString();
                String usuario = et_usuario.getText().toString();
                String clave = et_clave.getText().toString();
                String respuesta = et_respuesta.getText().toString();
                if(id.length() == 0){
                    et_id.setError("Campo obligatorio.");
                }else if(nombre.length() == 0){
                    et_nombre_usu.setError("Campo obligatorio.");
                }else if(apellido.length() == 0){
                    et_apellido_usu.setError("Campo obligatorio.");
                }else if(correo.length() == 0){
                    et_correo_usu.setError("Campo obligatorio.");
                }else if(usuario.length() == 0){
                    et_usuario.setError("Campo obligatorio.");
                }else if(clave.length() == 0){
                    et_clave.setError("Campo obligatorio.");
                }else if(sp_tipo.getSelectedItemPosition() == 0){
                    Toast.makeText(getApplicationContext(), "Debe seleccionar el tipo usuario.", Toast.LENGTH_SHORT).show();
                }else if(sp_estadoUsu.getSelectedItemPosition() == 0){
                    Toast.makeText(getApplicationContext(), "Debe seleccionar el estado usuario.", Toast.LENGTH_SHORT).show();
                }else if(sp_pregunta.getSelectedItemPosition() > 0){
                    String esta = sp_estadoUsu.getSelectedItem().toString();

                    update_usuarios(getApplicationContext(), id, nombre, apellido, correo, usuario, clave, Tipo, datoStatusUsua, Pregunt, respuesta);
                }else{
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la dto_categorias.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private String timedate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        String fecha = sdf.format(cal.getTime());
        return fecha;
    }
// metodo para guardar usuario


    // metodo para actualizar usuario
    private void update_usuarios(final Context context, final String id_usu,
                               final String nom_usu, final String apell_usu,
                               final String correo, final String usuario,
                               final String clave, final String tipo,
                               final String estado_usu, final String pregunta,
                               final String respuesta){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Setting_VAR.URL_update_usuario, new Response.Listener<String>() {
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
                map.put("id", id_usu);
                map.put("nombre", nom_usu);
                map.put("apellido", apell_usu);
                map.put("correo", correo);
                map.put("usuario", usuario);
                map.put("clave", clave);
                map.put("tipo", tipo);
                map.put("estado", estado_usu);
                map.put("pregunta", pregunta);
                map.put("respuesta", respuesta);
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

}