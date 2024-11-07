package com.example.lab06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Chip cJava, cKotlin, cMuseo; //Los chips de filtrado
    RecyclerView recyclerView; //Lista de los elementos
    List<DataClass> dataList; //Lista de todos los elementos
    List <DataClass> TemporalList; //Lista de elementos filtrados
    MyAdapter adapter; //Puente de datos entre ReciclerView
    DataClass androidData; // Datos de los elementos o card views
    SearchView searchView; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cJava = findViewById(R.id.cJava);
        cKotlin = findViewById(R.id.cKotlin);
        cMuseo = findViewById(R.id.cMuseo);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        //Aca se listan los elementos de la lista completa
        dataList = new ArrayList<>();
        androidData = new DataClass("Convento de Santa Catalina", R.string.camera, "Arquitectura", R.drawable.catalina_convento);
        dataList.add(androidData);
        androidData = new DataClass("Catedral de Arequipa", R.string.recyclerview, "Arquitectura", R.drawable.catedral_arequipa);
        dataList.add(androidData);
        androidData = new DataClass("Iglesia Compañia de Jesus", R.string.date, "Iglesia", R.drawable.iglesia_jesus);
        dataList.add(androidData);
        androidData = new DataClass("Iglesia de santo Domingo", R.string.edit, "Iglesia", R.drawable.iglesia_santo_domingo);
        dataList.add(androidData);
        androidData = new DataClass("Museo de santuarios Andinos", R.string.rating, "Museo", R.drawable.museo_sa);
        dataList.add(androidData);
        adapter = new MyAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        TemporalList = new ArrayList<>();

        // Configuracion de los chips al hacer click
        cJava.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mostrarFiltrado(cJava.getText().toString());
                } else {
                    // Si se desmarca el chip, volvemos a mostrar la lista completa
                    adapter.setSearchList(dataList);
                }
            }
        });

        cKotlin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mostrarFiltrado(cKotlin.getText().toString());
                } else {
                    // Si se desmarca el chip, volvemos a mostrar la lista completa
                    adapter.setSearchList(dataList);
                }
            }
        });


        cMuseo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mostrarFiltrado(cMuseo.getText().toString());
                } else {
                    // Si se desmarca el chip, volvemos a mostrar la lista completa
                    adapter.setSearchList(dataList);
                }
            }
        });
    }
    //Muestra el filtrado de los chips
    public void mostrarFiltrado(String filtro){
        TemporalList = new ArrayList<>();
        for (int i=0; i < dataList.size();i++){
            if( dataList.get(i).getDataLang().equals(filtro)){
                TemporalList.add(dataList.get(i));
            }
        }
        adapter.setSearchList(TemporalList);
    }

    //Muestra la funcion de busqueda
    private void searchList(String text){
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList){
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(this, "Lo que busca no Existe", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }
}