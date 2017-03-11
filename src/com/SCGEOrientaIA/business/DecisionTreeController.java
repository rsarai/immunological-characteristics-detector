package com.SCGEOrientaIA.business;

import java.util.ArrayList;
import java.util.List;

import com.SCGEOrientaIA.algorithms.Clonalg;
import com.SCGEOrientaIA.dao.util.ADAOPerguntaResposta;
import com.SCGEOrientaIA.dao.util.jdbc.SQLServerPerguntaResposta;
import com.SCGEOrientaIA.helper.DecisionTreeHelper;
import com.SCGEOrientaIA.helper.KeywordHelper;
import com.SCGEOrientaIA.helper.PerguntaRespostaHelper;
import com.SCGEOrientaIA.util.Antibody;
import com.SCGEOrientaIA.util.EntropyNode;
import com.SCGEOrientaIA.util.Keyword;
import com.SCGEOrientaIA.util.KeywordGroup;
import com.SCGEOrientaIA.util.Pergunta;
import com.SCGEOrientaIA.util.PerguntaResposta;

public class DecisionTreeController {
	
	/**
	 * Dado como entrada uma pergunta, ele é capaz de achar as respostas que mais se enquadram a mesma.
	 * Para a maximização do acerto do método, a pergunta deve ser bem elaborada. 
	 * @param pergunta
	 * @param numberOfAnswers
	 * @return List<PerguntaResposta>
	 */
	public List<PerguntaResposta> answerQuestion(Pergunta pergunta, int numberOfAnswers, boolean isUsingImmunologicalSystems){
		List<EntropyNode> entropyNodeList = (isUsingImmunologicalSystems) ? instanceEntropyTreeWithImmunological(pergunta) : instanceEntropyTree();
		
		PerguntaResposta actualPerguntaResposta, closestPerguntaResposta = null;
		
		List<PerguntaResposta> closestAnswers = new ArrayList<PerguntaResposta>();
		
		
		//Registra todas as respostas que têm um percentual de proximidade superior a 0.
		for(EntropyNode en : entropyNodeList){
			actualPerguntaResposta = searchOnEntropyNode(pergunta, en, 0);
			actualPerguntaResposta.setQuestionAccuracy(PerguntaRespostaHelper.calculateMatchPrecision(pergunta, actualPerguntaResposta));
			if (actualPerguntaResposta.getQuestionAccuracy() > 0){
				closestAnswers.add(actualPerguntaResposta);
			}
		}
		
		//Seleciona as melhores respostas baseadas em sua precisão somada com o cálculo do valor de satisfação. (Feedback do usuário)
		while(numberOfAnswers < closestAnswers.size()){
			double closestPrecision = 100;
			int closestPrecisionIndex = 0;
			for (int i = 0; i < closestAnswers.size(); i++){
				if (closestAnswers.get(i).getQuestionAccuracy() + closestAnswers.get(i).getValorSatisfacao() < closestPrecision){
					closestPrecision = closestAnswers.get(i).getQuestionAccuracy() + closestAnswers.get(i).getValorSatisfacao();
					closestPrecisionIndex = i;
				}
			}
			closestAnswers.remove(closestPrecisionIndex);
		}
		
		return closestAnswers;
	}
	
	/**
	 * Método criado apenas para debug, ele avalia a precisão da árvore.
	 * @return double
	 */
	private double testTreeAccuracy(){
		ADAOPerguntaResposta pDB = new SQLServerPerguntaResposta();
		
		//tratamento						//todas as perguntas do sistema     //só as perguntas tratadas
		List<PerguntaResposta> completeList = pDB.retrive(null); 				//pDB.retrive(new PerguntaResposta(null, new Pergunta(0, null, true), null, null, null));
		List<PerguntaResposta> prunningSet = new ArrayList<PerguntaResposta>();
		List<PerguntaResposta> growingSet = new ArrayList<PerguntaResposta>(); 
		
		List<Integer> lIndex = DecisionTreeHelper.returnRandomValues(completeList.size());
		
		for (int i = 0; i < completeList.size(); i++){
			if (lIndex.contains(i)){
				prunningSet.add(completeList.get(i));
			} else {
				growingSet.add(completeList.get(i));
			}
		}
		
		List<EntropyNode> entropyNodeList = DecisionTreeHelper.perguntaRespostaListToEntropyNodeTreeList(completeList);
		
		double accuracy = 0;
		for (PerguntaResposta p : prunningSet){
			PerguntaResposta actualPerguntaResposta, closestPerguntaResposta = null;
			double closestPrecision = 0, actualPrecision;
			for(EntropyNode en : entropyNodeList){
				actualPerguntaResposta = searchOnEntropyNode(p.getPergunta(), en, 0);
				actualPrecision = PerguntaRespostaHelper.calculateMatchPrecision(p.getPergunta(), actualPerguntaResposta);
				
				if (actualPrecision > closestPrecision){
					closestPrecision = actualPrecision;
					closestPerguntaResposta = actualPerguntaResposta;
				}
			}
			
			if (p.getResposta().getResposta().equals(p.getPergunta().getPergunta())){
				accuracy++;
			}
		}
		
		
		return accuracy;
	}
	
	/**
	 * Retorna nós raízes de uma árvore, classificados por Assunto. 
	 * @return List<EntropyNode>
	 */
	public List<EntropyNode> instanceEntropyTreeWithImmunological(Pergunta pergunta){
		
		ADAOPerguntaResposta pDB = new SQLServerPerguntaResposta();
		//tratamento						//só as perguntas tratadas		                                                          //todas as perguntas do sistema
		List<Antibody> abList = Clonalg.saidaArvoreDecisao(pergunta);
		List<PerguntaResposta> completeList = pDB.retrive(new PerguntaResposta(null, new Pergunta(0, null, true), null, null, null, 0));		
		List<PerguntaResposta> matches = new ArrayList<PerguntaResposta>();
		
		for (Antibody ab : abList){
			for (PerguntaResposta pr : completeList){
				if (pr.getPergunta().getPergunta().equals(ab.getDescription())){
					matches.add(pr);
					break;
				}
			}
		}
		
		return DecisionTreeHelper.perguntaRespostaListToEntropyNodeTreeList(completeList);
	}
	
	/**
	 * Retorna nós raízes de uma árvore, classificados por Assunto. 
	 * @return List<EntropyNode>
	 */
	public List<EntropyNode> instanceEntropyTree(){
		
		ADAOPerguntaResposta pDB = new SQLServerPerguntaResposta();
		//tratamento						//só as perguntas tratadas		                                                          //todas as perguntas do sistema
		List<PerguntaResposta> completeList = pDB.retrive(new PerguntaResposta(null, new Pergunta(0, null, true), null, null, null, 0)); //= pDB.retrive(null); 				
		return DecisionTreeHelper.perguntaRespostaListToEntropyNodeTreeList(completeList);
	}
	
	/**
	 * Retorna uma PerguntaResposta que se assemelha a pergunta realizada. Método recursivo, a cada vez
	 * que ele é re-executado matches e entropyNode variam.
	 * @param pergunta
	 * @param entropyNode
	 * @param matches
	 * @return
	 */
	
	private PerguntaResposta searchOnEntropyNode(Pergunta pergunta, EntropyNode entropyNode, int matches){

		if (entropyNode.getEntropy() == 1.0d || matches == pergunta.getKeywordList().size()){
			return entropyNode.getPerguntaRespostaList().get(0);
		}
		
		for(Keyword kw : pergunta.getKeywordList()){
			if (entropyNode.getKeyword().getKeyword().equals(kw.getKeyword())){
				if (entropyNode.getLeftNode() == null){
					return entropyNode.getPerguntaRespostaList().get(0);
				} else {
					return searchOnEntropyNode(pergunta, entropyNode.getLeftNode(), matches+1);
				}
			}
		}
		
		if (entropyNode.getRightNode() == null){
			return entropyNode.getPerguntaRespostaList().get(0);
		} else {
			return searchOnEntropyNode(pergunta, entropyNode.getRightNode(), matches);
		}
	}
}
