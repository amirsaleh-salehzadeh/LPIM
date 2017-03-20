package lpim.main.receipt;

import lpim.main.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EnterReceipt extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_reminder);
		TextView textView = (TextView)findViewById(R.id.mReminderTxtView);
		textView.setText("Enter receipt");
	}

	
}
