package com.SCGEOrientaIA.business;

import java.util.List;

import com.SCGEOrientaIA.dao.util.ADAOPerguntaResposta;
import com.SCGEOrientaIA.dao.util.jdbc.SQLServerPerguntaResposta;
import com.SCGEOrientaIA.util.PerguntaResposta;

public class PerguntaRespostaController {
	/**
	 * Retorna uma lista de PerguntaResposta baseado em um filtro passado como parâmetro.
	 * @param perguntaResposta
	 * @return
	 */
	public List<PerguntaResposta> retrivePerguntaRespostaList(PerguntaResposta perguntaResposta){
		ADAOPerguntaResposta pDB = new SQLServerPerguntaResposta();
		return pDB.retrive(perguntaResposta);
	}
}
