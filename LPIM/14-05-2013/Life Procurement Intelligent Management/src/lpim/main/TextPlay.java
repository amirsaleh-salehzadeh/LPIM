package lpim.main;

import java.util.Random;

import lpim.main.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TextPlay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);
		Button chkCmd = (Button)findViewById(R.id.bResults);
		final ToggleButton passTog = (ToggleButton)findViewById(R.id.tbPassword);
		final EditText input = (EditText) findViewById(R.id.etCommands);
		final TextView display = (TextView) findViewById(R.id.textView1);
		passTog.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (passTog.isChecked()) {
					input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				} else {
					input.setInputType(InputType.TYPE_CLASS_TEXT);
				}
			}
		});
		chkCmd.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String check = input.getText().toString();
				display.setText(check);
				if(check.contentEquals("left")){
					display.setGravity(Gravity.LEFT);
				}else if (check.contentEquals("center")) {
					display.setGravity(Gravity.CENTER);
				}else if (check.contentEquals("right")) {
					display.setGravity(Gravity.RIGHT);
				}else if (check.contentEquals("blue")) {
					display.setTextColor(Color.BLUE);
				}else if (check.contentEquals("WTF")) {
					Random random = new Random();
					display.setText("WTF!!!!!!");
					display.setTextSize(random.nextInt(40));
					display.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
					switch (random.nextInt(3)) {
					case 0:
						display.setGravity(Gravity.CENTER);
						break;
					case 1:
						display.setGravity(Gravity.RIGHT);
						break;
					case 2:
						display.setGravity(Gravity.LEFT);
						break;

					default:
						break;
					}
				}else {
					display.setText("Invalid");
					display.setGravity(Gravity.CENTER);
				}
			}
		});
	}

}
