package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.Manifest;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.database.EtablissementsDBAdaptateur;
import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements EtablissementAdapter.ItemClickListener, SearchFragment.OnFragmentInteractionListener {

    private List<Etablissement> etablissements;
    private RecyclerView recyclerView;
    private EtablissementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_DOCUMENTS}, 1);

        EtablissementsDBAdaptateur dbAdapter = new EtablissementsDBAdaptateur(this);
        dbAdapter.open();
        etablissements = dbAdapter.getAllEtablissements();
        dbAdapter.close();

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog();
            }
        });

        // set up the RecyclerView
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EtablissementAdapter(this, etablissements);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("etablissement", etablissements.get(position));
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(String nom, String ville, String type) {
        List<Etablissement> searchResult = new ArrayList<>();

        for (Etablissement e : etablissements) {
            if(e.getNom().toLowerCase().equals(nom.toLowerCase())) {
                searchResult.add(e);
            }
            else if(!ville.isEmpty()) {
                if (e.getVille().toLowerCase().contains(ville.toLowerCase()) && e.getType().equals(type)) {
                    searchResult.add(e);
                }
            }
            else if(e.getType().equals(type)) {
                searchResult.add(e);
            }


        }

        EtablissementAdapter adapter = new EtablissementAdapter(this, searchResult);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    void showDialog() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SearchFragment.newInstance();
        newFragment.show(ft, "dialog");
    }

    @Override
    public void onResume() {
        super.onResume();

        EtablissementsDBAdaptateur dbAdapter = new EtablissementsDBAdaptateur(this);
        dbAdapter.open();
        etablissements = dbAdapter.getAllEtablissements();
        dbAdapter.close();
        adapter = new EtablissementAdapter(this, etablissements);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}
