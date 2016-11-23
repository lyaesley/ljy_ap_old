package com.lyae.model;

import java.util.Comparator;

public class Team implements Comparator<Team>{

	private String teamName;
	private int matchCount;
	private int point;
	private int win;
	private int draw;
	private int lose;
	private int goal;
	private int goalLoss;
	private int goalDiff;
	
	
	public Team() {
		super();
	}

	public Team(String teamName) {
		super();
		this.teamName = teamName;
	}
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public int getMatchCount() {
		return matchCount;
	}
	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getLose() {
		return lose;
	}
	public void setLose(int lose) {
		this.lose = lose;
	}
	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	public int getGoalLoss() {
		return goalLoss;
	}
	public void setGoalLoss(int goalLoss) {
		this.goalLoss = goalLoss;
	}
	public int getGoalDiff() {
		return goalDiff;
	}
	public void setGoalDiff(int goalDiff) {
		this.goalDiff = goalDiff;
	}
	
	// 결과 누적 저장 
	public void setSumTeamName(String teamName) {
		this.teamName += teamName;
	}
	public void setSumMatchCount() {
		this.matchCount += 1;
	}
	public void setSumPoint(int point) {
		this.point += point;
	}
	public void setSumWin(int win) {
		this.win += win;
	}
	public void setSumDraw(int draw) {
		this.draw += draw;
	}
	public void setSumLose(int lose) {
		this.lose += lose;
	}
	public void setSumGoal(int goal) {
		this.goal += goal;
	}
	public void setSumGoalLoss(int goalLoss) {
		this.goalLoss += goalLoss;
	}
	public void setGoalDiff() {
		this.goalDiff = this.goal - this.goalLoss;
	}
	
	
	@Override
	public String toString() {
		return "Team [teamName=" + teamName + ", matchCount=" + matchCount + ", point=" + point + ", win=" + win
				+ ", draw=" + draw + ", lose=" + lose + ", goal=" + goal + ", goalLoss=" + goalLoss + ", goalDiff="
				+ goalDiff + "]";
	}

	@Override
	public int compare(Team o1, Team o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
