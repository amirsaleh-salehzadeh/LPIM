package lpim.main.intelligent;

import lpim.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class IReminder extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.i_reminder);
		TextView textView = (TextView)findViewById(R.id.iReminderTxtView);
		textView.setText("Sorry I gonna make this service available in few days");
	}

	
}
