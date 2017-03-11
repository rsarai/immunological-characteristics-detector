package com.SCGEOrientaIA.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.SCGEOrientaIA.business.AlgorithmsController;
import com.SCGEOrientaIA.programSettings.ProjectProperties;
import com.SCGEOrientaIA.util.Antibody;
import com.SCGEOrientaIA.util.Antigen;
public class CSCA {
	private static Properties projectProperties = ProjectProperties.getProperties();
	
	public static void main(String[] args) {
		csca();
	}
	
	public static void csca(){
		
		System.out.println("Inicializando o algoritmo de Clonal Selection Classifier Algorithm");
		List<Antigen> ag = AlgorithmsController.generateAntigens();
		List<Antibody> ab = AlgorithmsController.generateAntibodies(); //SAÍDA - Conjunto de células de memória (anticorpos)
		int stopCondition= Integer.parseInt(projectProperties.getProperty("stopCondition"));
		int p = 10; //Número de partições, que os antígenos podem ser divididos
		//ideia: ser o número de assuntos que existem?
		int S = stopCondition / p; //qtd de antigenos que irá buscar
		
		int countAntigenos = 0; //controle da qtd de antigenos por partição
		
		for(int i = 0; i < stopCondition; i++){
			List<Antigen> antigenPartition = new ArrayList<Antigen>();
			List<Antibody> antibodiesAffinity;
			for(int j = countAntigenos; j < S; j++){
				Iterator<Antigen> it = ag.iterator();
				antigenPartition.add(j, it.next()); 
				antibodiesAffinity = AlgorithmsController.affinity(antigenPartition.get(j),ab);
				
			}
			//Criar um método para receber uma lista de antigenos e list de anticorpos
			
			
			countAntigenos += S;
		}
		
		
	}
}
