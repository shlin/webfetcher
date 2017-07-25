package org.pstar.webfetcher.jdbc.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector implements MySQLConnectorImpl {
	private Connection conn;

	private String strConnectionUrl;
	private String strDatabase;
	private String strHost;
	private String strPassword;
	private String strUsername;

	/**
	 * @param host
	 * @param username
	 * @param password
	 * @param database
	 */
	public MySQLConnector(String host, String username, String password, String database) {
		this.strHost = host;
		this.strUsername = username;
		this.strPassword = password;
		this.strDatabase = database;
	}

	@Override
	public Connection getConnection() {
		if (this.conn == null)
			this.initConnection();
		return this.conn;
	}

	/**
	 * @return the strDatabase
	 */
	public String getStrDatabase() {
		return strDatabase;
	}

	/**
	 * @return the strHost
	 */
	public String getStrHost() {
		return strHost;
	}

	/**
	 * @return the strUsername
	 */
	public String getStrUsername() {
		return strUsername;
	}

	private void initConnection() {
		this.strConnectionUrl = String.format("jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=utf8",
				this.strHost, this.strDatabase);

		try {
			this.initDriver();
			this.conn = DriverManager.getConnection(this.strConnectionUrl, this.strUsername, this.strPassword);
			// if (this.conn != null && !this.conn.isClosed())
			// System.out.println("資料庫連線成功！");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("找不到JDBC驅動程式類別");
			e.printStackTrace();
		}
	}

}
