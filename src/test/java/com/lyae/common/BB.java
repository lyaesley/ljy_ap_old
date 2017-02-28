package com.lyae.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class BB {
	
	/**
     * 현재날짜를 yyyyMMdd 패턴 문자열로 포맷한다.
     * 
     * @return the string
     */
	public static String formatCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(new SimpleTimeZone(9 * 60 * 60 * 1000, "KST"));
        GregorianCalendar gcal = new GregorianCalendar();

        return sdf.format(gcal.getTime());
    }

    /**
     * 현재날짜를 dd 패턴 문자열로 포맷한다.
     * 
     * @return the string
     */
    public static String formatCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        sdf.setTimeZone(new SimpleTimeZone(9 * 60 * 60 * 1000, "KST"));
        GregorianCalendar gcal = new GregorianCalendar();

        return sdf.format(gcal.getTime());
    }
    /**
     * 현재날짜를 yyyymm 패턴 문자열로 포맷한다.
     * 
     * @return the string
     */
    public static String formatCurrentMonth() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        return sdf.format(d);
    }
    
  //자리 난수(정수형) 발생기
    public static int getKeyCode(){
        
        Random rand = new Random(System.nanoTime());
        
        int tmpRandom = Math.abs(rand.nextInt(999990));
        
        if(tmpRandom>900000){}
        else{tmpRandom = tmpRandom + 100000;}
        
        return tmpRandom;
    } 
    
	public static void main(String[] args){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREAN);
		java.util.Date reqDate = cal.getTime();
		System.out.println(reqDate);
		System.out.println(formatCurrentDate());
		System.out.println(formatCurrentDay());
		System.out.println(formatCurrentMonth());
		System.out.println(getKeyCode());
	}
	
	
}
