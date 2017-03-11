package com.SCGEOrientaIA.business;

import java.util.List;

import com.SCGEOrientaIA.dao.util.ADAOPergunta;
import com.SCGEOrientaIA.dao.util.jdbc.SQLServerPergunta;
import com.SCGEOrientaIA.helper.PreProcess;
import com.SCGEOrientaIA.util.Keyword;
import com.SCGEOrientaIA.util.Pergunta;

public class PerguntaController {
	/**
	 * Gera e insere palavras chave para cada pergunta existente no banco de dados, deve ser executado
	 * apenas uma vez. Se existir necessidade de us�-lo para re-cadastrar perguntas, a tabela
	 * Pergunta_Keywords deve ter todos os seus registros apagados. A precis�o da �rvore pode ser
	 * superior caso o m�todo <b>PreProcess.cleanQuestions()</b> seja refinado. Atualmente ele exclui
	 * algumas palavras que n�s consideramos n�o ter muita relev�ncia para o processo de cria��o.
	 * @return 
	 **/
	public boolean RetriveAndInsertKeywordsFromPergunta(){
		ADAOPergunta prDAO = new SQLServerPergunta();
		List<Pergunta> prList = prDAO.retrive(null);
		for (Pergunta pr : prList){
			for (String s : PreProcess.cleanQuestion(pr.getPergunta()).split(" ")){
				boolean hasRepetition = false;
				
				for (Keyword kw : pr.getKeywordList()){
					if (kw.getKeyword().equals(s)){
						hasRepetition = true;
						kw.setRepeatedTimesPerText(kw.getRepeatedTimesPerText() + 1);
						break;
					}
				}
				
				if (!hasRepetition){
					pr.getKeywordList().add(new Keyword(s, 1));
				}
			}
			prDAO.registerKeywords(pr);
		}
		return true;
	}
}
