package com.willianhdz.proyecto_final_daute.ui.list_recycle_pro;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.willianhdz.proyecto_final_daute.MySingleton;
import com.willianhdz.proyecto_final_daute.R;
import com.willianhdz.proyecto_final_daute.Setting_VAR;
import com.willianhdz.proyecto_final_daute.ui.dts.dto_productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.Request.Method;

public class RecyclerviewProductos extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    RecyclerView recyclerViewProductos;
    ArrayList<dto_productos> listaProductos;

    ProgressDialog progressDialog;
    JsonObjectRequest jsonObjectRequest;

    public RecyclerviewProductos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_recyclerview_productos, container, false);
        listaProductos = new ArrayList<>();

        recyclerViewProductos = (RecyclerView) vista.findViewById(R.id.rvProductos);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewProductos.setHasFixedSize(true);

        //Agregando el metodo
        cargarService();
        return vista;
    }

    private void cargarService(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Consultando registros, por favor espere!");
        progressDialog.show();

        String url = Setting_VAR.URL_consultarAllProductos;

        jsonObjectRequest = new JsonObjectRequest(Method.GET,url, (String) null, this, this);

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "Error de conexion "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("Error",error.toString());
        progressDialog.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        dto_productos productos = null;

        JSONArray json = response.optJSONArray("productos");
        try {

            for (int i = 0; i < json.length(); i++) {
                productos = new dto_productos();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                productos.setId_producto(jsonObject.optInt("id_producto"));
                productos.setNom_producto(jsonObject.optString("nom_producto"));
                productos.setDes_producto(jsonObject.optString("des_producto"));
                productos.setStock(jsonObject.optDouble("stock"));
                productos.setPrecio(jsonObject.optDouble("precio"));
                productos.setUnidad_de_medida(jsonObject.optString("unidad_de_medida"));
                productos.setEstado_producto(jsonObject.optInt("estado_producto"));
                productos.setCategoria(jsonObject.optInt("categoria"));
                productos.setFecha(jsonObject.optString("fecha_entrada"));

                listaProductos.add(productos);
        }

            progressDialog.hide();
            ProductoAdapter adapter = new ProductoAdapter(listaProductos);
            recyclerViewProductos.setAdapter(adapter);

    }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error en la conexion " +
                    " "+response, Toast.LENGTH_LONG).show();
            progressDialog.hide();
        }
    }
}