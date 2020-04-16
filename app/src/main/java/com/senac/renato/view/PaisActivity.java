package com.senac.renato.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.senac.renato.R;
import com.senac.renato.control.PaisControl;

public class PaisActivity extends AppCompatActivity {

    private EditText editNomePais;
    private ListView lvPaises;
    private Button btnSalvar;
    private PaisControl control;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);
        initialize();
        control = new PaisControl(this);
    }

    private void initialize() {
        lvPaises = findViewById(R.id.lvPaises);
        editNomePais = findViewById(R.id.editNomePais);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.salvarAction();
            }
        });
    }


    public EditText getEditNomePais() {
        return editNomePais;
    }

    public void setEditNomePais(EditText editNomePais) {
        this.editNomePais = editNomePais;
    }

    public ListView getLvPaises() {
        return lvPaises;
    }

    public void setLvPaises(ListView lvPaises) {
        this.lvPaises = lvPaises;
    }

    public Button getBtnSalvar() {
        return btnSalvar;
    }

    public void setBtnSalvar(Button btnSalvar) {
        this.btnSalvar = btnSalvar;
    }
}
