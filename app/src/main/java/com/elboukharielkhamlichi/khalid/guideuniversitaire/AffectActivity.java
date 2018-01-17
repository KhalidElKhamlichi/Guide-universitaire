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

import com.elboukharielkhamlichi.khalid.guideuniversitaire.database.EtablissementsDBAdaptateur;
import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

import java.util.ArrayList;
import java.util.List;

public class AffectActivity extends AppCompatActivity implements EtablissementAdapter.ItemClickListener{

    private EtablissementsDBAdaptateur dbAdapter;
    private RecyclerView recyclerView;
    private EtablissementAdapter adapter;
    private ArrayAdapter<String> spAdapter;
    private Spinner sp;
    private Etablissement e;
    private List<Etablissement> allEtablissements;
    private List<Etablissement> etablissements;
    private List<String> spEtablissements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affect);

        sp = (Spinner) findViewById(R.id.spinner);

        dbAdapter = new EtablissementsDBAdaptateur(this);
        dbAdapter.open();
        allEtablissements = dbAdapter.getAllEtablissements();
        dbAdapter.close();

        e = (Etablissement) getIntent().getSerializableExtra("etablissement");

        spEtablissements = new ArrayList<>();

        for (Etablissement et : allEtablissements) {
            if(et.getEid() != e.getEid() && !e.getEtablissements().contains(new Integer(et.getEid()))) {
                if (!et.getType().equals("universite") && e.getType().equals("universite"))
                    spEtablissements.add(et.getNom());
                else if (et.getType().equals("universite") && !e.getType().equals("universite"))
                    spEtablissements.add(et.getNom());
            }
        }
        spAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spEtablissements);

        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spAdapter);

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

                e.getEtablissements().add(new Integer(et.getEid()));
                et.getEtablissements().add(new Integer(e.getEid()));

                dbAdapter.open();
                dbAdapter.updateEtablissement(e);
                dbAdapter.updateEtablissement(et);
                dbAdapter.close();

                etablissements.add(et);
                spEtablissements.remove(et.getNom());
                spAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();

                break;
            }
        }
    }

    public List<Etablissement> getEtablissementsFromIds() {
        List<Etablissement> list = new ArrayList<>();
        for (Etablissement eta : allEtablissements) {
            if(e.getEtablissements().contains(new Integer(eta.getEid())))
                list.add(eta);
        }
        return list;
    }

    @Override
    public void onItemClick(View view, int position) {

        final Etablissement etablissement = etablissements.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Supprimer affectation")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        e.getEtablissements().remove(new Integer(etablissement.getEid()));
                        etablissement.getEtablissements().remove(new Integer(e.getEid()));

                        dbAdapter.open();
                        dbAdapter.updateEtablissement(e);
                        dbAdapter.updateEtablissement(etablissement);
                        dbAdapter.close();

                        etablissements.remove(etablissement);
                        spEtablissements.add(etablissement.getNom());
                        spAdapter.notifyDataSetChanged();
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
