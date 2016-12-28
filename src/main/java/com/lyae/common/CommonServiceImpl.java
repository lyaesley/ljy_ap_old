package com.lyae.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyae.common.CommonDAO;
import com.lyae.model.MatchRecord;
import com.lyae.model.Team;

@Service
public class CommonServiceImpl implements CommonService {
	Logger log = Logger.getLogger(CommonServiceImpl.class.getName());
	
	@Autowired
	CommonDAO dao;

	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		return null;

	}

	@Override
	public List<Team> list(List<MatchRecord> matchList, String homeOrAway) throws IOException {
		// TODO Auto-generated method stub
		//json 파일 읽어서 MatchRecord Object 에 리스트 저장
//		List<MatchRecord> matchList = dao.jasonToObject(fileName);
		// 팀별 Object 생성
		HashMap<String, Team> teamObj = dao.getTeamNameListWithSetTeamObject(matchList);
		
		if(homeOrAway.equals("home")){
			dao.setMatchResultOfHomeAway(matchList, teamObj, homeOrAway);
		}else if(homeOrAway.equals("away")){
			dao.setMatchResultOfHomeAway(matchList, teamObj, homeOrAway);
		}else{
			// 매치 결과 팀별 Object 에 저장
			dao.setMatchResult(matchList, teamObj);
		}
		
		// 순위 정렬
		List<Team> leagueList = dao.sortDescByPoint(teamObj);

//		List<Team> leagueList = new ArrayList<Team>(teamObj.values());
		
		if(log.isDebugEnabled()){
			log.debug(homeOrAway+" : "+leagueList);
		}
		
		return leagueList;
	}

	@Override
	public List<MatchRecord> getMatchResult(String league) throws IOException {
			List<MatchRecord> matchList = dao.jasonToObject(league);
		return matchList;
	}

	@Override
	public List<MatchRecord> view(String league, String team, String homeOrAway) throws IOException {
		// TODO Auto-generated method stub
			List<MatchRecord> matchList = this.getMatchResult(league);
			List<MatchRecord> matchofTeam = new ArrayList<MatchRecord>();
			if(homeOrAway == "home"){
				for(MatchRecord i : matchList){
					if(i.getHometeam().equals(team)){
//						MatchRecord teamObj = new MatchRecord();
						matchofTeam.add(i);
					}
				}
			}else if(homeOrAway == "away"){
				for(MatchRecord i : matchList){
					if(i.getHometeam().equals(team)){
//						MatchRecord teamObj = new MatchRecord();
						matchofTeam.add(i);
					}
				}
			}else{
				for(MatchRecord i : matchList){
					if(i.getHometeam().equals(team)){
	//					MatchRecord teamObj = new MatchRecord();
						matchofTeam.add(i);
					}else if(i.getAwayteam().equals(team)){
						matchofTeam.add(i);
					}
				}
			}
		return matchofTeam;
	}
	
	@Override
	public List<Team> sortHomeAwaybyResult(List<Team> o1, List<Team> o2){
		String team1, team2;

		for(int i = 0; i < o1.size(); i++){
			team1 = o1.get(i).getTeamName();
			Loop2: for(int j = i; j < o2.size(); j++){
				team2 = o2.get(j).getTeamName();
				
				if(team1.equals(team2)){
					Collections.swap(o2, i, j);
					break Loop2;
				}
			}
		}
		return o2;
	}// end of sortResultList
		
}
