package com.SCGEOrientaIA.util;

public class Keyword {
	private String keyword;
	private int repeatedTimesPerText;
	private int repeatedTimesPerTopic;
	
	public Keyword(){}
	public Keyword(String keyword, int repeatedTimes){
		this.keyword = keyword; this.repeatedTimesPerText = repeatedTimes;
		repeatedTimesPerTopic = 1;
	}
	public Keyword(String keyword, int repeatedTimes, int repeatedTimesPerTopic){
		this.keyword = keyword; this.repeatedTimesPerText = repeatedTimes;
		this.repeatedTimesPerTopic = repeatedTimesPerTopic;
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getRepeatedTimesPerText() {
		return repeatedTimesPerText;
	}
	public void setRepeatedTimesPerText(int repeatedTimes) {
		this.repeatedTimesPerText = repeatedTimes;
	}
	public int getRepeatedTimesPerTopic() {
		return repeatedTimesPerTopic;
	}
	public void setRepeatedTimesPerTopic(int repeatedTimesPerTopic) {
		this.repeatedTimesPerTopic = repeatedTimesPerTopic;
	}
	
}
