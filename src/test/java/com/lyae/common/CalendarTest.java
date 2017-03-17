package com.lyae.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {
	
	public static void main(String args[]){
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		System.out.println(cal.getTime());
		
		System.out.println(cal.get(Calendar.YEAR));
		System.out.println(cal.get(Calendar.MONTH)+1);
		System.out.println(cal.get(Calendar.DATE));
		
		System.out.println(cal.get(Calendar.YEAR) +"년"+cal.get(Calendar.MONTH) +"월"+ cal.get(Calendar.DATE));
		System.out.println(cal.get(Calendar.YEAR) +"년"+(cal.get(Calendar.MONTH)+2) +"월"+ cal.get(Calendar.DATE));
		
		cal.set(Calendar.DATE, 1);
		System.out.println(dateFormat.format(cal.getTime()));
		cal.add(Calendar.HOUR, 3);
		System.out.println(dateFormat.format(cal.getTime()));
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.DATE, 5);
		
		System.out.println("DAY_OF_WEEK : "+cal2.get(Calendar.DAY_OF_WEEK)); // 요일 1:일 2: 월 .. 7: 토
		System.out.println("DAY_OF_MONTH : "+cal2.get(Calendar.DAY_OF_MONTH)); // 요일 1:일 2: 월 .. 7: 토
		System.out.println("WEEK_OF_MONTH : "+cal2.get(Calendar.WEEK_OF_MONTH)); //월의몇번째 주?
		System.out.println("DAY_OF_WEEK_IN_MONTH : "+cal2.get(Calendar.DAY_OF_WEEK_IN_MONTH)); // 이번달의 몇번째 요일인지?
		System.out.println(Calendar.SUNDAY);
		
		if (Calendar.SUNDAY == cal2.get(Calendar.DAY_OF_WEEK) && cal2.get(Calendar.DAY_OF_MONTH) <= 7) {
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		System.out.println(sdf.format(today));
		
	}
	
	
}