package com.lyae.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyae.model.MatchRecord;

@Controller
public class TestController {
	Logger log = Logger.getLogger(TestController.class.getName());
	@Autowired
	TestDao testDao;
	
	@RequestMapping("/cal/test")
	@ResponseBody
	public void calTest(@RequestParam HashMap<String,String> map){
		testDao.calTest(map);
		
	}
	
	@RequestMapping(value="/db/test")
	@ResponseBody
	public String dbTest(){
		
		return testDao.dbTest().toString();
	}
	
	@RequestMapping(value="/jsonTest")
	public List<MatchRecord> jackson() throws IOException{
		byte[] mapData = Files.readAllBytes(Paths.get("D://Dev/workspace/Ljy_AP/src/main/resources/json/epl.json"));
//		Map<String,String> myMap = new HashMap<String, String>();
		List<String> jsonTxt =  Files.readAllLines(Paths.get("D://Dev/workspace/Ljy_AP/src/main/resources/json/epl.json"));
		//1. create a mapper
		ObjectMapper objectMapper = new ObjectMapper();
		List<MatchRecord> eplList = new ArrayList<MatchRecord>();
		try{
			
		//2. As Array
//		myMap = objectMapper.readValue(mapData, HashMap.class);
		//3. As List
//		List<LinkedHashMap> myList = objectMapper.readValue(mapData,objectMapper.getTypeFactory().constructCollectionType(List.class, LinkedHashMap.class));  
//		System.out.println("Map is : "+myList);
//		System.out.println(myList.get(0).get(1));
//
		//4. As List Another
//		List<LinkedHashMap> myList = objectMapper.readValue(mapData, new TypeReference<List<LinkedHashMap>>(){});
//		List<Epl> eplList = objectMapper.readValue(mapData,objectMapper.getTypeFactory().constructCollectionType(List.class, Epl.class));  
		eplList = objectMapper.readValue(mapData, new TypeReference<List<MatchRecord>>(){});  
			if(log.isDebugEnabled()){
				log.debug(jsonTxt);
				log.debug(eplList);
				log.debug(eplList.size());
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eplList;
	}
	
	@RequestMapping(value="/common/parseJsonFile")
	public void parseJsonFile(){
//		String jsonInfo = "{\"books\":[{\"genre\":\"소설\",\"price\":\"100\",\"name\":\"사람은 무엇으로 사는가?\",\"writer\":\"톨스토이\",\"publisher\":\"톨스토이 출판사\"},{\"genre\":\"소설\",\"price\":\"300\",\"name\":\"홍길동전\",\"writer\":\"허균\",\"publisher\":\"허균 출판사\"},{\"genre\":\"소설\",\"price\":\"900\",\"name\":\"레미제라블\",\"writer\":\"빅토르 위고\",\"publisher\":\"빅토르 위고 출판사\"}],\"persons\":[{\"nickname\":\"남궁민수\",\"age\":\"25\",\"name\":\"송강호\",\"gender\":\"남자\"},{\"nickname\":\"예니콜\",\"age\":\"21\",\"name\":\"전지현\",\"gender\":\"여자\"}]}";
//		String jsonInfo = "[{\"Div\":\"E0\",\"Date\":\"2008-08-15\",\"HomeTeam\":\"Bournemouth\",\"AwayTeam\":\"Aston Villa\",\"FTHG\":0,\"FTAG\":1,\"FTR\":\"A\",\"HTHG\":0,\"HTAG\":0,\"HTR\":\"D\",\"Referee\":\"M Clattenburg\",\"HS\":11,\"AS\":7,\"HST\":2,\"AST\":3,\"HF\":13,\"AF\":13,\"HC\":6,\"AC\":3,\"HY\":3,\"AY\":4,\"HR\":0,\"AR\":0,\"B365H\":2,\"B365D\":3.6,\"B365A\":4,\"BWH\":2,\"BWD\":3.3,\"BWA\":3.7,\"IWH\":2.1,\"IWD\":3.3,\"IWA\":3.3,\"LBH\":2.05,\"LBD\":3.3,\"LBA\":4,\"PSH\":1.95,\"PSD\":3.65,\"PSA\":4.27,\"WHH\":1.91,\"WHD\":3.5,\"WHA\":4,\"VCH\":2,\"VCD\":3.5,\"VCA\":4.2,\"Bb1X2\":45,\"BbMxH\":2.1,\"BbAvH\":1.96,\"BbMxD\":3.65,\"BbAvD\":3.48,\"BbMxA\":4.33,\"BbAvA\":3.98,\"BbOU\":43,\"BbMx>2.5\":2.11,\"BbAv>2.5\":2.02,\"BbMx<2.5\":1.88,\"BbAv<2.5\":1.79,\"BbAH\":26,\"BbAHh\":-0.5,\"BbMxAHH\":1.98,\"BbAvAHH\":1.93,\"BbMxAHA\":1.99,\"BbAvAHA\":1.92,\"PSCH\":1.82,\"PSCD\":3.88,\"PSCA\":4.7},{\"Div\":\"E0\",\"Date\":\"2008-08-15\",\"HomeTeam\":\"Chelsea\",\"AwayTeam\":\"Swansea\",\"FTHG\":2,\"FTAG\":2,\"FTR\":\"D\",\"HTHG\":2,\"HTAG\":1,\"HTR\":\"H\",\"Referee\":\"M Oliver\",\"HS\":11,\"AS\":18,\"HST\":3,\"AST\":10,\"HF\":15,\"AF\":16,\"HC\":4,\"AC\":8,\"HY\":1,\"AY\":3,\"HR\":1,\"AR\":0,\"B365H\":1.36,\"B365D\":5,\"B365A\":11,\"BWH\":1.4,\"BWD\":4.75,\"BWA\":9,\"IWH\":1.33,\"IWD\":4.8,\"IWA\":8.3,\"LBH\":1.4,\"LBD\":4.5,\"LBA\":10,\"PSH\":1.39,\"PSD\":4.92,\"PSA\":10.39,\"WHH\":1.4,\"WHD\":4,\"WHA\":10,\"VCH\":1.4,\"VCD\":5,\"VCA\":9.5,\"Bb1X2\":45,\"BbMxH\":1.43,\"BbAvH\":1.37,\"BbMxD\":5,\"BbAvD\":4.66,\"BbMxA\":11.26,\"BbAvA\":9.57,\"BbOU\":43,\"BbMx>2.5\":1.88,\"BbAv>2.5\":1.8,\"BbMx<2.5\":2.07,\"BbAv<2.5\":1.99,\"BbAH\":27,\"BbAHh\":-1.5,\"BbMxAHH\":2.24,\"BbAvAHH\":2.16,\"BbMxAHA\":1.8,\"BbAvAHA\":1.73,\"PSCH\":1.37,\"PSCD\":5.04,\"PSCA\":10.88}]";
		String jsonInfo = "{\"match\":[{\"Div\":\"E0\",\"Date\":\"2008-08-15\",\"HomeTeam\":\"Bournemouth\",\"AwayTeam\":\"Aston Villa\",\"FTHG\":0,\"FTAG\":1,\"FTR\":\"A\",\"HTHG\":0,\"HTAG\":0,\"HTR\":\"D\",\"Referee\":\"M Clattenburg\",\"HS\":11,\"AS\":7,\"HST\":2,\"AST\":3,\"HF\":13,\"AF\":13,\"HC\":6,\"AC\":3,\"HY\":3,\"AY\":4,\"HR\":0,\"AR\":0,\"B365H\":2,\"B365D\":3.6,\"B365A\":4,\"BWH\":2,\"BWD\":3.3,\"BWA\":3.7,\"IWH\":2.1,\"IWD\":3.3,\"IWA\":3.3,\"LBH\":2.05,\"LBD\":3.3,\"LBA\":4,\"PSH\":1.95,\"PSD\":3.65,\"PSA\":4.27,\"WHH\":1.91,\"WHD\":3.5,\"WHA\":4,\"VCH\":2,\"VCD\":3.5,\"VCA\":4.2,\"Bb1X2\":45,\"BbMxH\":2.1,\"BbAvH\":1.96,\"BbMxD\":3.65,\"BbAvD\":3.48,\"BbMxA\":4.33,\"BbAvA\":3.98,\"BbOU\":43,\"BbMx>2.5\":2.11,\"BbAv>2.5\":2.02,\"BbMx<2.5\":1.88,\"BbAv<2.5\":1.79,\"BbAH\":26,\"BbAHh\":-0.5,\"BbMxAHH\":1.98,\"BbAvAHH\":1.93,\"BbMxAHA\":1.99,\"BbAvAHA\":1.92,\"PSCH\":1.82,\"PSCD\":3.88,\"PSCA\":4.7},{\"Div\":\"E0\",\"Date\":\"2008-08-15\",\"HomeTeam\":\"Chelsea\",\"AwayTeam\":\"Swansea\",\"FTHG\":2,\"FTAG\":2,\"FTR\":\"D\",\"HTHG\":2,\"HTAG\":1,\"HTR\":\"H\",\"Referee\":\"M Oliver\",\"HS\":11,\"AS\":18,\"HST\":3,\"AST\":10,\"HF\":15,\"AF\":16,\"HC\":4,\"AC\":8,\"HY\":1,\"AY\":3,\"HR\":1,\"AR\":0,\"B365H\":1.36,\"B365D\":5,\"B365A\":11,\"BWH\":1.4,\"BWD\":4.75,\"BWA\":9,\"IWH\":1.33,\"IWD\":4.8,\"IWA\":8.3,\"LBH\":1.4,\"LBD\":4.5,\"LBA\":10,\"PSH\":1.39,\"PSD\":4.92,\"PSA\":10.39,\"WHH\":1.4,\"WHD\":4,\"WHA\":10,\"VCH\":1.4,\"VCD\":5,\"VCA\":9.5,\"Bb1X2\":45,\"BbMxH\":1.43,\"BbAvH\":1.37,\"BbMxD\":5,\"BbAvD\":4.66,\"BbMxA\":11.26,\"BbAvA\":9.57,\"BbOU\":43,\"BbMx>2.5\":1.88,\"BbAv>2.5\":1.8,\"BbMx<2.5\":2.07,\"BbAv<2.5\":1.99,\"BbAH\":27,\"BbAHh\":-1.5,\"BbMxAHH\":2.24,\"BbAvAHH\":2.16,\"BbMxAHA\":1.8,\"BbAvAHA\":1.73,\"PSCH\":1.37,\"PSCD\":5.04,\"PSCA\":10.88}]}";

		JSONParser jsonParse = new JSONParser();
		try {

			//JSON데이터를 넣어 JSON Object 로 만들어 준다.
			JSONObject jsonObject = (JSONObject) jsonParse.parse(jsonInfo);
			System.out.println("jsonObject = " + jsonObject);
			//books의 배열을 추출
            JSONArray jsonArray = (JSONArray) jsonObject.get("match");
            
            for(int i=0; i<jsonArray.size(); i++){
            	 
                System.out.println("=MATCH_"+i+" ===========================================");
                 
                //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
                JSONObject bookObject = (JSONObject) jsonArray.get(i);
               /* 
                System.out.println("bookInfo: name==>"+bookObject.get("name"));
                System.out.println("bookInfo: writer==>"+bookObject.get("writer"));
                System.out.println("bookInfo: price==>"+bookObject.get("price"));
                System.out.println("bookInfo: genre==>"+bookObject.get("genre"));
                System.out.println("bookInfo: publisher==>"+bookObject.get("publisher"));
                */
                //JSON name으로 추출
                System.out.println("bookInfo: Date==>"+bookObject.get("Date"));
                System.out.println("bookInfo: HomeTeam==>"+bookObject.get("HomeTeam"));
                System.out.println("bookInfo: AwayTeam==>"+bookObject.get("AwayTeam"));
                System.out.println("bookInfo: FTR==>"+bookObject.get("FTR"));
                System.out.println("bookInfo: FTHG==>"+bookObject.get("FTHG"));
                System.out.println("bookInfo: FTAG==>"+bookObject.get("FTAG"));
 
            }
            
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
