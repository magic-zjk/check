package com.jifeng.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 * @author sushengli
 * 2015年5月22日下午2:19:56
 */
public enum StatusType {
	
	NORMAL(0,"正常"),
	OBSOLETE(1,"已废弃"),
	LOCKED(2,"已被锁");

	private Integer code;
	
	private String value;
	
	StatusType(Integer code,String value){
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
		StatusType[] vs = StatusType.values();
		for(StatusType v : vs){
			map.put(v.getCode(), v.getValue());
		}
		return map;
	}
	
}
