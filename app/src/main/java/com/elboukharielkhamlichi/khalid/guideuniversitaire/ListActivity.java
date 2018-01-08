package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.database.AppDatabase;
import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

import java.util.List;

public class ListActivity extends AppCompatActivity implements EtablissementAdapter.ItemClickListener {

    private AppDatabase appDB;
    private List<Etablissement> etablissements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_DOCUMENTS}, 1);
        appDB = AppDatabase.getAppDatabase(this);
        etablissements = appDB.etablissementDao().getAll();
        System.out.println("onCreate of listActivity");
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EtablissementAdapter adapter = new EtablissementAdapter(this, etablissements);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        System.out.println("rrrrrrrrrrrrr");
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("etablissement", etablissements.get(position));
        startActivity(intent);
    }
}
