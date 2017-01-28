package com.example.cerverodesing.cervero_admin;

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
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.Usuario;
import com.example.cerverodesing.cervero_admin.item_adapters.UsuarioAdapter;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;

import java.util.Arrays;
import java.util.List;

public class UsuariosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Button mButtonAgain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usuarios, container, false);

        mButtonAgain = (Button) rootView.findViewById(R.id.button_usuarios_reintent);
        mButtonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillReciclerView();
            }
        });

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_users);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));

        LinearLayoutManager llmanager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(llmanager);

        FillReciclerView();

        return rootView;
    }

    public void FillReciclerView(){
        mButtonAgain.setVisibility(View.GONE);

        cervero_provider.getUsuarios(false, this.getContext(), this.getActivity(), new Response.Listener<Usuario[]>() {
                    @Override
                    public void onResponse(Usuario[] response) {
                        List<Usuario> myList = Arrays.asList(response);
                        mRecyclerView.setAdapter(new UsuarioAdapter(myList, UsuariosFragment.this));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mButtonAgain.setVisibility(View.VISIBLE);
                        Toast.makeText(UsuariosFragment.this.getContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
