package com.lyae.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyae.controller.CommonController;
import com.lyae.model.Soccer;

@Repository
public class CommonDAOImpl implements CommonDAO{
	Logger log = Logger.getLogger(CommonDAOImpl.class.getName());
	
	public List<Soccer> jasonToObject(String fileName) throws IOException{
		byte[] mapData = Files.readAllBytes(Paths.get("D://Dev/workspace/Ljy_AP/src/main/resources/json/"+fileName+".json"));
//		Map<String,String> myMap = new HashMap<String, String>();
		//로그용
		List<String> jsonTxt =  Files.readAllLines(Paths.get("D://Dev/workspace/Ljy_AP/src/main/resources/json/epl.json"));
		//1. create a mapper
		ObjectMapper objectMapper = new ObjectMapper();
		//model 선언
		List<Soccer> eplList = new ArrayList<Soccer>();
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
		eplList = objectMapper.readValue(mapData, new TypeReference<List<Soccer>>(){});  
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
	
	public List<String> getTeamName(List<Soccer> list){
		Iterator<Soccer> iter = list.iterator();
//		List<String> teamList = new ArrayList<String>();
		TreeSet<String> tSet = new TreeSet<String>();
		
		while(iter.hasNext()){
			tSet.add(iter.next().getHometeam());
//			System.out.println(iter.next().getHometeam());
		}
		System.out.println(tSet);
		
		NavigableSet<String> descSet = tSet.descendingSet();
		System.out.println(descSet);
		
		return null;
		
	}

}

