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
import lpim.main.tools.NVL;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MProductActivity extends Activity {
	String productName ="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_reminder_product);
		EditText editText = (EditText) findViewById(R.id.productSearchEditText);
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable i) {
				productName = i.toString();
				new ReadProduct().execute(productName);
				
			}
		});
		new ReadProduct().execute(productName);
		Toast.makeText(this, "Loading data", Toast.LENGTH_SHORT).show();
	}

	
	
	
	private ArrayList<String> listGenerator(ArrayList<ProductENT> ents) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < ents.size(); i++) {
			list.add(ents.get(i).getProductName());
		}
		return list;
	}

	
	
	
	private class ReadProduct extends
			AsyncTask<String, Void, ArrayList<ProductENT>> {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		@Override
		protected ArrayList<ProductENT> doInBackground(String... params) {
			final ArrayList<ProductENT> list = new ArrayList<ProductENT>();
			try {
				HttpGet httpGet = new HttpGet(
						"http://192.168.1.163:8082/LPIMWS/REST/WebService/GetProducts?productName="+params[0]);
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
					ProductENT ent = new ProductENT();
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					ent.setProductName(jsonObject.getString("productName"));
					ent.setProductID(NVL.getInt(jsonObject
							.getString("productID")));
					ent.setImg(jsonObject.getString("img"));
					list.add(ent);
					Log.i(MProductActivity.class.getName(),
							jsonObject.getString("productID"));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<ProductENT> result) {
			setListView(result);
		}
		
	}
	
	
	
	public void setListView(ArrayList<ProductENT> ents) {
		
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, listGenerator(ents));
		final ListView listview = (ListView) findViewById(R.id.productListView);
		listview.setAdapter(adapter);
	}
	
	
	
	public void alert(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	
	
	
	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				ArrayList<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

}
