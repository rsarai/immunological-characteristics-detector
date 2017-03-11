package com.SCGEOrientaIA.dao.util;

import java.util.List;

import com.SCGEOrientaIA.util.Pergunta;

public abstract class ADAOPergunta implements IObjectDAO {
	/**
	 * @author Carlos
	 * M�todo retorna lista de Perguntas, para n�o filtrar deve-se passar como par�metro NULL.
	 * <br><b>Pergunta.temTratento sempre ser� filtrado.</b><br>
	 * Para filtrar por Pergunta deve ser usado como par�metro o seguinte objeto:<br>new Pergunta(0, "pergunta", false (ou true));<br>
	 * Para filtrar por ID deve ser usado como par�metro o seguinte objeto:<br>new Pergunta(ID, "" (ou null), false (ou true));
	 */
	public abstract List<Pergunta> retrive(Pergunta filter);
	public abstract Boolean registerKeywords(Pergunta pergunta);
}
