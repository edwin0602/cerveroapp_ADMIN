package com.example.cerverodesing.cervero_admin.provider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cerverodesing.cervero_admin.cervero_models.Equipo;
import com.example.cerverodesing.cervero_admin.cervero_models.ResultService;
import com.example.cerverodesing.cervero_admin.cervero_models.Ticket;
import com.example.cerverodesing.cervero_admin.cervero_models.Tupla;
import com.example.cerverodesing.cervero_admin.cervero_models.Usuario;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class cervero_provider {
    private static ProgressDialog pDialog;

    private static final String BASE_URL = "http://rest.cerverodev.com";

    private static final String KEY_PREFERENCES = "cerverodev";
    private static final String KEY_USER = "cerverodev.USER";
    private static final String KEY_PASS = "cerverodev.PASS";

    private static final String KEY_GROUP_SELECTED = "cerverodev.LASTGROUP";

    private static String MY_TOKEN = "cerverodev.TOKEN";

    public static Tupla[] MY_GROUPS = null;
    public static Tupla MY_CURRENT_GROUP = null;

    public static void SET_CURRENT_GROUP(Context Me, int GrupoPosition){
        Tupla selected = MY_GROUPS[GrupoPosition];
        SharedPreferences sharedPref = Me.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(KEY_GROUP_SELECTED,  GrupoPosition);
        editor.commit();

        MY_CURRENT_GROUP = selected;
    }

    public static void LOAD_LAST_GROUP(Context Me){
        SharedPreferences sharedPref = Me.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        int pos = sharedPref.getInt(KEY_GROUP_SELECTED, 0);

        MY_CURRENT_GROUP = MY_GROUPS[pos];
    }

    public static void autoLogin(Context Me, Response.Listener<JSONObject> responseHandler, Response.ErrorListener responseError) {

        String url = BASE_URL + "/api/account/AuthenticateApp/";

        SharedPreferences sharedPref = Me.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);

        String user = sharedPref.getString(KEY_USER, "");
        String pass = sharedPref.getString(KEY_PASS, "");

        Map<String, String> params = new HashMap<String, String>();
        params.put("Username", user);
        params.put("Password", pass);

        JSONObject jsonObj = new JSONObject(params);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObj, responseHandler, responseError);

        RequestQueue queue = Volley.newRequestQueue(Me);
        queue.add(jsObjRequest);
    }

    public static void getLogin(Context Me, Activity My, String user, String pass, Response.Listener<ResultService> responseHandler, Response.ErrorListener responseError) {
        pDialog = new ProgressDialog(My);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Validando credenciales");
        pDialog.setIndeterminate(true);
        pDialog.show();

        String url = BASE_URL + "/api/account/Authenticate/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("Username", user);
        params.put("Password", pass);

        JSONObject jsonObj = new JSONObject(params);

        GsonRequest<ResultService> gsonRequest = new GsonRequest<ResultService>(jsonObj, url, ResultService.class, responseHandler, responseError, new cerveroFinally<ResultService>() {
            @Override
            public void Finally(ResultService response) {
                pDialog.dismiss();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(Me);
        queue.add(gsonRequest);
    }

    public static void saveLoginCredentials(Context Me, String user, String pass, String token){
        SharedPreferences sharedPref = Me.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_USER, user);
        editor.putString(KEY_PASS, pass);
        editor.commit();

        MY_TOKEN = token;
    }

    public static void saveLoginCredentials(String token){
        MY_TOKEN = token;
    }

    public static void removeLoginCredentials(Context Me){
        SharedPreferences sharedPref = Me.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();

        MY_TOKEN = "";
    }

    public static void getGrupos(Boolean isHidden, final Context Me, Activity My, Response.Listener<Tupla[]> responseHandler, Response.ErrorListener responseError) {

        pDialog = new ProgressDialog(My);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Obteniendo grupos asignados");
        pDialog.setIndeterminate(true);

        if(!isHidden){
            pDialog.show();
        }

        String url = BASE_URL + "/api/account/getGrupos/";

        Map<String, String> params = new HashMap<String, String>();
        JSONObject jsonObj = new JSONObject(params);

        GsonRequest<Tupla[]> gsonRequest = new GsonRequest<Tupla[]>(jsonObj, url, MY_TOKEN, Tupla[].class, responseHandler, responseError, new cerveroFinally<Tupla[]>() {
            @Override
            public void Finally(Tupla[] response) {
                MY_GROUPS = response;
                if(MY_GROUPS.length>0){
                    LOAD_LAST_GROUP(Me);
                }

                pDialog.dismiss();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(Me);
        queue.add(gsonRequest);
    }

    public static void getEquipos(Boolean isHidden, Context Me, Activity My, Response.Listener<Equipo[]> responseHandler, Response.ErrorListener responseError) {

        String url = BASE_URL + "/api/account/getTaquillas/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("codigo_grupo", MY_CURRENT_GROUP.m_Item1 + "");
        params.put("idx", "0");
        params.put("cantidad", "100");

        JSONObject jsonObj = new JSONObject(params);

        GsonRequest<Equipo[]> gsonRequest = new GsonRequest<Equipo[]>(jsonObj, url, MY_TOKEN, Equipo[].class, responseHandler, responseError, new cerveroFinally<Equipo[]>() {
            @Override
            public void Finally(Equipo[] response) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(Me);
        queue.add(gsonRequest);
    }

    public static void getUsuarios(Boolean isHidden, Context Me, Activity My, Response.Listener<Usuario[]> responseHandler, Response.ErrorListener responseError) {

        String url = BASE_URL + "/api/account/getUsuarios/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("codigo_grupo", MY_CURRENT_GROUP.m_Item1 + "");
        params.put("codigo_role", "7");
        params.put("idx", "0");
        params.put("cantidad", "100");

        JSONObject jsonObj = new JSONObject(params);

        GsonRequest<Usuario[]> gsonRequest = new GsonRequest<Usuario[]>(jsonObj, url, MY_TOKEN, Usuario[].class, responseHandler, responseError, new cerveroFinally<Usuario[]>() {
            @Override
            public void Finally(Usuario[] response) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(Me);
        queue.add(gsonRequest);
    }

    public static void getGanadores(int año, int mes, int dia, Context Me, Activity My, Response.Listener<Ticket[]> responseHandler, Response.ErrorListener responseError) {

        String url = BASE_URL + "/api/account/getGanadores/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("fecha", año + "-" + mes + "-" + dia+"T00:00:01Z") ;
        params.put("idx", "0");
        params.put("cantidad", "100");

        JSONObject jsonObj = new JSONObject(params);

        GsonRequest<Ticket[]> gsonRequest = new GsonRequest<Ticket[]>(jsonObj, url, MY_TOKEN, Ticket[].class, responseHandler, responseError, new cerveroFinally<Ticket[]>() {
            @Override
            public void Finally(Ticket[] response) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(Me);
        queue.add(gsonRequest);
    }

    public static void getTicket(int codigo, Context Me, Activity My, Response.Listener<Ticket[]> responseHandler, Response.ErrorListener responseError) {

        String url = BASE_URL + "/api/account/getDetalleTicket/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("codigo_ticket", codigo+"") ;

        JSONObject jsonObj = new JSONObject(params);

        GsonRequest<Ticket[]> gsonRequest = new GsonRequest<Ticket[]>(jsonObj, url, MY_TOKEN, Ticket[].class, responseHandler, responseError, new cerveroFinally<Ticket[]>() {
            @Override
            public void Finally(Ticket[] response) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(Me);
        queue.add(gsonRequest);
    }

}
