package com.SCGEOrientaIA.helper;

public class EntropyHelper {
	
	/**
	 * M�todo que retorna o logar�tmo na base 2 de um dado valor.
	 * Nativamente o pacote Math s� pode fazer c�lculos do logar�tmo na base 10.
	 * @param value
	 * @return
	 */
	public static double log2(double value){
		return Math.log(value)/Math.log(2.0d);
	}
	
	/**
	 * Calcula a entropia baseado nos est�mulos positivos e negativos de uma �rvore.
	 * @param positiveTimes
	 * @param totalTimes
	 * @return
	 */
	public static double calculateEntropy(int positiveTimes, int totalTimes){
		double positive = positiveTimes*1.0d/totalTimes*1.0d;
		double negative = (totalTimes-positiveTimes)*1.0d/totalTimes*1.0d;
		return - (positive)*log2(positive) - (negative)*log2(negative);
	}
}
