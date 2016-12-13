package com.lyae.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import com.lyae.model.MatchRecord;
import com.lyae.model.Team;

public interface CommonDAO {

	List<MatchRecord> jasonToObject(String fileName) throws IOException;
	
	HashMap<String, Team> getTeamNameListWithSetTeamObject(List<MatchRecord> list);
	
	void setMatchResult(List<MatchRecord> list, HashMap<String, Team> teamObjList);

	void setMatchResultOfHomeAway(List<MatchRecord> list, HashMap<String, Team> teamObjList, String homeOrAway);
	
	List<Team> sortDescByPoint(HashMap<String, Team> teamObjList);
	
	List<Team> sortDescByGoaldiff(HashMap<String, Team> teamObjList);
	
	List<Team> sortDescByGoal(HashMap<String, Team> teamObjList);
	
	List<Team> sortDescByGoalLoss(HashMap<String, Team> teamObjList);
	
}
