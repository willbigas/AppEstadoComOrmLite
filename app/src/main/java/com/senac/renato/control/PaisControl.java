package com.senac.renato.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.senac.renato.model.dao.PaisDao;
import com.senac.renato.model.vo.Pais;
import com.senac.renato.view.PaisActivity;

import java.sql.SQLException;
import java.util.List;

public class PaisControl {

    private PaisActivity activity;

    //Para o ListView
    private ArrayAdapter<Pais> adapterPaises;
    private List<Pais> listPaises;

    private Pais pais;

    //Criação dos DAOS
    private PaisDao paisDao;

    public PaisControl(PaisActivity activity) {
        this.activity = activity;
        paisDao = new PaisDao(this.activity);
        configListViewEstados();
    }

    private void configListViewEstados(){
        //Elementos da lista
        try {
            listPaises = paisDao.getDao().queryForAll();
            adapterPaises = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_list_item_1,
                    listPaises
            );
            activity.getLvPaises().setAdapter(adapterPaises);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addCliqueLongoLvPaises();
        addCliqueCurtoLvPaises();
    }

    private void addCliqueLongoLvPaises(){
        activity.getLvPaises().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pais = adapterPaises.getItem(position);
                confirmarExclusaoAction(pais);
                return true;
            }
        });
    }

    public void addCliqueCurtoLvPaises(){
        activity.getLvPaises().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pais = adapterPaises.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Pais");
                alerta.setMessage(pais.toString());
                alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pais = null;
                    }
                });
                alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popularFormAction(pais);
                    }
                });
                alerta.show();
            }
        });
    }

    private Pais getEstadoForm(){
        Pais p = new Pais();
        p.setNome(activity.getEditNomePais().getText().toString());
        return p;
    }


    private void limparForm(){
        activity.getEditNomePais().setText("");
    }

    public void popularFormAction(Pais p){
        activity.getEditNomePais().setText(p.getNome());
    }

    public void confirmarExclusaoAction(final Pais p){
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluindo país");
        alerta.setMessage("Deseja realmente excluir o país " + p.getNome()+"?");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pais = null;
            }
        });
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    paisDao.getDao().delete(p);
                    adapterPaises.remove(p);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                pais = null;
            }
        });
        alerta.show();
    }


    private void cadastrar(){
        Pais pais = getEstadoForm();
        try {
            int res = paisDao.getDao().create(pais); //Envia par o banco de dados
            adapterPaises.add(pais); //Atualiza no ListView

            if(res>0){
                Toast.makeText(activity, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Tente novamente em breve", Toast.LENGTH_SHORT).show();
            }

            //LOG
            Log.i("Testando", "Cadastrou");
            Toast.makeText(activity, "Id:" + pais.getId(), Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editar(Pais pais){
        this.pais.setNome(pais.getNome());
        try {
            adapterPaises.notifyDataSetChanged(); //Atualiza no view
            int res = paisDao.getDao().update(this.pais); //Editar no banco de dados
            if(res>0){
                Toast.makeText(activity, "Sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Tente mais tarde", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarAction(){
        if(pais ==null){
            cadastrar();
        } else {
            editar(getEstadoForm());
        }

        pais = null;

       /*
       Método utilizando creatrOrUpdate()
       try {
            Dao.CreateOrUpdateStatus res = estadoDao.getDao().createOrUpdate(estado);
            if(res.isCreated()){
                //Criou
            } else if(res.isUpdated()){
                //Editado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        limparForm();
    }
}