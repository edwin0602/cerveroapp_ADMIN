package com.example.cerverodesing.cervero_admin.item_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cerverodesing.cervero_admin.R;
import com.example.cerverodesing.cervero_admin.cervero_models.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView userEstado;
        TextView userID;
        LinearLayout userIcon;

        UsuarioViewHolder(View itemView) {
            super(itemView);
            userIcon = (LinearLayout)itemView.findViewById(R.id.thumbnail);
            userName = (TextView)itemView.findViewById(R.id.user_item_nombre);
            userEstado = (TextView)itemView.findViewById(R.id.user_item_estado);
            userID = (TextView)itemView.findViewById(R.id.user_item_codigo);
        }
    }

    private List<Usuario> items;

    public UsuarioAdapter(List<Usuario> items) {
        this.items= items;
    }

    @Override
    public UsuarioAdapter.UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_usuario, parent, false);
        UsuarioAdapter.UsuarioViewHolder pvh = new UsuarioAdapter.UsuarioViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(UsuarioAdapter.UsuarioViewHolder holder, int i) {

        Usuario current = items.get(i);

        holder.userName.setText(current.Nombre());
        holder.userID.setText("Usuario: " + current.user);
        holder.userEstado.setText("Doc. " + current.Persona_documento);

        //holder.equipoIcon.setBackgroundColor(Color.parseColor(current.getColor()));
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
