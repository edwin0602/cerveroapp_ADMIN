package com.example.cerverodesing.cervero_admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.cervero_models.Ticket;
import com.example.cerverodesing.cervero_admin.item_adapters.TicketAdapter;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;
import com.example.cerverodesing.cervero_admin.utilities.Converters;
import com.example.cerverodesing.cervero_admin.utilities.RecyclerTouchListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PremiosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    List<Ticket> myList;

    TextView totalView;
    RelativeLayout premiosLayout;
    Calendar myCalendar = Calendar.getInstance();
    Button SENDButton;

    String myFormat = "EEEE, dd MMMM";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_premios, container, false);

        premiosLayout = (RelativeLayout) rootView.findViewById(R.id.LayoutPremios);
        totalView = (TextView) rootView.findViewById(R.id.textViewPremiosTotal);

        SENDButton = (Button) rootView.findViewById(R.id.buttonFecha);
        SENDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PremiosFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        LoadPremios(myCalendar.getTime());
                    }
                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

        LoadPremios(myCalendar.getTime());
        return rootView;
    }

    public void LoadPremios(Date fecha) {
        SENDButton.setText(Converters.DateToString(myCalendar.getTime(), myFormat));
        cervero_provider.getGanadores(fecha, this.getContext(), this.getActivity(), new Response.Listener<Ticket[]>() {
                    @Override
                    public void onResponse(Ticket[] response) {
                        myList = Arrays.asList(response);
                        Double total = 0.0;
                        for (Ticket item: myList) {
                            total += item.Total;
                        }

                        totalView.setText(Converters.DoubleToCurrency(total));
                        premiosLayout.setVisibility(View.VISIBLE);

                        mRecyclerView.setAdapter(new TicketAdapter(myList));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(PremiosFragment.this.getContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

}
