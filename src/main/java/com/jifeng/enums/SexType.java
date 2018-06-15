package com.jifeng.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 性别枚举
 * @author sushengli
 * 2015年5月22日下午2:19:56
 */
public enum SexType {
	
	NORMAL(0,"女"),
	OBSOLETE(1,"男");

	private Integer code;
	
	private String value;
	
	SexType(Integer code,String value){
		this.code = code;
		this.value = value;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static Map<Integer,String> getMap(){
		Map<Integer,String> map = new HashMap<Integer,String>();
		SexType[] vs = SexType.values();
		for(SexType v : vs){
			map.put(v.getCode(), v.getValue());
		}
		return map;
	}
	
}
