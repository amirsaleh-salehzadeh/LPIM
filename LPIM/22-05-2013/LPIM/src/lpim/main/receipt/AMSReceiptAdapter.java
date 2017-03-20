package lpim.main.receipt;

import java.util.ArrayList;
import java.util.HashMap;
import lpim.main.R;
import lpim.main.tools.AMSTools;
import lpim.main.tools.ImageLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AMSReceiptAdapter extends BaseAdapter implements ListAdapter {
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	Context context;

	public AMSReceiptAdapter(Context c, Activity a,
			ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
		context = c;
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
			vi = inflater.inflate(R.layout.listview_order_row_list, null);
//		TextView receiptNo = (TextView) vi.findViewById(R.id.receipt_number); // title
//		TextView date = (TextView) vi.findViewById(R.id.date); // title
		TextView title = (TextView) vi.findViewById(R.id.title); // title
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb
		HashMap<String, String> list = new HashMap<String, String>();
		list = data.get(position);
		title.setText(list.get(OrderListActivity.KEY_PRODUCT_NAME));
//		receiptNo.setText(list.get(OrderListActivity.KEY_RCEIPT_NUMBER));
//		date.setText(list.get(OrderListActivity.KEY_DATE));
		imageLoader.DisplayImage(list.get(OrderListActivity.KEY_IMG),
				thumb_image);
//		final String id = list.get(OrderListActivity.KEY_ID);
//		final String name = list.get(OrderListActivity.KEY_PRODUCT_NAME);
		return vi;
	}

}