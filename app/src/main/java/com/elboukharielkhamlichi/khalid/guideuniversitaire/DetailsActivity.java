package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView nom = (TextView) findViewById(R.id.Nom);
        TextView ville = (TextView) findViewById(R.id.Ville);
        TextView email = (TextView) findViewById(R.id.Email);
        TextView tel = (TextView) findViewById(R.id.Tel);
        TextView adresse = (TextView) findViewById(R.id.Adresse);
        ImageView image = (ImageView) findViewById(R.id.imageView);

        Etablissement e = (Etablissement) getIntent().getSerializableExtra("etablissement");

        nom.setText(e.getNom());
        ville.setText(e.getVille());
        email.setText(e.getEmail());
        tel.setText(e.getTel());
        adresse.setText(e.getAdresse());
        image.setImageURI(Uri.parse(e.getImageUri()));
    }
}
