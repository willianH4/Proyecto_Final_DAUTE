package com.willianhdz.proyecto_final_daute.ui.list_recycle_usu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.willianhdz.proyecto_final_daute.ui.usuario.Edit_Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DetalleUsuario extends AppCompatActivity implements View.OnClickListener {
    private TextView tvcodigo, tvNombre, tvApe, tvCorreo, tvUsu, tvClave, tvTipo, tvEstado, tvPregun, tvRespu, tvfecha;
    private Button btnEditarPro, btnBorrarPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_usuario);
        tvcodigo = findViewById(R.id.tvId);
        tvNombre = findViewById(R.id.tvNombre);
        tvApe = findViewById(R.id.tvApellido);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvUsu = findViewById(R.id.tvUsuario);
        tvClave = findViewById(R.id.tvClave);
        tvTipo = findViewById(R.id.tvTipo);
        tvEstado = findViewById(R.id.tvEstado);
        tvPregun = findViewById(R.id.tvPregunta);
        tvRespu = findViewById(R.id.tvRespuesta);
        tvfecha = findViewById(R.id.tvFecha);

        btnEditarPro = findViewById(R.id.btnEditarCat);
        btnBorrarPro = findViewById(R.id.btnBorrarCat);

        Bundle bundle = getIntent().getExtras();
       String codigo = bundle.getString("id");
        String nombre = bundle.getString("nombre");
        String ape = bundle.getString("apellido");
        String correo = bundle.getString("correo");
        String usu = bundle.getString("usuario");
        String clave = bundle.getString("clave");
        String tipo = bundle.getString("tipo");
        String est = bundle.getString("estado");
        String pregun = bundle.getString("pregunta");
        String respu = bundle.getString("respuesta");
        String fech = bundle.getString("fecha");



        tvcodigo.setText(codigo);
        tvNombre.setText(nombre);
        tvApe.setText(ape);
        tvCorreo.setText(correo);
        tvUsu.setText(usu);
        tvClave.setText(clave);
        tvTipo.setText(tipo);
        tvEstado.setText(est);
        tvRespu.setText(respu);
        tvPregun.setText(pregun);
        tvfecha.setText(fech);

        btnEditarPro.setOnClickListener(this);
        btnBorrarPro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String id = tvcodigo.getText().toString();
        if (view.getId() == R.id.btnBorrarCat){
            delete_usuarios(this, id);
            //Toast.makeText(this, "Hole Borrado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(view.getId() == R.id.btnEditarCat){
            //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            String code = tvcodigo.getText().toString();
            String name = tvNombre.getText().toString();
            String ape = tvApe.getText().toString();
            String corre = tvCorreo.getText().toString();
            String usua = tvUsu.getText().toString();
            String cla = tvClave.getText().toString();
            String tipo = tvTipo.getText().toString();
            String est = tvEstado.getText().toString();
            String preg = tvPregun.getText().toString();
            String resp = tvRespu.getText().toString();
            String fecha = tvfecha.getText().toString();

            Intent intent = new Intent(getApplicationContext(), Edit_Usuario.class);
            intent.putExtra("senal", "1");
            intent.putExtra("id", code);
            intent.putExtra("nombre", name);
            intent.putExtra("apellido", ape);
            intent.putExtra("correo", corre);
            intent.putExtra("usuario", usua);
            intent.putExtra("clave", cla);
            intent.putExtra("tipo", tipo);
            intent.putExtra("estado", est);
            intent.putExtra("pregunta", preg);
            intent.putExtra("respuesta", resp);
            intent.putExtra("fecha", fecha);
            startActivity(intent);
        }

        //Intent intent = new Intent(this, HomeFragment.class);
        //startActivity(intent);
    }

    private void delete_usuarios(final Context context, final String id_usu){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Setting_VAR.URL_delete_usuario, new Response.Listener<String>() {
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
                map.put("id_usuario", id_usu);

                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}