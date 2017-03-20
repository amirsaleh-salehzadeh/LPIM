package lpim.main.notification;

import java.util.ArrayList;
import java.util.HashMap;

import lpim.main.MainActivity;
import lpim.main.R;
import lpim.main.tools.ImageLoader;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

public class NotificationSetting {
	private Activity activity;
	public ImageLoader imageLoader;
	Context context;
	public NotificationSetting(Context c, Class cl) {
		imageLoader = new ImageLoader(activity.getApplicationContext());
		context = c;
		NotificationManager notificationManager = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(c, cl);
		Notification notification = new Notification(R.drawable.ic_launcher,"helllllo",System.currentTimeMillis());
		PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, intent, 0);
		notification.setLatestEventInfo(c, "titleeeeeeeeeee", "contennnnnnnnnnnt", pendingIntent);
		notificationManager.notify(1, notification);
	}
}
