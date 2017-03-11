package com.SCGEOrientaIA.dao.util.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.SCGEOrientaIA.dao.database.ADatabase;
import com.SCGEOrientaIA.dao.database.SQLServer.SQLDatabase;
import com.SCGEOrientaIA.dao.util.ADAOPergunta;
import com.SCGEOrientaIA.util.Keyword;
import com.SCGEOrientaIA.util.Pergunta;

public class SQLServerPergunta extends ADAOPergunta {
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
	public List<Pergunta> retrive(Pergunta filter) {
		// TODO Auto-generated method stub
		ADatabase db = new SQLDatabase();

		try {
			db.openConnection();

			Statement stmt = db.getConnection().createStatement();
			StringBuilder queryString = new StringBuilder();

			queryString.append("Select * from Pergunta as p left join pergunta_keywords as pk on p.id = pk.id_p");
			
			if (filter == null){
				queryString.append(";");
			} else {
				Boolean hasWhere = false;
				
				if (filter.getId() != 0)
				{
					hasWhere = true;
					
					queryString.append(" where id = ")
							   .append(filter.getId())
							   .append(" ");
				}
				
				if (filter.getPergunta() != null && !filter.getPergunta().equals("")) {
					if (!hasWhere){
						hasWhere = true;
						queryString.append(" where pergunta = '");
					} else {
						queryString.append(" and pergunta = '");
					}
					queryString.append(filter.getPergunta())
							   .append("' ");
				} 
				
				if (!hasWhere){
					hasWhere = true;
					queryString.append(" where temTratamento = ");
				}else{ 
					queryString.append(" and temTratamento = ");
				}
				queryString.append(filter.isTemTratamento()?1:0)
						   .append(";");
			}

			ResultSet rs = stmt.executeQuery(queryString.toString());

			List<Pergunta> lPergunta = new ArrayList<Pergunta>();
			
			Pergunta oldP = null;
			while (rs.next()){
				
				Pergunta newP = new Pergunta(rs.getInt(1), rs.getString(2), rs.getInt(3) == 1);
				
				if (rs.getString(5) != null){
					Keyword newK = new Keyword(rs.getString(5), rs.getInt(6));
	
					if (oldP != null && (oldP.getId() == newP.getId())){					
						oldP.getKeywordList().add(newK);
						continue;
					} else {
						lPergunta.add(newP);
					}
				} else {
					lPergunta.add(newP);
				}
				
				oldP = newP;
			}

			db.CloseConnection();

			return lPergunta;

		} catch(Exception e){
			System.out.println(e.toString());
		}

		return null;
	}

	@Override
	public Boolean registerKeywords(Pergunta pergunta) {
		ADatabase db = new SQLDatabase();

		try {
			db.openConnection();

			Statement stmt = db.getConnection().createStatement();
			StringBuilder queryString = new StringBuilder();

			for(Keyword keyword : pergunta.getKeywordList()){
				queryString.append(" insert into Pergunta_keywords (id_p, keyword, repeatedTimes) values (");
				queryString.append(pergunta.getId());
				queryString.append(", '");
				queryString.append(keyword.getKeyword());
				queryString.append("', ");
				queryString.append(keyword.getRepeatedTimesPerText());
				queryString.append(");");
			}
			
			stmt.execute(queryString.toString());

			db.CloseConnection();

			return true;

		} catch(Exception e){
			System.out.println(e.toString());
		}

		return false;
	}

}
