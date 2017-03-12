package com.lyae.controller;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.internal.matchers.SubstringMatcher;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public Map<String,String> dbTest(){
		
		return sqlSessionTemplate.selectOne("soccer.selectTest");
	}

	public void calTest(HashMap<String, String> map) {
		String ym = map.get("date");
		
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTimeZone());
		System.out.println(cal.getTime());
		System.out.println(cal.get(Calendar.YEAR) +"년" +cal.get(Calendar.MONTH) +"월"+ cal.get(Calendar.DATE));
		System.out.println(cal.get(Calendar.YEAR) +"년"+ (cal.get(Calendar.MONTH)+1) +"월"+ cal.get(Calendar.DATE));

		cal.set(Calendar.DATE, 1);
		cal.set( Calendar.MONTH, Integer.parseInt(ym.substring(4,6) )-1 );
		cal.set(Calendar.YEAR, Integer.parseInt(ym.substring(0,4)));
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String symd = df.format(cal.getTime());
		System.out.println(symd);
		
		cal.add(Calendar.MONTH, +1);
		String eymd =  df.format(cal.getTime());
		System.out.println(eymd);
		
		
	}
	
}

