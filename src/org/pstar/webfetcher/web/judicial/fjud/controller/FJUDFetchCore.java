package org.pstar.webfetcher.web.judicial.fjud.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.pstar.webfetcher.core.FetchCore;
import org.pstar.webfetcher.jdbc.mysql.MySQLConnector;
import org.pstar.webfetcher.web.judicial.fjud.model.Judicial;
import org.pstar.webfetcher.web.judicial.fjud.model.JudicialDAO;
import org.pstar.webfetcher.web.judicial.fjud.ui.AppWindow;
import org.pstar.webfetcher.web.judicial.fjud.ui.CheckBoxListEntry;

public class FJUDFetchCore extends FetchCore implements FJUDCtrlViewerImpl, FJUDCtrlTaskImpl {
	private AppWindow appWindow;
	private HashMap<String, String> cookies;
	private FJUDFetchTask currentThread;
	private JudicialDAO judicialDAO;
	private MySQLConnector mysql;
	private PrintStream outputFile;
	private boolean outputMySQL;
	private DefaultListModel<String> recordListModel;
	private HashMap<String, Judicial> recordMap;
	private ResourceBundle resource;
	private HashSet<String> titleExecludeSet;

	public FJUDFetchCore() {
		this.initResource();
		SwingUtilities.invokeLater(new FJUDInitFetchTask(this));
		SwingUtilities.invokeLater(() -> this.appWindow = new AppWindow(this));
	}

	@Override
	public void addRecord(Judicial record) {
		boolean toBeSave = this.titleExecludeSet.stream().filter(kw -> record.getJudTitle().contains(kw)).count() == 0;
		int currentProgress = this.appWindow.getProgressBarDocs().getValue();

		if (toBeSave) {
			this.recordMap.put(record.getUUID(), record);
			this.recordListModel.addElement(record.getSummary());
			this.appWindow.getListFJUDList().setModel(this.recordListModel);

			if (this.outputMySQL)
				this.judicialDAO.saveDataToMySQL(record);
			else
				this.outputFile.println(this.judicialDAO.saveDataToFile(record));
		}

		this.appWindow.getProgressBarDocs().setMaximum(record.getTotal());
		this.appWindow.getProgressBarDocs().setValue(currentProgress + 1);
	}

	@Override
	public void doChangeOutputType(boolean type) {
		this.outputMySQL = type;
		this.appWindow.getTxtFilePath().setEnabled(!type);
		this.appWindow.getBtnChoiceFile().setEnabled(!type);
		this.appWindow.getTxtMySQLHost().setEnabled(type);
		this.appWindow.getTxtMySQLUser().setEnabled(type);
		this.appWindow.getTxtMySQLPassword().setEnabled(type);
		this.appWindow.getTxtDatabaseName().setEnabled(type);
	}

	@Override
	public void doFetch() {
		String strTitleExeclude = this.appWindow.getTxtFJUDTitleExclude().getText().trim();
		this.titleExecludeSet = new HashSet<String>();
		this.recordMap = new HashMap<String, Judicial>();
		this.recordListModel = new DefaultListModel<String>();
		this.currentThread = new FJUDFetchTask(this);

		if (this.outputMySQL)
			this.initMySQL();
		else
			this.initFile();		

		if (!strTitleExeclude.isEmpty())
			for (String strKw : strTitleExeclude.split("\\s"))
				this.titleExecludeSet.add(strKw);

		this.titleExecludeSet.add("附帶民訴");
		this.appWindow.getProgressBar().setValue(0);
		this.appWindow.getProgressBarDocs().setValue(0);
		this.currentThread.start();
	}

	@Override
	public void doFinishOneCourt() {
		int total = this.appWindow.getjListCourt().getCheckedItems().size();
		int currentProgress = this.appWindow.getProgressBar().getValue();

		this.appWindow.getProgressBar().setMaximum(total);
		this.appWindow.getProgressBar().setValue(currentProgress + 1);
	}

	@Override
	public void doFinishTask() {
		this.appWindow.getBtnStart().setEnabled(true);
		this.appWindow.getBtnStop().setEnabled(false);
	}

	@Override
	public void doPaintShowContent() {
		String selectedVal = this.appWindow.getListFJUDList().getSelectedValue();

		if (selectedVal != null) {
			String key = UUID.nameUUIDFromBytes(selectedVal.getBytes()).toString();
			this.appWindow.getTxtFJUDContent().setText(this.recordMap.get(key).getContent());
		}
	}

	@Override
	public void doStop() {
		this.currentThread.stopTask();
	}

	@Override
	public HashMap<String, String> getCookies() {
		return this.cookies;
	}

	@Override
	public HashSet<String> getCourtList() {
		HashSet<String> selectedCourt = new HashSet<String>();

		this.appWindow.getjListCourt().getCheckedItems().forEach(court -> {
			selectedCourt.add(court.getText());
		});

		return selectedCourt;
	}

	@Override
	public DefaultListModel<CheckBoxListEntry> getCourtListModel() {
		DefaultListModel<CheckBoxListEntry> jListCourtModel = new DefaultListModel<CheckBoxListEntry>();
		JSONArray courts = new JSONArray(this.resource.getString("Courts"));

		courts.forEach(court -> {
			jListCourtModel.addElement(new CheckBoxListEntry(court, false));
		});

		return jListCourtModel;
	}

	@Override
	public HashMap<String, String> getFetchParameters() {
		HashMap<String, String> params = new HashMap<String, String>();

		params.put("v_sys", "M");
		params.put("jud_title", this.appWindow.getTxtFJUDTitle().getText());
		params.put("keyword", this.appWindow.getTxtFulltextKeyword().getText());
		params.put("sdate", this.appWindow.getDateStartPicker().getJFormattedTextField().getText().replaceAll("-", ""));
		params.put("edate", this.appWindow.getDateEndPicker().getJFormattedTextField().getText().replaceAll("-", ""));

		return params;
	}

	@Override
	public long getFetchTimeIntervel() {
		return (long) this.getIntFetchTimeIntervel();
	}

	@Override
	public int getIntFetchTimeIntervel() {
		return (int) this.appWindow.getSpinnerFetchTimeInterval().getValue() * 1000;
	}

	@Override
	public String getReferrer() {
		return "http://jirs.judicial.gov.tw/FJUD/FJUDQRY02_1.aspx";
	}

	private void initFile() {
		try {
			String table = this.appWindow.getTxtTableName().getText().trim();
			this.judicialDAO = new JudicialDAO(table);
			this.outputFile = new PrintStream(new FileOutputStream(this.appWindow.getTxtFilePath().getText()));
			this.outputFile.println(this.judicialDAO.createTableToFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initMySQL() {
		String host = this.appWindow.getTxtMySQLHost().getText().trim();
		String user = this.appWindow.getTxtMySQLUser().getText().trim();
		String password = String.valueOf(this.appWindow.getTxtMySQLPassword().getPassword()).trim();
		String database = this.appWindow.getTxtDatabaseName().getText().trim();
		String table = this.appWindow.getTxtTableName().getText().trim();

		this.mysql = new MySQLConnector(host, user, password, database);
		this.judicialDAO = new JudicialDAO(this.mysql, table);
		this.judicialDAO.createTable();
	}

	private void initResource() {
		this.outputMySQL = true;
		this.cookies = new HashMap<String, String>();
		this.resource = ResourceBundle.getBundle("org.pstar.webfetcher.web.judicial.fjud.config");
	}

	@Override
	public void setCookies(Map<String, String> cookies) {
		cookies.forEach((k, v) -> this.cookies.put(k, v));
	}
}
