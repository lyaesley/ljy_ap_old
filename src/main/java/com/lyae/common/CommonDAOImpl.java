package com.lyae.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
//		TreeSet<String> deDuplication = new TreeSet<String>();
		HashSet<String> deDuplication = new HashSet<String>();
		
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
	
	public void setMatchResult(List<MatchRecord> list, HashMap<String, Team> teamObjList){
		
//		Team homeObj = new Team();
//		Team awayObj = new Team();
		MatchRecord row = null;
		for(int i= 0; i<list.size(); i++){
			row = list.get(i);
			Team homeObj = teamObjList.get(row.getHometeam());
			Team awayObj = teamObjList.get(row.getAwayteam());
			
			//H=HomeTeam 승 , A=AwayTeam 승, D=Draw
			if(row.getFtr().equals("H")){
				homeObj.setSumWin(1);	//H승 +1
				awayObj.setSumLose(1);	//A패  +1
				
				homeObj.setSumPoint(3); //H승점 +3
			}else if (row.getFtr().equals("A")){
				homeObj.setSumLose(1);	//H패 +1
				awayObj.setSumWin(1);	//A승 +1
				
				awayObj.setSumPoint(3); //A승점 +3
			}else {
				homeObj.setSumDraw(1);	//H무 +1
				awayObj.setSumDraw(1);	//A무 +1
				
				homeObj.setSumPoint(1); //H승점 +1
				awayObj.setSumPoint(1); //A승점 +1
			}
				
			homeObj.setSumMatchCount(); //경기수 +1
			awayObj.setSumMatchCount(); //경기수 +1
			
			homeObj.setSumGoal(row.getFthg()); //H득점 +
			awayObj.setSumGoal(row.getFtag()); //A득점 +

			homeObj.setSumGoalLoss(row.getFtag()); //H실점 +
			awayObj.setSumGoalLoss(row.getFthg()); //A실점 +
			
			homeObj.setGoalDiff();
			awayObj.setGoalDiff();
		}//end of for
	}
	
	public List<Team> sortDescByPoint(HashMap<String, Team> teamObjList){
		List<Team> list = new ArrayList<Team>(teamObjList.values());
		Collections.sort(list, new Comparator<Team>() {
			@Override
			public int compare(Team o1, Team o2) {
				// TODO Auto-generated method stub
				return o1.getPoint() > o2.getPoint() ? -1 : o1.getPoint() < o2.getPoint() ? 1 : 0 ;
			}
		});
		return list;
	}
}

