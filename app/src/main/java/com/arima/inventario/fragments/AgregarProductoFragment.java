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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AgregarProductoFragment extends DefaultFragment {
    private TextView nombre;
    private TextView stock;
    private Button agregar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        return root;
    }

    @Override
    public View setFragmentLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.add_product, container, false);
    }

    @Override
    public void createViewItems(View root) {
        nombre = root.findViewById(R.id.nombre_producto);
        stock = root.findViewById(R.id.stock_producto);
        agregar = root.findViewById(R.id.button_add_product);
        agregar.setOnClickListener(v -> { addProduct(root); });
    }


    public void addProduct(View view){
        Producto prod = new Producto();
        prod.setNombre(nombre.getText().toString());
        prod.setStock(Integer.parseInt(stock.getText().toString()));

        firestoreManager.addDocument("lista_inventarios/inventario1/productos", prod, documentReference -> {
            if(documentReference.isSuccessful()) {
                Snackbar.make(view, "exito", Snackbar.LENGTH_LONG).show();
            }else{
                Snackbar.make(view, "error", Snackbar.LENGTH_LONG).show();
            }
        });







        /*firestoreManager.getDocument("inventarios", "hnoscaceres21", documentSnapshot -> {
            if(documentSnapshot.isSuccessful()){
                List<String> p = new ArrayList<>();
                p.add(nombre.getText().toString());
                p.add(stock.getText().toString());

                firestoreManager.setField("inventarios", "hnoscaceres21", "Productos", p, task -> {
                    if(task.isSuccessful()){
                        nombre.setText("");
                        stock.setText("");
                        Snackbar.make(view, "exito", Snackbar.LENGTH_LONG).show();
                    }else{
                        Snackbar.make(view, "error agregar", Snackbar.LENGTH_LONG).show();
                    }
                });
            }else{
                Snackbar.make(view, "error document", Snackbar.LENGTH_LONG).show();
            }
        });*/
    }
}
