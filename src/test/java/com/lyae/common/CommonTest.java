package com.lyae.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lyae.model.MatchRecord;
import com.lyae.model.Team;


public class CommonTest {
	
	public static void main(String[] args) throws IOException {
		Logger log = Logger.getLogger(CommonTest.class.getName());
		CommonDAOImpl dao = new CommonDAOImpl();
		CommonServiceImpl srv = new CommonServiceImpl();
		
		// TODO Auto-generated method stub
		if(log.isDebugEnabled()){
			log.debug("start test");
			
		}
//		System.out.println("start test");
		List<MatchRecord> sp1 = new ArrayList<MatchRecord>();
		List<Team> sortList = new ArrayList<Team>();
		List<Team> o1 = new ArrayList<Team>();
		List<Team> o2 = new ArrayList<Team>();
		List<HashMap<String,Team>> testlist = new ArrayList<HashMap<String, Team>>();
		
		sp1 = dao.jasonToObject("spain");
		System.out.println("00"+sp1);
		HashMap<String, Team> teamObj = dao.getTeamNameListWithSetTeamObject(sp1);
		System.out.println("11"+teamObj);
		dao.setMatchResult(sp1, teamObj);
		System.out.println("22"+teamObj);
		sortList = dao.sortDescByPoint(teamObj);
		System.out.println("33"+sortList);
		
		HashMap<String, Team> teamObj2 = dao.getTeamNameListWithSetTeamObject(sp1);
		System.out.println("-----"+teamObj2);
		
		dao.setMatchResultOfHomeAway(sp1, teamObj2, "home");
		o1 = dao.sortDescByPoint(teamObj2);
		System.out.println("44"+o1);
		o2 = srv.sortHomeAwaybyResult(sortList, o1);
		System.out.println("55"+o2);
		//ÇÖÇÈ½º Ãß°¡
		System.out.println("66");
		
	}

}
