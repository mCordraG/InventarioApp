package com.arima.inventario.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.arima.inventario.R;
import com.arima.inventario.model.Producto;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class EditarProductoFragment extends DefaultFragment{
    private TextView nombre;
    private TextView stock;
    private Button editar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        return root;
    }

    @Override
    public View setFragmentLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.edit_product, container, false);
    }

    @Override
    public void createViewItems(View root) {
        nombre = root.findViewById(R.id.nombre_producto);
        stock = root.findViewById(R.id.stock_producto);
        editar = root.findViewById(R.id.button_edit_product);
        editar.setOnClickListener(v -> { editProduct(root); });
    }

    public void editProduct(View view) {
        Map<String, Object> data = new HashMap<>();
        data.put("nombre",nombre.getText().toString());
        data.put("stock",Integer.parseInt(stock.getText().toString()));

        firestoreManager.setFields("lista_inventarios/inventario1/productos", "V9mw9s0l4vUmfISZbdBt", data, queryDocumentSnapshots->{
            if(queryDocumentSnapshots.isSuccessful()) {
                Snackbar.make(view, "exito", Snackbar.LENGTH_LONG).show();
            }else{
                Snackbar.make(view, "error", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}




