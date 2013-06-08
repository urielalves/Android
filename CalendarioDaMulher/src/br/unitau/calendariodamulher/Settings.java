package br.unitau.calendariodamulher;

import java.io.FileNotFoundException;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {
	private final String FILE = "settings.txt";
	private Button btn_enviar;
	private EditText et_nome;
	private EditText et_dia;
	private EditText et_mes;
	private EditText et_ano;
	private EditText et_ciclo;
	private EditText et_duracao;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		btn_enviar = (Button) findViewById(R.id.btn_enviar);
		et_nome = (EditText) findViewById(R.id.et_nome);
		et_dia = (EditText) findViewById(R.id.et_dia);
		et_mes = (EditText) findViewById(R.id.et_mes);
		et_ano = (EditText) findViewById(R.id.et_ano);
		et_ciclo = (EditText) findViewById(R.id.et_ciclo);
		et_duracao = (EditText) findViewById(R.id.et_duracao);

		btn_enviar.setOnClickListener(onClickEnviar);
		try {
			String parametros = lerArquivoString();
			String[] dados = parametros.split("\n");
			et_nome.setText(dados[0]);
			et_dia.setText(dados[1]);
			et_mes.setText(dados[2]);
			et_ano.setText(dados[3]);
			et_ciclo.setText(dados[4]);
			et_duracao.setText(dados[5]);
		} catch (Exception e) {
		}
	}

	public OnClickListener onClickEnviar = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String dados = null;
			dados = et_nome.getText().toString();
			dados += "\n" + et_dia.getText().toString();
			dados += "\n" + et_mes.getText().toString();
			dados += "\n" + et_ano.getText().toString();
			dados += "\n" + et_ciclo.getText().toString();
			dados += "\n" + et_duracao.getText().toString();
			if (verificarDados())
				if (salvarArquivoString(dados)) {
					startActivity(new Intent(Settings.this, Dashboard.class));
					finish();
				}
		}
	};

	public boolean verificarDados() {
		int ciclo = Integer.parseInt(et_ciclo.getText().toString());
		int duracao = Integer.parseInt(et_duracao.getText().toString());
		int dia = Integer.parseInt(et_dia.getText().toString());
		int mes = Integer.parseInt(et_mes.getText().toString());
		int ano = Integer.parseInt(et_ano.getText().toString());
		if (ciclo < 20 || ciclo > 50) {
			Toast.makeText(getApplicationContext(), "informe o ciclo entre 20 e 50 dias",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (duracao < 3 || duracao > 9) {
			Toast.makeText(getApplicationContext(), "informe a duração entre 3 e 9 dias",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (mes == 2 && dia > 28 && (ano % 4 != 0)) {
			Toast.makeText(getApplicationContext(),
					"data invalida. mês informado tem apenas 28 dias", Toast.LENGTH_LONG).show();
			return false;
		} else if (mes == 2 && dia > 29) {
			Toast.makeText(getApplicationContext(),
					"data invalida. mês informado tem apenas 29 dias", Toast.LENGTH_LONG).show();
			return false;
		} else if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
			Toast.makeText(getApplicationContext(),
					"data invalida. mês informado tem apenas 30 dias", Toast.LENGTH_LONG).show();
			return false;
		} else if (dia > 31 || mes > 12) {
			Toast.makeText(getApplicationContext(), "data invalida", Toast.LENGTH_LONG).show();
			return false;
		} else if (ano < 2013) {
			Toast.makeText(getApplicationContext(), "informe uma data mais recente",
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	protected boolean salvarArquivoString(String dados) {
		ManageFile filewrite = new ManageFile(FILE, this);
		if (filewrite.WriteFile(dados) == true)
			return true;
		return false;
	}

	private String lerArquivoString() {
		try {
			ManageFile fileread = new ManageFile(FILE, this);
			return fileread.ReadFile();
		} catch (FileNotFoundException e) {
			Toast.makeText(this, "Arquivo nao encontrado.", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
