package lpim.main.manual;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ProductService extends IntentService {
	private static final String TAG = "ProductService";

	public ProductService() {
		super("ProductService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(
				"http://www.yr.no/place/South_Africa/Eastern_Cape/Port_Elizabeth/forecast.xml");// intent.getStringExtra("Location"));
		String XML = "";
		try {
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				XML += line;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FileOutputStream fos;
		try {
			fos = openFileOutput("weatherData", Context.MODE_PRIVATE);
			fos.write(XML.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intentDone = new Intent();
		intent.setAction("WeatherDataDone");
		sendBroadcast(intentDone);

	}

}
