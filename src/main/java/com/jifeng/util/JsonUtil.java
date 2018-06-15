package com.jifeng.util;

import com.google.gson.Gson;

public class JsonUtil {

	/**
	 * Objec转化为json
	 * 
	 * @param obj
	 * @return
	 */
	public static String convertObjectToJson(Object obj) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(obj);
		return jsonStr;
	}

	/**
	 * jso转化为Object
	 * 
	 * @param <T>
	 * @param json
	 * @param convertClass
	 * @return
	 */
	public static <T> T convertJsonToObject(String json, Class<T> convertClass) {
		T obj = null;
		Gson gson = new Gson();
		obj = gson.fromJson(json, convertClass);
		return obj;
	}
}
