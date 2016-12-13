package com.lyae.common;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.lyae.model.MatchRecord;
import com.lyae.model.Team;

public interface CommonService {
	Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;

	List<MatchRecord> view(String league, String team, String homeOrAway) throws IOException;

	List<MatchRecord> getMatchResult(String league) throws IOException;

	List<Team> list(List<MatchRecord> matchList, String homeOrAway) throws IOException;

	List<Team> sortResultList(List<Team> o1, List<Team> o2);
}
