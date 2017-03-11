package com.SCGEOrientaIA.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.SCGEOrientaIA.util.Assunto;
import com.SCGEOrientaIA.util.EntropyNode;
import com.SCGEOrientaIA.util.Keyword;
import com.SCGEOrientaIA.util.KeywordGroup;
import com.SCGEOrientaIA.util.PerguntaResposta;

public class DecisionTreeHelper {
	/**
	 * Converte uma List<KeywordGroup> (DEVE ESTAR ORDENADA) em uma Lista de Nós raízes <EntropyNode>, classificadas por assunto.  
	 * @return
	 */
	public static List<EntropyNode> perguntaRespostaListToEntropyNodeTreeList(List<PerguntaResposta> perguntaRespostaList){
		
		List<EntropyNode> entropyNodeList = new ArrayList<EntropyNode>();
		
		List<Assunto> assList = PerguntaRespostaHelper.getAssuntoListByPerguntaRespostaList(perguntaRespostaList);
		
		for(Assunto a : assList){
			//Filtrar todas as PERGUNTAS por assunto
			List<PerguntaResposta> prList = PerguntaRespostaHelper.filterPerguntaRespostaByTopic(perguntaRespostaList, a);
			entropyNodeList.add(
					prepareEntropyTree(prList)
			);
		}
		
		return entropyNodeList;
	}
	
	/**
	 * Gera uma árvore com base em uma lista de perguntas. Os nós são filtrados por assuntos, verifica-se e exclui as
	 * palavras-chave repetidas para todas as perguntas, e tem-se então a possibilidade de calcular a sua entropia. 
	 * @return  
	 */
	public static EntropyNode prepareEntropyTree(List<PerguntaResposta> completePerguntaRespostaList){

		//Caso base para recursão.
		if (completePerguntaRespostaList == null)
			return null;
		
		if (completePerguntaRespostaList.size() <= 1){
			return new EntropyNode(null, completePerguntaRespostaList, 1);
		}
		
		//Número de perguntas
		int ammountOfQuestions = completePerguntaRespostaList.size();
		
		//Listar todas as keywords relativas ao assunto da listaCompletaPerguntaResposta, que está classificada por assunto
		List<KeywordGroup> kgList = KeywordHelper.perguntaRespostaToKeywordGroupList(completePerguntaRespostaList);
		
		//Lista de nós de entropia que serão avaliados
		EntropyNode biggerEntropyNode = new EntropyNode();
					
		for (Keyword kw : kgList.get(0).getKeywordList()){
			//Filtrar todas as perguntas que possuem uma keyword específica
			List<PerguntaResposta> prList = PerguntaRespostaHelper.filterPerguntaRespostaByKeyword(completePerguntaRespostaList, kw, true);
			int ammountOfTimesKeywordWasFoundInQuestions = prList.size();
			
			double entropy = EntropyHelper.calculateEntropy(ammountOfTimesKeywordWasFoundInQuestions, ammountOfQuestions);
						
			if (biggerEntropyNode != null && biggerEntropyNode.getEntropy() < entropy){
				biggerEntropyNode = new EntropyNode(kw, prList, entropy);
			}
		}
		
		//Não há mais nós calculáveis na árvore
		if (biggerEntropyNode.getKeyword() == null)
			return null;
				
		biggerEntropyNode.setLeftNode(prepareEntropyTree(biggerEntropyNode.getPerguntaRespostaList()));
		biggerEntropyNode.setRightNode(prepareEntropyTree(PerguntaRespostaHelper.filterPerguntaRespostaByKeyword(completePerguntaRespostaList, biggerEntropyNode.getKeyword(), false)));
		
		return biggerEntropyNode;
	}
	
	/**
	 * Método usado para criação de um PrunningSet e um GrowingSet, que são usados para
	 * fazer testes da precisão da árvore. Método criado mas não utilizado em sua geração final.	
	 * @param length
	 * @return
	 */
	public static List<Integer> returnRandomValues(int length){
		List<Integer> lInt = new ArrayList<Integer>();
		Random r = new Random();
		int aux;
		while (length/3 > lInt.size()){
			aux = r.nextInt(length);
			if (!lInt.contains(aux)){
				lInt.add(aux);
			}
		}
		return lInt;
	}
}
