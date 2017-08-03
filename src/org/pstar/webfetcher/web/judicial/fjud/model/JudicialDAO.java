package org.pstar.webfetcher.web.judicial.fjud.model;

import java.sql.SQLException;
import java.sql.Statement;

import org.pstar.webfetcher.jdbc.mysql.MySQLConnectorImpl;

public class JudicialDAO {
	private MySQLConnectorImpl mysql;
	private String tableName;

	/**
	 * @param mysql
	 * @param table
	 */
	public JudicialDAO(MySQLConnectorImpl mysql, String table) {
		this.mysql = mysql;
		this.tableName = table;
		this.initMySQL();
	}

	public JudicialDAO(String table) {
		this.tableName = table;
	}

	public void createTable() {
		this.doStmtExecUpdate(this.sqlCreateTable());
	}

	public String createTableToFile() {
		return this.sqlCreateTable();
	}

	public void doDataClean() {
		this.doStmtExecUpdate(this.sqlCleanData());
	}

	public void doDataDeduplication() {
		this.doStmtExecUpdate(this.sqlDataDeduplication());
	}

	private void doStmtExecUpdate(String sql) {
		Statement stmt;

		try {
			stmt = this.mysql.getConnection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initMySQL() {
		this.doDataClean();
		this.doDataDeduplication();
	}

	public String saveDataToFile(Judicial data) {
		return this.sqlInsert(data);
	}

	public void saveDataToMySQL(Judicial data) {
		this.doStmtExecUpdate(this.sqlInsert(data));
	}

	private String sqlCleanData() {
		String sql = String.format("DELETE FROM `%s` WHERE `jud_title` LIKE '  %%' OR `jud_id` LIKE '  %%';",
				this.tableName);
		return sql;
	}

	private String sqlCreateTable() {
		return this.sqlCreateTable(this.tableName);
	}

	private String sqlCreateTable(String table) {
		String sql = String.format("CREATE TABLE  IF NOT EXISTS `%s` ("
				+ "`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `court` VARCHAR(40) NOT NULL,"
				+ "`category` ENUM('刑事', '民事', '行政', '公懲') NOT NULL DEFAULT '刑事', `date` DATE NOT NULL,"
				+ "`jud_id` VARCHAR(30) NOT NULL, `jud_title` VARCHAR(100) NOT NULL,"
				+ "`content` MEDIUMTEXT NOT NULL, PRIMARY KEY (`id`)," + "INDEX `court` (`court` ASC),"
				+ "INDEX `category` (`category` ASC), UNIQUE INDEX `jud_id` (`date` ASC, `jud_id` ASC))"
				+ " CHARACTER SET utf8, COLLATE utf8_general_ci;", table);

		return sql;
	}

	private String sqlDataDeduplication() {
		String sql = String.format(
				"DELETE FROM `%s` WHERE `id` NOT IN (SELECT `tid` FROM(SELECT MIN(id) AS tid  FROM `%s` GROUP BY `court`, `date`, `jud_id`) AS T);",
				this.tableName, this.tableName);
		return sql;
	}

	private String sqlInsert(Judicial data) {
		String sql = String.format(
				"INSERT INTO `%s`(`court`, `date`, `jud_id`, `jud_title`, `content`) VALUES('%s','%s','%s','%s','%s');",
				this.tableName, data.getCourt(), data.getGeneralDate(), data.getJudId(), data.getJudTitle(),
				data.getContent().replaceAll("'", "''"));
		return sql;
	}
}
