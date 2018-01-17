package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.database.AppDatabase;
import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

import java.util.List;

public class DeleteActivity extends AppCompatActivity implements EtablissementAdapter.ItemClickListener{

    //private AppDatabase appDB;
    private EtablissementsDBAdaptateur dbAdapter;
    private Dialog dialog;
    private List<Etablissement> etablissements;
    private EtablissementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        /*appDB = AppDatabase.getAppDatabase(this);
        etablissements = appDB.etablissementDao().getAll();*/

        dbAdapter = new EtablissementsDBAdaptateur(this);
        dbAdapter.open();
        etablissements = dbAdapter.getAllEtablissements();
        dbAdapter.close();

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EtablissementAdapter(this, etablissements);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {

        final Etablissement etablissement = etablissements.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Supprimer université")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //appDB.etablissementDao().delete(etablissement);
                        dbAdapter.open();
                        dbAdapter.removeUniversite(etablissement);
                        dbAdapter.close();
                        etablissements.remove(etablissement);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        dialog = builder.create();
        dialog.show();
    }


}
