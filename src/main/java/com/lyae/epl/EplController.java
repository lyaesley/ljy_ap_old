package com.lyae.epl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lyae.common.CommonService;

@Controller
@RequestMapping("/epl")	
public class EplController {
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping("ranking")
	public ModelAndView rankingList(){
		
		return null;
	}
}
