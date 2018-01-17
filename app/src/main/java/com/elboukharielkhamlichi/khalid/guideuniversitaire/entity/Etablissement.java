package com.elboukharielkhamlichi.khalid.guideuniversitaire.entity;

import android.net.Uri;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.EtablissementConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khalid on 12/29/2017.
 */


public class Etablissement implements Serializable {

    private int eid;

    private String nom;
    private String ville;
    private String email;
    private String tel;
    private String adresse;
    private String type;
    private List<Integer> etablissements;

    private String imageUri;

    public Etablissement() {}

    public Etablissement(String nom, String ville, String email, String tel, String adresse, String type, Uri imageUri) {
        this.nom = nom;
        this.ville = ville;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
        this.type = type;

        if(imageUri != null)
            this.imageUri = imageUri.toString();
        else
            this.imageUri = Uri.parse("android.resource://com.elboukharielkhamlichi.khalid.guideuniversitaire/drawable/uae").toString();

        etablissements = new ArrayList<>();
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public List<Integer> getEtablissements() {
        return etablissements;
    }

    public void setEtablissements(List<Integer> etablissements) {
        this.etablissements = etablissements;
    }

    @Override
    public String toString() {
        return "Etablissement{" +
                "eid=" + eid +
                ", nom='" + nom + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
