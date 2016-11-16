package com.lyae.model;

public class Epl {

	private String date;
	private String homeTeam;
	private String awayTeam;
	private String ftr;
	private int fthg;
	private int ftag;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public String getFtr() {
		return ftr;
	}
	public void setFtr(String ftr) {
		this.ftr = ftr;
	}
	public int getFthg() {
		return fthg;
	}
	public void setFthg(int fthg) {
		this.fthg = fthg;
	}
	public int getFtag() {
		return ftag;
	}
	public void setFtag(int ftag) {
		this.ftag = ftag;
	}
	
	@Override
	public String toString() {
		return "Epl [getDate()=" + getDate() + ", getHomeTeam()=" + getHomeTeam() + ", getAwayTeam()=" + getAwayTeam()
				+ ", getFtr()=" + getFtr() + ", getFthg()=" + getFthg() + ", getFtag()=" + getFtag() + "]";
	}
	
	
}
