package com.lyae.common;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.lyae.model.Team;

public interface CommonService {
	Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;

	List<Team> list(String leagueName) throws IOException;
}
