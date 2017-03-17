package com.lyae.common;

import java.util.ArrayList;
import java.util.List;

import com.lyae.model.Team;

public class ModelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Team team = new Team();
		
//		team.setTeamName(null);
//		System.out.println(team.getTeamName().trim());
		
		List<Team> _idmsLogin = new ArrayList();
		
		for(Team idmsLogin: _idmsLogin){
			String loginDate = "";
			if(idmsLogin.getTeamName() != null){
				loginDate = "11";
			}
			idmsLogin.setTeamName(null);
			System.out.println(idmsLogin.getTeamName().trim()+","+loginDate);
		}
		
	}

}
