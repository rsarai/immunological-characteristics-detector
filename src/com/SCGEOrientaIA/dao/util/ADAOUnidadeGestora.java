package com.SCGEOrientaIA.dao.util;

import java.util.List;

import com.SCGEOrientaIA.util.UnidadeGestora;

public abstract class ADAOUnidadeGestora implements IObjectDAO {
	public abstract List<UnidadeGestora> retrive(UnidadeGestora filter);
}
