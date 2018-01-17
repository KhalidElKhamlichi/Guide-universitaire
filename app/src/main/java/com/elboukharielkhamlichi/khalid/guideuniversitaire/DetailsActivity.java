package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements EtablissementAdapter.ItemClickListener {

    private final int REQUEST_CODE = 1;
    private Etablissement e;

    private EtablissementsDBAdaptateur dbAdapter;
    private RecyclerView recyclerView;
    private EtablissementAdapter adapter;
    private List<Etablissement> allEtablissements;
    private List<Etablissement> etablissements;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        e = (Etablissement) getIntent().getSerializableExtra("etablissement");

        text = (TextView) findViewById(R.id.textView);

        if(!e.getType().equals("universite"))
            text.setText("Université");

        setEditTexts();

        dbAdapter = new EtablissementsDBAdaptateur(this);
        dbAdapter.open();
        allEtablissements = dbAdapter.getAllEtablissements();
        dbAdapter.close();

        recyclerView = findViewById(R.id.detRecyclerView);
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        etablissements = getEtablissementsFromIds();
        adapter = new EtablissementAdapter(this, etablissements);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /*public void modifier(View view) {
        Intent intent = new Intent(this, ModifyActivity.class);
        intent.putExtra("etablissement", e);
        startActivityForResult(intent, REQUEST_CODE);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                e = (Etablissement) data.getSerializableExtra("newEtab");
                setEditTexts();

            }

        }
    }

    private void setEditTexts() {

        TextView nom = (TextView) findViewById(R.id.Nom);
        TextView ville = (TextView) findViewById(R.id.Ville);
        TextView email = (TextView) findViewById(R.id.Email);
        TextView tel = (TextView) findViewById(R.id.Tel);
        TextView adresse = (TextView) findViewById(R.id.Adresse);
        ImageView image = (ImageView) findViewById(R.id.imageView);

        nom.setText(e.getNom());
        ville.setText(e.getVille());
        email.setText(e.getEmail());
        tel.setText(e.getTel());
        adresse.setText(e.getAdresse());
        image.setImageURI(Uri.parse(e.getImageUri()));
    }


    /*public void affecter(View view) {
        Intent intent = new Intent(this, AffectActivity.class);
        intent.putExtra("etablissement", e);
        startActivity(intent);
    }*/

    public List<Etablissement> getEtablissementsFromIds() {
        List<Etablissement> list = new ArrayList<>();
        for (Etablissement eta : allEtablissements) {
            if(e.getEtablissements().contains(new Integer(eta.getEid())))
                list.add(eta);
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuAff:
                Intent intentAff = new Intent(this, AffectActivity.class);
                intentAff.putExtra("etablissement", e);
                startActivity(intentAff);
                break;
            case R.id.menuModifier:
                Intent intentMod = new Intent(this, ModifyActivity.class);
                intentMod.putExtra("etablissement", e);
                startActivityForResult(intentMod, REQUEST_CODE);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("etablissement", etablissements.get(position));
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onResume() {
        super.onResume();

        dbAdapter.open();
        e = dbAdapter.getEtablissementById(e.getEid());
        dbAdapter.close();

        etablissements = getEtablissementsFromIds();
        adapter = new EtablissementAdapter(this, etablissements);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}
