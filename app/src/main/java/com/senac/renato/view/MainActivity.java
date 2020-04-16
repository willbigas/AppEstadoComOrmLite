package com.senac.renato.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.senac.renato.R;
import com.senac.renato.control.MainControl;

public class MainActivity extends AppCompatActivity {
    private EditText editNomeEstado;
    private EditText editUf;
    private ListView lvEstados;
    private Button btnSalvar;
    private ImageButton btnAdicionarPais;
    private Spinner spPaises;
    private MainControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        control = new MainControl(this);
    }

    private void initialize() {
        spPaises = findViewById(R.id.spinnerPais);
        lvEstados = findViewById(R.id.lvEstados);
        editNomeEstado = findViewById(R.id.editNomeEstado);
        editUf = findViewById(R.id.editUf);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnAdicionarPais = findViewById(R.id.btnAdicionarPais);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.salvarAction();
            }
        });
        btnAdicionarPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.redirecionarParaContextoDePaises();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        control.atualizarPaises();
    }

    public EditText getEditNomeEstado() {
        return editNomeEstado;
    }

    public void setEditNomeEstado(EditText editNomeEstado) {
        this.editNomeEstado = editNomeEstado;
    }

    public EditText getEditUf() {
        return editUf;
    }

    public void setEditUf(EditText editUf) {
        this.editUf = editUf;
    }

    public ListView getLvEstados() {
        return lvEstados;
    }

    public Spinner getSpPaises() {
        return spPaises;
    }
}
