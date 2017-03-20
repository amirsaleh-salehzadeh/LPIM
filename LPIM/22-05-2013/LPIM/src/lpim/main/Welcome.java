package lpim.main;


import java.util.Calendar;

import lpim.main.R;
import lpim.main.intelligent.IReminder;
import lpim.main.manual.product.MProductActivity;
import lpim.main.notification.NotificationActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(this, IReminder.class);
		Notification notification = new Notification(R.drawable.ic_launcher,"helllllo",System.currentTimeMillis());
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		notification.setLatestEventInfo(this, "titleeeeeeeeeee", "contennnnnnnnnnnt", pendingIntent);
		notificationManager.notify(1, notification);
		
		
		
		Calendar myAlarmDate = Calendar.getInstance();
		myAlarmDate.setTimeInMillis(System.currentTimeMillis());
		myAlarmDate.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		myAlarmDate.set(Calendar.HOUR_OF_DAY, 16);
		myAlarmDate.set(Calendar.MINUTE, 55);
		myAlarmDate.set(Calendar.SECOND, 00);
//		myAlarmDate.set(2013, 5, 22, 16, 33, 00);
		AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

		Intent _myIntent = new Intent(this, IReminder.class);
		_myIntent.putExtra("MyMessage","HERE I AM PASSING THEPERTICULAR MESSAGE WHICH SHOULD BE SHOW ON RECEIVER OF ALARM");
		pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(),pendingIntent);
		
		
		Thread timer = new Thread(){
			public void run() {
				try {
					sleep(5000);
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
