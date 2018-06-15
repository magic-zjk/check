package com.jifeng.util;




import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtilString {
	//字符串转换json
	public static JSONObject paseJson(String text){
		return JSONObject.parseObject(text);
	}
	
	//jsonarray
	public static JSONArray paseArray(String text){
		return JSONArray.parseArray(text);
	}
	
}
