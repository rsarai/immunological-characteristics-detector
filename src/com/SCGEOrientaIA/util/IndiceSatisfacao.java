package com.SCGEOrientaIA.util;

public enum IndiceSatisfacao {
	NAOAVALIADO("N/A"),
	RUIM("RUIM"),
	REGULAR("REGULAR"),
	BOM("BOM"),
	MUITOBOM("MUITO BOM"),
	OTIMO("OTIMO");
	
	private final String name;
	
	private IndiceSatisfacao(String name){
		this.name = name;
	}
	
	public boolean equals(String name){
		return (name == null) ? false : this.name.equals(name);
	}
	
	public String toString(){
		return this.name;
	}
	
	public static IndiceSatisfacao classify(String name){
		switch(name){
			case "RUIM":
				return IndiceSatisfacao.RUIM;
			case "REGULAR":
				return IndiceSatisfacao.REGULAR;
			case "BOM":
				return IndiceSatisfacao.BOM;
			case "MUITO BOM":
				return IndiceSatisfacao.MUITOBOM;
			case "OTIMO":
				return IndiceSatisfacao.OTIMO;
			default:
				return IndiceSatisfacao.NAOAVALIADO;
		}
	}
}
