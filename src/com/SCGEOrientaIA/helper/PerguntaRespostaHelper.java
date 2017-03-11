package com.SCGEOrientaIA.helper;

import java.util.ArrayList;
import java.util.List;

import com.SCGEOrientaIA.util.Assunto;
import com.SCGEOrientaIA.util.Keyword;
import com.SCGEOrientaIA.util.Pergunta;
import com.SCGEOrientaIA.util.PerguntaResposta;

public class PerguntaRespostaHelper {
	
	/**
	 * Dado um Assunto(Topic) qualquer, o método retornará uma lista com a referência dos nós que possui o mesmo.
	 * @param perguntaRespostaList
	 * @param assunto
	 * @return
	 */
	public static List<PerguntaResposta> filterPerguntaRespostaByTopic(List<PerguntaResposta> perguntaRespostaList, Assunto assunto){
		List<PerguntaResposta> filteredList = new ArrayList<PerguntaResposta>();
		for(PerguntaResposta pr : perguntaRespostaList){
			if (pr.getAssunto().getId() == assunto.getId()){
				filteredList.add(pr);
			}
		}
		return filteredList;
	}
	
	/**
	 * Dado uma Lista de PerguntaResposta, os assuntos serão filtrados (sem repetições) e retornado em uma lista de assuntos.
	 * @param perguntaRespostaList
	 * @return
	 */
	public static List<Assunto> getAssuntoListByPerguntaRespostaList(List<PerguntaResposta> perguntaRespostaList){
		List<Integer> assuntoIDList = new ArrayList<Integer>();
		List<Assunto> assuntoList = new ArrayList<Assunto>();
		for(PerguntaResposta pr : perguntaRespostaList){
			if (!assuntoIDList.contains(pr.getAssunto().getId())){
				assuntoIDList.add(pr.getAssunto().getId());
				assuntoList.add(pr.getAssunto());
			}
		}
		return assuntoList;
	}
	
	
	/**
	 * Retorna PerguntaResposta que possuem Keyword se HasKeyword for true.
	 * Retorna PerguntaResposta que não possuem Keyword se HasKeyword for false;
	 * @param perguntaRespostaList
	 * @param hasKeyword
	 * @param keyword 
	 * @return
	 */
	public static List<PerguntaResposta> filterPerguntaRespostaByKeyword(List<PerguntaResposta> perguntaRespostaList, Keyword keyword, boolean hasKeyword){
		List<PerguntaResposta> prAux = new ArrayList<PerguntaResposta>();
		for (PerguntaResposta pr : perguntaRespostaList){
			if (hasKeyword){
				for (Keyword kw : pr.getPergunta().getKeywordList()){
					if (kw.getKeyword().equals(keyword.getKeyword())){
						prAux.add(pr);
						break;
					}
				}
			} else {
				boolean keywordFound = false;
				for (Keyword kw : pr.getPergunta().getKeywordList()){
					if (kw.getKeyword().equals(keyword.getKeyword())){
						keywordFound = true;
						break;
					}
				}
				
				if (!keywordFound)
					prAux.add(pr);
			}
		}
		return prAux;
	}
	
	/**
	 * Retorna o percentual de precisão entre duas perguntas.
	 * @return
	 */
	public static double calculateMatchPrecision(Pergunta pergunta, PerguntaResposta perguntaResposta){
		
		double matches = 0;
		
		for(Keyword kw : pergunta.getKeywordList()){
			for(Keyword kwPR : perguntaResposta.getPergunta().getKeywordList()){
				if (kwPR.getKeyword().equals(kw.getKeyword())){
					matches++;
					break;
				}
			}
		}
		
		return 100d*(matches/pergunta.getKeywordList().size());
	}
}
