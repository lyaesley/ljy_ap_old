package com.lyae.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;


import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class StatDao {
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	public List<Map<String,String>> test(HashMap<String,String> param) {
		
		return sqlMapClientTemplate.queryForList("stat.test", param);
	}

	public List<Map<String, String>> getRewardMdlLst(Map<String, String> param) {
		return sqlMapClientTemplate.queryForList("stat.getRewardMdlLst", param);
	}
	
	public List<Map<String, String>> getRewardMdlCnt(Map<String, String> param) {
		return sqlMapClientTemplate.queryForList("stat.getRewardMdlCnt", param);
	}
	
	public void insertStat(Map<String, String> param) {
		sqlMapClientTemplate.insert("stat.insertStat", param);
	}

	public List<Map<String,String>> getStat(Map<String,String> param) {
		
		return sqlMapClientTemplate.queryForList("stat.getStat", param);
	}
	
}
