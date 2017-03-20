package lpim.main.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

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
import lpim.main.manual.product.MProductActivity;
import lpim.main.receipt.OrderListActivity;

import android.R.bool;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class AMSTools {

	/**
	 * @param args
	 */
	public static boolean checkToggleButton(String string) {
		File root = new File(Environment.getExternalStorageDirectory(),
				"LPIMNotes");
		File file = new File(root, "productsPeriod.txt");
		String line = null;
		String p = "";
		BufferedReader buffreader;
		try {
			buffreader = new BufferedReader(new FileReader(file));
			while ((line = buffreader.readLine()) != null) {
				p += line;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] lines = p.split(";");
		for (int i = 0; i < lines.length; i++) {
			String[] q = lines[i].split(",");
			if (q[0].equalsIgnoreCase(string))
				return true;
		}
		return false;
	}

	private static String getProductPeriod(String string) {
		String res = "";
		File root = new File(Environment.getExternalStorageDirectory(),
				"LPIMNotes");
		File file = new File(root, "productsPeriod.txt");
		String line = null;
		String p = "";
		BufferedReader buffreader;
		try {
			buffreader = new BufferedReader(new FileReader(file));
			while ((line = buffreader.readLine()) != null) {
				p += line;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] lines = p.split(";");
		for (int i = 0; i < lines.length; i++) {
			String[] q = lines[i].split(",");
			if (q[0].equalsIgnoreCase(string))
				return q[1];
		}
		return res;
	}

	public static void dropFromList(String id) {
		File root = new File(Environment.getExternalStorageDirectory(),
				"LPIMNotes");
		File file = new File(root, "productsPeriod.txt");
		String line = null;
		String p = "";
		BufferedReader buffreader;
		try {
			buffreader = new BufferedReader(new FileReader(file));
			while ((line = buffreader.readLine()) != null) {
				if (line.split(",")[0].equalsIgnoreCase(id)) {
					line = "";
					p += line;
				} else {
					p += line + "\n";
				}
			}
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.print(p);

			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		file = new File(root, "Notification.txt");
		try {
			buffreader = new BufferedReader(new FileReader(file));
			while ((line = buffreader.readLine()) != null) {
				if (line.split(",")[0].equalsIgnoreCase(id)) {
					line = "";
					p += line;
				} else {
					p += line + "\n";
				}
			}
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.print(p);

			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean androidSynchFile(Context context, String x) {
		boolean res = false;
		File root = new File(Environment.getExternalStorageDirectory(),
				"LPIMNotes");
		if (!root.exists()) {
			root.mkdirs();
		}
		File file = new File(root, "productsPeriod.txt");
		String line = null;
		String p = "";
		try {
			BufferedReader buffreader = new BufferedReader(new FileReader(file));
			while ((line = buffreader.readLine()) != null) {
				// if(line.split(",")[0].equalsIgnoreCase(x.split(",")[0]))
				// line = "";
				p += line + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		FileWriter writer;
		try {
			if (!file.exists())
				file.createNewFile();
			String[] lines = p.split(";");
			boolean validator = true;
			for (int i = 0; i < lines.length; i++) {
				String[] newInput = x.split(",");
				String[] q = lines[i].split(",");
				if (q[0].equalsIgnoreCase(newInput[0])) {
					validator = false;
					return false;
				}
			}
			writer = new FileWriter(file, true);
			if (validator) {
				writer.append(x);
			}
			writer.flush();
			writer.close();
			res = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	public static String GETIP() {
		return "http://192.168.1.227:8082";
	}

	public static void synchTimeOfReceipt(String string, String date) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(AMSTools.GETIP()
					+ "/LPIMWS/REST/WebService/GetOrdersString?orderNo="
					+ string);
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

		String json = builder.toString();
		json = json.replace("\"", "");
		String[] strings = json.split(",");
		System.out.println(json);
		for (int i = 0; i < strings.length; i++) {
			if (checkToggleButton(strings[i])) {
				date = calculateDate(strings[i], date);
				synchNotification(strings[i], date);
			}
		}
	}

	private static String calculateDate(String string, String date) {
		String period = getProductPeriod(string);
		date = date.replace("-", "");
		Date tradeDate;
		try {
			tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(date);
			Calendar c = Calendar.getInstance(); 
			c.setTime(tradeDate); 
			c.add(Calendar.DATE, Integer.parseInt(period));
			tradeDate = c.getTime();
			date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(tradeDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(date);
		return date;
	}

	private static void synchNotification(String string, String date) {
		String x = string + "," + date + ";\n";
		File root = new File(Environment.getExternalStorageDirectory(),
				"LPIMNotes");
		if (!root.exists()) {
			root.mkdirs();
		}
		File file = new File(root, "Notification.txt");
		String line = null;
		String p = "";
		try {
			BufferedReader buffreader = new BufferedReader(new FileReader(file));
			while ((line = buffreader.readLine()) != null) {
				// if(line.split(",")[0].equalsIgnoreCase(x.split(",")[0]))
				// line = "";
				p += line + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileWriter writer;
		try {
			if (!file.exists())
				file.createNewFile();
			String[] lines = p.split(";");
			boolean validator = true;
			for (int i = 0; i < lines.length; i++) {
				String[] newInput = x.split(",");
				String[] q = lines[i].split(",");
				if (q[0].equalsIgnoreCase(newInput[0])) {
					validator = false;
				}
			}
			writer = new FileWriter(file, true);
			if (validator) {
				writer.append(x);
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
