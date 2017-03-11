package com.SCGEOrientaIA.util;

import java.util.ArrayList;
import java.util.List;

public class KeywordGroup {
	private List<Keyword> keywordList;
	private Assunto assunto;
	
	public KeywordGroup(){
		keywordList = new ArrayList<Keyword>();
	}
	
	public KeywordGroup(List<Keyword>keywordList, Assunto assunto){
		this.keywordList = keywordList;
		this.assunto = assunto;
	}
	
	public Keyword contains(String parameter){
		for (Keyword k : keywordList){
			if (k.getKeyword().equals(parameter)){
				return k;
			}
		}
		return null;
	}

	public List<Keyword> getKeywordList() {
		return keywordList;
	}
	public void setKeywordList(List<Keyword> keywordList) {
		this.keywordList = keywordList;
	}
	public Assunto getAssunto() {
		return assunto;
	}
	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}
}
