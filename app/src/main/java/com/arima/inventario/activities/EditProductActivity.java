package com.arima.inventario.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.arima.inventario.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProductActivity extends DefaultActivity {
    private TextView nombre;
    private TextView stock;
    private Button editar;
    private Button eliminar;
    private String recieveName;
    private String recieveStock;
    private String recieveID;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_edit);
    }

    @Override
    public void createViews() {
        super.createViews();
        nombre = findViewById(R.id.nombre_producto);
        stock = findViewById(R.id.stock_producto);
        editar = findViewById(R.id.button_edit_product);
        eliminar = findViewById(R.id.button_add_product);
        editar.setOnClickListener(v -> { editProduct(editar); });
        eliminar.setOnClickListener(v -> { deleteProduct(editar); });
        Intent intent = getIntent();
        Bundle texto = intent.getExtras();
        recieveName = texto.get("Nombre").toString();
        recieveStock = texto.get("Stock").toString();
        recieveID = texto.get("ID").toString();
        nombre.setText(recieveName);
        stock.setText(recieveStock);
    }

    public void editProduct(View view) {
        Map<String, Object> data = new HashMap<>();
        String nProducto = nombre.getText().toString();
        String sProducto = stock.getText().toString();
        String keyInventory = preferencesManager.getStringPreference("InventoryID");

        if(nProducto.length() > 0 && sProducto.length() > 0 && recieveID.length() > 0){
            data.put("nombre",nProducto);
            data.put("stock",Integer.parseInt(sProducto));

            firestoreManager.setFields("lista_inventarios/inventario1/productos", recieveID, data, queryDocumentSnapshots->{
                if(queryDocumentSnapshots.isSuccessful()) {
                    Snackbar.make(view, "exito", Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(view, "error", Snackbar.LENGTH_LONG).show();
                }
            });
        }else{
            List<String> validacion = new ArrayList<>();
            validacion.add(nProducto.length() <= 0 ? "Nombre" : "");
            validacion.add(sProducto.length() <= 0 ? "Stock" : "");
            validacion.add(recieveID.length() <= 0 ? "ID" : "");
            Snackbar.make(view, validacion.toString() + " requerido", Snackbar.LENGTH_LONG).show();
        }
        goToListPage();
    }

    public void deleteProduct(View view){
        String keyInventory = preferencesManager.getStringPreference("InventoryID");
        firestoreManager.deleteDocument("lista_inventarios/inventario1/productos", recieveID);
        goToListPage();
    }

    public void goToListPage(){
        Intent intent = new Intent(thisActivity, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout() {
        authManager.logout();
        preferencesManager.clearSharedPreferences();
        redirectToLogin();
    }

    private void redirectToLogin() {
        Intent intent = new Intent(thisActivity, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
