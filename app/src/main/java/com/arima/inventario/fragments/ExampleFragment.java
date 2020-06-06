package com.arima.inventario.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.arima.inventario.R;
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
    TextView exampleText;
    LinearLayout lista;
    TextView texto;
    List<Producto> listado;
    Button eliminar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        loadExampleTexts();
        getProductos();
        return root;
    }

    @Override
    public View setFragmentLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_example, container, false);
    }

    @Override
    public void createViewItems(View root) {
        exampleText = root.findViewById(R.id.example);
        lista = root.findViewById(R.id.lista_desplegable);
        texto = root.findViewById(R.id.nuevo_texto);
        eliminar = root.findViewById(R.id.eliminar_producto);
        eliminar.setOnClickListener(v -> { deleteProduct(root); });
    }

    public void loadExampleTexts() {
        firestoreManager.getDocument(
                FirestoreManager.FS_COLLECTION_EXAMPLE,
                FirestoreManager.FS_DOCUMENT_EXAMPLE,
                documentSnapshot -> {
                    if(!documentSnapshot.isSuccessful()) {
                        exampleText.setText("---");
                        return;
                    }
                    Example object = documentSnapshot.getResult().toObject(Example.class);
                    exampleText.setText(object.getExampleField());
                });
    }

    public void getProductos() {
        firestoreManager.getCollection("lista_inventarios/inventario1/productos", queryDocumentSnapshots -> {
            if(queryDocumentSnapshots.isSuccessful()) {
                List<DocumentSnapshot> documento = queryDocumentSnapshots.getResult().getDocuments();
                List<Producto> productos = new ArrayList<>();
                for (DocumentSnapshot prod: documento) {
                    Producto p = prod.toObject(Producto.class);
                    p.setId(prod.getId());
                    productos.add(p);
                }
                this.listado = productos;
                texto.setText(productos.toString());
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

}