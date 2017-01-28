package com.example.cerverodesing.cervero_admin.item_adapters;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cerverodesing.cervero_admin.GruposActivity;
import com.example.cerverodesing.cervero_admin.R;
import com.example.cerverodesing.cervero_admin.UsuariosFragment;
import com.example.cerverodesing.cervero_admin.cervero_models.ResultService;
import com.example.cerverodesing.cervero_admin.cervero_models.Tupla;
import com.example.cerverodesing.cervero_admin.cervero_models.Usuario;
import com.example.cerverodesing.cervero_admin.provider.cervero_provider;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView userUsuario;
        TextView userNombre;
        TextView userID;
        LinearLayout userIcon;

        int CodigoUsuario;

        Switch mySwitch;

        UsuarioViewHolder(View itemView) {
            super(itemView);
            userIcon = (LinearLayout)itemView.findViewById(R.id.thumbnail);

            userNombre = (TextView)itemView.findViewById(R.id.user_item_nombre);
            userUsuario = (TextView)itemView.findViewById(R.id.user_item_usuario);
            userID = (TextView)itemView.findViewById(R.id.user_item_documento);
            userIcon = (LinearLayout)itemView.findViewById(R.id.user_item_icon);

            mySwitch = (Switch) itemView.findViewById(R.id.user_item_switch);
        }
    }

    private List<Usuario> items;
    private Fragment parentFragment;

    public UsuarioAdapter(List<Usuario> items, Fragment Parent) {
        this.items= items;
        this.parentFragment = Parent;
    }

    @Override
    public UsuarioAdapter.UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_usuario, parent, false);
        UsuarioAdapter.UsuarioViewHolder pvh = new UsuarioAdapter.UsuarioViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final UsuarioAdapter.UsuarioViewHolder holder, int i) {
        Usuario current = items.get(i);

        holder.userNombre.setText(current.Nombre());
        holder.userUsuario.setText(current.user);
        holder.userID.setText("Doc. " + current.Persona_documento);
        holder.userIcon.setBackgroundColor(Color.parseColor(current.getColor()));
        holder.mySwitch.setChecked(current.CheckEstado());

        holder.CodigoUsuario = current.codigo;

        holder.mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cervero_provider.changeUsuarioEstado(isChecked, holder.CodigoUsuario, parentFragment.getContext(), parentFragment.getActivity(), new Response.Listener<ResultService>() {
                            @Override
                            public void onResponse(ResultService response) {
                                if(response.codigoRespuesta == 1){
                                    holder.mySwitch.setChecked(true);
                                }else{
                                    holder.mySwitch.setChecked(false);
                                }

                                Toast.makeText(parentFragment.getContext(), response.Mensaje, Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(parentFragment.getContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        });
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
