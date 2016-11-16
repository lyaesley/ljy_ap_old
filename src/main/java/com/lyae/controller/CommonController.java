package com.lyae.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
	Logger log = Logger.getLogger(CommonController.class.getName());
	
	@RequestMapping(value="/common/parseJsonFile")
	public void parseJsonFile(){
		if(log.isDebugEnabled()){
			log.debug("parseJsonFile");
		}
	}
	
}
