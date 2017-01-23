package com.example.cerverodesing.cervero_admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.Ticket;
import com.example.cerverodesing.cervero_admin.cervero_models.Tupla;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;

public class TicketActivity extends AppCompatActivity {

    int ID_TICKET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTicket);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        ID_TICKET = (b != null)? b.getInt("codigo"):-1;

        cervero_provider.getTicket(ID_TICKET, this.getApplicationContext(), this, new Response.Listener<Ticket[]>() {
                    @Override
                    public void onResponse(Ticket[] response) {
                        Toast.makeText(TicketActivity.this, "Grupos actualizados", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Grupos:", "Error inesperado: " + volleyError.getMessage());
                    }
                }
        );

    }
}
