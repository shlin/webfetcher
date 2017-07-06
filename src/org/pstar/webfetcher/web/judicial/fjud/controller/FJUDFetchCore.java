package org.pstar.webfetcher.web.judicial.fjud.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.pstar.webfetcher.core.FetchCore;
import org.pstar.webfetcher.web.judicial.fjud.ui.AppWindow;
import org.pstar.webfetcher.web.judicial.fjud.ui.CheckBoxListEntry;

public class FJUDFetchCore extends FetchCore implements FJUDCtrlViewerImpl, FJUDCtrlTaskImpl {
	private AppWindow appWindow;
	private ResourceBundle resource;
	private FJUDFetchTask currentThread;
	private HashMap<String, String> cookies;

	public FJUDFetchCore() {
		this.initResource();
		SwingUtilities.invokeLater(new FJUDInitFetchTask(this));
		SwingUtilities.invokeLater(() -> this.appWindow = new AppWindow(this));
	}

	private void initResource() {
		this.cookies = new HashMap<String, String>();
		this.resource = ResourceBundle.getBundle("org.pstar.webfetcher.web.judicial.fjud.config");
	}

	@Override
	public void doFetch() {
		this.currentThread = new FJUDFetchTask(this);
		this.currentThread.start();
	}

	@Override
	public void doStop() {
		this.currentThread.stopTask();
	}

	@Override
	public void doChangeOutputType(boolean type) {
		this.appWindow.getTxtFilePath().setEnabled(!type);
		this.appWindow.getBtnChoiceFile().setEnabled(!type);
		this.appWindow.getTxtMySQLHost().setEnabled(type);
		this.appWindow.getTxtMySQLUser().setEnabled(type);
		this.appWindow.getTxtMySQLPassword().setEnabled(type);
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
		HashSet<String> selectedCourt = new HashSet<String>();

		this.appWindow.getjListCourt().getCheckedItems().forEach(court -> {
			selectedCourt.add(court.getText().split("\\s")[0]);
		});

		params.put("courtFullName", selectedCourt.toString().replaceAll("[\\[\\]]", ""));
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
	public HashMap<String, String> getCookies() {
		return this.cookies;
	}

	@Override
	public void setCookies(Map<String, String> cookies) {
		cookies.forEach((k, v) -> this.cookies.put(k, v));
	}
}
