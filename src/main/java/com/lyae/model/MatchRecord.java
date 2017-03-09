package com.lyae.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchRecord {
	
	private String div;
	private String date;
	private String hometeam;
	private String awayteam;
	private String ftr;
	private int fthg;
	private int ftag;
	private int hthg;
	private int htag;
	private String htr;
	
	
	public String getDiv() {
		return div;
	}
	public void setDiv(String div) {
		this.div = div;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHometeam() {
		return hometeam;
	}
	public void setHometeam(String hometeam) {
		this.hometeam = hometeam;
	}
	public String getAwayteam() {
		return awayteam;
	}
	public void setAwayteam(String awayteam) {
		this.awayteam = awayteam;
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
	public int getHthg() {
		return hthg;
	}
	public void setHthg(int hthg) {
		this.hthg = hthg;
	}
	public int getHtag() {
		return htag;
	}
	public void setHtag(int htag) {
		this.htag = htag;
	}
	public String getHtr() {
		return htr;
	}
	public void setHtr(String htr) {
		this.htr = htr;
	}
	@Override
	public String toString() {
		return "MatchRecord [date=" + date + ", hometeam=" + hometeam + ", awayteam=" + awayteam + ", ftr=" + ftr
				+ ", fthg=" + fthg + ", ftag=" + ftag + "]";
	}
	
	
	
	
}
