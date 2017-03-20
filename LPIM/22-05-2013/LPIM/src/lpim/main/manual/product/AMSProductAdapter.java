package lpim.main.manual.product;

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

public class AMSProductAdapter extends BaseAdapter implements ListAdapter {
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	Context context;

	public AMSProductAdapter(Context c, Activity a,
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
			vi = inflater.inflate(R.layout.listview_product_row_list, null);

		TextView title = (TextView) vi.findViewById(R.id.title); // title
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb
																				// image
		ImageView imageView = (ImageView) vi
				.findViewById(R.id.product_selection_status);
		HashMap<String, String> list = new HashMap<String, String>();
		list = data.get(position);
		title.setText(list.get(MProductActivity.KEY_TITLE));
		if (AMSTools.checkToggleButton(list.get(MProductActivity.KEY_ID)))
			imageView.setImageResource(R.drawable.yes);
		else
			imageView.setImageResource(R.drawable.no);
		imageLoader.DisplayImage(list.get(MProductActivity.KEY_THUMB_URL),
				thumb_image);
		final String id = list.get(MProductActivity.KEY_ID);
		final String name = list.get(MProductActivity.KEY_TITLE);
		OnClickListener itemListener = new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(context, (String) " : " + id + name,
						Toast.LENGTH_SHORT).show();
				inflater = (LayoutInflater) context
						.getSystemService(context.LAYOUT_INFLATER_SERVICE);
				if (AMSTools.checkToggleButton(id)) {
					AMSTools.dropFromList(id);
					// Intent intent = new
					// Intent("lpim.main.manual.product.MPRODUCTACTIVITY");
					// context.startActivity(intent);
					notifyDataSetChanged();
				} else
					showDialogBox(id, name);
			}
		};
		vi.setOnClickListener(itemListener);
		return vi;
	}

	private void showDialogBox(String id, String name) {
		InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(
				null,
				InputMethodManager.HIDE_NOT_ALWAYS);
		View layout = inflater.inflate(R.layout.product_selection_dialog_box,
				null);
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
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(layout);
		builder.setTitle("Set product usage period . . . ");
		// Setting Dialog Message
		builder.setPositiveButton("Save",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						AMSTools.androidSynchFile(context, ids.getText() + ","
								+ nameBox.getText() + ";\n");
						Toast.makeText(
								context.getApplicationContext(),
								"You clicked on YES ID " + nameBox.getText()
										+ ">>>" + ids.getText(),
								Toast.LENGTH_SHORT).show();
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(context.getApplicationContext(),
								"You clicked on Cancel", Toast.LENGTH_SHORT)
								.show();
						dialog.dismiss();
					}
				});
		builder.show();
	}
}