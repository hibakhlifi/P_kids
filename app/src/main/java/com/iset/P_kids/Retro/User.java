package com.iset.P_kids.Retro;

import com.google.gson.annotations.SerializedName;

public class User
{
    @SerializedName("Id")
    private int id;

    @SerializedName("Nom")
    private String nom;
    @SerializedName("Prénom")
    private String prénom;
    @SerializedName("Email")
    private String email;

    public User() {
        this.id = id;
        this.nom = nom;
        this.prénom = prénom;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrénom() {
        return prénom;
    }

    public void setPrénom(String prénom) {
        this.prénom = prénom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
