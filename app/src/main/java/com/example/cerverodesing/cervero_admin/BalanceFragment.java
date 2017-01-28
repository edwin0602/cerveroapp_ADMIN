package com.example.cerverodesing.cervero_admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.Balance;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;
import com.example.cerverodesing.cervero_admin.utilities.Converters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BalanceFragment extends Fragment {

    Button StartEdit, EndEdit;
    ImageButton SENDButton;
    Date startDate, endDate = null;
    TextView backMessage, TVVenta, TVItemsVenta, TVPremios, TVItemPremios, TVPagado, TVItemPagado, TVGanado, TVItemGanado, TVCaducado, TVItemCaducado;
    LinearLayout PremiosLayout;
    RelativeLayout VentaLayout;

    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "dd MMM";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_balance, container, false);

        backMessage = (TextView) rootView.findViewById(R.id.textViewBackBalance);

        StartEdit = (Button) rootView.findViewById(R.id.buttonStart);
        EndEdit = (Button) rootView.findViewById(R.id.buttonEnd);
        SENDButton = (ImageButton) rootView.findViewById(R.id.buttonSend);

        TVVenta = (TextView) rootView.findViewById(R.id.textViewVenta);
        TVItemsVenta = (TextView) rootView.findViewById(R.id.textViewVentaItem);

        TVPremios = (TextView) rootView.findViewById(R.id.textViewPremios);
        TVItemPremios = (TextView) rootView.findViewById(R.id.textViewPremiosItem);

        TVPagado = (TextView) rootView.findViewById(R.id.textViewPagado);
        TVItemPagado = (TextView) rootView.findViewById(R.id.textViewPagadoItem);

        TVGanado = (TextView) rootView.findViewById(R.id.textViewGanado);
        TVItemGanado = (TextView) rootView.findViewById(R.id.textViewGanadoItem);

        TVCaducado = (TextView) rootView.findViewById(R.id.textViewCaducado);
        TVItemCaducado = (TextView) rootView.findViewById(R.id.textViewCaducadoItem);

        PremiosLayout = (LinearLayout) rootView.findViewById(R.id.LayoutPremios);
        VentaLayout = (RelativeLayout) rootView.findViewById(R.id.LayoutVenta);

        StartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BalanceFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        StartEdit.setText(Converters.DateToString(myCalendar.getTime(), myFormat));
                        startDate = myCalendar.getTime();
                    }
                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BalanceFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        EndEdit.setText(Converters.DateToString(myCalendar.getTime(), myFormat));
                        endDate = myCalendar.getTime();

                        if (startDate == null) {
                            StartEdit.setText(Converters.DateToString(myCalendar.getTime(), myFormat));
                            startDate = myCalendar.getTime();
                        }
                    }
                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        SENDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadData();
            }
        });

        return rootView;
    }

    public void LoadData() {

        VentaLayout.setVisibility(View.INVISIBLE);
        PremiosLayout.setVisibility(View.INVISIBLE);

        if (startDate == null) {
            Toast.makeText(this.getContext(), "Debe seleccionar una fecha inicial.", Toast.LENGTH_SHORT).show();
        } else {
            if (endDate == null) {
                Toast.makeText(this.getContext(), "Debe seleccionar una fecha final.", Toast.LENGTH_SHORT).show();
            } else {
                cervero_provider.getBalance(startDate, endDate, this.getContext(), this.getActivity(), new Response.Listener<Balance>() {
                            @Override
                            public void onResponse(Balance response) {

                                if (response != null) {
                                    backMessage.setVisibility(View.GONE);

                                    VentaLayout.setVisibility(View.VISIBLE);
                                    PremiosLayout.setVisibility(View.VISIBLE);

                                    TVVenta.setText(Converters.DoubleToCurrency(response.Venta));
                                    TVItemsVenta.setText(response.Tickets_totales + " TICKETS | VENTA TOTAL" );

                                    TVPremios.setText(Converters.DoubleToCurrency(response.Premios));
                                    TVItemPremios.setText(response.Tickets_premiados + " Tickets");

                                    TVPagado.setText(Converters.DoubleToCurrency(response.Pagado));
                                    TVItemPagado.setText(response.Tickets_pagado + " Tickets");

                                    TVGanado.setText(Converters.DoubleToCurrency(response.Ganado));
                                    TVItemGanado.setText(response.Tickets_ganado + " Tickets");

                                    TVCaducado.setText(Converters.DoubleToCurrency(response.Caducados));
                                    TVItemCaducado.setText(response.Tickets_caducados + " Tickets");
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(BalanceFragment.this.getContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        }
    }

}
