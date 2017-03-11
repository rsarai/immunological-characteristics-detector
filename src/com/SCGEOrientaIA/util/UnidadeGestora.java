package com.SCGEOrientaIA.util;

public class UnidadeGestora {
	private int id;
	private String unidadeGestora;
	
	public UnidadeGestora(){}
	public UnidadeGestora(int id, String unidadeGestora){
		this.id = id; this.unidadeGestora = unidadeGestora;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUnidadeGestora() {
		return unidadeGestora;
	}
	public void setUnidadeGestora(String unidadeGestora) {
		this.unidadeGestora = unidadeGestora;
	}
}
