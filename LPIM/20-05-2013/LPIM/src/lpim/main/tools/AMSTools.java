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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import lpim.main.R;
import lpim.main.manual.product.MProductActivity;

import android.R.bool;
import android.content.Context;
import android.os.Environment;

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

	public static Object invoke(Object obj, String field)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		String field2invoke = field.substring(0, 1).toUpperCase()
				+ field.substring(1);
		Object v = obj.getClass().getMethod("get" + field2invoke, null)
				.invoke(obj, null);
		return v;
	}

	public static Object invokeMethod(Object obj, String method)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Object v = obj.getClass().getMethod(method, null).invoke(obj, null);
		return v;
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
				p += line;
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
		return "http://192.168.1.163:8082";
	}

	public static void printObject(Object obj, boolean drillListFields,
			boolean drillObjectFields, boolean showFullName) {
		StringBuffer sb = new StringBuffer();
		printObject(obj, sb, 0, drillListFields, drillObjectFields,
				showFullName);
		System.out.println(sb.toString() + "\n");
	}

	public static void printObject(Object obj) {
		printObject(obj, true, true, true);
	}

	private static void printObject(Object obj, StringBuffer sb, int level,
			boolean drillListFields, boolean drillObjectFields,
			boolean showFullName) {
		String tab = "";
		for (int m = 0; m < level; m++)
			tab += "\t";
		if (level > 0)
			tab += level + ".";
		if (obj == null) {
			sb.append("{null}");
			return;
		}
		if (drillListFields && obj instanceof List) {
			List l = (List) obj;
			sb.append("\n>>>" + obj.getClass().getSimpleName() + ";");
			for (int j = 0; j < l.size(); j++) {
				sb.append("\n>>>");
				printObject(l.get(j), sb, level + 1, drillListFields,
						drillObjectFields, showFullName);
			}
			sb.append("\n<<<");
		}
		Class cls = obj.getClass();
		ArrayList<Field> flds = new ArrayList<Field>();
		Field[] fs;
		while (cls != null) {
			fs = cls.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				flds.add(fs[i]);
			}
			cls = cls.getSuperclass();
		}
		sb.append(tab + obj.getClass().getSimpleName() + ":[");
		for (int i = 0; i < flds.size(); i++) {
			String firstChar = flds.get(i).getName().substring(0, 1)
					.toUpperCase();
			String otherChars = flds.get(i).getName().substring(1);
			String fld = firstChar + otherChars;
			Object value = "";
			try {
				value = invoke(obj, fld);
			} catch (Exception e) {
				value = "(err)";
				// e.printStackTrace();
			}
			if (value == null) {
				sb.append(flds.get(i).getName() + "={null};");
			} else if (drillListFields && value instanceof List) {
				List l = (List) value;
				sb.append("\n>>>" + tab + flds.get(i).getName() + ":"
						+ value.getClass().getSimpleName() + ";");
				for (int j = 0; j < l.size(); j++) {
					sb.append("\n>>>");
					printObject(l.get(j), sb, level + 1, drillListFields,
							drillObjectFields, showFullName);
				}
				sb.append("\n<<<");
			} else if (drillListFields && value instanceof Object[]) {
				Object[] l = (Object[]) value;
				sb.append("\n>>" + tab + flds.get(i).getName() + ":"
						+ value.getClass().getSimpleName() + ";");
				sb.append("[");
				for (int j = 0; j < l.length; j++) {
					// printObject(l[j],sb,level+1,drillListFields,drillObjectFields,showFullName);
					sb.append(l[j].toString() + ";");
				}
				sb.append("]\n<<");
			} else if (drillObjectFields
					&& (value instanceof Integer || value instanceof Double)) {
				sb.append(flds.get(i).getName() + "=" + value + ";");
			} else if (drillObjectFields && !(value instanceof String)
					&& value instanceof Object) {
				String pkg = value.getClass().getPackage().getName();
				// if(pkg.indexOf("aip")>=0){
				sb.append("\n>");
				printObject(value, sb, level + 1, drillListFields,
						drillObjectFields, showFullName);
				sb.append("\n>\t" + tab + (level + 1) + ".");
				// }else{
				// sb.append(flds.get(i).getName()+"="+value+";");
				// }
			} else {
				sb.append(flds.get(i).getName() + "=" + value + ";");
			}
		}
		sb.append("]:fullName=" + obj.toString());
	}

}
