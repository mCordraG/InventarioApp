package com.arima.inventario.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arima.inventario.R;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.LineaViewHolder> implements View.OnClickListener {
    private List<String> nombres;

    private View.OnClickListener listener;

    public ProductosAdapter(List<String> nombres) {
        this.nombres = nombres;
    }

    @NonNull
    @Override
    public LineaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_producto, parent, false);
        itemView.setOnClickListener(this);
        LineaViewHolder viewHolder = new LineaViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LineaViewHolder holder, int position) {
        holder.setProducto(nombres.get(position));
    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    public static class LineaViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public LineaViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nombre);
        }

        public void setProducto(String nombre) {
            name.setText(nombre);
        }
    }

}
