package com.senac.renato.model.bo;

public class EstadoBO {
    public static boolean validarNome(String nome){
        return nome!=null && !nome.equals("");
    }
}
