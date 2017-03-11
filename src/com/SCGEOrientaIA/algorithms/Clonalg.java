package com.SCGEOrientaIA.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.SCGEOrientaIA.business.AlgorithmsController;
import com.SCGEOrientaIA.programSettings.ProjectProperties;
import com.SCGEOrientaIA.util.Antibody;
import com.SCGEOrientaIA.util.Antigen;
import com.SCGEOrientaIA.util.Pergunta;

/*
 * Manutenção de células de memória
 * seleção e clone dos antibodies com mais afinidade
 * Morte dos antibodies com menos afinidades
 * Mutação
 * Geração e manutenção da diversidade
 * 
 * The goal of the algorithm is to develop a memory pool of antibodies that represents a
solution to an engineering problem

The algorithm provides two mechanisms for searching for the desired final pool of
memory antibodies. The first is a local search provided via affinity maturation
(hypermutation) of cloned antibodies.
 */

public class Clonalg {
	private static Properties projectProperties = ProjectProperties.getProperties();
	
	public static List<Antibody> clonalG(Pergunta pergunta){
		System.out.println("Initialization... \nGenerate Antigens and Antibodies");
		Antigen ant = new Antigen(pergunta.getPergunta());
		List<Antigen> ag = new ArrayList<>();
		ag.add(ant);
		
		
		List<Antibody> p = AlgorithmsController.generateAntibodies();
		List<Antibody> memoryCells = AlgorithmsController.generatePopulationOfMemoryCells();
		int stopCondition= Integer.parseInt(projectProperties.getProperty("stopCondition"));
		
		List<Antibody> populationOfAntibodies = p;
		
		//salvando as celulas de memória com as geradas aleatóriamente para gerar a população de anticorpos
		populationOfAntibodies.addAll(memoryCells);
		
		for (int i = 0; i < stopCondition; i++){
			//Para cada Antigeno de ag faça:
			for(Antigen antigen : ag){
				
				//P* = n individuos de P com maior afinidade com ag
				List<Antibody> antibodiesWithHigherAffinity = AlgorithmsController.affinity(antigen, populationOfAntibodies);
				//tratar o caso dele não retornar antibodies com a afinidade superior ao limite
				
				if(antibodiesWithHigherAffinity == null){
					break;
				}else{
					//clones de P* (antibodiesWithHigherAffinity) proporcional a afinidade com ag
					List<Antibody> clonesWithHigherAffinity = AlgorithmsController.cloning(antibodiesWithHigherAffinity);
					
					//C* = hypermutation (inversamente proporcional a afinidade ag)
					List<Antibody> antibodiesMutate = AlgorithmsController.hypermutate(clonesWithHigherAffinity);
					Antibody higherAffinityFromMutation = AlgorithmsController.higherAffinity(antigen, antibodiesMutate);
					Antibody higherAffinityFromMemory = AlgorithmsController.higherAffinity(antigen, memoryCells);
					
					
					//removendo todos as células que não células de memória da população
					AlgorithmsController.removeFromPopulationAllNonMemoryCells(populationOfAntibodies, p);
					//Anticorpos com menor afinidade a serão substituidos 
					AlgorithmsController.replaceCellsWithLessAffinity(p);

					if(higherAffinityFromMutation.getAffinity() > higherAffinityFromMemory.getAffinity()){
						//guardar dado novo na memória  memoryCells = memoryCells + higherAffinityFromMutation
						// ou  em arquivo ou no banco
						memoryCells.add(higherAffinityFromMutation);
						populationOfAntibodies.add(higherAffinityFromMutation);
					}
					
					//inserindo nova população de células que não são de memória, após substituir as com menos afinidade 
					populationOfAntibodies.addAll(p);
				}
			}
		}
		
		return populationOfAntibodies;
	}
	
	public static List<Antibody> saidaArvoreDecisao(Pergunta pergunta){
		List<Antibody> population = clonalG(pergunta);
		List<Antibody> retorno = new ArrayList<>();
		for(Antibody a : population ){
			if(a.getAffinity() > 10){
				retorno.add(a);
			}
		}
		return retorno;
	}
}
