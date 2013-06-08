package br.unitau.calendariodamulher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Dashboard extends Activity {

	private ImageButton btn_calendar;
	private ImageButton btn_settings;
	private ImageButton btn_about;
	private ImageButton btn_help;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash);
		btn_calendar = (ImageButton) findViewById(R.id.btn_calendar);
		btn_settings = (ImageButton) findViewById(R.id.btn_settings);
		btn_help = (ImageButton) findViewById(R.id.btn_help);
		btn_about = (ImageButton) findViewById(R.id.btn_about);
		btn_calendar.setOnClickListener(onClickCalendar);
		btn_settings.setOnClickListener(onClickSettings);
		btn_about.setOnClickListener(onClickAbout);
		btn_help.setOnClickListener(onClickHelp);

	}

	public OnClickListener onClickCalendar = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			startActivity(new Intent(Dashboard.this, Calendar.class));
		}
	};
	public OnClickListener onClickSettings = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			startActivity(new Intent(Dashboard.this, Settings.class));
		}
	};
	public OnClickListener onClickAbout = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			startActivity(new Intent(Dashboard.this, Developer.class));
		}
	};
	public OnClickListener onClickHelp = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			startActivity(new Intent(Dashboard.this, Help.class));
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

}
