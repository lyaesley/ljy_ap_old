package com.lyae.common;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/epl")
public class CommonController {
	Logger log = Logger.getLogger(CommonController.class.getName());
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping("/list")
	public void list(Model model) throws IOException{
		model.addAttribute("list",commonService.list("epl"));
	}
	
}
