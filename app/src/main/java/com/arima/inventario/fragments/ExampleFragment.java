package com.arima.inventario.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arima.inventario.R;
import com.arima.inventario.activities.MainActivity;
import com.arima.inventario.adapters.ProductosAdapter;
import com.arima.inventario.managers.FirestoreManager;
import com.arima.inventario.model.Example;
import com.arima.inventario.model.Producto;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExampleFragment extends DefaultFragment {

    List<Producto> listado;
    private List<String> keys;
    private List<String> nombres;
    private List<Integer> stock;
    private RecyclerView productos;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        getProductList();
        return root;
    }

    @Override
    public View setFragmentLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_example, container, false);
    }

    @Override
    public void createViewItems(View root) { productos = root.findViewById(R.id.productos_list); }

    public void getProductList() {
        firestoreManager.getCollection("lista_inventarios/inventario1/productos", queryDocumentSnapshots -> {
            if(queryDocumentSnapshots.isSuccessful()) {
                List<DocumentSnapshot> documento = queryDocumentSnapshots.getResult().getDocuments();

                keys = new ArrayList<>();
                nombres = new ArrayList<>();
                stock = new ArrayList<>();

                for(int i = 0; i < documento.size(); i ++) {
                    DocumentSnapshot documentoSnap = documento.get(i);
                    keys.add(i, documentoSnap.getId());
                    Producto producto = documentoSnap.toObject(Producto.class);
                    nombres.add(i, producto.getNombre());
                    stock.add(i, producto.getStock());
                }
                final ProductosAdapter productosAdapter = new ProductosAdapter(nombres);
                productosAdapter.setOnClickListener(
                        v -> goToEncuestaPage(keys.get(productos.getChildAdapterPosition(v)))
                );

                productos.setAdapter(productosAdapter);
                productos.setLayoutManager(new LinearLayoutManager(thisFragment.getActivity(), LinearLayoutManager.VERTICAL, false));

            } else {

            }
        });
    }

    public void deleteProduct(View view){
        Producto p = new Producto();
        p.setId("CIru5lZFsaNnfthEjBuo");
        p.setNombre("producto");
        p.setStock(0);

        Iterator<Producto> i = listado.iterator();
        while (i.hasNext()) {
            Producto s = i.next();
            if(s.getId().equals(p.getId())){
                firestoreManager.deleteDocument("lista_inventarios/inventario1/productos", p.getId());
                getProductos();
            }
        }
    }

    public void goToEncuestaPage(String key){
        Intent intent = new Intent(thisFragment.getActivity(), MainActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }
}