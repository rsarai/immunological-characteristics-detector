package com.SCGEOrientaIA.dao.util;

import java.util.List;

import com.SCGEOrientaIA.util.Resposta;

public abstract class ADAOResposta implements IObjectDAO {
	public abstract List<Resposta> retrive(Resposta filter);
}
