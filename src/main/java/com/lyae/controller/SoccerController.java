package com.lyae.controller;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lyae.service.SoccerService;

@Controller
@RequestMapping("/soccer")
public class SoccerController {
	Logger log = Logger.getLogger(SoccerController.class.getName());
	
	@Autowired
	SoccerService soccerService;
	
	@RequestMapping(value = "/insertDB", method=RequestMethod.GET)
	public String insertSoccerMatchRecord(@RequestParam HashMap<String, String> param){
		
		param.get("league");
		soccerService.insertSoccerMatchRecord(param);
		
		return "";
	}
}
