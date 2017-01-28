package com.example.cerverodesing.cervero_admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.Detalle_Ticket;
import com.example.cerverodesing.cervero_admin.cervero_models.Ticket;
import com.example.cerverodesing.cervero_admin.cervero_models.Tupla;
import com.example.cerverodesing.cervero_admin.item_adapters.DetalleAdapter;
import com.example.cerverodesing.cervero_admin.item_adapters.TicketAdapter;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;

import java.util.Arrays;
import java.util.List;

public class TicketActivity extends AppCompatActivity {

    int ID_TICKET;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        Bundle b = getIntent().getExtras();
        ID_TICKET = (b != null)? b.getInt("codigo"):-1;

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarTicket);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ticket N° " + ID_TICKET);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_detalle);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llmanager = new LinearLayoutManager(this.getBaseContext());
        mRecyclerView.setLayoutManager(llmanager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        cervero_provider.getTicket(ID_TICKET, this.getApplicationContext(), this, new Response.Listener<Detalle_Ticket[]>() {
                    @Override
                    public void onResponse(Detalle_Ticket[] response) {
                        List<Detalle_Ticket> myList = Arrays.asList(response);
                        mRecyclerView.setAdapter(new DetalleAdapter(myList));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Detalle:", "Error inesperado: " + volleyError.getMessage());
                    }
                }
        );

    }
}
