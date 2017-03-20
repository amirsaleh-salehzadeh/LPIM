package lpim.main.manual.product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import lpim.main.R;
import lpim.main.tools.AMSTools;
import lpim.main.tools.ImageLoader;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MProductActivity extends Activity {
	String productName = "";
	public static final String KEY_TITLE = "title";
	public static final String KEY_ID = "product_id";
	public static final String KEY_THUMB_URL = "thumb_url";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_reminder_product);
		Toast.makeText(this, "Loading data", Toast.LENGTH_LONG).show();

		EditText editText = (EditText) findViewById(R.id.productSearchEditText);
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence i, int start, int before,
					int count) {
				productName = i.toString();
				new ReadProduct().execute(productName);

			}

			@Override
			public void beforeTextChanged(CharSequence i, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable i) {
				new ReadProduct().execute(productName);
			}
		});
		editText.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					in.hideSoftInputFromWindow(
							v.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					return true;
				}
				return false;
			}
		});
		new ReadProduct().execute(productName);
	}

	public class ReadProduct extends
			AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				String... params) {
			final ArrayList<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();

			try {
				HttpGet httpGet = new HttpGet(AMSTools.GETIP()
						+ "/LPIMWS/REST/WebService/GetProducts?productName="
						+ params[0]);
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e(MProductActivity.class.toString(),
							"Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				JSONArray jsonArray = new JSONArray(builder.toString());
				Log.i(MProductActivity.class.getName(), "Number of entries "
						+ jsonArray.length());
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> ent = new HashMap<String, String>();
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					ent.put(KEY_TITLE, jsonObject.getString("productName"));
					ent.put(KEY_ID, jsonObject.getString("productID"));
					ent.put(KEY_THUMB_URL, AMSTools.GETIP() + "/LPIMWS/"
							+ jsonObject.getString("img"));
					lists.add(ent);
					Log.i(MProductActivity.class.getName(),
							jsonObject.getString("productID"));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lists;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			setListView(result);
		}

	}

	public void setListView(ArrayList<HashMap<String, String>> result) {
		AMSProductAdapter adapter = new AMSProductAdapter(
				MProductActivity.this, this, result);
		final ListView listview = (ListView) findViewById(R.id.productListView);
		listview.setAdapter(adapter);
	}

}
