package com.lyae.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AA {
	
	public static void main(String args[]){
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		System.out.println(cal.getTime());
		
		System.out.println(dateFormat.format(cal.getTime()));
		cal.add(Calendar.HOUR, 3);
		System.out.println(dateFormat.format(cal.getTime()));
		
	}
	
	
}
