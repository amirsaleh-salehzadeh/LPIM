package lpim.main.intelligent;



import lpim.main.R;
import lpim.main.tools.AMSTools;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class IReminder extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.i_reminder);
		TextView textView = (TextView) findViewById(R.id.iReminderTxtView);
		textView.setText("Sorry I gonna make this service available in few days");
		Toast.makeText(this, "app started", Toast.LENGTH_SHORT).show();

	}

}
