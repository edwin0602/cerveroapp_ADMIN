package com.example.cerverodesing.cervero_admin.item_adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cerverodesing.cervero_admin.R;
import com.example.cerverodesing.cervero_admin.cervero_models.Ticket;

import java.text.NumberFormat;
import java.util.List;


public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView ticketName;
        TextView ticketInversion;
        TextView ticketGanancia;
        TextView ticketFecha;
        TextView ticketEstado;
        LinearLayout ticketEstadoColor;

        TicketViewHolder(View itemView) {
            super(itemView);

            ticketName = (TextView)itemView.findViewById(R.id.ticket_item_nombre);
            ticketInversion = (TextView)itemView.findViewById(R.id.ticket_item_inversion);
            ticketGanancia = (TextView)itemView.findViewById(R.id.ticket_item_ganancia);
            ticketFecha = (TextView)itemView.findViewById(R.id.ticket_item_fecha);
            ticketEstado = (TextView)itemView.findViewById(R.id.ticket_item_estado);
            ticketEstadoColor = (LinearLayout)itemView.findViewById(R.id.ticket_item_estado_bar);
        }
    }

    private List<Ticket> items;

    public TicketAdapter(List<Ticket> items) {
        this.items= items;
    }

    @Override
    public TicketAdapter.TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_ticket, parent, false);
        TicketAdapter.TicketViewHolder pvh = new TicketAdapter.TicketViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TicketAdapter.TicketViewHolder holder, int i) {
        Ticket current = items.get(i);

        holder.ticketName.setText("Ticket N° " + current.Codigo + "");
        holder.ticketInversion.setText("Inv. " + NumberFormat.getCurrencyInstance().format(current.Valor));
        holder.ticketGanancia.setText("Gan. " + NumberFormat.getCurrencyInstance().format(current.Total));
        holder.ticketFecha.setText("Reg. "+current.Fechar.split("T")[0]);
        holder.ticketEstado.setText(current.getEstado());

        holder.ticketEstadoColor.setBackgroundColor(Color.parseColor(current.getColor()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
