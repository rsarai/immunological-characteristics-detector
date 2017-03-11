package com.SCGEOrientaIA.dao.util.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.SCGEOrientaIA.dao.database.ADatabase;
import com.SCGEOrientaIA.dao.database.SQLServer.SQLDatabase;
import com.SCGEOrientaIA.dao.util.ADAOUnidadeGestora;
import com.SCGEOrientaIA.util.UnidadeGestora;

public class SQLServerUnidadeGestora extends ADAOUnidadeGestora {

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
	public List<UnidadeGestora> retrive(UnidadeGestora filter) {
		ADatabase db = new SQLDatabase();
		
		try {
			db.openConnection();

			Statement stmt = db.getConnection().createStatement();
			StringBuilder queryString = new StringBuilder();

			if (filter == null){
				queryString.append("Select * from UnidadeGestora;");
			} else {
				Boolean hasWhere = false;
				
				queryString.append("Select * from UnidadeGestora ");
				
				if (filter.getId() != 0){
					hasWhere = true;
					queryString.append(" where id = ")
							   .append(filter.getId())
							   .append(" ");
				}
				
				if (filter.getUnidadeGestora() != null && !filter.getUnidadeGestora().equals("")){
					if (!hasWhere){
						hasWhere = true;
						queryString.append(" where UnidadeGestora = '");
					} else {
						queryString.append(" and UnidadeGestora = '");
					}
					queryString.append(filter.getUnidadeGestora())
							   .append("' ");
				}
			}
			
			ResultSet rs = stmt.executeQuery(queryString.toString());

			List<UnidadeGestora> lUnidadeGestora = new ArrayList<UnidadeGestora>();
			while (rs.next()){
				lUnidadeGestora.add(new UnidadeGestora(rs.getInt(1), rs.getString(2)));
			}

			db.CloseConnection();
			
			return lUnidadeGestora;
			
		} catch(Exception e){
			System.out.println(e.toString());
		}

		return null;
	}

}
