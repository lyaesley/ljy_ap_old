package com.lyae.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyae.dao.SoccerDao;


@Service
public class SoccerService {
	Logger log = Logger.getLogger(SoccerService.class.getName());
	
	@Autowired
	SoccerDao soccerDao;

	public void insertSoccerMatchRecord(Map<String, String> param) {

		//jackson JSON 유틸 만들고 디비 저장 해야함 
	}
}
