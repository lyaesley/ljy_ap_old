package com.lyae.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.junit.Test;

import com.impay.common.Crypto;
import com.impay.util.CommonUtil;

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
    
    @Test
	public void impaytest()	{
		System.out.println("========impay test========");
		String pNum = "01062138294";
		String mgmtNum ="7051446424";
		String impayYn="N";
		String cpData = null;
		
		pNum = CommonUtil.checkNull(pNum);
		mgmtNum = CommonUtil.checkNull(mgmtNum);
		
		cpData = pNum + "|" + mgmtNum + "|" + impayYn;
		System.out.println(cpData);
		
		Crypto crypto = new Crypto();
		System.out.println("암호화11 : "+crypto.encrypt_01(cpData));
		
		cpData =  "01062138294|7051446424|N";
		System.out.println("암호화22 : "+crypto.encrypt_01(cpData));

		cpData = "gtpMF2i5wjiQum3aVY86/k8wVOewcJXMlamT98BKno0=";
//		gtpMF2i5wjiQum3aVY86/k8wVOewcJ_xml_amT98BKno0=
//		gtpMF2i5wjiQum3aVY86/k8wVOewcJXMlamT98BKno0=
		//gtpMF2i5wjiQum3aVY86/hDPtAFesJ5FC4JlbEMSipY=
		//String cpData = "jsVTTwIeBsKy6fD3lYJI9mmgd4gMLzJZTKzfY6QMFV4=";
		String sss = 	crypto.decrypt_01(cpData);
		System.out.println("복호화 : " + sss);
		
	}
    
	public static void main(String[] args){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREAN);
		java.util.Date reqDate = cal.getTime();
		System.out.println(reqDate);
		System.out.println(formatCurrentDate());
		System.out.println(formatCurrentDay());
		System.out.println(formatCurrentMonth());
		System.out.println(getKeyCode());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sysdate","asdfasf");
	    System.out.println(map.toString() );	
	}
	
	
}
