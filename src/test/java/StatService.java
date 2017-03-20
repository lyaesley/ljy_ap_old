

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

import org.aspectj.weaver.ast.Instanceof;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
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
			resultMap =  statDAO.getRewardMdl(param);
			
			for (Map<String,String> node :  resultMap) {
				storeMap.put(node.get("COMP_MDL"), node);
			}
			
			System.out.println(storeMap);
//			System.out.println(resultMap);
//			if (resultMap != null ){
//				JsonData = ConvUtil.toJsonObjectByClass(resultMap);
//			}
			
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
			resultMap =  statDAO.getRewardMdl(param);
			if (resultMap != null ){
//				JsonData = ConvUtil.toJsonObjectByClass(resultMap);
			}
			
			setParam.put("C_VAL1", sYmd);
			setParam.put("C_VAL2", eYmd);
			setParam.put("C_VAL3", "d");
		}   
		System.out.println(param.toString());

		setParam.put("STAT_CD", "RewardMdl");
		setParam.put("STAT_DATA", JsonData);
			
		statDAO.insertStat(setParam);		
		
		return JsonData;
	}
	
	@Test
	public void jsonTest() throws JsonProcessingException{
		
		String param = "[{COMP_MDL=CGCA, COMP_MDL_NM=IPHONE6+_64G, CNT=1}, {COMP_MDL=CGDH, COMP_MDL_NM=IPHONE6S_64GW, CNT=3}, {COMP_MDL=CGDK, COMP_MDL_NM=IPHONE6S_64GG, CNT=8}, {COMP_MDL=CGDN, COMP_MDL_NM=IPHONE6S_64GR, CNT=2}, {COMP_MDL=CGDX, COMP_MDL_NM=IPHONE6S+_64GW, CNT=1}, {COMP_MDL=CGE1, COMP_MDL_NM=IPHONE6S+_64GG, CNT=3}, {COMP_MDL=CGE4, COMP_MDL_NM=IPHONE6S+_64GR, CNT=2}, {COMP_MDL=CGFG, COMP_MDL_NM=IPHONE SE_64GW, CNT=1}, {COMP_MDL=CGFK, COMP_MDL_NM=IPHONE SE_64GR, CNT=1}, {COMP_MDL=CGHI, COMP_MDL_NM=IPHONE7_32GB, CNT=8}, {COMP_MDL=CGHJ, COMP_MDL_NM=IPHONE7_32GW, CNT=1}, {COMP_MDL=CGHM, COMP_MDL_NM=IPHONE7_128GB, CNT=11}, {COMP_MDL=CGHN, COMP_MDL_NM=IPHONE7_128GW, CNT=1}, {COMP_MDL=CGHO, COMP_MDL_NM=IPHONE7_128GG, CNT=1}, {COMP_MDL=CGHP, COMP_MDL_NM=IPHONE7_128GR, CNT=3}, {COMP_MDL=CGHQ, COMP_MDL_NM=IPHONE7_128GJ, CNT=5}, {COMP_MDL=CGHT, COMP_MDL_NM=IPHONE7_256GG, CNT=1}, {COMP_MDL=CGHV, COMP_MDL_NM=IPHONE7_256GJ, CNT=1}, {COMP_MDL=CGIB, COMP_MDL_NM=IPHONE7+_128GB, CNT=2}, {COMP_MDL=CGID, COMP_MDL_NM=IPHONE7+_128GG, CNT=1}, {COMP_MDL=CGIE, COMP_MDL_NM=IPHONE7+_128GR, CNT=4}, {COMP_MDL=CGIF, COMP_MDL_NM=IPHONE7+_128GJ, CNT=3}, {COMP_MDL=CGIJ, COMP_MDL_NM=IPHONE7+_256GR, CNT=1}, {COMP_MDL=CGJA, COMP_MDL_NM=IPHONE6S_32G, CNT=3}, {COMP_MDL=CGJB, COMP_MDL_NM=IPHONE6S_32GW, CNT=1}, {COMP_MDL=CGJH, COMP_MDL_NM=IPHONE6S+_32GR, CNT=1}, {COMP_MDL=LGL8, COMP_MDL_NM=LG-F600S, CNT=3}, {COMP_MDL=LGML, COMP_MDL_NM=LG-F700S, CNT=2}, {COMP_MDL=LGMT, COMP_MDL_NM=LG-F770S, CNT=1}, {COMP_MDL=LGN1, COMP_MDL_NM=LG-F800S, CNT=3}, {COMP_MDL=LGN8, COMP_MDL_NM=null, CNT=1}, {COMP_MDL=PTY9, COMP_MDL_NM=IM-100S, CNT=1}, {COMP_MDL=SS1C, COMP_MDL_NM=SM-G930S_32GA, CNT=2}, {COMP_MDL=SS1E, COMP_MDL_NM=SM-A810SB, CNT=5}, {COMP_MDL=SS1M, COMP_MDL_NM=SM-G935S_32GG, CNT=1}, {COMP_MDL=SS1T, COMP_MDL_NM=SM-A810SS, CNT=1}, {COMP_MDL=SS29, COMP_MDL_NM=SM-G930S_32G, CNT=5}, {COMP_MDL=SS2D, COMP_MDL_NM=SM-G935S_64GB, CNT=5}, {COMP_MDL=SS2G, COMP_MDL_NM=SM-A520S, CNT=1}, {COMP_MDL=SS2L, COMP_MDL_NM=SM-G935S_128G, CNT=1}, {COMP_MDL=SS2Q, COMP_MDL_NM=SM-A520SP, CNT=1}, {COMP_MDL=SS32, COMP_MDL_NM=SM-A510SP, CNT=3}, {COMP_MDL=SS33, COMP_MDL_NM=SM-A510SW, CNT=1}, {COMP_MDL=SS37, COMP_MDL_NM=SM-A710SP, CNT=1}, {COMP_MDL=SS41, COMP_MDL_NM=SM-A710SW, CNT=1}, {COMP_MDL=SS49, COMP_MDL_NM=SM-G935S_32GS, CNT=3}, {COMP_MDL=SS50, COMP_MDL_NM=SM-G935SSO_32GS, CNT=1}, {COMP_MDL=SS51, COMP_MDL_NM=SM-G935S_32GW, CNT=1}, {COMP_MDL=SS56, COMP_MDL_NM=SM-G930S_32GS, CNT=11}, {COMP_MDL=SS64, COMP_MDL_NM=SM-G930S_64GG, CNT=4}, {COMP_MDL=SS66, COMP_MDL_NM=SM-G935S_64GG, CNT=4}, {COMP_MDL=SS67, COMP_MDL_NM=SM-G935SSO_64GG, CNT=1}, {COMP_MDL=SS71, COMP_MDL_NM=SM-J510S, CNT=2}, {COMP_MDL=SS74, COMP_MDL_NM=SM-J510SW, CNT=1}, {COMP_MDL=SS78, COMP_MDL_NM=SM-G935S_32GP, CNT=1}, {COMP_MDL=SSP9, COMP_MDL_NM=SM-G925S_32GW, CNT=1}, {COMP_MDL=SST4, COMP_MDL_NM=SM-N920S_64GG, CNT=9}, {COMP_MDL=SSTD, COMP_MDL_NM=SM-N915S, CNT=1}, {COMP_MDL=SSU7, COMP_MDL_NM=SM-N920S_64GS, CNT=12}, {COMP_MDL=SSV0, COMP_MDL_NM=SM-N920S_64GP, CNT=14}, {COMP_MDL=TJB1, COMP_MDL_NM=TG-L900S, CNT=1}]";
		String json = ConvUtil.toJsonObjectByClass(param);
		System.out.println(json);
	}
	public  List<Map<String,String>> getRewardMdl(HashMap<String, String> param) throws IOException{
		param.put("stat_cd", "RewardMdl");
		List<Map<String,String>> statResult = statDAO.getStat(param);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		Object clob = null;
		for(Map<String,String> list : statResult){
/*
			System.out.println(list.toString());
			if( list.get("C_VAL3") != null){
				if ( list.get("C_VAL3").equals("month")){
					clob = list.get("STAT_DATA");
						if( clob instanceof Clob ){
							String strClob = clobToString( (Clob)clob );
							paramMap.put(list.get("C_VAL1"), strClob);
						}
						
				} else if ( list.get("C_VAL3").equals("cnt")) {
						monthMap.put(list.get("C_VAL1"), list.get("STAT_DATA"));
						
						if( modelMap.get("C_VAL1") instanceof Clob ){
							clob = clobToString( (Clob)modelMap.get("model") );
							monthMap.put(list.get("C_VAL1"), list.get("STAT_DATA"));
						}
				}
			}
			System.out.println(modelMap);
			System.out.println(monthMap);
			*/
		}//end of for
			
		
	    return statResult;
	}
	
	/*
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
*/
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
