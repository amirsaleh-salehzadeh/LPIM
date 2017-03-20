package lpim.main;


import lpim.main.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	int counter;
	Button ireminder, mreminder, ssetting, ereceipt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		counter = 0;
		ireminder = (Button) findViewById(R.id.iReminderBtn);
		mreminder = (Button) findViewById(R.id.mReminderBtn);
		ssetting =  (Button) findViewById(R.id.shoppingDayBtn);
		ereceipt = (Button) findViewById(R.id.enterReceiptBtn);
		
		ireminder.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("lpim.main.intelligent.IREMINDER");
				startActivity(intent);
			}
		});
		mreminder.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("lpim.main.manual.MREMINDER");
				startActivity(intent);
			}
		});
		ssetting.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("lpim.main.shopping.SHOPPINGSETTING");
				startActivity(intent);
			}
		});
		ereceipt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("lpim.main.receipt.ENTERRECEIPT");
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
