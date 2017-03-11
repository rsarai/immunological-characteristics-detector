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
	 * Converte uma List<KeywordGroup> (DEVE ESTAR ORDENADA) em uma Lista de N�s ra�zes <EntropyNode>, classificadas por assunto.  
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
	 * Gera uma �rvore com base em uma lista de perguntas. Os n�s s�o filtrados por assuntos, verifica-se e exclui as
	 * palavras-chave repetidas para todas as perguntas, e tem-se ent�o a possibilidade de calcular a sua entropia. 
	 * @return  
	 */
	public static EntropyNode prepareEntropyTree(List<PerguntaResposta> completePerguntaRespostaList){

		//Caso base para recurs�o.
		if (completePerguntaRespostaList == null)
			return null;
		
		if (completePerguntaRespostaList.size() <= 1){
			return new EntropyNode(null, completePerguntaRespostaList, 1);
		}
		
		//N�mero de perguntas
		int ammountOfQuestions = completePerguntaRespostaList.size();
		
		//Listar todas as keywords relativas ao assunto da listaCompletaPerguntaResposta, que est� classificada por assunto
		List<KeywordGroup> kgList = KeywordHelper.perguntaRespostaToKeywordGroupList(completePerguntaRespostaList);
		
		//Lista de n�s de entropia que ser�o avaliados
		EntropyNode biggerEntropyNode = new EntropyNode();
					
		for (Keyword kw : kgList.get(0).getKeywordList()){
			//Filtrar todas as perguntas que possuem uma keyword espec�fica
			List<PerguntaResposta> prList = PerguntaRespostaHelper.filterPerguntaRespostaByKeyword(completePerguntaRespostaList, kw, true);
			int ammountOfTimesKeywordWasFoundInQuestions = prList.size();
			
			double entropy = EntropyHelper.calculateEntropy(ammountOfTimesKeywordWasFoundInQuestions, ammountOfQuestions);
						
			if (biggerEntropyNode != null && biggerEntropyNode.getEntropy() < entropy){
				biggerEntropyNode = new EntropyNode(kw, prList, entropy);
			}
		}
		
		//N�o h� mais n�s calcul�veis na �rvore
		if (biggerEntropyNode.getKeyword() == null)
			return null;
				
		biggerEntropyNode.setLeftNode(prepareEntropyTree(biggerEntropyNode.getPerguntaRespostaList()));
		biggerEntropyNode.setRightNode(prepareEntropyTree(PerguntaRespostaHelper.filterPerguntaRespostaByKeyword(completePerguntaRespostaList, biggerEntropyNode.getKeyword(), false)));
		
		return biggerEntropyNode;
	}
	
	/**
	 * M�todo usado para cria��o de um PrunningSet e um GrowingSet, que s�o usados para
	 * fazer testes da precis�o da �rvore. M�todo criado mas n�o utilizado em sua gera��o final.	
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
