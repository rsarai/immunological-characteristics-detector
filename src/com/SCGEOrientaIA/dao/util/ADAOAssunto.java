package com.SCGEOrientaIA.dao.util;

import java.util.List;

import com.SCGEOrientaIA.util.Assunto;

public abstract class ADAOAssunto implements IObjectDAO {

	public abstract List<Assunto> retrive(Assunto filter);

}
