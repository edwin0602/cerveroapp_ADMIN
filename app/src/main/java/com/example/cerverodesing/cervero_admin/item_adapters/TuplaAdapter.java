package com.example.cerverodesing.cervero_admin.item_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cerverodesing.cervero_admin.R;
import com.example.cerverodesing.cervero_admin.cervero_models.Tupla;

import java.util.List;

public class TuplaAdapter extends RecyclerView.Adapter<TuplaAdapter.TuplaViewHolder> {

    public static class TuplaViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemID;

        TuplaViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView)itemView.findViewById(R.id.item_nombre);
            itemID = (TextView)itemView.findViewById(R.id.item_codigo);
        }
    }

    private List<Tupla> items;

    public TuplaAdapter(List<Tupla> items) {
        this.items= items;
    }

    @Override
    public TuplaAdapter.TuplaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_tupla, parent, false);
        TuplaAdapter.TuplaViewHolder pvh = new TuplaAdapter.TuplaViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TuplaAdapter.TuplaViewHolder holder, int i) {

        Tupla current = items.get(i);

        holder.itemName.setText(current.m_Item2);
        holder.itemID.setText("COD. " + current.m_Item1);
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
