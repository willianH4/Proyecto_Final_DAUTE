package com.willianhdz.proyecto_final_daute.ui.list_recycle_cate;

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
import com.example.app_crud_mysql.MainActivity;
import com.example.app_crud_mysql.MySingleton;
import com.example.app_crud_mysql.R;
import com.example.app_crud_mysql.Setting_VAR;
import com.example.app_crud_mysql.ui.categoria.Edit_Categoria;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.R;

public class DetalleCategoria extends AppCompatActivity implements View.OnClickListener {
    private TextView tvCodigo, tvNombre, tvEstado;
    private Button btnEditarCat, btnBorrarCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_categoria);
        tvCodigo = findViewById(R.id.tvCodigoCatDetalle);
        tvNombre = findViewById(R.id.tvNombreCatDetalle);
        tvEstado = findViewById(R.id.tvEstadoCatDetalle);
        btnEditarCat = findViewById(R.id.btnEditarCat);
        btnBorrarCat = findViewById(R.id.btnBorrarCat);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("codigo");
        String noma = bundle.getString("nombre");
        String esta = bundle.getString("estado");

        tvCodigo.setText(id);
        tvNombre.setText(noma);
        tvEstado.setText(esta);

        btnEditarCat.setOnClickListener(this);
        btnBorrarCat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String id = tvCodigo.getText().toString();
        if (view.getId() == R.id.btnBorrarCat){
            delete_server(this, Integer.parseInt(id));
            //Toast.makeText(this, "Hole Borrado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(view.getId() == R.id.btnEditarCat){
            //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            String code = tvCodigo.getText().toString();
            String name = tvNombre.getText().toString();
            String estado = tvEstado.getText().toString();

            Intent intent = new Intent(getApplicationContext(), Edit_Categoria.class);
            intent.putExtra("senal", "1");
            intent.putExtra("codigo", code);
            intent.putExtra("nombre", name);
            intent.putExtra("estado", estado);
            startActivity(intent);
        }

        //Intent intent = new Intent(this, HomeFragment.class);
        //startActivity(intent);
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
}