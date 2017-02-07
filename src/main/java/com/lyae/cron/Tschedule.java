package com.lyae.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Tschedule {
	@Scheduled(cron="0/10 * * * * ?")
	public void excute(){
		java.util.Calendar calendar = java.util.Calendar.getInstance(); 
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		System.out.println("현재 시간은: " + dateFormat.format(calendar.getTime()));

		
	}
	
}
