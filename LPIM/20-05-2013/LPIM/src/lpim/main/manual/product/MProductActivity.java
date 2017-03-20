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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

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

			}
		});

		new ReadProduct().execute(productName);
	}

	private class ReadProduct extends
			AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				String... params) {
			final ArrayList<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();

			try {
				HttpGet httpGet = new HttpGet(
						AMSTools.GETIP()+"/LPIMWS/REST/WebService/GetProducts?productName="
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
					ent.put(KEY_THUMB_URL, AMSTools.GETIP()+"/LPIMWS/"
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

	public class AMSAdapter extends BaseAdapter {

		public AMSAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
			activity = a;
			data = d;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			imageLoader = new ImageLoader(activity.getApplicationContext());
		}

		public int getCount() {
			return data.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.listview_row_list, null);

			TextView title = (TextView) vi.findViewById(R.id.title); // title
			ImageView thumb_image = (ImageView) vi
					.findViewById(R.id.list_image); // thumb image
			ToggleButton toggleButton = (ToggleButton) vi
					.findViewById(R.id.productToggleButton);
			HashMap<String, String> list = new HashMap<String, String>();
			list = data.get(position);

			// Setting all values in listview
			title.setText(list.get(MProductActivity.KEY_TITLE));
			toggleButton.setChecked(AMSTools.checkToggleButton(list.get(MProductActivity.KEY_ID)));
			toggleButton.setTag(list.get(MProductActivity.KEY_ID));
			imageLoader.DisplayImage(list.get(MProductActivity.KEY_THUMB_URL),
					thumb_image);
			final String id = list.get(MProductActivity.KEY_ID);
			final String name = list.get(MProductActivity.KEY_TITLE);
			OnClickListener itemListener = new OnClickListener() {
				public void onClick(View arg0) {
					Toast.makeText(MProductActivity.this, (String) " : " + id + name,
							Toast.LENGTH_SHORT).show();
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(
							R.layout.product_selection_dialog_box, null);
					final EditText nameBox = (EditText) layout
							.findViewById(R.id.product_edit_text_days);
					final EditText ids = (EditText) layout
							.findViewById(R.id.product_edit_text_id);
					ids.setText(id);
					ids.setVisibility(View.GONE);
					TextView productTextView = (TextView) layout
							.findViewById(R.id.product_name_dialog_box);
					productTextView.setTextColor(Color.RED);
					productTextView.setTextSize((float) 20.0);
					productTextView.setTypeface(null, Typeface.BOLD);
					productTextView.setText(name);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MProductActivity.this);
					builder.setView(layout);
					builder.setTitle("Set product usage period . . . ");
					// Setting Dialog Message
					builder.setPositiveButton("Save",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									AMSTools.androidSynchFile(MProductActivity.this,ids.getText()+","+ nameBox.getText() +";\n");
									Toast.makeText(getApplicationContext(),
											"You clicked on YES ID "+nameBox.getText()+">>>"+ids.getText(), Toast.LENGTH_SHORT)
											.show();
								}
							});
					builder.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(getApplicationContext(),
											"You clicked on Cancel", Toast.LENGTH_SHORT)
											.show();
									dialog.dismiss();
								}
							});
					builder.show();
				}
			};
			vi.setOnClickListener(itemListener);
			return vi;
		}
	}

	public void setListView(ArrayList<HashMap<String, String>> result) {
		AMSAdapter adapter = new AMSAdapter(this, result);
		final ListView listview = (ListView) findViewById(R.id.productListView);
		listview.setAdapter(adapter);
	}

	public void alert(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

}
