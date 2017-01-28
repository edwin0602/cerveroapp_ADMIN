package com.example.cerverodesing.cervero_admin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.Ticket;
import com.example.cerverodesing.cervero_admin.item_adapters.TicketAdapter;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import layout.EmptyFragment;
import layout.NotFoundFragment;

public class PagosFragment extends Fragment {

    @Bind(R.id.editTextSerial)
    EditText EditTextSerial;

    @Bind(R.id.buttonFind)
    Button SendButton;

    @Bind(R.id.FRContent)
    FrameLayout FRContenido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pagos, container, false);
        ButterKnife.bind(this, view);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        getFragmentManager().beginTransaction().replace(R.id.FRContent, new EmptyFragment()).commit();

        EditTextSerial.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serial = EditTextSerial.getText().toString();

                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                cervero_provider.PayableTicket = null;
                getFragmentManager().beginTransaction().replace(R.id.FRContent, new EmptyFragment()).commit();

                cervero_provider.findWinner(serial, PagosFragment.this.getContext(), PagosFragment.this.getActivity(), new Response.Listener<Ticket>() {
                            @Override
                            public void onResponse(Ticket response) {
                                if (response == null) {
                                    getFragmentManager().beginTransaction().replace(R.id.FRContent, new NotFoundFragment()).commit();
                                    Toast.makeText(PagosFragment.this.getContext(), "No se encontro ningun ticket", Toast.LENGTH_SHORT).show();
                                } else {

                                    cervero_provider.PayableTicket = response;

                                    TicketFragment tkFragment = new TicketFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.FRContent, tkFragment).commit();

                                    Toast.makeText(PagosFragment.this.getContext(), "Ticket encontrado: No." + response.Codigo, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                getFragmentManager().beginTransaction().replace(R.id.FRContent, new NotFoundFragment()).commit();
                                Toast.makeText(PagosFragment.this.getContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                );

            }
        });


        return view;
    }

}
