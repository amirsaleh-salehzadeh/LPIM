package lpim.main;


import lpim.main.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Welcome extends Activity{

	MediaPlayer mediaPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		mediaPlayer = MediaPlayer.create(Welcome.this, R.raw.click1);
		mediaPlayer.start();
		Thread timer = new Thread(){
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					Intent intent = new Intent("lpim.main.MAINACTIVITY");
					startActivity(intent);
				}
			}
		};
		timer.start();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mediaPlayer.release();
		finish();
	}

	

}
