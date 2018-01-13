package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

public class DetailsActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 1;
    private Etablissement e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        e = (Etablissement) getIntent().getSerializableExtra("etablissement");

        setEditTexts();
    }

    public void modifier(View view) {
        Intent intent = new Intent(this, ModifyActivity.class);
        intent.putExtra("etablissement", e);
        startActivityForResult(intent, REQUEST_CODE);
    }

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


    public void affecter(View view) {
        Intent intent = new Intent(this, AffectActivity.class);
        intent.putExtra("etablissement", e);
        startActivity(intent);
    }
}
