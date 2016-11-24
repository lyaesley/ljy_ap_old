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
		// TODO Auto-generated method stub
		if(log.isDebugEnabled()){
			log.debug("start test");
			
		}
//		System.out.println("start test");
		List<MatchRecord> epl = new ArrayList<MatchRecord>();
		List<Team> sortList = new ArrayList<Team>();
		List<HashMap<String,Team>> testlist = new ArrayList<HashMap<String, Team>>();
		
		epl = dao.jasonToObject("epl");
		System.out.println("00"+epl);
		HashMap<String, Team> teamObj = dao.getTeamNameListWithSetTeamObject(epl);
		System.out.println("11"+teamObj);
		dao.setMatchResult(epl, teamObj);
		System.out.println("22"+teamObj);
		sortList = dao.sortDescByGoalLoss(teamObj);
		System.out.println("33"+sortList);
		
		
	}

}
