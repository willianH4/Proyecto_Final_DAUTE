package com.example.app_crud_mysql.ui.list_recycle_cate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.app_crud_mysql.MySingleton;
import com.example.app_crud_mysql.R;
import com.example.app_crud_mysql.Setting_VAR;

import com.example.app_crud_mysql.ui.dts.dto_categorias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    ArrayList<String> lista = null;
    ArrayList<dto_categorias> listaCategoria;

    private RecyclerView recyclerView;
    private AdaptadorCategoria adaptadorCategoria;


    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = root.findViewById(R.id.rvProd);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recibirAllCat();

        return root;
    }



    private void recibirAllCat(){
        listaCategoria = new ArrayList<dto_categorias>();
        lista = new ArrayList<String>();
        String urlConsultaCategoria = Setting_VAR.URL_consultaAllCategorias;
        StringRequest request = new StringRequest(Request.Method.POST, urlConsultaCategoria, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray respuestaJSOn = new JSONArray(response);
                    int totalEnct = respuestaJSOn.length();

                    dto_categorias objCategorias = null;
                    for (int i = 0; i < respuestaJSOn.length(); i++){
                        JSONObject categoriaObj = respuestaJSOn.getJSONObject(i);
                        int idC = categoriaObj.getInt("id_categoria");
                        String name = categoriaObj.getString("nom_categoria");
                        int stado = categoriaObj.getInt("estado_categoria");

                        objCategorias = new dto_categorias(idC, name, stado);

                        listaCategoria.add(objCategorias);


                        //lista.add(listaCategoria.get(i).getIdCategoria() + " - " + listaCategoria.get(i).getNombre());
                        adaptadorCategoria = new AdaptadorCategoria(getContext(), listaCategoria);

                        recyclerView.setAdapter(adaptadorCategoria);

                        Log.i("Id Categoria:    ", String.valueOf(objCategorias.getId_categoria()));
                        Log.i("Nombre:    ", String.valueOf(objCategorias.getNom_categoria()));

                    }
                    //resultado.setText("Datos: " + response.toString());
                    //Toast.makeText(getContext(), "Id: " + idCategori + "\nNombre: " + nombreCat + "\nEstado: " + estadoCat, Toast.LENGTH_SHORT).show();

                } catch (JSONException ex){
                    String none = ex.toString();
                    Log.i("NO consulta ***** ", none);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String err = volleyError.toString();
                Log.i("No se pudo **********", err);
            }
        });
        //tiempo de respuesta, establece politica de reintentos
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}