package org.pstar.webfetcher.web.judicial.fjud.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

import org.pstar.webfetcher.jdbc.mysql.MySQLConnectorImpl;

public class JudicialDAO {
	private HashMap<String, Judicial> fullTable;
	private MySQLConnectorImpl mysql;
	private HashSet<Integer> removeSet;
	private String tableName;

	/**
	 * @param mysql
	 * @param table
	 */
	public JudicialDAO(MySQLConnectorImpl mysql, String table) {
		this.mysql = mysql;
		this.tableName = table;
		this.fullTable = new HashMap<String, Judicial>();
		this.removeSet = new HashSet<Integer>();
		this.initMySQL();
	}

	public JudicialDAO(String table) {
		this.tableName = table;
	}

	public void createTable() {
		try {
			Statement stmt = this.mysql.getConnection().createStatement();
			stmt.executeUpdate(this.sqlCreateTable());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String createTableToFile() {
		return this.sqlCreateTable();
	}

	private void doRemoveData(String table, int id) {
		try {
			String sql = String.format("DELETE FROM `%s` WHERE `id` = '%d';", table, id);
			Statement stmt = this.mysql.getConnection().createStatement();

			stmt.executeUpdate(sql);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// private void doDataDeduplication() {
	// try {
	// Statement stmt = this.mysql.getConnection().createStatement();
	// stmt.executeUpdate(this.sqlDropTable("tmpTable"));
	// stmt.executeUpdate(this.sqlCreateTable("tmpTable"));
	// stmt.executeUpdate(this.sqlCloneUniqueDataToTable("tmpTable"));
	// stmt.executeUpdate(this.sqlDropTable(this.tableName));
	// stmt.executeUpdate(this.sqlRenameTable("tmpTable", this.tableName));
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * @return the fullTable
	 */
	public HashMap<String, Judicial> getFullTable() {
		return fullTable;
	}

	private void initMySQL() {
		// int fullTotal = 0, uniqueTotal = 0;
		ResultSet rs;
		Statement stmt;

		try {
			stmt = this.mysql.getConnection().createStatement();
			stmt.executeUpdate(this.sqlCleanData());

			rs = stmt.executeQuery(this.sqlSelectAllData());
			while (rs.next()) {
				int dataId = rs.getInt("id");
				String court = rs.getString("court");
				String date = rs.getDate("date").toString();
				String jud_id = rs.getString("jud_id");
				String jud_title = rs.getString("jud_title");
				String content = rs.getString("content");

				Judicial currentRow = new Judicial(court, date, jud_id, jud_title, content, 0);

				if (!this.fullTable.containsKey(currentRow.getUUID())) {
					this.fullTable.put(currentRow.getUUID(), currentRow);
				} else {
					this.removeSet.add(dataId);
				}
			}
			stmt.close();

			this.removeSet.forEach(id -> this.doRemoveData(this.tableName, id));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String saveDataToFile(Judicial data) {
		return this.sqlInsert(data);
	}

	public void saveDataToMySQL(Judicial data) {
		try {
			if (!this.fullTable.containsKey(data.getUUID())) {
				Statement stmt = this.mysql.getConnection().createStatement();
				stmt.executeUpdate(this.sqlInsert(data));
			}
		} catch (SQLException e) {
			if (!e.getMessage().toLowerCase().contains("duplicate"))
				e.printStackTrace();
		}
	}

	private String sqlCleanData() {
		String sql = String.format("DELETE FROM `%s` WHERE `jud_title` LIKE '  %%' OR `jud_id` LIKE '  %%';",
				this.tableName);
		return sql;
	}

	// private String sqlCloneUniqueDataToTable(String table) {
	// String sql = String.format("INSERT INTO `%s` SELECT * FROM `%s` WHERE 1
	// GROUP BY `date`,`jud_id`;", table,
	// this.tableName);
	// return sql;
	// }

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

	// private String sqlSelectUniqueData() {
	// String sql = String.format("SELECT * FROM `%s` WHERE 1 GROUP BY
	// `date`,`jud_id`;", this.tableName);
	// return sql;
	// }

	// private String sqlRenameTable(String oldTable, String newTable) {
	// String sql = String.format("RENAME TABLE `%s` TO `%s`", oldTable,
	// newTable);
	// return sql;
	// }

	// private String sqlDropTable(String table) {
	// String sql = String.format("DROP TABLE `%s`;", table);
	// return sql;
	// }

	private String sqlInsert(Judicial data) {
		String sql = String.format(
				"INSERT INTO `%s`(`court`, `date`, `jud_id`, `jud_title`, `content`) VALUES('%s','%s','%s','%s','%s');",
				this.tableName, data.getCourt(), data.getGeneralDate(), data.getJudId(), data.getJudTitle(),
				data.getContent());
		return sql;
	}

	private String sqlSelectAllData() {
		return this.sqlSelectAllData(this.tableName);
	}

	private String sqlSelectAllData(String table) {
		String sql = String.format("SELECT * FROM `%s`;", table);
		return sql;
	}
}
