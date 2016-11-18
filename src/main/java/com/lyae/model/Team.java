package com.lyae.model;

public class Team {

	private String teamName;
	private int matchCount;
	private int point;
	private int win;
	private int draw;
	private int lose;
	private int goal;
	private int goalLoss;
	private int goalDiff;
	
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
	
	@Override
	public String toString() {
		return "Team [teamName=" + teamName + ", matchCount=" + matchCount + ", point=" + point + ", win=" + win
				+ ", draw=" + draw + ", lose=" + lose + ", goal=" + goal + ", goalLoss=" + goalLoss + ", goalDiff="
				+ goalDiff + "]";
	}
	
	
}
