package com.example.cerverodesing.cervero_admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.Tupla;
import com.example.cerverodesing.cervero_admin.item_adapters.TuplaAdapter;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;
import com.example.cerverodesing.cervero_admin.utilities.RecyclerTouchListener;

import java.util.Arrays;

public class GruposActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_grupos);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llmanager = new LinearLayoutManager(this.getBaseContext());
        mRecyclerView.setLayoutManager(llmanager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(this.getApplicationContext(), new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        cervero_provider.SET_CURRENT_GROUP(GruposActivity.this.getApplicationContext() ,position);
                        GruposActivity.this.setResult(RESULT_OK);
                        finish();
                    }
                })
        );

        mRecyclerView.setAdapter(new TuplaAdapter(Arrays.asList(cervero_provider.MY_GROUPS)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grupos_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_groups_update) {
            cervero_provider.getGrupos(false, this.getApplicationContext(), this, new Response.Listener<Tupla[]>() {
                        @Override
                        public void onResponse(Tupla[] response) {
                            Toast.makeText(GruposActivity.this, "Grupos actualizados", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("Grupos:", "Error inesperado: " + volleyError.getMessage());
                        }
                    }
            );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
