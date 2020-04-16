package com.senac.renato.model.vo;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable
public class Pais {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, width = 110, unique = true)
    private String nome;

    @ForeignCollectionField(eager = true)
    private Collection<Estado> collectionEstados;

    public Pais() {
    }

    public Collection<Estado> getCollectionEstados() {
        return collectionEstados;
    }

    public void setCollectionEstados(Collection<Estado> collectionEstados) {
        this.collectionEstados = collectionEstados;
    }

    public Pais(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Pais(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NonNull
    @Override
    public String toString() {
        return nome + " (" + id + ")";
    }
}