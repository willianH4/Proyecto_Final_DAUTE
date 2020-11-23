package com.willianhdz.proyecto_final_daute.ui.list_recycle_pro;

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
import com.willianhdz.proyecto_final_daute.ui.producto.Edit_Producto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DetalleProducto extends AppCompatActivity implements View.OnClickListener {
    private TextView tvcodigo, tvNombre, tvDescrip, tvStock, tvprecio, tvUnidad, tvEstado, tvcategori, tvfecha;
    private Button btnEditarPro, btnBorrarPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        tvcodigo = findViewById(R.id.tvCodigoProDetalle);
        tvNombre = findViewById(R.id.tvNombreProDetalle);
        tvDescrip = findViewById(R.id.tvDescProDetalle);
        tvStock = findViewById(R.id.tvStockProDetalle);
        tvprecio = findViewById(R.id.tvPrecioProDetalle);
        tvUnidad = findViewById(R.id.tvUnidadProDetalle);
        tvEstado = findViewById(R.id.tvEstadoProDetalle);
        tvcategori = findViewById(R.id.tvCategoriaProDetalle);
        tvfecha = findViewById(R.id.tvFechaProDetalle);

        btnEditarPro = findViewById(R.id.btnEditarCat);
        btnBorrarPro = findViewById(R.id.btnBorrarCat);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("codigo");
        String noma = bundle.getString("nombre");
        String des = bundle.getString("des");
        String stock = bundle.getString("stock");
        String pre = bundle.getString("precio");
        String uni = bundle.getString("unidad");
        String esta = bundle.getString("estado");
        String cat = bundle.getString("cate");
        String fech = bundle.getString("fecha");



        tvcodigo.setText(id);
        tvNombre.setText(noma);
        tvDescrip.setText(des);
        tvStock.setText(stock);
        tvprecio.setText(pre);
        tvUnidad.setText(uni);
        tvEstado.setText(esta);
        tvcategori.setText(cat);
        tvfecha.setText(fech);

        btnEditarPro.setOnClickListener(this);
        btnBorrarPro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String id = tvcodigo.getText().toString();
        if (view.getId() == R.id.btnBorrarCat){
            delete_productos(this, Integer.parseInt(id));
            //Toast.makeText(this, "Hole Borrado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(view.getId() == R.id.btnEditarCat){
            //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            String code = tvcodigo.getText().toString();
            String name = tvNombre.getText().toString();
            String des = tvDescrip.getText().toString();
            String stock = tvStock.getText().toString();
            String pre = tvprecio.getText().toString();
            String uni = tvUnidad.getText().toString();
            String est = tvEstado.getText().toString();
            String cate = tvcategori.getText().toString();
            String fecha = tvfecha.getText().toString();

            Intent intent = new Intent(getApplicationContext(), Edit_Producto.class);
            intent.putExtra("senal", "1");
            intent.putExtra("codigo", code);
            intent.putExtra("nombre", name);
            intent.putExtra("des", des);
            intent.putExtra("stock", stock);
            intent.putExtra("precio", pre);
            intent.putExtra("unidad", uni);
            intent.putExtra("estado", est);
            intent.putExtra("cate", cate);
            intent.putExtra("fecha", fecha);
            startActivity(intent);
        }

        //Intent intent = new Intent(this, HomeFragment.class);
        //startActivity(intent);
    }

    private void delete_productos(final Context context, final int id_prod){
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
                map.put("id_producto", String.valueOf(id_prod));

                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}