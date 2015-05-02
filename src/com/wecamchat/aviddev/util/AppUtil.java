package com.wecamchat.aviddev.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wecamchat.aviddev.api.io.DBClass;
import com.wecamchat.aviddev.api.io.User;
import com.wecamchat.aviddev.model.bo.Profile;
import com.wecamchat.aviddev.model.bo.Unique;

/**
 * Created by tsingh on 21/1/15.
 */
public class AppUtil {

	static HashMap<String, String> varTypeMapping;
	static {

		varTypeMapping = new HashMap<String, String>();
		varTypeMapping.put("Boolean", "INTEGER");
		varTypeMapping.put("String", "TEXT");
		varTypeMapping.put("Integer", "INTEGER");
		varTypeMapping.put("Double", "REAL");
	}

	public static <T> T parseJson(String json, Class<T> tClass) {
		return new Gson().fromJson(json, tClass);
	}

	public static <T> T parseJson(JsonElement result, Class<T> tClass) {
		return new Gson().fromJson(result, tClass);
	}

	public static JsonObject parseJson(String response) {
		JsonObject jo = null;
		JsonElement e = null;
		return new JsonParser().parse(response).getAsJsonObject();
	}

	public static String getJson(Object profile) {
		return new Gson().toJson(profile);
	}

	public static ContentValues getContentValues(DBClass object) {
		ContentValues cv = new ContentValues();
		Class<? extends DBClass> cl = object.getClass();
		Field[] fs = cl.getDeclaredFields();
		try {
			for (Field field : fs) {
				String name = field.getName();
				Method method = cl.getMethod("get"
						+ Character.toUpperCase(name.charAt(0))
						+ name.substring(1));
				Object value = method.invoke(object);
				if (TextUtils.equals(field.getType().getSimpleName(), "String")) {
					cv.put(String.valueOf(field.getName()), (String) value);
					Log.d("nishant",
							"get string value from cv "
									+ (String) cv.get(String.valueOf(field
											.getName())));
				} else if (TextUtils.equals(field.getType().getSimpleName(),
						"Integer")) {
					cv.put(String.valueOf(field.getName()), (Integer) value);
					Log.d("nishant", "get integer value from cv "
							+ (Integer) cv.get(String.valueOf(field.getName())));
				} else if (TextUtils.equals(field.getType().getSimpleName(),
						"Long")) {
					cv.put(String.valueOf(field.getName()), (Long) value);
					Log.d("nishant",
							"get value from cv "
									+ (Long) cv.get(String.valueOf(field
											.getName())));
				} else if (TextUtils.equals(field.getType().getSimpleName(),
						"Float")) {
					cv.put(String.valueOf(field.getName()), (Float) value);
					Log.d("nishant",
							"get  float value from cv "
									+ (Float) cv.get(String.valueOf(field
											.getName())));
				} else if (TextUtils.equals(field.getType().getSimpleName(),
						"Double")) {
					cv.put(String.valueOf(field.getName()), (Double) value);
					Log.d("nishant",
							"get value from cv "
									+ (Double) cv.get(String.valueOf(field
											.getName())));
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return cv;
	}

	public <T extends DBClass> T getFromContentValue(ContentValues cv,
			Class<T> clazz) {
		try {
			Constructor<T> cons = clazz.getConstructor();
			T obj = cons.newInstance();
			Set<String> fields = cv.keySet();
			for (String field : fields) {

			}
		} catch (NoSuchMethodException e) {
			// Logger.ERROR(tag, msg, e.getCause())
			return null;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String getCreateTableStatement(Class<User> clazz) {
		String tableName = clazz.getSimpleName();
		Field[] fs = clazz.getDeclaredFields();
		List<TableFieldItem> fieldNames = new ArrayList<TableFieldItem>();
		for (Field f : fs) {
			TableFieldItem item = new TableFieldItem();
			item.fieldName = f.getName();
			item.isPrimitive = f.getType().isPrimitive();
			item.variableType = varTypeMapping.get(f.getType().getSimpleName()) != null ? varTypeMapping
					.get(f.getType().getSimpleName()) : "TEXT";
			item.isUnique = f.getAnnotation(Unique.class) != null;
			item.isTransient = Modifier.isTransient(f.getModifiers());
			fieldNames.add(item);
		}
		String string = "Create table " + tableName + " (" + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,";

		string = addFieldsInTable(string, fieldNames);

		string = string + " ) ;";

		Log.d("nishant", "final string is " + string);
		return null;
	}

	private static String addFieldsInTable(String string,
			List<TableFieldItem> fieldNames) {
		for (int i = 0; i < fieldNames.size(); i++) {
			TableFieldItem item = fieldNames.get(i);
			if (!item.isTransient) {
				string = string
						+ " "
						+ item.fieldName
						+ " "
						+ item.variableType
						+ " "
						+ String.valueOf(item.isUnique ? "UNIQUE" : "")
						+ String.valueOf(i <= (fieldNames.size() - 2) ? " , "
								: "  ");
			}
		}
		return string;
	}

	static class TableFieldItem {
		String fieldName;
		boolean isUnique;
		boolean isPrimitive;
		String variableType;
		boolean isTransient;
	}

}
