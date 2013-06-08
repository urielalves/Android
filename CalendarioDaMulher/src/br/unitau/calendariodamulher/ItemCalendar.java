package br.unitau.calendariodamulher;

public class ItemCalendar {

	private String data;
	private String mes;
	private String conteudo;
	
	public ItemCalendar(String data, String mes, String conteudo) {
		this.data = data;
		this.mes = mes;
		this.conteudo = conteudo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	
}
