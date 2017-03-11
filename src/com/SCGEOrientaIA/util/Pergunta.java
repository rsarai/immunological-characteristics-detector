package com.SCGEOrientaIA.util;

import java.util.ArrayList;
import java.util.List;

public class Pergunta {
	private int id;
	private String pergunta;
	private boolean temTratamento;
	private List<Keyword> keywordList;
	
	public Pergunta(){
		keywordList = new ArrayList<Keyword>();
	}
	public Pergunta(int id, String pergunta, boolean temTratamento){
		this.id = id; this.pergunta = pergunta; this.temTratamento = temTratamento;
		keywordList = new ArrayList<Keyword>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	public boolean isTemTratamento() {
		return temTratamento;
	}
	public void setTemTratamento(boolean temTratamento) {
		this.temTratamento = temTratamento;
	}
	public List<Keyword> getKeywordList() {
		return keywordList;
	}
	public void setKeywordList(List<Keyword> keywordList) {
		this.keywordList = keywordList;
	}
	
	public Keyword containKeyword(String keyword){
		for(Keyword k : keywordList){
			if (k.getKeyword().equals(keyword)){
				return k;
			}
		}
		return null;
	}
}
