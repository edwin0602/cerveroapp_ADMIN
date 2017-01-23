package com.example.cerverodesing.cervero_admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.ResultService;
import com.example.cerverodesing.cervero_admin.cervero_models.Tupla;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;
import com.google.gson.Gson;

import org.json.JSONObject;

public class SplashActivity extends Activity {

    ImageView imgSplash;
    TextView txtMessage, txtError;
    Animation animFadein, animMove;
    Button btnError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txtError = (TextView)findViewById(R.id.SplashTextError);
        btnError = (Button)findViewById(R.id.SplashLoginbutton);

        LaunchAnimation();
        ValidarSession();
    }

    private void LaunchAnimation() {
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        animMove = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_down);

        imgSplash = (ImageView) findViewById(R.id.imageViewLogo);
        txtMessage = (TextView) findViewById(R.id.textView);

        imgSplash.startAnimation(animMove);
        txtMessage.startAnimation(animFadein);
    }

    private void ValidarSession() {
        cervero_provider.autoLogin(this.getApplicationContext(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new Gson();
                        ResultService respObject = gson.fromJson(response.toString(), ResultService.class);

                        if (respObject.codigoRespuesta == 0) {
                            cervero_provider.saveLoginCredentials(respObject.Mensaje);
                            ObtenerGrupos();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);

                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtError.setVisibility(View.VISIBLE);
                        btnError.setVisibility(View.VISIBLE);

                        Log.d("Login", "Error inesperado: " + error.getMessage());
                    }
                }
        );
    }

    private void ObtenerGrupos() {
        cervero_provider.getGrupos(true, this.getApplicationContext(), this, new Response.Listener<Tupla[]>() {
                    @Override
                    public void onResponse(Tupla[] response) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        txtError.setVisibility(View.VISIBLE);
                        btnError.setVisibility(View.VISIBLE);

                        Log.d("Grupos:", "Error inesperado: " + volleyError.getMessage());
                    }
                }
        );
    }
}
