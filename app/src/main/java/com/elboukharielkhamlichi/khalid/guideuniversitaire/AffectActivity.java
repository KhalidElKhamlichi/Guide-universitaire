package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.database.AppDatabase;
import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

public class AffectActivity extends AppCompatActivity {

    private AppDatabase appDB;
    private RecyclerView recyclerView;
    private EtablissementAdapter adapter;

    private Etablissement e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affect);

        appDB = AppDatabase.getAppDatabase(this);
        e = (Etablissement) getIntent().getSerializableExtra("etablissement");

        // set up the RecyclerView
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EtablissementAdapter(this, e.getEtablissements());
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}
