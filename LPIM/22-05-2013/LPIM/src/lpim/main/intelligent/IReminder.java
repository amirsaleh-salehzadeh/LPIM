package lpim.main.intelligent;

import lpim.main.R;
import lpim.main.manual.product.MProductActivity;
import lpim.main.tools.AMSTools;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
		Toast.makeText(this, "app started Ireminder", Toast.LENGTH_SHORT)
				.show();
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				IReminder.this);

		// Setting Dialog Title
		alertDialog.setTitle("Save File...");

		// Setting Dialog Message
		alertDialog.setMessage("Do you want to save this file?");

		// Setting Icon to Dialog
//		alertDialog.setIcon(R.drawable.save);

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// User pressed YES button. Write Logic Here
						Toast.makeText(getApplicationContext(),
								"You clicked on YES", Toast.LENGTH_SHORT)
								.show();
					}
				});

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// User pressed No button. Write Logic Here
						Toast.makeText(getApplicationContext(),
								"You clicked on NO", Toast.LENGTH_SHORT).show();
					}
				});

		// Setting Netural "Cancel" Button
		alertDialog.setNeutralButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// User pressed Cancel button. Write Logic Here
						Toast.makeText(getApplicationContext(),
								"You clicked on Cancel", Toast.LENGTH_SHORT)
								.show();
					}
				});

		// Showing Alert Message
		alertDialog.show();

	}

}
