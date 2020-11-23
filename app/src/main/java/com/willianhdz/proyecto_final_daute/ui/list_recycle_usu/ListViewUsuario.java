package com.willianhdz.proyecto_final_daute.ui.list_recycle_usu;

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
import com.example.app_crud_mysql.MySingleton;
import com.example.app_crud_mysql.R;
import com.example.app_crud_mysql.Setting_VAR;
import com.example.app_crud_mysql.ui.dts.dto_usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewUsuario extends Fragment {

    private ListView lst;
    private Button listar;
    ArrayList<String> lista = null;
    ArrayList<dto_usuarios> listaUsers;
    private LinearLayoutCompat resultado;


    public ListViewUsuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_view_usuario, container, false);
        ConstraintLayout frameLayout1 = root.findViewById(R.id.constraintLayout);
        final LinearLayoutCompat frameLayout2 = root.findViewById(R.id.fm2);

        lst = root.findViewById(R.id.lstUsuario);
        resultado = root.findViewById(R.id.fm2);

        recibirAllUsers();
        return root;
    }

    private void recibirAllUsers(){
        listaUsers = new ArrayList<dto_usuarios>();
        lista = new ArrayList<String>();
        String urlConsultaUsuarios = Setting_VAR.URL_consultarAllUsuario2;
        StringRequest request = new StringRequest(Request.Method.POST, urlConsultaUsuarios, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
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

                        lista.add("Id usuario: "+listaUsers.get(i).getId() + "\nNombre: " + listaUsers.get(i).getNombre() + "\nApellidos " + listaUsers.get(i).getApellido() + "\nCorreo: "
                        + listaUsers.get(i).getCorreo() + "\nUsuario: " + listaUsers.get(i).getUsuario() + "\nClave: " + listaUsers.get(i).getClave() + "\nTipo: "
                         + listaUsers.get(i).getTipo() + "\nEstado " + listaUsers.get(i).getEstado() + "\nPregunta: " + listaUsers.get(i).getPregunta() + "\nRespuesta: "
                        + listaUsers.get(i).getRespuesta() + "\nFecha de registro: " + listaUsers.get(i).getFecha_registro());

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lista);
                        lst.setAdapter(adapter);

                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String id = "" + listaUsers.get(i).getId();
                                String nombre = "" + listaUsers.get(i).getNombre();
                                String apellidos = "" + listaUsers.get(i).getApellido();
                                String correo = "" + listaUsers.get(i).getCorreo();
                                String usuario = "" + listaUsers.get(i).getUsuario();
                                String clave = "" + listaUsers.get(i).getClave();
                                String tipo = "" + listaUsers.get(i).getTipo();
                                String estado = "" + listaUsers.get(i).getEstado();
                                String pregunta = "" + listaUsers.get(i).getPregunta();
                                String respuesta = "" + listaUsers.get(i).getRespuesta();
                                String fecha = "" + listaUsers.get(i).getFecha_registro();

                                /*
                                Bundle envio = new Bundle();
                                envio.putInt("id", Integer.parseInt(codigo));
                                envio.putString("nombre", nombre);
                                envio.putInt("estado", Integer.parseInt(estado));*/


                                Intent intent = new Intent(getActivity(), DetalleUsuario.class);
                                intent.putExtra("id", id);
                                intent.putExtra("nombre", nombre);
                                intent.putExtra("apellido", apellidos);
                                intent.putExtra("correo", correo);
                                intent.putExtra("usuario", usuario);
                                intent.putExtra("clave", clave);
                                intent.putExtra("tipo", tipo);
                                intent.putExtra("estado", estado);
                                intent.putExtra("pregunta", pregunta);
                                intent.putExtra("respuesta", respuesta);
                                intent.putExtra("fecha", fecha);
                                startActivity(intent);
                            }
                        });
                        //usuariosAdapter = new UsuariosAdapter(getContext(), listaUsers);
                        //recyclerView.setAdapter(usuariosAdapter);

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