package in.wptrafficanalyzer.listviewtogglebutton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	String[] countries = new String[] { "India", "Pakistan", "Sri Lanka",
			"China", "Bangladesh", "Nepal", "Afghanistan", "North Korea",
			"South Korea", "Japan" };

	// Array of booleans to store toggle button status
	public boolean[] status = { true, false, false, false, false, false, false,
			false, false, false };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/** Restore from the previous state if exists */
		if (savedInstanceState != null) {
			status = savedInstanceState.getBooleanArray("status");
		}

		ListView lvCountries = (ListView) findViewById(R.id.lv_countries);
		OnItemClickListener itemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> lv, View item, int position,
					long id) {

				ListView lView = (ListView) lv;

				SimpleAdapter adapter = (SimpleAdapter) lView.getAdapter();

				HashMap<String, Object> hm = (HashMap) adapter
						.getItem(position);

				/** The clicked Item in the ListView */
				RelativeLayout rLayout = (RelativeLayout) item;

				/** Getting the toggle button corresponding to the clicked item */
				ToggleButton tgl = (ToggleButton) rLayout.getChildAt(1);

				String strStatus = "";
				if (tgl.isChecked()) {
					tgl.setChecked(false);
					strStatus = "Off";
					status[position] = false;
				} else {
					tgl.setChecked(true);
					strStatus = "On";
					status[position] = true;
				}

				Toast.makeText(getBaseContext(),
						(String) hm.get("txt") + " : " + strStatus,
						Toast.LENGTH_SHORT).show();

			}
		};

		lvCountries.setOnItemClickListener(itemClickListener);

		// Each row in the list stores country name and its status
		List<HashMap<String, Object>> aList = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("txt", countries[i]);
			hm.put("stat", status[i]);

			aList.add(hm);

		}

		// Keys used in Hashmap
		String[] from = { "txt", "stat" };

		// Ids of views in listview_layout
		int[] to = { R.id.tv_item, R.id.tgl_status };

		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList,
				R.layout.lv_layout, from, to);

		lvCountries.setAdapter(adapter);

	}

	/**
	 * Saving the current state of the activity for configuration changes [
	 * Portrait <=> Landscape ]
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBooleanArray("status", status);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
