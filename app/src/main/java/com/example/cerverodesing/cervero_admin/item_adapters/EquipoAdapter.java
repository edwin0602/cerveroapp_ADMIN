package com.example.cerverodesing.cervero_admin.item_adapters;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cerverodesing.cervero_admin.R;
import com.example.cerverodesing.cervero_admin.cervero_models.Equipo;

import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {

    public static class EquipoViewHolder extends RecyclerView.ViewHolder {
        TextView equipoName;
        TextView equipoID;
        TextView equipoEstado;
        TextView equipoCode;
        LinearLayout equipoMarkup;

        EquipoViewHolder(View itemView) {
            super(itemView);
            equipoName = (TextView)itemView.findViewById(R.id.equipo_item_nombre);
            equipoID = (TextView)itemView.findViewById(R.id.equipo_item_codigo);
            equipoEstado = (TextView)itemView.findViewById(R.id.equipo_item_estado);
            equipoCode = (TextView)itemView.findViewById(R.id.equipo_item_install);
            equipoMarkup = (LinearLayout)itemView.findViewById(R.id.equipo_item_icon);
        }
    }

    private List<Equipo> items;

    public EquipoAdapter(List<Equipo> items) {
        this.items= items;
    }

    @Override
    public EquipoAdapter.EquipoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_equipo, parent, false);
        EquipoAdapter.EquipoViewHolder pvh = new EquipoAdapter.EquipoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(EquipoAdapter.EquipoViewHolder holder, int i) {

        Equipo current = items.get(i);

        holder.equipoName.setText(current.Nombre);
        holder.equipoID.setText("Cod. " + current.Codigo);
        holder.equipoEstado.setText(current.getEstado());
        holder.equipoCode.setText(current.Codigo_instalacion);
        holder.equipoMarkup.setBackgroundColor(Color.parseColor(current.getColor()));
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
