package org.pstar.webfetcher.web.judicial.fjud.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.pstar.webfetcher.web.judicial.fjud.model.Judicial;

public interface FJUDCtrlTaskImpl {
	public void addRecord(Judicial record);

	public void doFinishOneCourt();

	public void doFinishTask();

	public HashMap<String, String> getCookies();

	public HashSet<String> getCourtList();

	public HashMap<String, String> getFetchParameters();

	public long getFetchTimeIntervel();

	public int getIntFetchTimeIntervel();

	public String getReferrer();

	public void setCookies(Map<String, String> cookies);
}
