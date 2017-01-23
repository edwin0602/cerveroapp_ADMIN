package com.example.cerverodesing.cervero_admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.Ticket;
import com.example.cerverodesing.cervero_admin.item_adapters.TicketAdapter;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;
import com.example.cerverodesing.cervero_admin.utilities.RecyclerTouchListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PremiosFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RecyclerView mRecyclerView;
    List<Ticket> myList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_premios, container, false);

        Calendar c = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datePickerDialog.show();
                    }
                }
        );

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_premios);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llmanager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(llmanager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));

        mRecyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(this.getContext(), new RecyclerTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (myList.size()>0) {
                            Ticket sel = myList.get(position);
                            Intent intent = new Intent(PremiosFragment.this.getContext(), TicketActivity.class);

                            intent.putExtra("codigo",sel.Codigo);
                            startActivity(intent);
                        }
                    }
                })
        );

        return rootView;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int dia, int mes, int año) {
        LoadPremios(dia, (mes + 1), año);
    }

    public void LoadPremios(int i, int i1, int i2) {

        cervero_provider.getGanadores(i, i1, i2, this.getContext(), this.getActivity(), new Response.Listener<Ticket[]>() {
                    @Override
                    public void onResponse(Ticket[] response) {
                        myList = Arrays.asList(response);
                        mRecyclerView.setAdapter(new TicketAdapter(myList));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Error:", volleyError.getMessage());
                    }
                }
        );

    }

}
