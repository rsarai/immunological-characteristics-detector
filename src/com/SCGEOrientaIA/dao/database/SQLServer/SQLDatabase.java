package com.SCGEOrientaIA.dao.database.SQLServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.SCGEOrientaIA.dao.database.ADatabase;
import com.SCGEOrientaIA.programSettings.Config;

public class SQLDatabase extends ADatabase {

	public SQLDatabase() {
		super(Config.SQLServerDBAdress, Config.SQLServerDBLogin, Config.SQLServerDBPassword);
	}

	@Override
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return this.connection = DriverManager.getConnection(this.dbAdress, this.dbLogin, this.dbPassword);
	}

	@Override
	public void CloseConnection() throws SQLException {
		this.connection.close();
	}
	
}
