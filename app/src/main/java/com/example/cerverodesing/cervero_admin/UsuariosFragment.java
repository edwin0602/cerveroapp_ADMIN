package com.example.cerverodesing.cervero_admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.Usuario;
import com.example.cerverodesing.cervero_admin.item_adapters.UsuarioAdapter;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;

import java.util.Arrays;
import java.util.List;

public class UsuariosFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usuarios, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_users);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llmanager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(llmanager);

        cervero_provider.getUsuarios(false, this.getContext(), this.getActivity(), new Response.Listener<Usuario[]>() {
                    @Override
                    public void onResponse(Usuario[] response) {
                        List<Usuario> myList = Arrays.asList(response);
                        mRecyclerView.setAdapter(new UsuarioAdapter(myList));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Error:", volleyError.getMessage());
                    }
                }
        );

        return rootView;
    }
}
