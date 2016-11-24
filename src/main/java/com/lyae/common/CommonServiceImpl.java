package com.lyae.common;

import java.io.IOException;
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
	public List<Team> list(String fileName) throws IOException {
		// TODO Auto-generated method stub
		//json 파일 읽어서 MatchRecord Object 에 리스트 저장
		List<MatchRecord> matchList = dao.jasonToObject(fileName);
		
		// 팀별 Object 생성
		HashMap<String, Team> teamObj = dao.getTeamNameListWithSetTeamObject(matchList);
		
		// 매치 결과 팀별 Object 에 저장
		dao.setMatchResult(matchList, teamObj);
		
		// 순위 정렬
		List<Team> leagueList = dao.sortDescByPoint(teamObj);
		
		if(log.isDebugEnabled()){
			log.debug(leagueList);
		}
		
		return leagueList;
	}
	
		
}
