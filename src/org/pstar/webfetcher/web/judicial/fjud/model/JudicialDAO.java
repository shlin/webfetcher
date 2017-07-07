package org.pstar.webfetcher.web.judicial.fjud.model;

import java.sql.SQLException;
import java.sql.Statement;

import org.pstar.webfetcher.jdbc.mysql.MySQLConnectorImpl;

public class JudicialDAO {
	private MySQLConnectorImpl mysql;
	private String tableName;

	public JudicialDAO(String table) {
		this.tableName = table;
	}

	/**
	 * @param mysql
	 * @param table
	 */
	public JudicialDAO(MySQLConnectorImpl mysql, String table) {
		this.mysql = mysql;
		this.tableName = table;
	}

	private String sqlCreateTable() {
		String sql = String.format("CREATE TABLE  IF NOT EXISTS `%s` ("
				+ "`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `court` VARCHAR(40) NOT NULL,"
				+ "`category` ENUM('刑事', '民事', '行政', '公懲') NOT NULL DEFAULT '刑事', `date` DATE NOT NULL,"
				+ "`jud_id` VARCHAR(30) NOT NULL, `jud_title` VARCHAR(100) NOT NULL,"
				+ "`content` MEDIUMTEXT NOT NULL, PRIMARY KEY (`id`)," + "INDEX `court` (`court` ASC),"
				+ "INDEX `category` (`category` ASC), UNIQUE INDEX `jud_id` (`date` ASC, `jud_id` ASC),"
				+ "INDEX `date` (`date` ASC)) CHARACTER SET utf8, COLLATE utf8_general_ci;", this.tableName);

		return sql;
	}

	private String sqlInsert(Judicial data) {
		String sql = String.format(
				"INSERT INTO `%s`(`court`, `date`, `jud_id`, `jud_title`, `content`) VALUES('%s','%s','%s','%s','%s');",
				this.tableName, data.getCourt(), data.getGeneralDate(), data.getJudId(), data.getJudTitle(),
				data.getContent());
		return sql;
	}

	public void createTable() {
		try {
			Statement stmt = this.mysql.getConnection().createStatement();
			int rows = stmt.executeUpdate(this.sqlCreateTable());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String createTableToFile() {
		return this.sqlCreateTable();
	}

	public String saveDataToFile(Judicial data) {
		return this.sqlInsert(data);
	}

	public void saveDataToMySQL(Judicial data) {
		try {
			Statement stmt = this.mysql.getConnection().createStatement();
			int rows = stmt.executeUpdate(this.sqlInsert(data));
		} catch (SQLException e) {
			if (!e.getMessage().toLowerCase().contains("duplicate"))
				e.printStackTrace();
		}
	}
}
