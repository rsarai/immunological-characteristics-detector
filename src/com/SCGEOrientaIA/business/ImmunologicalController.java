package com.SCGEOrientaIA.business;

import java.util.List;

import com.SCGEOrientaIA.dao.util.ADAOPergunta;
import com.SCGEOrientaIA.dao.util.jdbc.SQLServerPergunta;
import com.SCGEOrientaIA.util.Antibody;
import com.SCGEOrientaIA.util.Antigen;
import com.SCGEOrientaIA.util.Pergunta;


public class ImmunologicalController {
	public List<Antigen> retriveAntigenFromDatabase(){
		return AlgorithmsController.generateAntigens();
	}
	
	public List<Antibody> retriveAntibodyFromDatabase(){
		return AlgorithmsController.generateAntibodies();
	}
	
	public void registerAntigenKeywords(){
		List<Antigen> aList = retriveAntigenFromDatabase();
		
		ADAOPergunta pDB = new SQLServerPergunta();
		
		List<Pergunta> pList = pDB.retrive(null);
		
		for(Antigen a : aList){
			for(Pergunta p : pList){
				if (p.getPergunta().equals(a.getDescription())){
					p.setKeywordList(a.getKeywords());
					pDB.registerKeywords(p);
				}
			}
		}
	}
}
