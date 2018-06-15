/** 
 * File Name:ClassLoaderTest.java
 * Project Name:account-check  
 * Package Name:com.gfan.test 
 * Create Date:2016年10月31日下午3:58:55 
 * Copyright (c) 2016, Imopan.com All Rights Reserved. 
 */
package com.gfan.test;

/** 
 * Description: TODO<br>
 * Create Date: 2016年10月31日 下午3:58:55
 * 
 * @author liangbing 
 * @version 1.0 
 */
public class ClassLoaderTest {
	
	public static void main(String[] args) {
		ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
		while(classLoader != null){
			System.out.println(classLoader.toString());
			classLoader = classLoader.getParent();
		}
	}

}
