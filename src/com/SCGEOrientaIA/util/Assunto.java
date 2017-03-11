package com.SCGEOrientaIA.util;

public class Assunto {
	private int id;
	private String assunto;
	
	public Assunto(){}
	public Assunto(int id, String assunto){
		this.id = id; this.assunto = assunto;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
}
