package com.example.cerverodesing.cervero_admin.item_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cerverodesing.cervero_admin.R;
import com.example.cerverodesing.cervero_admin.cervero_models.Detalle_Ticket;

import java.util.List;

public class DetalleAdapter extends RecyclerView.Adapter<DetalleAdapter.DetalleViewHolder> {

    public static class DetalleViewHolder extends RecyclerView.ViewHolder {
        TextView codigo;
        TextView descripcion;
        TextView estado;
        TextView valor;
        TextView encuentro;

        DetalleViewHolder(View itemView) {
            super(itemView);

            codigo = (TextView)itemView.findViewById(R.id.detalle_item_codigo);
            descripcion = (TextView)itemView.findViewById(R.id.detalle_item_descripcion);
            valor = (TextView)itemView.findViewById(R.id.detalle_item_valor);
            encuentro = (TextView)itemView.findViewById(R.id.detalle_item_encuentro);
            estado = (TextView)itemView.findViewById(R.id.detalle_item_estado);
        }
    }

    private List<Detalle_Ticket> items;

    public DetalleAdapter(List<Detalle_Ticket> items) {
        this.items= items;
    }

    @Override
    public DetalleAdapter.DetalleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_detalle, parent, false);
        DetalleAdapter.DetalleViewHolder pvh = new DetalleAdapter.DetalleViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DetalleAdapter.DetalleViewHolder holder, int i) {
        Detalle_Ticket current = items.get(i);

        String[] detalle = current.descripcion.split("&");
        holder.descripcion.setText(detalle[0]);
        holder.encuentro.setText(detalle[1]);

        holder.codigo.setText("COD." + current.codigo);
        holder.estado.setText(current.Estado());

        holder.valor.setText(current.valor);
        if(current.condicion != null){
            holder.valor.setText(current.valor + " | " + current.condicion);
        }


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
