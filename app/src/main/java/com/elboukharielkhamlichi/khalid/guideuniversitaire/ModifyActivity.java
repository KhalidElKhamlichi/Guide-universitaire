package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.database.AppDatabase;
import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

public class ModifyActivity extends AppCompatActivity {

    private EditText nom;
    private EditText ville;
    private EditText email;
    private EditText tel;
    private EditText adresse;
    private ImageView imgView;

    private AppDatabase appDB;
    static final int SELECT_PICTURE = 1;

    private Etablissement e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        appDB = AppDatabase.getAppDatabase(this);

        e = (Etablissement) getIntent().getSerializableExtra("etablissement");

        nom = (EditText) findViewById(R.id.etNom);
        ville = (EditText) findViewById(R.id.etVille);
        email = (EditText) findViewById(R.id.etEmail);
        tel = (EditText) findViewById(R.id.etTel);
        adresse = (EditText) findViewById(R.id.etAdresse);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        imgView = (ImageView) findViewById(R.id.imageView);

        nom.setText(e.getNom());
        ville.setText(e.getVille());
        email.setText(e.getEmail());
        tel.setText(e.getTel());
        adresse.setText(e.getAdresse());
        imgView.setImageURI(Uri.parse(e.getImageUri()));

        if(e.getType().equals("ecole"))
            radioGroup.check(R.id.rbEcole);
        if(e.getType().equals("institut"))
            radioGroup.check(R.id.rbInstitut);
        if(e.getType().equals("faculte"))
            radioGroup.check(R.id.rbFaculte);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbEcole)
                    e.setType("ecole");
                if(checkedId == R.id.rbFaculte)
                    e.setType("faculte");
                if(checkedId == R.id.rbInstitut)
                    e.setType("institut");
                if(checkedId == R.id.rbUni)
                    e.setType("universite");

            }
        });
    }

    public void enregistrer(View view) {

        if(!nom.getText().toString().isEmpty())
            e.setNom(nom.getText().toString());
        if(!ville.getText().toString().isEmpty())
            e.setVille(ville.getText().toString());
        if(!tel.getText().toString().isEmpty())
            e.setTel(tel.getText().toString());
        if(!email.getText().toString().isEmpty())
            e.setEmail(email.getText().toString());
        if(!adresse.getText().toString().isEmpty())
            e.setAdresse(adresse.getText().toString());

        appDB.etablissementDao().update(e);

        Intent intent = new Intent();
        intent.putExtra("newEtab", e);
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    public void pickImage(View view) {

        Intent intent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }else{
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {


                Uri imageUri = data.getData();
                imgView.setImageURI(imageUri);
                e.setImageUri(imageUri.toString());
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    ContentResolver resolver = this.getContentResolver();
                    resolver.takePersistableUriPermission(imageUri, takeFlags);
                }
            }

        }
    }



}
