package com.lyae.controller;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public Map<String,String> dbTest(){
		
		return sqlSessionTemplate.selectOne("soccer.selectTest");
	}
	
}

