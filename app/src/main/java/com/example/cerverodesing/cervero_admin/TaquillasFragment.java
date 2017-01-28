package com.example.cerverodesing.cervero_admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.Equipo;
import com.example.cerverodesing.cervero_admin.cervero_models.Ticket;
import com.example.cerverodesing.cervero_admin.item_adapters.EquipoAdapter;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;
import com.example.cerverodesing.cervero_admin.utilities.RecyclerTouchListener;

import java.util.Arrays;
import java.util.List;

public class TaquillasFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Button mButtonAgain;

    List<Equipo> myList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_taquillas, container, false);

        mButtonAgain = (Button) rootView.findViewById(R.id.button_taquillas_reintent);
        mButtonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillReciclerView();
            }
        });

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_taquillas);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));

        LinearLayoutManager llmanager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(llmanager);

        FillReciclerView();

        return rootView;
    }

    public void FillReciclerView(){
        mButtonAgain.setVisibility(View.GONE);

        cervero_provider.getEquipos(false, this.getContext(), this.getActivity(), new Response.Listener<Equipo[]>() {
                    @Override
                    public void onResponse(Equipo[] response) {
                        myList = Arrays.asList(response);
                        mRecyclerView.setAdapter(new EquipoAdapter(myList));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mButtonAgain.setVisibility(View.VISIBLE);
                        Toast.makeText(TaquillasFragment.this.getContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}
