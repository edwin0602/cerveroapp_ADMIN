package com.example.cerverodesing.cervero_admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.ResultService;
import com.example.cerverodesing.cervero_admin.cervero_models.Tupla;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;

public class LoginActivity extends Activity {

    private EditText mUserView;
    private EditText mPasswordView;
    private TextView mErrorView;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mErrorView = (TextView) findViewById(R.id.textViewResponse);
        mUserView = (EditText) findViewById(R.id.login_usuario);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    iniciarSesion();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });

    }

    private void iniciarSesion(){

        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        email = mUserView.getText().toString();
        password = mPasswordView.getText().toString();

        if(!validateData()){
            cervero_provider.getLogin(this.getApplicationContext(), this, email, password, new Response.Listener<ResultService>() {
                        @Override
                        public void onResponse(ResultService response) {
                            if (response.codigoRespuesta == 0) {
                                cervero_provider.saveLoginCredentials(LoginActivity.this.getApplicationContext(), email, password, response.Mensaje);
                                ObtenerGrupos();
                            } else {
                                mErrorView.setText(response.Mensaje);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mErrorView.setText("Error inesperado: " + error.getMessage());
                        }
                    }
            );
        }

    }

    private void ObtenerGrupos() {
        cervero_provider.getGrupos(false, this.getApplicationContext(), this, new Response.Listener<Tupla[]>() {
                    @Override
                    public void onResponse(Tupla[] response) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Error:", volleyError.getMessage());
                    }
                }
        );
    }

    private boolean validateData(){
        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !(password.length() > 4)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        } else if (!(email.length() > 3)) {
            mUserView.setError(getString(R.string.error_invalid_user));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {focusView.requestFocus();}

        return cancel;
    }
}
