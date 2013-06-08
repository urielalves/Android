package br.unitau.calendariodamulher;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {

	private LayoutInflater calInflater;
	private ArrayList<ItemCalendar> itens;
	private String[] meses = {"jan", "fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez"};

	public CalendarAdapter(Context context, ArrayList<ItemCalendar> itens) {
		this.itens = itens;
		calInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public ItemCalendar getItem(int pos) {
		return itens.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		ItemSuporte itemHolder;
		if (view == null){
			view = calInflater.inflate(R.layout.item_calendar_list, null);
			itemHolder = new ItemSuporte();
			itemHolder.data = (TextView) view.findViewById(R.id.data);
			itemHolder.mes = (TextView) view.findViewById(R.id.mes);
			itemHolder.conteudo = (TextView) view.findViewById(R.id.conteudo);
			view.setTag(itemHolder);
		} else {
			itemHolder = (ItemSuporte) view.getTag();
		}
		
		ItemCalendar item = itens.get(pos);
		itemHolder.data.setText(item.getData());
		itemHolder.mes.setText(meses[Integer.parseInt(item.getMes())-1]);
		itemHolder.conteudo.setText(item.getConteudo());
		if(item.getConteudo().equals("menstruação")){
			itemHolder.conteudo.setBackgroundResource(R.drawable.shape_red);
		} else if(item.getConteudo().equals("ovulação")){
			itemHolder.conteudo.setBackgroundResource(R.drawable.shape_green);
		} else if(item.getConteudo().equals("período fértil")){
			itemHolder.conteudo.setBackgroundResource(R.drawable.shape_yellow);
		} else {
			itemHolder.conteudo.setBackgroundResource(R.drawable.shape_blue);
		}
		
		return view;
	}

	public class ItemSuporte{
		TextView data;
		TextView mes;
		TextView conteudo;
	}
}
