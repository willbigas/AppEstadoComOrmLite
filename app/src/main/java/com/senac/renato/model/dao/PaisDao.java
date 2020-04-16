package com.senac.renato.model.dao;

import android.content.Context;

import com.senac.renato.model.helpers.DaoHelper;
import com.senac.renato.model.vo.Pais;

import java.sql.SQLException;
import java.util.List;

public class PaisDao extends DaoHelper<Pais> {
    public PaisDao(Context c) {
        super(c, Pais.class);
    }

    public List<Pais> listar(){
        try {
            return getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pais> listaPorNome(String nome){
        try {
            return getDao().queryBuilder().where().eq("nome", nome).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}