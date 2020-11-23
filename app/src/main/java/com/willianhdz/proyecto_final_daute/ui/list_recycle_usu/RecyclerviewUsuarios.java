package com.willianhdz.proyecto_final_daute.ui.list_recycle_usu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.willianhdz.proyecto_final_daute.MySingleton;
import com.willianhdz.proyecto_final_daute.R;
import com.willianhdz.proyecto_final_daute.Setting_VAR;
import com.willianhdz.proyecto_final_daute.ui.dts.dto_usuarios;
import com.willianhdz.proyecto_final_daute.ui.list_recycle_cate.DashboardViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerviewUsuarios extends Fragment {
    ArrayList<String> lista = null;
    ArrayList<dto_usuarios> listaUsers;

    private RecyclerView recyclerView;
    private UsuariosAdapter usuariosAdapter;

    private DashboardViewModel dashboardViewModel;

    public RecyclerviewUsuarios() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_recyclerview_usuarios, container, false);

        recyclerView = root.findViewById(R.id.rvUsuarios);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recibirAllUsers();
        return root;
    }

    private void recibirAllUsers(){
        listaUsers = new ArrayList<dto_usuarios>();
        lista = new ArrayList<String>();
        String urlConsultaUsuarios = Setting_VAR.URL_consultarAllUsuario2;
        StringRequest request = new StringRequest(Request.Method.POST, urlConsultaUsuarios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray respuestaJSOn = new JSONArray(response);
                    int totalEnct = respuestaJSOn.length();

                    dto_usuarios objUsuarios = null;
                    for (int i = 0; i < respuestaJSOn.length(); i++){
                        JSONObject users = respuestaJSOn.getJSONObject(i);
                        int id = users.getInt("id");
                        String nombre = users.getString("nombre");
                        String apellidos = users.getString("apellido");
                        String correo = users.getString("correo");
                        String usuario = users.getString("usuario");
                        String clave = users.getString("clave");
                        int tipo = users.getInt("tipo");
                        int estado = users.getInt("estado");
                        String pregunta = users.getString("pregunta");
                        String respuesta = users.getString("respuesta");
                        String fecha = users.getString("fecha_registro");

                        objUsuarios = new dto_usuarios(id,nombre,apellidos,correo,usuario,clave,tipo,estado,pregunta,respuesta,fecha);

                        listaUsers.add(objUsuarios);

                        usuariosAdapter = new UsuariosAdapter(getContext(), listaUsers);

                        recyclerView.setAdapter(usuariosAdapter);

                        Log.i("Id:    ", String.valueOf(objUsuarios.getId()));
                        Log.i("Nombre:    ", String.valueOf(objUsuarios.getNombre()));

                    }

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