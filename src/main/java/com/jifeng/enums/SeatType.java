package com.jifeng.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 1.4页面编辑的位置类别
 * @author sushengli
 * 2015年5月22日下午2:19:56
 */
public enum SeatType {
	
	BANNER(1,"banner"),
	CATEGORY(2,"商家分类"),
	BUSINESS(3,"商家"),
	ACTIVITY(4,"活动");

	private Integer code;
	
	private String value;
	
	SeatType(Integer code,String value){
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
		SeatType[] vs = SeatType.values();
		for(SeatType v : vs){
			map.put(v.getCode(), v.getValue());
		}
		return map;
	}
	
}
