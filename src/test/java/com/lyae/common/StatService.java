package com.lyae.common;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ts.util.ConvUtil;
import com.tsp.dao.StatDao;
import com.tsp.util.StringUtil;

import oracle.sql.CLOB;


@Component
public class StatService {
	
	
	@Autowired
	private StatDao statDAO;

	public List<Map<String,String>> test(HashMap<String, String> param) {
		
		return statDAO.test(param);
	}

	public String insertRewardMdl(HashMap<String, String> param) throws Exception {
		/**
		 * STAT_CD : 통계코드 
		 * STAT_DATA : 통계데이터(JSON 형식으로 저장)
		 * C_VAL1 ~ 4 : 커스텀값 1,2는 인덱스 
		 * 
		 * */
		String ym = null; 
		ym = param.get("date");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MONTH, Integer.parseInt(ym.substring(4)) -1);
		cal.set(Calendar.YEAR, Integer.parseInt(ym.substring(0,4)));
		
		String sYmd = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		String eYmd = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		
		
		Map<String, String> setParam = new HashMap<String,String>();
		List<Map<String, String>> resultMap =  new ArrayList<Map<String,String>>();
		String JsonData = null;
		
		if(param.get("flag").toLowerCase().equals("model")){
			param.put("sYmd", sYmd);
			resultMap =  statDAO.getRewardMdlLst(param);
			JsonData = ConvUtil.toJsonObjectByClass(resultMap);
			
			setParam.put("C_VAL1", sYmd);
			setParam.put("C_VAL3", "model");
		}else {
			param.put("sYmd", sYmd);
			param.put("eYmd", eYmd);
			resultMap =  statDAO.getRewardMdlCnt(param);
			JsonData = ConvUtil.toJsonObjectByClass(resultMap);
			
			setParam.put("C_VAL1", sYmd);
			setParam.put("C_VAL2", eYmd);
			setParam.put("C_VAL3", "cnt");
		}   
		System.out.println(param.toString());

		setParam.put("STAT_CD", "RewardMdl");
		setParam.put("STAT_DATA", JsonData);
			
		statDAO.insertStat(setParam);		
		
		return JsonData;
	}

	public  List<Map<String,String>> getRewardMdl(HashMap<String, String> param) throws IOException{
		param.put("stat_cd", "RewardMdl");
		List<Map<String,String>> result = statDAO.getStat(param);

		Map<String, Object> modelMap = new HashMap<String, Object>();
		Map<String, Object> monthMap = new HashMap<String, Object>();
		
		Object clob = null;
		for(Map<String,String> list : result){
			System.out.println(list.toString());
			System.out.println(list.get("C_VAL3"));
			
			if( list.get("C_VAL3") != null){
				
				if ( list.get("C_VAL3").equals("model")){
						modelMap.put(list.get("C_VAL3"), list.get("STAT_DATA"));
						
						if( modelMap.get("C_VAL3") instanceof Clob ){
							clob = clobToString( (Clob)modelMap.get("model") );
							monthMap.put(list.get("C_VAL3"), list.get("STAT_DATA"));
						}
						
				} else if ( list.get("C_VAL3").equals("cnt")) {
						monthMap.put(list.get("C_VAL1"), list.get("STAT_DATA"));
						
						if( modelMap.get("C_VAL1") instanceof Clob ){
							clob = clobToString( (Clob)modelMap.get("model") );
							monthMap.put(list.get("C_VAL1"), list.get("STAT_DATA"));
						}
				}
			}
		}//end of for
		
				System.out.println(modelMap);
				System.out.println(monthMap);
			
	    return result;
	}
	
	public List<Map<String,String>> getStat(HashMap<String, String> param) {
		return statDAO.getStat(param);		
	}

	
	public static String clobToString(Clob clob) throws IOException{
		
		if(clob == null){
			return "";
		}
		
		StringBuffer strOut = new StringBuffer();

		  String str = "";

		  BufferedReader br;
		try {
			br = new BufferedReader(clob.getCharacterStream());

			while ((str = br.readLine()) != null) {
				strOut.append(str);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  return strOut.toString();
	}
	
	
}
