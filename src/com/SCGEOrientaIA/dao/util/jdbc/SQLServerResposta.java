package com.SCGEOrientaIA.dao.util.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.SCGEOrientaIA.dao.database.ADatabase;
import com.SCGEOrientaIA.dao.database.SQLServer.SQLDatabase;
import com.SCGEOrientaIA.dao.util.ADAOResposta;
import com.SCGEOrientaIA.util.Resposta;

public class SQLServerResposta extends ADAOResposta {

	@Override
	public boolean create(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Resposta> retrive(Resposta filter) {
		// TODO Auto-generated method stub
		ADatabase db = new SQLDatabase();

		try {
			db.openConnection();

			Statement stmt = db.getConnection().createStatement();
			StringBuilder queryString = new StringBuilder();

			if (filter == null){
				queryString.append("Select * from Resposta;");
			} else {
				queryString.append("Select * from Resposta");

				Boolean hasWhere = false;
				
				if (filter.getId() != 0)
				{
					hasWhere = true;
					
					queryString.append(" where id = ")
							   .append(filter.getId())
							   .append(" ");
				}
				
				if (filter.getResposta() != null && !filter.getResposta().equals("")) {
					if (!hasWhere){
						hasWhere = true;
						queryString.append(" where resposta = '");
					} else {
						queryString.append(" and resposta = '");
					}
					queryString.append(filter.getResposta())
							   .append("' ");
				}
			}

			ResultSet rs = stmt.executeQuery(queryString.toString());

			List<Resposta> lResposta = new ArrayList<Resposta>();
			while (rs.next()){
				lResposta.add(new Resposta(rs.getInt(1), rs.getString(2)));
			}

			db.CloseConnection();

			return lResposta;

		} catch(Exception e){
			System.out.println(e.toString());
		}

		return null;
	}

}
