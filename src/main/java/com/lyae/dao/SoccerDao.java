package com.lyae.dao;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lyae.model.MatchRecord;

@Repository
public class SoccerDao {
	Logger log = Logger.getLogger(SoccerDao.class.getName());
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public void insertSoccerMatchRecord(MatchRecord matchRecord){
		sqlSessionTemplate.insert("soccer.insertSoccerMatchRecord", matchRecord);
	}
}
