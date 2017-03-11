package com.SCGEOrientaIA.dao.util;

import java.util.List;

import com.SCGEOrientaIA.util.Pergunta;

public abstract class ADAOPergunta implements IObjectDAO {
	/**
	 * @author Carlos
	 * Método retorna lista de Perguntas, para não filtrar deve-se passar como parâmetro NULL.
	 * <br><b>Pergunta.temTratento sempre será filtrado.</b><br>
	 * Para filtrar por Pergunta deve ser usado como parâmetro o seguinte objeto:<br>new Pergunta(0, "pergunta", false (ou true));<br>
	 * Para filtrar por ID deve ser usado como parâmetro o seguinte objeto:<br>new Pergunta(ID, "" (ou null), false (ou true));
	 */
	public abstract List<Pergunta> retrive(Pergunta filter);
	public abstract Boolean registerKeywords(Pergunta pergunta);
}
