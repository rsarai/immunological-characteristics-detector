package com.SCGEOrientaIA.dao.database;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ADatabase {
	protected String dbAdress, dbLogin, dbPassword;
	protected Connection connection;
	
	protected ADatabase(String dbAdress, String dbLogin, String dbPassword){
		this.dbAdress = dbAdress; this.dbLogin = dbLogin; this.dbPassword = dbPassword;
	}
	
	public abstract Connection openConnection() throws SQLException, ClassNotFoundException;
	public abstract void CloseConnection() throws SQLException;

	public Connection getConnection() {
		return connection;
	}
}
