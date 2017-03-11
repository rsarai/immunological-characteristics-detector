package com.SCGEOrientaIA.dao.util;

import java.util.List;

import com.SCGEOrientaIA.util.PerguntaResposta;

public abstract class ADAOPerguntaResposta implements IObjectDAO {
	public abstract List<PerguntaResposta> retrive(PerguntaResposta filter);
}
