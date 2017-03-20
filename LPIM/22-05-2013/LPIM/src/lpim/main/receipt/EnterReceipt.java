package lpim.main.receipt;

import lpim.main.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class EnterReceipt extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_receipt);

		final EditText editText = (EditText) findViewById(R.id.receipt_input);
		Button button = (Button) findViewById(R.id.receipt_submit_button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				in.hideSoftInputFromWindow(v.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				Bundle b = new Bundle();
				b.putString("key", editText.getText().toString()); // Your id
				Intent intent = new Intent(EnterReceipt.this,
						OrderListActivity.class);
				intent.putExtras(b); // Put your id to your next Intent
				startActivity(intent);
				finish();
			}
		});

	}

}
