package com.lyae.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import com.lyae.model.MatchRecord;
import com.lyae.model.Team;

@Repository
public class CommonDAOImpl implements CommonDAO{
	Logger log = Logger.getLogger(CommonDAOImpl.class.getName());
	
	public List<MatchRecord> jasonToObject(String leageName) throws IOException{
		byte[] mapData = Files.readAllBytes(Paths.get("D://Dev/workspace/Ljy_AP/src/main/resources/json/"+leageName+".json"));
//		Map<String,String> myMap = new HashMap<String, String>();
		//로그용
		List<String> jsonTxt =  Files.readAllLines(Paths.get("D://Dev/workspace/Ljy_AP/src/main/resources/json/epl.json"));
		//1. create a mapper
		ObjectMapper objectMapper = new ObjectMapper();
		//model 선언
		List<MatchRecord> matchRecordList = new ArrayList<MatchRecord>();
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
		matchRecordList = objectMapper.readValue(mapData, new TypeReference<List<MatchRecord>>(){});  
			if(log.isDebugEnabled()){
				log.debug(jsonTxt);
				log.debug(matchRecordList);
				log.debug(matchRecordList.size());
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return matchRecordList;
	}
	
	public HashMap<String, Team> getTeamNameListWithSetTeamObject(List<MatchRecord> list){
		Iterator<MatchRecord> iter = list.iterator();
		TreeSet<String> deDuplication = new TreeSet<String>();
//		HashSet<String> deDuplication = new HashSet<String>();
		
		while(iter.hasNext()){
			deDuplication.add(iter.next().getHometeam());
//			System.out.println(iter.next().getHometeam());
		}
		
		ArrayList<String> teamNameList = new ArrayList<String>(deDuplication);
		
		System.out.println("teamlist = " + teamNameList);

		
		HashMap<String, Team> teamObjList = new HashMap<String, Team>();
		
		for(String teamName : teamNameList){
			teamObjList.put(teamName, new Team(teamName));
		}
//		System.out.println("teamObj =" + teamObj);
//		System.out.println("Liverpool = " + teamObj.get("Liverpool"));
		/*
		List<Team> teamObject = new ArrayList<Team>();
		teamObject.add(new Team(teamList.get(0)));
		teamObject.add(new Team(teamList.get(1)));
		
		System.out.println("teamObject = " + teamObject.get(0).getTeamName());
		*/
		
		//desc sort
//		NavigableSet<String> descSet = tSet.descendingSet();
//		System.out.println(descSet);
		
		return teamObjList;
		
	}

}

