package com.senac.renato.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.senac.renato.model.dao.EstadoDao;
import com.senac.renato.model.dao.PaisDao;
import com.senac.renato.model.vo.Estado;
import com.senac.renato.model.vo.Pais;
import com.senac.renato.view.MainActivity;
import com.senac.renato.view.PaisActivity;

import java.sql.SQLException;
import java.util.List;

public class MainControl {
    private MainActivity activity;

    //Para o ListView
    private ArrayAdapter<Estado> adapterEstados;
    private List<Estado> listEstados;

    //Para spinner pais
    private ArrayAdapter<Pais> adapterPaises;

    private Estado estado;

    //Criação dos DAOS
    private PaisDao paisDao;
    private EstadoDao estadoDao;

    public MainControl(MainActivity activity) {
        this.activity = activity;
        paisDao = new PaisDao(this.activity);
        estadoDao = new EstadoDao(this.activity);
        configSpinner();
        configListViewEstados();
    }

    private void configSpinner() {
        try {
            paisDao.getDao().createIfNotExists(new Pais(1, "Brasil"));
            paisDao.getDao().createIfNotExists(new Pais(2, "Argentina"));
            paisDao.getDao().createIfNotExists(new Pais(3, "Uruguai"));

            adapterPaises = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_spinner_item,
                    paisDao.listar()
            );
            activity.getSpPaises().setAdapter(adapterPaises);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configListViewEstados() {
        //Elementos da lista
        try {
            listEstados = estadoDao.getDao().queryForAll();
            adapterEstados = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_list_item_1,
                    listEstados
            );
            activity.getLvEstados().setAdapter(adapterEstados);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addCliqueLongoLvEstados();
        addCliqueCurtoLvEstados();
    }

    private void addCliqueLongoLvEstados() {
        activity.getLvEstados().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                estado = adapterEstados.getItem(position);
                confirmarExclusaoAction(estado);
                return true;
            }
        });
    }

    public void addCliqueCurtoLvEstados() {
        activity.getLvEstados().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                estado = adapterEstados.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Estado");
                alerta.setMessage(estado.toString());
                alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        estado = null;
                    }
                });
                alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popularFormAction(estado);
                    }
                });
                alerta.show();
            }
        });
    }

    private Estado getEstadoForm() {
        Estado e = new Estado();
        e.setNome(activity.getEditNomeEstado().getText().toString());
        e.setUf(activity.getEditUf().getText().toString());
        e.setPais((Pais) activity.getSpPaises().getSelectedItem());
        return e;
    }


    private void limparForm() {
        activity.getEditNomeEstado().setText("");
        activity.getEditUf().setText("");
    }

    public void popularFormAction(Estado e) {
        activity.getEditNomeEstado().setText(e.getNome());
        activity.getEditUf().setText(e.getUf());
    }

    public void confirmarExclusaoAction(final Estado e) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluindo estado");
        alerta.setMessage("Deseja realmente excluir o estado " + e.getNome() + "?");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                estado = null;
            }
        });
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    estadoDao.getDao().delete(e);
                    adapterEstados.remove(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                estado = null;
            }
        });
        alerta.show();
    }


    private void cadastrar() {
        Estado estado = getEstadoForm();
        try {
            int res = estadoDao.getDao().create(estado); //Envia par o banco de dados
            adapterEstados.add(estado); //Atualiza no ListView

            if (res > 0) {
                Toast.makeText(activity, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Tente novamente em breve", Toast.LENGTH_SHORT).show();
            }

            //LOG
            Log.i("Testando", "Cadastrou");
            Toast.makeText(activity, "Id:" + estado.getId(), Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editar(Estado newEstado) {
        estado.setNome(newEstado.getNome());
        estado.setUf(newEstado.getUf());
        estado.setPais(newEstado.getPais());
        try {
            adapterEstados.notifyDataSetChanged(); //Atualiza no view
            int res = estadoDao.getDao().update(estado); //Editar no banco de dados
            if (res > 0) {
                Toast.makeText(activity, "Sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Tente mais tarde", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarAction() {
        if (estado == null) {
            cadastrar();
        } else {
            editar(getEstadoForm());
        }

        estado = null;

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

    public void redirecionarParaContextoDePaises() {
        Intent intent = new Intent(activity, PaisActivity.class);
        activity.startActivity(intent);
    }

    public void atualizarPaises() {
        adapterPaises.clear();
        adapterPaises.addAll(paisDao.listar());
        adapterPaises.notifyDataSetChanged();
    }
}