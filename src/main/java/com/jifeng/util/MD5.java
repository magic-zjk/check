package com.jifeng.util;

import java.security.MessageDigest;

/*
 * MD5 算法
 */
public class MD5 {

	public MD5() {
	}

	public static String GetMD5Code(String strObj) {
		MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
	        byte[] byteArray = strObj.getBytes("UTF-8");
	        byte[] md5Bytes = md5.digest(byteArray);
	        StringBuffer hexValue = new StringBuffer();
	        for (int i = 0; i < md5Bytes.length; i++) {
	            int val = ((int) md5Bytes[i]) & 0xff;
	            if (val < 16) {
	                hexValue.append("0");
	            }
	            hexValue.append(Integer.toHexString(val));
	        }
	        return hexValue.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
	}

	public static void main(String[] args) {
		System.out.println(MD5.GetMD5Code("1234567890"));
	}
}