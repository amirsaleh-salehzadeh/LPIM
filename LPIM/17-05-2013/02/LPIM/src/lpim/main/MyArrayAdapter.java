package lpim.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public MyArrayAdapter(Context context, String[] values) {
		super(context, R.layout.row_layout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		textView.setText(values[position]);
		// Change the icon for Windows and iPhone
		String s = values[position];
		if (s.startsWith("iPhone")) {
			imageView.setImageResource(R.drawable.blue_button);
		} else {
			imageView.setImageResource(R.drawable.green_button);
		}

		return rowView;
	}
}
