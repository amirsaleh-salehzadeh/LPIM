package lpim.main.receipt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lpim.main.R;
import lpim.main.tools.AMSTools;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderListActivity extends Activity {

	String productName = "";
	public static String KEY_RCEIPT_NUMBER = "receipt_no";
	public static String KEY_DATE = "date";
	public static final String KEY_PRODUCT_NAME = "product_name";
	public static final String KEY_ID = "product_id";
	public static final String KEY_IMG = "product_image";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list);
		Bundle b = getIntent().getExtras();
		final String value = b.getString("key");
		// new ReadReceipt().execute(value);
		try {

			ArrayList<HashMap<String, String>> list = new ReadReceipt()
					.execute(value).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView receiptNo = (TextView) findViewById(R.id.receipt_number); // title
		TextView date = (TextView) findViewById(R.id.date); // title
		receiptNo.setText(KEY_RCEIPT_NUMBER);
		date.setText(OrderListActivity.KEY_DATE);
		// final EditText editText = (EditText)
		// findViewById(R.id.receipt_id_input);
		// editText.setVisibility(View.GONE);
		Button button = (Button) findViewById(R.id.notify_me);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AMSTools.synchTimeOfReceipt(KEY_RCEIPT_NUMBER, KEY_DATE);
				Intent intent = new Intent("lpim.main.MAINACTIVITY");
				startActivity(intent);
				Toast.makeText(getBaseContext(),
						"Notification of your grocery activated",
						Toast.LENGTH_LONG);

			}
		});

	}

	public class ReadReceipt extends
			AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				String... params) {
			final ArrayList<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();

			try {
				HttpGet httpGet = new HttpGet(AMSTools.GETIP()
						+ "/LPIMWS/REST/WebService/GetOrder?orderNo="
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
					Log.e(OrderListActivity.class.toString(),
							"Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				String json = builder.toString();
				JSONObject jsonObject = new JSONObject(new String(json));
				JSONArray jsonArray = new JSONArray(
						jsonObject.getString("ents"));
				System.out.println();
				Log.i(OrderListActivity.class.getName(), "Number of entries "
						+ jsonArray.length());
				HashMap<String, String> ent = new HashMap<String, String>();
				ent.put(KEY_RCEIPT_NUMBER, jsonObject.getString("mainOrderId"));
				KEY_RCEIPT_NUMBER = jsonObject.getString("mainOrderId");
				KEY_DATE = jsonObject.getString("orderDate");
				ent.put(KEY_DATE, jsonObject.getString("orderDate"));
				for (int i = 0; i < jsonArray.length(); i++) {
					ent = new HashMap<String, String>();
					JSONObject object = jsonArray.getJSONObject(i);

					ent.put(KEY_ID, object.getString("productId"));
					ent.put(KEY_PRODUCT_NAME, object.getString("productName"));
					ent.put(KEY_IMG,
							AMSTools.GETIP() + "/LPIMWS/"
									+ object.getString("img"));
					lists.add(ent);
				}
			} catch (JSONException e) {
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
		AMSReceiptAdapter adapter = new AMSReceiptAdapter(
				OrderListActivity.this, this, result);
		final ListView listview = (ListView) findViewById(R.id.order_list_view);
		listview.setAdapter(adapter);
	}

}
