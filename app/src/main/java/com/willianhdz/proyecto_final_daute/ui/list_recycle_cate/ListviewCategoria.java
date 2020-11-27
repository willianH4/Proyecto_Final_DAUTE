package com.willianhdz.proyecto_final_daute.ui.list_recycle_cate;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.willianhdz.proyecto_final_daute.MySingleton;
import com.willianhdz.proyecto_final_daute.R;
import com.willianhdz.proyecto_final_daute.Setting_VAR;
import com.willianhdz.proyecto_final_daute.ui.dts.dto_categorias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListviewCategoria extends Fragment {
    private ListView lst;
    private Button listar;
    ArrayList<String> lista = null;
    ArrayList<dto_categorias> listaCategoria;
    private LinearLayoutCompat resultado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_listview_categoria, container, false);
        ConstraintLayout frameLayout1 = root.findViewById(R.id.constraintLayout);
        final LinearLayoutCompat frameLayout2 = root.findViewById(R.id.fm2);

        lst = root.findViewById(R.id.lstCategoria);
        resultado = root.findViewById(R.id.fm2);

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

                        //lista.add(listaCategoria.get(i).getId_categoria() + " - " + listaCategoria.get(i).getNom_categoria());

                        lista.add("\n**       **\n"+ "Id Categoria: " + listaCategoria.get(i).getId_categoria() + "\nNombre Categoria: "
                                + listaCategoria.get(i).getNom_categoria() + "\nEstado Categoria " + listaCategoria.get(i).getEstado_categoria());

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lista);
                        lst.setAdapter(adapter);

                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String codigo = "" + listaCategoria.get(i).getId_categoria();
                                String nombre = "" + listaCategoria.get(i).getNom_categoria();
                                String estado = "" + listaCategoria.get(i).getEstado_categoria();

                                /*
                                Bundle envio = new Bundle();
                                envio.putInt("id", Integer.parseInt(codigo));
                                envio.putString("nombre", nombre);
                                envio.putInt("estado", Integer.parseInt(estado));*/


                                Intent intent = new Intent(getActivity(), DetalleCategoria.class);
                                intent.putExtra("codigo", codigo);
                                intent.putExtra("nombre", nombre);
                                intent.putExtra("estado", estado);
                                startActivity(intent);
                            }
                        });

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