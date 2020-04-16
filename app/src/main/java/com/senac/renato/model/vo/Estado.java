package com.senac.renato.model.vo;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Estado {
    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String nome;

    @DatabaseField(canBeNull = false, width = 2)
    private String uf;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Pais pais;

    public Estado() {
    }

    public Estado(String nome, String uf, Pais pais) {
        this.nome = nome;
        this.uf = uf;
        this.pais = pais;
    }

    public Estado(Integer id, String nome, String uf, Pais pais) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.pais = pais;
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @NonNull
    @Override
    public String toString() {
        return nome + " ("+uf+")/"+pais.getNome();
    }
}
