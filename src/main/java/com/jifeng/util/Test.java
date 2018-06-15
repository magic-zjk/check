package com.jifeng.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		/*
		RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		String salt = randomNumberGenerator.nextBytes().toHex();
		String newPassword = new SimpleHash(
                "md5",	
                "123456",
                ByteSource.Util.bytes("admin"+salt),
                2).toHex();
		System.out.println(salt);
		System.out.println(newPassword);
		*/
		//System.out.println(Integer.MAX_VALUE);
//		String a = "djfkejr.djfd";
//		String as[] = a.split("\\|");
//		System.out.println(as.length);
//		User user = new User();
//		user.setUserName("apvisa");
//		user.setPassword("123456");
//		PasswordHelper ph = new PasswordHelper();
//		ph.encryptPassword(user);
//		System.out.println(user.getPassword());
//		System.out.println(user.getSalt());
//		display("2015-08-20", "2015-09-02");
		System.out.println(Const.END_TIME);
		System.out.println(DateUtil.addDayToString(Const.END_TIME, 1));
		
	}
	public static void display(String dateFirst, String dateSecond){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         
        try{
            Date dateOne = dateFormat.parse(dateFirst);
            Date dateTwo = dateFormat.parse(dateSecond);
             
            Calendar calendar = Calendar.getInstance();
             
            calendar.setTime(dateOne);
             
            while(calendar.getTime().before(dateTwo) ||calendar.getTime().equals(dateTwo)){               
                System.out.println(dateFormat.format(calendar.getTime()));
                 
                calendar.add(Calendar.DAY_OF_MONTH, 1);               
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
         
    }
}
