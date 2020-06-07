package com.arima.inventario.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.arima.inventario.R;
import com.arima.inventario.activities.EditProductActivity;
import com.arima.inventario.activities.MainActivity;
import com.arima.inventario.model.Producto;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EditarProductoFragment extends DefaultFragment{
    private TextView nombre;
    private TextView stock;
    private TextView id;
    private Button editar;
    private Button eliminar;


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
        eliminar = root.findViewById(R.id.button_delete_product);
        editar.setOnClickListener(v -> { editProduct(root); });
        eliminar.setOnClickListener(v -> { editProduct(root); });
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

    public void deleteProduct(View view){
        Producto p = new Producto();
        p.setId("CIru5lZFsaNnfthEjBuo");
        p.setNombre("producto");
        p.setStock(0);

        firestoreManager.deleteDocument("lista_inventarios/inventario1/productos", p.getId());
        goToEncuestaPage();
    }

    public void goToEncuestaPage(){
        Intent intent = new Intent(thisFragment.getActivity(), MainActivity.class);
        startActivity(intent);
    }
}




