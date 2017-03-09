package com.lyae.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lyae.model.MatchRecord;
import com.lyae.model.Team;

@Controller
@RequestMapping("/soccerf")
//@SessionAttributes("MatchResult")
public class CommonController {
	Logger log = Logger.getLogger(CommonController.class.getName());
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping("/{league}")
	public String list(@PathVariable String league, Model model, HttpSession session) throws IOException{
		
		List<MatchRecord> matchList = commonService.getMatchResult(league);
		List<Team> all = commonService.list(matchList,"ALL");
		List<Team> home = commonService.list(matchList,"home");
		List<Team> away = commonService.list(matchList,"away");
		//commonService.sortHomeAwaybyResult(all, home);
		home = commonService.sortHomeAwaybyResult(all, home);
		away = commonService.sortHomeAwaybyResult(all, away);
		session.setAttribute("MatchList", matchList);
		model.addAttribute("list",all);
		model.addAttribute("home",home);
		model.addAttribute("away",away);
		model.addAttribute("league", league);
		return "soccer/list";
	}
	
	@RequestMapping("/{league}/{team}")
	public String teamMatchHistory(@PathVariable String league, @PathVariable String team, Model model, HttpSession session) throws IOException{
		if(log.isDebugEnabled()){
			log.debug("league/{team}");
		}
//		session.getAttribute("MatchList");
//		model.addAttribute("list", session.getAttribute("MatchList"));
		model.addAttribute("list", commonService.view(league, team, "all"));
		model.addAttribute("team", team);
		return "soccer/view";
	}
	
	@RequestMapping("/{league}/home")
	public String listHome(@PathVariable String league, Model model) throws IOException{
		if(log.isDebugEnabled()){
			log.debug("league/home");
		}
		List<MatchRecord> matchList = commonService.getMatchResult(league);
		model.addAttribute("list",commonService.list(matchList,"home"));
		model.addAttribute("league", league);
		return "soccer/list";
	}
	
	@RequestMapping("/{league}/away")
	public String listAway(@PathVariable String league, Model model) throws IOException{
		if(log.isDebugEnabled()){
			log.debug("league/away");
		}
		List<MatchRecord> matchList = commonService.getMatchResult(league);
		model.addAttribute("list",commonService.list(matchList,"away"));
		model.addAttribute("league", league);
		return "soccer/list";
	}
}
