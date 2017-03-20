package com.lyae.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lyae.controller.TestController;
import com.lyae.controller.TestDao;
import com.lyae.util.ConvUtil;

@Component
public class TestService {
	Logger log = Logger.getLogger(TestService.class.getName());
	@Autowired
	private TestDao testDao;
	
	public String insertRewardMdl(HashMap<String, String> param) throws Exception {
		/**
		 * STAT_CD : 통계코드 
		 * STAT_DATA : 통계데이터(JSON 형식으로 저장)
		 * C_VAL1 ~ 4 : 커스텀값 1,2는 인덱스 
		 * 
		 * */
		String ym = param.get("date");
		
		Map<String, String> setParam = new HashMap<String,String>();
		List<Map<String, String>> resultMap =  new ArrayList<Map<String,String>>();
		Map<String,Map<String, String>> storeMap =  new HashMap<String,Map<String,String>>();
		String JsonData = null;
		
		if(param.get("flag").toLowerCase().equals("m")){    // 월별통계
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.MONTH, Integer.parseInt(ym.substring(4)) -1);
			cal.set(Calendar.YEAR, Integer.parseInt(ym.substring(0,4)));
			
			String sYmd = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
			cal.add(Calendar.MONTH, 1);
			String eYmd = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());

			param.put("sYmd", sYmd);
			param.put("eYmd", eYmd);
			resultMap =  testDao.getRewardMdl(param);
			
			for (Map<String,String> node :  resultMap) {
				storeMap.put(node.get("MODEL"), node);
			}
			
			log.info("storeMap : " + storeMap);
			
			if (resultMap != null ){
				JsonData = ConvUtil.toJsonObjectByClass(storeMap);
			}
			
			setParam.put("C_VAL1", sYmd);
			setParam.put("C_VAL2", eYmd);
			setParam.put("C_VAL3", "m");
		}else {  	//일별통계
			Calendar cal = Calendar.getInstance();
			String sYmd = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
			cal.add(Calendar.DATE, 1);
			String eYmd = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
			
			param.put("sYmd", sYmd);
			param.put("eYmd", eYmd);
			resultMap =  testDao.getRewardMdl(param);
			
			if (resultMap != null ){
				JsonData = ConvUtil.toJsonObjectByClass(resultMap);
			}
			
			setParam.put("C_VAL1", sYmd);
			setParam.put("C_VAL2", eYmd);
			setParam.put("C_VAL3", "d");
		}   
		System.out.println(param.toString());

		setParam.put("STAT_CD", "RewardMdl");
		setParam.put("STAT_DATA", JsonData);
			
		testDao.insertStat(setParam);		
		
		return JsonData;
	}

}
