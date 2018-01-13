package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.database.AppDatabase;
import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

import java.util.ArrayList;
import java.util.List;

public class AffectActivity extends AppCompatActivity implements EtablissementAdapter.ItemClickListener{

    private AppDatabase appDB;
    private RecyclerView recyclerView;
    private EtablissementAdapter adapter;
    private Spinner sp;
    private Etablissement e;
    private List<Etablissement> allEtablissements;
    private List<Etablissement> etablissements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affect);

        sp = (Spinner) findViewById(R.id.spinner);

        appDB = AppDatabase.getAppDatabase(this);
        allEtablissements = appDB.etablissementDao().getAll();
        List<String> spEtablissements = new ArrayList<>();
        e = (Etablissement) getIntent().getSerializableExtra("etablissement");
        /*if(e.getEtablissements() == null)
            e.setEtablissements(new ArrayList<Etablissement>());
        appDB.etablissementDao().update(e);*/

        for (Etablissement et : allEtablissements) {
            if(et.getEid() != e.getEid()) {
                if (!et.getType().equals("universite") && e.getType().equals("universite"))
                    spEtablissements.add(et.getNom());
                else if (et.getType().equals("universite") && !e.getType().equals("universite"))
                    spEtablissements.add(et.getNom());
            }
        }

        ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spEtablissements);

        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrAdapter);

        // set up the RecyclerView
        recyclerView = findViewById(R.id.affRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        etablissements = getEtablissementsFromIds();
        adapter = new EtablissementAdapter(this, etablissements);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void affecter(View view) {
        if(!e.getType().equals("universite") && e.getEtablissements().size() > 0) {
            Toast.makeText(this, "Supprimez l'université existante avant d'affecter une autre.", Toast.LENGTH_LONG).show();
            return;
        }
        String selected = sp.getSelectedItem().toString();
        for (Etablissement et : allEtablissements) {
            if(et.getNom().equals(selected)) {
                e.getEtablissements().add(et.getEid());
                et.getEtablissements().add(e.getEid());
                appDB.etablissementDao().update(e);
                appDB.etablissementDao().update(et);
                break;
            }
        }
        adapter.notifyDataSetChanged();
        System.out.println("update succeeded " + e.getEtablissements().size());
    }

    public List<Etablissement> getEtablissementsFromIds() {
        List<Etablissement> list = new ArrayList<>();
        for (Etablissement eta : allEtablissements) {
            if(e.getEtablissements().contains(eta.getEid()))
                list.add(eta);
        }
        return list;
    }

    @Override
    public void onItemClick(View view, int position) {

        final Etablissement etablissement = allEtablissements.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Supprimer affectation")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        e.getEtablissements().remove(e.getEtablissements().indexOf(etablissement.getEid()));
                        etablissement.getEtablissements().remove(etablissement.getEtablissements().indexOf(etablissement.getEid()));
                        appDB.etablissementDao().update(e);
                        appDB.etablissementDao().update(etablissement);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }
}
