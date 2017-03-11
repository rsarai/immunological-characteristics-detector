package com.SCGEOrientaIA.dao.util.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.SCGEOrientaIA.dao.database.ADatabase;
import com.SCGEOrientaIA.dao.database.SQLServer.SQLDatabase;
import com.SCGEOrientaIA.dao.util.ADAOPerguntaResposta;
import com.SCGEOrientaIA.util.Assunto;
import com.SCGEOrientaIA.util.IndiceSatisfacao;
import com.SCGEOrientaIA.util.Keyword;
import com.SCGEOrientaIA.util.Pergunta;
import com.SCGEOrientaIA.util.PerguntaResposta;
import com.SCGEOrientaIA.util.Resposta;
import com.SCGEOrientaIA.util.UnidadeGestora;

public class SQLServerPerguntaResposta extends ADAOPerguntaResposta {

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
	public List<PerguntaResposta> retrive(PerguntaResposta filter) {
		ADatabase db = new SQLDatabase();
		
		try {
			db.openConnection();

			Statement stmt = db.getConnection().createStatement();
			StringBuilder queryString = new StringBuilder();

			queryString.append(" select p.id, p.pergunta, p.temTratamento, r.id, r.resposta, a.id, a.assunto, i.satisfacao, u.id, u.unidadeGestora, pk.keyword, pk.repeatedTimes, vs.valorSatisfacao ")
					   .append(" from Pergunta_Resposta as pr ")
					   .append(" inner join pergunta_keywords as pk on pk.id_p = pr.id_p ")
					   .append(" inner join pergunta as p on p.id = pr.id_p ")
					   .append(" inner join resposta as r on r.id = pr.id_r ")
					   .append(" inner join assunto as a on a.id = pr.id_a ")
					   .append(" inner join indiceSatisfacao as i on i.id = pr.id_i ")
					   .append(" inner join unidadeGestora as u on u.id = pr.id_u ")
					   .append(" inner join ")
					   .append(" ( ")
					   .append("   select id, sum(valorSatisfacao) as valorSatisfacao from ")
					   .append("     (")
					   .append("       select p.id, (count(*)*i.constanteSatisfacao) as valorSatisfacao from pergunta as p ")
					   .append("       inner join pergunta_resposta as pr on pr.id_p = p.id ")
					   .append("       inner join indiceSatisfacao as i on pr.id_i = i.id ")
					   .append("       group by p.id, i.constanteSatisfacao ")
					   .append("     ) as valorSatisfacao ")
					   .append("   group by id ")
					   .append(" ) as vs on vs.id = p.id ");

			
			if (filter != null){
				Boolean hasWhere = false;
				
				//Filtrar por assunto
				if (filter.getAssunto() != null){
					if (filter.getAssunto().getAssunto() != null && !filter.getAssunto().getAssunto().equals("")){
						hasWhere = true;
						queryString.append(" where a.assunto = '")
								   .append(filter.getAssunto().getAssunto())
								   .append("' ");
					}
					
					if (filter.getAssunto().getId()!= 0){
						if (!hasWhere){
							hasWhere = true;
							queryString.append(" where ");
						} else {
							queryString.append(" and ");
						}
						queryString.append("a.id = ")
								   .append(filter.getAssunto().getId())
								   .append(" ");
					}
				}
				
				//Filtrar por indice de satisfação
				if (filter.getIndiceSatisfacao() != null){
					if (!hasWhere){
						hasWhere = true;
						queryString.append(" where ");
					} else {
						queryString.append(" and ");
					}
					queryString.append(" satisfacao = ' ")
							   .append(filter.getIndiceSatisfacao().toString())
							   .append("' ");
				}
				
				//Filtrar por pergunta
				if (filter.getPergunta() != null){
					if (filter.getPergunta().getId()!= 0){
						if (!hasWhere){
							hasWhere = true;
							queryString.append(" where ");
						} else {
							queryString.append(" and ");
						}
						queryString.append(" p.id = ")
								   .append(filter.getPergunta().getId())
								   .append(" ");
					}
					
					if (filter.getPergunta().getPergunta() != null && !filter.getPergunta().getPergunta().equals("")){
						if (!hasWhere){
							hasWhere = true;
							queryString.append(" where ");
						} else {
							queryString.append(" and ");
						}
						queryString.append(" pergunta = '")
								   .append(filter.getPergunta().getPergunta())
								   .append("' ");
					}
					
					if (!hasWhere){
						hasWhere = true;
						queryString.append(" where ");
					} else {
						queryString.append(" and ");
					}
					
					queryString.append(" temTratamento = ")
							   .append(filter.getPergunta().isTemTratamento()?1:0)
							   .append(" ");
				}
				
				//Filtrar por resposta 
				if (filter.getResposta() != null){
					if (filter.getPergunta().getId() != 0){
						if (!hasWhere){
							hasWhere = true;
							queryString.append(" where ");
						} else {
							queryString.append(" and ");
						}
						queryString.append(" r.id = ")
								   .append(filter.getPergunta().getId())
								   .append(" ");
					}
					
					if (filter.getResposta().getResposta() != null && !filter.getResposta().getResposta().equals("")){
						if (!hasWhere){
							hasWhere = true;
							queryString.append(" where ");
						} else {
							queryString.append(" and ");
						}
						queryString.append(" resposta = '")
								   .append(filter.getPergunta().getPergunta())
								   .append("' ");
					}
				}
				
				//Filter por unidade gestora
				if (filter.getUnidadeGestora() != null){
					if (filter.getUnidadeGestora().getId() != 0){
						if (!hasWhere){
							hasWhere = true;
							queryString.append(" where ");
						} else {
							queryString.append(" and ");
						}
						queryString.append(" u.id = ")
								   .append(filter.getUnidadeGestora().getId())
								   .append(" ");
					}
					
					if (filter.getUnidadeGestora().getUnidadeGestora() != null && !filter.getUnidadeGestora().getUnidadeGestora().equals("")){
						if (!hasWhere){
							hasWhere = true;
							queryString.append(" where ");
						} else {
							queryString.append(" and ");
						}
						queryString.append(" unidadeGestora = '")
						   .append(filter.getUnidadeGestora().getUnidadeGestora())
						   .append("' ");
					}
				}				
			}
			
			queryString.append(" group by p.id, p.pergunta, p.temTratamento, r.id, r.resposta, a.id, a.assunto, i.satisfacao, u.id, u.unidadeGestora, pk.keyword, pk.repeatedTimes, vs.valorSatisfacao ")
			   		   .append(" order by p.id asc ");
			
			ResultSet rs = stmt.executeQuery(queryString.toString());

			List<PerguntaResposta> lPerguntaResposta = new ArrayList<PerguntaResposta>();
			Pergunta oldP = null;
			while (rs.next()){				
				Pergunta newP = new Pergunta(rs.getInt(1), rs.getString(2), (rs.getInt(3) == 1));
				Resposta r = new Resposta(rs.getInt(4), rs.getString(5));
				Assunto a = new Assunto(rs.getInt(6), rs.getString(7));
				IndiceSatisfacao i = IndiceSatisfacao.classify(rs.getString(8));
				UnidadeGestora u = new UnidadeGestora(rs.getInt(9), rs.getString(10));
				double vs = rs.getFloat(13);
				
				if (rs.getString(11) != null){
					Keyword newK = new Keyword(rs.getString(11), rs.getInt(12));
					
					if (oldP != null && (oldP.getId() == newP.getId())){
						oldP.getKeywordList().add(newK);
						continue;
					} else {
						newP.getKeywordList().add(newK);
						lPerguntaResposta.add(new PerguntaResposta(a, newP, r, u, i, vs));
					}
				} else {
					lPerguntaResposta.add(new PerguntaResposta(a, newP, r, u, i, vs));
				}
				
				oldP = newP;
			}

			db.CloseConnection();
			
			return lPerguntaResposta;
			
		} catch(Exception e){
			System.out.println(e.toString());
		}

		return null;
	}

}
