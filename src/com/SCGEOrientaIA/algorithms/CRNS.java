package com.SCGEOrientaIA.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.SCGEOrientaIA.util.Antibody;
import com.SCGEOrientaIA.util.Antigen;

/*
 * Constant size negative selection algorithm
 * /
 */

public class CRNS {
	private int n = 100; //quantidade maxima de antibodies
	
	public List<Antibody> treinamento(){
		List<Antibody> antibodies = new ArrayList<>();
		List<Antibody> treinamento = new ArrayList<>();
		double raio = 0; 
		int N = 0;
		
		while(N < n){
			Antibody x = new Antibody();
			for(Antibody si : treinamento){
				int dist = 0;//dist = calcular afinidade entre x e os antibodies de treinamento
				if(dist < raio){
					break;
				}else{
					N++;
					antibodies.add(x);
				}
			}
		}
		
		return antibodies;
	}
	
	public boolean reconhecimento(List<Antibody> si, List<Antibody> all){
		for (Antibody antibody : all) {
			int dist = 0;//dist = calcular afinidade entre x e os antibodies de treinamento
			int raioDetector = 0;
			if(dist < raioDetector){
				return false;
			}
		}
		return true;
	}
	
	public void vDetector(){
		List<Antibody> antibodies = new ArrayList<>();
		List<Antigen> antigens = new ArrayList<>();
		
		int pmin = 32; //numero de detectores no caso assuntos ou respostas
		
		int n = 5/pmin > 5/(1 - pmin) ? 5/pmin : 5/(1 - pmin);
	}
}
