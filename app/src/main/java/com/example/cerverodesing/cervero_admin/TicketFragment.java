package com.example.cerverodesing.cervero_admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cerverodesing.cervero_admin.cervero_models.Ticket;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;
import com.example.cerverodesing.cervero_admin.utilities.Converters;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TicketFragment extends Fragment {

    @Bind(R.id.buttonVerDetalle)
    ImageButton DetalleButton;

    @Bind(R.id.textViewInversion)
    TextView TvInversion;

    @Bind(R.id.textViewMonto)
    TextView TvMonto;

    @Bind(R.id.textViewRegistro)
    TextView TvRegistro;

    @Bind(R.id.textViewGanado)
    TextView TvGanado;

    @Bind(R.id.textViewCodigo)
    TextView TvCodigo;

    @Bind(R.id.FloatingPayButton)
    FloatingActionButton PayButton;

    Ticket loadedTicket = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        ButterKnife.bind(this, view);

        loadedTicket = cervero_provider.PayableTicket;

        DetalleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketFragment.this.getContext(), TicketActivity.class);

                intent.putExtra("codigo", loadedTicket.Codigo);
                startActivity(intent);
            }
        });

        TvMonto.setText(Converters.DoubleToCurrency(loadedTicket.Total));
        TvInversion.setText(Converters.DoubleToCurrency(loadedTicket.Valor));
        TvRegistro.setText(loadedTicket.Fechar.split("T")[0]);
        TvGanado.setText(loadedTicket.Fecha_ganado.split("T")[0]);
        TvCodigo.setText(loadedTicket.Codigo + "");

        PayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TicketFragment.this.getActivity());
                builder.setTitle("Confirmacion de pago");
                builder.setMessage("Esta seguro que desea realizar el pago del ticket No. " + loadedTicket.Codigo + ", por un valor de " + Converters.DoubleToCurrency(loadedTicket.Total) + "?")
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        }).setNegativeButton("Cancelar", null);

                builder.show();
            }
        });

        return view;
    }

}
