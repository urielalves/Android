package br.unitau.calendariodamulher;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity implements Runnable, OnClickListener {

	private static int FRAME_TIME = 3000;
	private static int TOTAL_TIME = 2 * FRAME_TIME;
	private ViewFlipper viewFlipper;
	private Animation inAnimation;
	private Animation outAnimation;
	private Handler handler = new Handler();
	private boolean started;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
		viewFlipper.setOnClickListener(this);

		inAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_alpha_in);
		outAnimation = AnimationUtils
				.loadAnimation(this, R.anim.slide_alpha_out);
		viewFlipper.setInAnimation(inAnimation);
		viewFlipper.setOutAnimation(outAnimation);
		viewFlipper.setFlipInterval(FRAME_TIME);
		viewFlipper.startFlipping();

		handler.postDelayed(this, TOTAL_TIME);
	}	

	public void run(){
		iniciarPrograma();
	}

	@Override
	public void onClick(View v) {
		iniciarPrograma();
	}
	
	private synchronized void iniciarPrograma() {

		if (!started) {
			started = true;
			viewFlipper.stopFlipping();
			Intent i = new Intent(MainActivity.this, Dashboard.class);
			startActivity(i);
			setResult(RESULT_OK);
			finish();
		}
	}
}
