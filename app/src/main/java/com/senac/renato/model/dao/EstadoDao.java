package com.senac.renato.model.dao;

import android.content.Context;

import com.senac.renato.model.helpers.DaoHelper;
import com.senac.renato.model.vo.Estado;

public class EstadoDao extends DaoHelper<Estado> {
    public EstadoDao(Context c) {
        super(c, Estado.class);
    }
}