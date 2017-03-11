package com.SCGEOrientaIA.dao.util.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.SCGEOrientaIA.dao.database.ADatabase;
import com.SCGEOrientaIA.dao.database.SQLServer.SQLDatabase;
import com.SCGEOrientaIA.dao.util.ADAOAssunto;
import com.SCGEOrientaIA.util.Assunto;

public class SQLServerAssunto extends ADAOAssunto {

	@Override
	public boolean create(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Assunto> retrive(Assunto filter) {

		ADatabase db = new SQLDatabase();
		
		try {
			db.openConnection();

			Statement stmt = db.getConnection().createStatement();
			StringBuilder queryString = new StringBuilder();

			if (filter == null){
				queryString.append("Select * from Assunto;");
			} else {
				Boolean hasWhere = false;
				
				queryString.append("Select * from Assunto ");
				
				if (filter.getId() != 0){
					hasWhere = true;
					queryString.append(" where id = ")
							   .append(filter.getId())
							   .append(" ");
				}
				
				if (filter.getAssunto() != null && !filter.getAssunto().equals("")){
					if (!hasWhere){
						hasWhere = true;
						queryString.append(" where assunto = '");
					} else {
						queryString.append(" and assunto = '");
					}
					queryString.append(filter.getAssunto())
							   .append("' ");
				}
			}
			
			ResultSet rs = stmt.executeQuery(queryString.toString());

			List<Assunto> lAssunto = new ArrayList<Assunto>();
			while (rs.next()){
				lAssunto.add(new Assunto(rs.getInt(1), rs.getString(2)));
			}

			db.CloseConnection();
			
			return lAssunto;
			
		} catch(Exception e){
			System.out.println(e.toString());
		}

		return null;
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

}
