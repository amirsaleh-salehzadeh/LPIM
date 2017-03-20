package lpim.main.manual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import lpim.main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MReminder extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_reminder);
		TextView textView = (TextView) findViewById(R.id.mReminderTxtView);
		textView.setText("Welcome to manual reminder");
		
		Button product = (Button) findViewById(R.id.mProductBtn);
		Button category = (Button) findViewById(R.id.mCategoryBtn);
		
		product.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("lpim.main.manual.MPRODUCT");
				startActivity(intent);
			}
		});
		category.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("lpim.main.manual.MCATEGORY");
				startActivity(intent);
			}
		});
		
	}

}
