package com.example.cerverodesing.cervero_admin.provider;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final JSONObject params;
    private final Response.Listener<T> listener;
    private final cerveroFinally<T> EndOfRequest;

    private String myToken = "";

    public GsonRequest(JSONObject _params, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener, cerveroFinally<T> endOfRequest) {
        super(Method.POST, url, errorListener);
        this.clazz = clazz;
        this.params = _params;
        this.listener = listener;
        this.EndOfRequest = endOfRequest;
    }

    public GsonRequest(JSONObject _params, String url, String Token, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener, cerveroFinally<T> endOfRequest) {
        super(Method.POST, url, errorListener);
        this.clazz = clazz;
        this.params = _params;
        this.listener = listener;
        this.EndOfRequest = endOfRequest;
        this.myToken = Token;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", myToken);
        return headers;
    }

    @Override
    public String getBodyContentType() {
        return String.format("application/json");
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return params.toString().getBytes();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
        this.EndOfRequest.Finally(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T item = gson.fromJson(json, clazz);

            return Response.success(item, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyError = error;
        }

        return volleyError;
    }
}

