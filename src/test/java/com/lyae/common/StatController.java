//package com.lyae.common;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.log4j.Logger;
//import org.apache.poi.ss.formula.functions.T;
//import org.codehaus.jackson.JsonGenerationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import org.tukaani.xz.lz.Matches;
//
//import com.tsp.service.StatService;
//
//@Controller
//public class StatController {
//	private final Logger logger = Logger.getLogger(getClass());
//	
//	@Autowired
//	StatService statService;
//	
//	@RequestMapping("/stat/test.do")
//	@ResponseBody
//	public String test(){
//		
//		HashMap<String,String> param = new HashMap<String,String>();
//		param.put("ymd", "20150401");
//		
//		String text = statService.test(param).toString();
//		
//		return text;
//	}
//	@RequestMapping("/stat/rewardMdl.do")
//	@ResponseBody
//	public String insertRewardMdl(@RequestParam HashMap<String,String> param){
//		/**
//		 * @parameter
//		 * 필수 : date = yyyyMM
//		 * 선택 : flag= model 
//		 */
//		if( (param.get("date")==null || !param.get("date").matches("[\\d]{6}")) ){
//			return "날짜를 넣어주세요. ?date=yyyyMM";
//		}
//		
//		if (param.get("flag")==null){
//			param.put("flag", "");
//		}
//		String result = "실패";
//		try {
//			result = statService.insertRewardMdl(param);
//		} catch (Exception e){
//			logger.info("insertRewardMdl 에러 ::" + e.toString() + "::" + e.getMessage());
//		}
//		
//		
//		return result;
//	}
//	
//	@RequestMapping("/stat/rewardMdl_excel.do")
//	@ResponseBody
//	public String getStat(@RequestParam HashMap<String, String> param) throws IOException{
//		
//		List<Map<String,String>> result  = statService.getRewardMdl(param);
//		
//		
//		return result.toString();
//	}
//}
