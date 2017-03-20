package lpim.main.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import lpim.main.R;
import lpim.main.manual.product.MProductActivity;
 
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class AMSsAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
 
    public AMSsAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
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
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.listview_row_list, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        ToggleButton toggleButton = (ToggleButton) vi.findViewById(R.id.productToggleButton);
        HashMap<String, String> list = new HashMap<String, String>();
        list = data.get(position);
 
        // Setting all values in listview
        title.setText(list.get(MProductActivity.KEY_TITLE));
        toggleButton.setChecked(true);
        toggleButton.setTag(list.get(MProductActivity.KEY_ID));
        imageLoader.DisplayImage(list.get(MProductActivity.KEY_THUMB_URL), thumb_image);
        
        OnClickListener itemListener=new OnClickListener(){
            public void onClick(View arg0) {
//            	Toast.makeText(this,
//            			(String) " : ",
//						Toast.LENGTH_SHORT).show();
            
            }           
        };
        vi.setOnClickListener(itemListener);
        return vi;
    }
}