package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.database.EtablissementsDBAdaptateur;
import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

public class AddActivity extends AppCompatActivity {


    static final int SELECT_PICTURE = 1;

    private Uri imageUri;
    private String type = "universite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbEcole)
                    type = "ecole";
                if(checkedId == R.id.rbFaculte)
                    type = "faculte";
                if(checkedId == R.id.rbInstitut)
                    type = "institut";

            }
        });


    }

    public void ajouter(View view) {
        EditText nom = (EditText) findViewById(R.id.etNom);
        EditText ville = (EditText) findViewById(R.id.etVille);
        EditText email = (EditText) findViewById(R.id.etEmail);
        EditText tel = (EditText) findViewById(R.id.etTel);
        EditText adresse = (EditText) findViewById(R.id.etAdresse);


        Etablissement etablissement = new Etablissement(nom.getText().toString(), ville.getText().toString(), email.getText().toString(),
                                        tel.getText().toString(), adresse.getText().toString(), type, imageUri);

        EtablissementsDBAdaptateur dbAdapter = new EtablissementsDBAdaptateur(this);

        dbAdapter.open();
        dbAdapter.insertEtablissement(etablissement);
        dbAdapter.close();

        Toast.makeText(this, "Etablissement ajouté", Toast.LENGTH_LONG).show();

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

                ImageView imgView = (ImageView) findViewById(R.id.imageView);
                imageUri = data.getData();
                imgView.setImageURI(imageUri);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    ContentResolver resolver = this.getContentResolver();
                    resolver.takePersistableUriPermission(imageUri, takeFlags);
                }
            }

        }
    }


}
