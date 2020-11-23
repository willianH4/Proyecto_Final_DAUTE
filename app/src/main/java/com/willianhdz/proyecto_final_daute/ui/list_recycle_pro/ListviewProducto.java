package com.willianhdz.proyecto_final_daute.ui.list_recycle_pro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.willianhdz.proyecto_final_daute.MySingleton;
import com.willianhdz.proyecto_final_daute.R;
import com.willianhdz.proyecto_final_daute.Setting_VAR;
import com.willianhdz.proyecto_final_daute.ui.dts.dto_productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListviewProducto extends Fragment {
    private ListView lst;
    private Button listar;
    ArrayList<String> lista = null;
    ArrayList<dto_productos> listaProducto;
    private LinearLayoutCompat resultado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_listview_producto, container, false);
        ConstraintLayout frameLayout1 = root.findViewById(R.id.constraintLayout2);
        final LinearLayoutCompat frameLayout2 = root.findViewById(R.id.fm3);

        lst = root.findViewById(R.id.lstProducto);
        resultado = root.findViewById(R.id.fm3);

        recibirAllPro();
        return root;
    }




    private void recibirAllPro(){
        listaProducto = new ArrayList<dto_productos>();
        lista = new ArrayList<String>();
        String urlConsultaProducto = Setting_VAR.URL_consultarAllProductos2;
        StringRequest request = new StringRequest(Request.Method.POST, urlConsultaProducto, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray respuestaJSOn = new JSONArray(response);
                    int totalEnct = respuestaJSOn.length();

                    dto_productos objProductos = null;
                    for (int i = 0; i < respuestaJSOn.length(); i++){
                        JSONObject productoObj = respuestaJSOn.getJSONObject(i);
                        int idP = productoObj.getInt("id_producto");
                        String name = productoObj.getString("nom_producto");
                        String des = productoObj.getString("des_producto");
                        double stock = productoObj.getDouble("stock");
                        double prec = productoObj.getDouble("precio");
                        String unidad = productoObj.getString("unidad_de_medida");
                        int estado = productoObj.getInt("estado_producto");
                        int cate = productoObj.getInt("categoria");
                        String fecha = productoObj.getString("fecha_entrada");

                        objProductos = new dto_productos(idP, name, des, stock, prec, unidad, estado, cate, fecha);

                        listaProducto.add(objProductos);

                        lista.add(listaProducto.get(i).getId_producto() + " - " + listaProducto.get(i).getNom_producto());

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lista);
                        lst.setAdapter(adapter);

                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String codigo = "" + listaProducto.get(i).getId_producto();
                                String nombre = "" + listaProducto.get(i).getNom_producto();
                                String des = "" + listaProducto.get(i).getDes_producto();
                                String stock = "" + listaProducto.get(i).getStock();
                                String precio = "" + listaProducto.get(i).getPrecio();
                                String unidad = "" + listaProducto.get(i).getUnidad_de_medida();
                                String estado = "" + listaProducto.get(i).getEstado_producto();
                                String cate = "" + listaProducto.get(i).getCategoria();
                                String fecha = "" + listaProducto.get(i).getFecha();
                                /*
                                Bundle envio = new Bundle();
                                envio.putInt("id", Integer.parseInt(codigo));
                                envio.putString("nombre", nombre);
                                envio.putInt("estado", Integer.parseInt(estado));*/

                                Intent intent = new Intent(getActivity(), DetalleProducto.class);
                                intent.putExtra("codigo", codigo);
                                intent.putExtra("nombre", nombre);
                                intent.putExtra("des", des);
                                intent.putExtra("stock", stock);
                                intent.putExtra("precio", precio);
                                intent.putExtra("unidad", unidad);
                                intent.putExtra("estado", estado);
                                intent.putExtra("cate", cate);
                                intent.putExtra("fecha", fecha);
                                startActivity(intent);
                            }
                        });
/*
                        Log.i("Id producto:    ", String.valueOf(objProductos.getId_producto()));
                        Log.i("Nombre:    ", String.valueOf(objProductos.getNom_producto()));
*/
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