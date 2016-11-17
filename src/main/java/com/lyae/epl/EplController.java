package com.lyae.epl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/epl")	
public class EplController {
	
	@RequestMapping("ranking")
	public ModelAndView rankingList(){
		
		return null;
	}
}
