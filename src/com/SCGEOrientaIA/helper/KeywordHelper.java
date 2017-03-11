package com.SCGEOrientaIA.helper;

import java.util.ArrayList;
import java.util.List;

import com.SCGEOrientaIA.util.Assunto;
import com.SCGEOrientaIA.util.Keyword;
import com.SCGEOrientaIA.util.KeywordGroup;
import com.SCGEOrientaIA.util.PerguntaResposta;

public class KeywordHelper {
	/**
	 * Agrupa todas as palavras-chave por categoria, encapsulado em um KeywordGroup.
	 * @param perguntaRespostaList
	 * @return
	 */
	public static List<KeywordGroup>perguntaRespostaToKeywordGroupList(List<PerguntaResposta> perguntaRespostaList){
		List<KeywordGroup> keywordGroupList = new ArrayList<KeywordGroup>();
		List<Assunto> assuntoList = PerguntaRespostaHelper.getAssuntoListByPerguntaRespostaList(perguntaRespostaList);
		
		//cadastrar todos os assuntos
		for(Assunto a : PerguntaRespostaHelper.getAssuntoListByPerguntaRespostaList(perguntaRespostaList)){
			keywordGroupList.add(new KeywordGroup(new ArrayList<Keyword>(), a));
		}
		
		//classificar as keywords		
		for (PerguntaResposta pr : perguntaRespostaList){
			for(KeywordGroup kwg : keywordGroupList){
				if (kwg.getAssunto().getId() == pr.getAssunto().getId()){
					
					for(Keyword k : pr.getPergunta().getKeywordList()){
						Keyword temp = kwg.contains(k.getKeyword());
						if (temp == null){
							Keyword aux = new Keyword(k.getKeyword(), k.getRepeatedTimesPerText());
							aux.setRepeatedTimesPerTopic(k.getRepeatedTimesPerTopic());
							kwg.getKeywordList().add(aux);
						} else {
							temp.setRepeatedTimesPerText(temp.getRepeatedTimesPerText() + k.getRepeatedTimesPerText());
							temp.setRepeatedTimesPerTopic(temp.getRepeatedTimesPerTopic() + k.getRepeatedTimesPerTopic());
						}
					}
					
					break;
				}
			}
		}
		
		return keywordGroupList;
	}
}
