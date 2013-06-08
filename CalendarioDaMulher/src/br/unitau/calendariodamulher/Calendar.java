package br.unitau.calendariodamulher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Calendar extends Activity {

	// arquivo de configuração
	private final String FILE = "settings.txt";
	//private final String FILE_CALENDAR = "calendar.txt";

	// itens de tela
	private Button btn_enviar;
	private EditText et_nome;
	private EditText et_dia;
	private EditText et_mes;
	private EditText et_ano;
	private EditText et_ciclo;
	private EditText et_duracao;
	private ListView lista;
	//private SlidingDrawer sd_settings;

	//itens de dados
	private java.util.Calendar dum;
	private DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
	private ArrayList<ItemCalendar> itens;
	private CalendarAdapter calendarAdp;
	private int dia, mes, ano, ciclo, duracao;
	private String nome;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);

		et_nome = (EditText) findViewById(R.id.et_nome);
		et_dia = (EditText) findViewById(R.id.et_dia);
		et_mes = (EditText) findViewById(R.id.et_mes);
		et_ano = (EditText) findViewById(R.id.et_ano);
		et_ciclo = (EditText) findViewById(R.id.et_ciclo);
		et_duracao = (EditText) findViewById(R.id.et_duracao);
		lista = (ListView) findViewById(R.id.lista);

		btn_enviar = (Button) findViewById(R.id.btn_enviar);
		btn_enviar.setOnClickListener(onClickEnviar);

		dum = java.util.Calendar.getInstance();

		try {
			String parametros = lerArquivoString(FILE);
			String[] dados = parametros.split("\n");
			nome = dados[0];
			dia = Integer.parseInt(dados[1]);
			mes = Integer.parseInt(dados[2]);
			ano = Integer.parseInt(dados[3]);
			ciclo = Integer.parseInt(dados[4]);
			duracao = Integer.parseInt(dados[5]);
			montarTelaConfig();

			dum.set(ano, mes - 1, dia);
			montarCalendario();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "erro " + e.toString(), Toast.LENGTH_LONG)
					.show();
		}
	}

	public void montarTelaConfig() {
		et_nome.setText(nome);
		et_dia.setText(String.valueOf(dia));
		et_mes.setText(String.valueOf(mes));
		et_ano.setText(String.valueOf(ano));
		et_ciclo.setText(String.valueOf(ciclo));
		et_duracao.setText(String.valueOf(duracao));
	}

	public void montarCalendario() {
		//int periodo = ciclo * 3; //3 ciclos
		itens = new ArrayList<ItemCalendar>();

		for (int i = 0; i < 3; i++) {
			java.util.Calendar diaCiclo = java.util.Calendar.getInstance();
			diaCiclo.setTime(dum.getTime());

			java.util.Calendar udm = java.util.Calendar.getInstance(); //ultimo dia de menstruação
			udm.setTime(dum.getTime());
			udm.add(java.util.Calendar.DAY_OF_YEAR, duracao - 1);

			java.util.Calendar ovulacao = java.util.Calendar.getInstance();
			ovulacao.setTime(dum.getTime());
			ovulacao.add(java.util.Calendar.DAY_OF_YEAR, ciclo - 14);

			java.util.Calendar ipf = java.util.Calendar.getInstance(); // inicio do periodo fertil
			ipf.setTime(ovulacao.getTime());
			ipf.add(java.util.Calendar.DAY_OF_YEAR, -4);

			java.util.Calendar fpf = java.util.Calendar.getInstance(); // final do periodo fertil
			fpf.setTime(ovulacao.getTime());
			fpf.add(java.util.Calendar.DAY_OF_YEAR, 2);

			java.util.Calendar ipc = java.util.Calendar.getInstance(); // inicio do proximo ciclo
			ipc.setTime(dum.getTime());
			ipc.add(java.util.Calendar.DAY_OF_YEAR, ciclo);

			for (int j = 0; j < ciclo; j++) {
				String data = String.valueOf(diaCiclo.get(java.util.Calendar.DAY_OF_MONTH));
				String mesCiclo = diaCiclo.getDisplayName(java.util.Calendar.MONTH,
						java.util.Calendar.LONG, Locale.ROOT);
				String conteudo = null;
				if (data.length() == 1)
					data = "0" + data;

				if (df.format(diaCiclo.getTime()).equals(df.format(ovulacao.getTime()))) {
					conteudo = "ovulação";
				} else if (df.format(diaCiclo.getTime()).equals(df.format(dum.getTime()))
						|| df.format(diaCiclo.getTime()).equals(df.format(udm.getTime()))
						|| diaCiclo.after(dum) && diaCiclo.before(udm)) {
					conteudo = "menstruação";
				} else if (df.format(diaCiclo.getTime()).equals(df.format(ipf.getTime()))
						|| df.format(diaCiclo.getTime()).equals(df.format(fpf.getTime()))
						|| diaCiclo.after(ipf) && diaCiclo.before(fpf)) {
					conteudo = "período fértil";
				} else {
					conteudo = "dia livre";
				}

				ItemCalendar item = new ItemCalendar(data, mesCiclo, conteudo);
				itens.add(item);
				diaCiclo.add(java.util.Calendar.DAY_OF_YEAR, 1);
			}
			dum.setTime(ipc.getTime());
		}

		//String dum1= df.format(dum.getTime());
		//dum.add(java.util.Calendar.DAY_OF_YEAR, Integer.parseInt(et_ciclo.getText().toString()));
		//String dum2 = df.format(dum.getTime());
		//tv_data.setText(dum1+"\n"+dum2);

		calendarAdp = new CalendarAdapter(getApplicationContext(), itens);
		lista.setAdapter(calendarAdp);
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
				if (salvarArquivoString(FILE, dados)) {
					//sd_settings.close();
					startActivity(new Intent(Calendar.this, Calendar.class));
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

	protected boolean salvarArquivoString(String file, String dados) {
		ManageFile filewrite = new ManageFile(file, this);
		if (filewrite.WriteFile(dados) == true)
			return true;
		return false;
	}

	private String lerArquivoString(String file) {
		try {
			ManageFile fileread = new ManageFile(file, this);
			return fileread.ReadFile();
		} catch (FileNotFoundException e) {
			Toast.makeText(this, "Arquivo nao encontrado.", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}